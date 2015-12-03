/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.ui.tree.postfilter.AbstractPostFilterGUI;
import Sirius.navigator.ui.tree.postfilter.PostFilter;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.udm2020di.protocol.CommonPostFilterProtocolStep;
import de.cismet.cids.custom.udm2020di.protocol.PostfilterProtocolRegistry;
import de.cismet.cids.custom.udm2020di.protocol.TagsPostFilterProtocolStep;
import de.cismet.cids.custom.udm2020di.serversearch.FilterByTagsSearch;
import de.cismet.cids.custom.udm2020di.serversearch.PostFilterTagsSearch;
import de.cismet.cids.custom.udm2020di.types.Tag;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.commons.gui.protocol.ProtocolHandler;
import java.util.Collections;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
//@ServiceProvider(service = PostFilterGUI.class)
public class CommonTagsPostFilterGui extends AbstractPostFilterGUI implements ActionListener {

    //~ Static fields/initializers ---------------------------------------------

    protected static final boolean TAGS_SELECTED_BY_DEFAULT = true;
    protected static final String[] SUPPORTED_META_CLASSES = {
            "BORIS_SITE",
            "MOSS",
            "EPRTR_INSTALLATION",
            "WAGW_STATION",
            "WAOW_STATION"
        };

    protected static final ConcurrentHashMap<Integer, LinkedBlockingDeque<Collection<MetaObject>>> QUEUE_MAP =
        new ConcurrentHashMap<Integer, LinkedBlockingDeque<Collection<MetaObject>>>();

    //~ Instance fields --------------------------------------------------------

    protected final Object filterInitializedLock = new Object();
    protected volatile Boolean filterInitialized = false;

    protected Logger LOGGER = Logger.getLogger(CommonTagsPostFilterGui.class);

    protected final PostFilterTagsSearch postfilterTagsSearch;
    protected final FilterByTagsSearch filterByTagsSearch;
    protected boolean active;
    protected final Map<Tag, JToggleButton> filterButtons = new Hashtable<Tag, JToggleButton>();

    protected boolean eventsEnabled = true;
    protected ImageIcon icon = new ImageIcon(getClass().getResource(
                NbBundle.getMessage(
                    CommonTagsPostFilterGui.class,
                    "CommonTagsPostFilterGui.icon")));

    // protected final ArrayList<Integer> availableTagIds = new ArrayList<Integer>();
    protected final Semaphore semaphore = new Semaphore(1);

    protected final TreeSet<Integer> supportedMetaClasses = new TreeSet<Integer>();

    protected final PostFilter postFilter = new PostFilter() {

            @Override
            public Integer getFilterChainOrderKeyPrio() {
                return CommonTagsPostFilterGui.this.getDisplayOrderKeyPrio();
            }

            @Override
            public Collection<Node> filter(final Collection<Node> input) {
                synchronized (filterInitializedLock) {
                    while (!filterInitialized) {
                        try {
                            LOGGER.warn("Filter not yet initialized! Forced waiting for filter to be initilaized!");
                            final long current = System.currentTimeMillis();
                            filterInitializedLock.wait();
                            LOGGER.warn("forcibly waited " + (System.currentTimeMillis() - current)
                                        + "ms for Filter to become initialized");
                        } catch (InterruptedException ex) {
                            LOGGER.error(ex.getMessage(), ex);
                        }
                    }
                }

                final Collection<Integer> filterTagIds = getFilterTagIds();
                if (!filterTagIds.isEmpty() && (filterTagIds.size() < filterButtons.size())) {
                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                disableButtons();
                            }
                        });

                    final List<Node> preFilteredNodes = preFilterNodes(input);
                    final Collection<Node> postFilteredNodes = new ArrayList<Node>(input);
                    postFilteredNodes.removeAll(preFilteredNodes);

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.info("PostFilter: filtering " + preFilteredNodes.size() + " pre-filtered nodes of "
                                    + input.size() + " available nodes  with "
                                    + filterTagIds.size() + " filter tags of "
                                    + filterButtons.size() + " available filter tags");
                    }

                    if (!preFilteredNodes.isEmpty()) {
                        filterByTagsSearch.setNodes(preFilteredNodes);
                        filterByTagsSearch.setFilterTagIds(filterTagIds);

                        try {
                            final Collection<Node> filteredNodes = SessionManager.getProxy()
                                        .customServerSearch(filterByTagsSearch);

                            postFilteredNodes.addAll(filteredNodes);
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug(postFilteredNodes.size() + " of " + input.size()
                                            + " nodes remaining after applying "
                                            + filterTagIds.size() + " filter tags to " + preFilteredNodes.size()
                                            + " pre-filtered nodes (" + filteredNodes.size()
                                            + " actually filtered nodes)");
                            }

                            return postFilteredNodes;
                        } catch (Exception e) {
                            LOGGER.error("could not apply filter tags for '" + input.size() + " nodes: "
                                        + e.getMessage(),
                                e);
                        }
                    } else {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(
                                "filter is not applied: no nodes left for filtering after applying pre-filter to "
                                        + input.size()
                                        + " nodes!");
                        }
                    }
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("filter is not applied: no or all tags selected!");
                    }
                }

                return input;
            }
        };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton applyButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton switchButton;
    private javax.swing.JPanel tagsPanel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CommonTagsPostfilter.
     */
    public CommonTagsPostFilterGui() {
        PostFilterTagsSearch initPostFilterTagsSearch = null;
        FilterByTagsSearch initFilterByTagsSearch = null;
        try {
            initPostFilterTagsSearch = new PostFilterTagsSearch();
            initFilterByTagsSearch = new FilterByTagsSearch();

            for (final String metaClassName : SUPPORTED_META_CLASSES) {
                final MetaClass metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", metaClassName);
                if (metaClass == null) {
                    LOGGER.warn("could not retrieve " + metaClassName + " class from UDM2020-DI, "
                                + "filter is disabled!");
                } else {
                    this.supportedMetaClasses.add(metaClass.getId());
                }
            }

            this.active = !this.supportedMetaClasses.isEmpty();
        } catch (IOException ex) {
            LOGGER.error("could not initialize PostFilterTagsSearch, disabling filter", ex);
            this.active = false;
        }
        this.postfilterTagsSearch = initPostFilterTagsSearch;
        this.filterByTagsSearch = initFilterByTagsSearch;
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of eventsEnabled.
     *
     * @return  the value of eventsEnabled
     */
    public boolean isEventsEnabled() {
        return eventsEnabled;
    }

    /**
     * Set the value of eventsEnabled.
     *
     * @param  eventsEnabled  new value of eventsEnabled
     */
    public synchronized void setEventsEnabled(final boolean eventsEnabled) {
        if (this.eventsEnabled == eventsEnabled) {
            LOGGER.warn("prossible sychronisation problem, ignoring setEventsEnabled:"
                        + eventsEnabled);
        }
        this.eventsEnabled = eventsEnabled;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        progressBar = new javax.swing.JProgressBar();
        tagsPanel = new javax.swing.JPanel();
        actionPanel = new javax.swing.JPanel();
        applyButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5),
                new java.awt.Dimension(0, 5),
                new java.awt.Dimension(32767, 5));
        switchButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5),
                new java.awt.Dimension(0, 5),
                new java.awt.Dimension(32767, 5));
        resetButton = new javax.swing.JButton();

        progressBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        progressBar.setIndeterminate(true);
        progressBar.setMinimumSize(new java.awt.Dimension(150, 14));
        progressBar.setPreferredSize(new java.awt.Dimension(250, 14));

        setLayout(new java.awt.BorderLayout(5, 5));
        add(tagsPanel, java.awt.BorderLayout.CENTER);

        actionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        actionPanel.setLayout(new javax.swing.BoxLayout(actionPanel, javax.swing.BoxLayout.PAGE_AXIS));

        applyButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/udm2020di/postfilter/funnel-arrow-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            applyButton,
            org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.applyButton.text"));                                                  // NOI18N
        applyButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.applyButton.toolTipText"));                                           // NOI18N
        applyButton.setActionCommand(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.applyButton.actionCommand"));                                         // NOI18N
        applyButton.setEnabled(false);
        applyButton.setMargin(new java.awt.Insets(4, 4, 4, 4));
        applyButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    applyButtonActionPerformed(evt);
                }
            });
        actionPanel.add(applyButton);
        actionPanel.add(filler1);

        switchButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/udm2020di/postfilter/funnel-exclamation-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            switchButton,
            org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.switchButton.text"));                                                       // NOI18N
        switchButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.switchButton.toolTipText"));                                                // NOI18N
        switchButton.setActionCommand(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.switchButton.actionCommand"));                                              // NOI18N
        switchButton.setEnabled(false);
        switchButton.setMargin(new java.awt.Insets(4, 4, 4, 4));
        switchButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    switchButtonActionPerformed(evt);
                }
            });
        actionPanel.add(switchButton);
        actionPanel.add(filler2);

        resetButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/udm2020di/postfilter/funnel-minus-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            resetButton,
            org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.resetButton.text"));                                                  // NOI18N
        resetButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.resetButton.toolTipText"));                                           // NOI18N
        resetButton.setActionCommand(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.resetButton.actionCommand"));                                         // NOI18N
        resetButton.setEnabled(false);
        resetButton.setMargin(new java.awt.Insets(4, 4, 4, 4));
        resetButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    resetButtonActionPerformed(evt);
                }
            });
        actionPanel.add(resetButton);

        add(actionPanel, java.awt.BorderLayout.EAST);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void applyButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        this.firePostFilterChanged();
        
        final Collection<Tag> filterTags = this.getFilterTags();
            final TagsPostFilterProtocolStep protocolStep = new TagsPostFilterProtocolStep(
                    this.getClass().getSimpleName(),
                    this.getTitle(),
                    this.icon,
                    filterTags);

            

        if (ProtocolHandler.getInstance().isRecordEnabled()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("recording post filter settings to protocol: "
                            + filterTags.size() + " filter tags");
            }
            
            PostfilterProtocolRegistry.getInstance().recordCascadingProtocolStep(this, protocolStep);
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("saving post filter settings to protocol: "
                            + filterTags.size() + " filter tags");
            }
            
            PostfilterProtocolRegistry.getInstance().createCascadingProtocolStep(this, protocolStep);
        }
    }//GEN-LAST:event_applyButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void resetButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        setEventsEnabled(false);
        for (final JToggleButton filterButton : filterButtons.values()) {
            filterButton.setSelected(true);

            // does not work as expected
            // if (!availableTagIds.contains(tagId)) {
            // filterButtons.get(tagId).setSelected(false);
            // } else {
            // filterButtons.get(tagId).setSelected(true);
            // }
        }
        PostfilterProtocolRegistry.getInstance().clearProtocolStep(this);
        this.enableButtons();
        this.tagsPanel.validate();
        setEventsEnabled(true);
    }//GEN-LAST:event_resetButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void switchButtonActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchButtonActionPerformed
        setEventsEnabled(false);
        for (final JToggleButton toggleButton : this.filterButtons.values()) {
            toggleButton.setSelected(!toggleButton.isSelected());
        }
        this.enableButtons();
        this.tagsPanel.validate();
        setEventsEnabled(true);
    }//GEN-LAST:event_switchButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  nodes  DOCUMENT ME!
     */
    @Override
    public void initializeFilter(final Collection<Node> nodes) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("initialize Filter with " + nodes.size() + " nodes");
        }

        final PostfilterProtocolRegistry registry = PostfilterProtocolRegistry.getInstance();
        this.selected = registry.isShouldRestoreSettings(this, nodes)
                    && (registry.getMasterPostFilter() != null)
                    && registry.getMasterPostFilter().equals(this.getClass().getSimpleName());

        final SwingWorker<Collection<JToggleButton>, Void> worker = new SwingWorker<Collection<JToggleButton>, Void>() {

                @Override
                protected Collection<JToggleButton> doInBackground() throws Exception {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("waiting for semaphore acquire!");
                    }
                    final long current = System.currentTimeMillis();
                    semaphore.acquire();
                    synchronized (filterInitializedLock) {
                        filterInitialized = false;
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("semaphore acquired in " + (System.currentTimeMillis() - current) + "ms");
                    }

                    filterButtons.clear();
                    // availableTagIds.clear();

                    try {
                        EventQueue.invokeAndWait(new Runnable() {

                                @Override
                                public void run() {
                                    if (LOGGER.isDebugEnabled()) {
                                        LOGGER.debug("showing progress bar");
                                    }
                                    CommonTagsPostFilterGui.this.tagsPanel.removeAll();
                                    CommonTagsPostFilterGui.this.tagsPanel.add(progressBar);
                                    CommonTagsPostFilterGui.this.tagsPanel.validate();
                                    CommonTagsPostFilterGui.this.tagsPanel.repaint();
                                    disableButtons();
                                }
                            });
                    } catch (Exception ex) {
                        LOGGER.error("could not show progress bar: " + ex.getMessage(), ex);
                    }

                    final Collection<JToggleButton> tagButtons = new ArrayList<JToggleButton>();
                    final List<Tag> filterTags;
                    if (registry.isShouldRestoreSettings(CommonTagsPostFilterGui.this, nodes)) {
                        filterTags = new ArrayList<Tag>();
                        final CommonPostFilterProtocolStep protocolStep = PostfilterProtocolRegistry.getInstance()
                                    .getProtocolStep(CommonTagsPostFilterGui.this);

                        if (TagsPostFilterProtocolStep.class.isAssignableFrom(protocolStep.getClass())) {
                            for (final Tag tag : ((TagsPostFilterProtocolStep)protocolStep).getFilterTags()) {
                                filterTags.add(tag.clone());
                            }
                        } else {
                            LOGGER.error("unexpected ProtocolStep:" + protocolStep.getClass().getSimpleName());
                        }

                        LOGGER.info("restoring " + filterTags.size() + " filter tags from saved configuration!");
                    } else {
                        final Collection<MetaObject> metaObjects = retrieveFilterTags(new ArrayList<Node>(nodes));
                        filterTags = filterCidsBeans(metaObjects);
                    }

                    Collections.sort(filterTags);
                    for (final Tag tag : filterTags) {
                        final JToggleButton tagButton = generateTagButton(tag);
                        if (tagButton != null) {
                            tagButtons.add(tagButton);
                        }
                    }

                    return tagButtons;
                }

                @Override
                protected void done() {
                    try {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("hiding progress bar");
                        }
                        final Collection<JToggleButton> tagButtons = this.get();
                        CommonTagsPostFilterGui.this.tagsPanel.removeAll();
                        CommonTagsPostFilterGui.this.tagsPanel.validate();
                        for (final JToggleButton tagButton : tagButtons) {
                            CommonTagsPostFilterGui.this.tagsPanel.add(tagButton);
                        }
                        CommonTagsPostFilterGui.this.tagsPanel.validate();
                        CommonTagsPostFilterGui.this.tagsPanel.repaint();
                        enableButtons();
                        synchronized (filterInitializedLock) {
                            filterInitialized = true;
                        }
                    } catch (Exception ex) {
                        LOGGER.error(ex.getMessage(), ex);
                    } finally {
                        semaphore.release();
                        synchronized (filterInitializedLock) {
                            filterInitializedLock.notifyAll();
                        }
                    }
                }
            };

        if ((nodes != null) && !nodes.isEmpty()) {
            worker.execute();
        } else {
            LOGGER.warn("no nodes provided, filter disabled");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   tag  cidsBean DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected JToggleButton generateTagButton(final Tag tag) {
        final int tagId = (int)tag.getId();
        final JToggleButton tagButton = new JToggleButton(tag.getKey());
        tagButton.setModel(new TagButtonModel((tag)));
        tagButton.setSelected(tag.isSelected());
        tagButton.setToolTipText(tag.getName());
        tagButton.setActionCommand(String.valueOf(tagId));
        tagButton.addActionListener(WeakListeners.create(
                ActionListener.class,
                CommonTagsPostFilterGui.this,
                tagButton));

        if (!this.filterButtons.containsKey(tag)) {
            this.filterButtons.put(tag, tagButton);
            return tagButton;
        } else {
            LOGGER.warn("tag '" + tag.getName()
                        + "with id " + tagId + " already added!");
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   nodes  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    protected Collection<MetaObject> retrieveFilterTags(final Collection<Node> nodes) throws Exception {
        final int key = nodes.hashCode();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("retrieving tags for " + nodes.size() + " nodes: " + key);
        }

        if (QUEUE_MAP.containsKey(key)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("retrieveFilterTags request '" + key + "' is queued");
            }
            final LinkedBlockingDeque<Collection<MetaObject>> queue = QUEUE_MAP.get(nodes.hashCode());
            final Collection<MetaObject> metaObjects = queue.take();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(metaObjects.size() + " tag objects '" + key + "' retrieved from queue");
            }
            queue.put(metaObjects);
            return metaObjects;
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("retrieveFilterTags request '" + key + "' is not queued, generating new request");
            }
            final LinkedBlockingDeque<Collection<MetaObject>> queue = new LinkedBlockingDeque<Collection<MetaObject>>();
            QUEUE_MAP.clear();
            QUEUE_MAP.put(key, queue);

            final Collection<MetaObject> metaObjects = new ArrayList<MetaObject>();
            final Map<Integer, Collection<Integer>> objectIdMap = new HashMap<Integer, Collection<Integer>>();

            for (final Node node : nodes) {
                if (MetaObjectNode.class.isAssignableFrom(node.getClass())) {
                    final MetaObjectNode metaObjectNode = (MetaObjectNode)node;
                    final Collection<Integer> objectIds;
                    if (objectIdMap.containsKey(node.getClassId())) {
                        objectIds = objectIdMap.get(node.getClassId());
                    } else {
                        objectIds = new ArrayList<Integer>();
                        objectIdMap.put(node.getClassId(), objectIds);
                    }
                    objectIds.add(metaObjectNode.getObjectId());
                }
            }

            try {
                CommonTagsPostFilterGui.this.postfilterTagsSearch.setObjectIdMap(objectIdMap);
                final Collection<MetaObject> result = SessionManager.getProxy()
                            .customServerSearch(postfilterTagsSearch);
                metaObjects.addAll(result);
            } catch (Exception e) {
                LOGGER.error("could not retrieve tags for " + nodes.size() + " nodes!", e);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(metaObjects.size() + " tags for " + nodes.size() + " nodes retrieved.");
            }

            queue.put(metaObjects);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(metaObjects.size() + " tag objects retrieved from server ");
            }
            return metaObjects;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   metaObjects  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected List<Tag> filterCidsBeans(
            final Collection<MetaObject> metaObjects) {
        final List<Tag> tags = new ArrayList<Tag>(metaObjects.size());
        // availableTagIds.clear();
        for (final MetaObject metaObject : metaObjects) {
            final Tag tag = new Tag(metaObject.getBean());
            tag.setSelected(TAGS_SELECTED_BY_DEFAULT);

            if ((getFilterTagGroup() == null)
                        || ((tag.getTaggroupKey() != null)
                            && tag.getTaggroupKey().equalsIgnoreCase(getFilterTagGroup()))) {
                tags.add(tag);
                // final int tagId = (Integer)cidsBean.getProperty("id");
                // availableTagIds.add(tagId);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(tags.size() + " of " + metaObjects.size() + " retrieved tags available for filtering");
        }
        return tags;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   input  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected List<Node> preFilterNodes(final Collection<Node> input) {
        final List<Node> nodes;
        if (!this.supportedMetaClasses.isEmpty()) {
            nodes = new ArrayList<Node>();
            for (final Node node : input) {
                if (this.supportedMetaClasses.contains(node.getClassId())
                            && (node instanceof MetaObjectNode)) {
                    nodes.add((MetaObjectNode)node);
                }
            }
        } else if (List.class.isAssignableFrom(input.getClass())) {
            nodes = (List<Node>)input;
        } else {
            nodes = new ArrayList<Node>(input);
        }

        return nodes;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  nodes  DOCUMENT ME!
     */
    @Override
    public void adjustFilter(final Collection<Node> nodes) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adjust Filter with " + nodes.size() + " nodes");
        }

//        final SwingWorker<Collection<CidsBean>, Void> worker
//                = new SwingWorker<Collection<CidsBean>, Void>() {
//
//                @Override
//                protected Collection<CidsBean> doInBackground() throws Exception {
//                    return retrieveFilterTags(nodes);
//                }
//
//                @Override
//                protected void done() {
//                    try {
//                        final Collection<CidsBean> cidsBeans = this.get();
//                        setEventsEnabled(false);
//                        for (final Integer tagId : filterButtons.keySet()) {
//                            if (!availableTagIds.contains(tagId)) {
//                                filterButtons.get(tagId).setSelected(false);
//                            }
//                        }
//                        setEventsEnabled(true);
//                        enableButtons();
//                    } catch (Exception ex) {
//                        logger.error(ex.getMessage(), ex);
//                    }
//                }
//            };
// FIXME: Does not work as expected!
//        if ((nodes != null) && !nodes.isEmpty()) {
//            worker.execute();
//        } else {
//            logger.warn("no nodes provided, filter disabled");
//        }
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    enableButtons();
                }
            });
    }

    /**
     * DOCUMENT ME!
     *
     * @param   nodes  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public boolean canHandle(final Collection<Node> nodes) {
        this.active = !this.preFilterNodes(nodes).isEmpty();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("filter can handle " + nodes.size() + " nodes:" + this.active);
        }
        return this.active;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public boolean isActive() {
        return this.active && this.applyButton.isEnabled();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getTitle() {
        return org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.title");
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public PostFilter getFilter() {
        return this.postFilter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Integer getDisplayOrderKeyPrio() {
        return 1000;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getIcon() {
        return icon;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected String getFilterTagGroup() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected int selectedTagButtons() {
        int i = 0;
        for (final JToggleButton toggleButton : this.filterButtons.values()) {
            i += toggleButton.isSelected() ? 1 : 0;
        }

        return i;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (isEventsEnabled()) {
            if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("CTRL KEY pressed, performing switch action");
                }
                this.switchButtonActionPerformed(e);
            } else {
                this.enableButtons();
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    protected void disableButtons() {
        CommonTagsPostFilterGui.this.applyButton.setEnabled(false);
        CommonTagsPostFilterGui.this.switchButton.setEnabled(false);
        CommonTagsPostFilterGui.this.applyButton.setEnabled(false);
    }

    /**
     * DOCUMENT ME!
     */
    protected void enableButtons() {
        final int selectedTagButtons = selectedTagButtons();
        CommonTagsPostFilterGui.this.applyButton.setEnabled( /*!availableTagIds.isEmpty()
                                                              *&& */!filterButtons.isEmpty()
                    && (selectedTagButtons > 0));
        CommonTagsPostFilterGui.this.switchButton.setEnabled( /*!availableTagIds.isEmpty()
                                                               *&& */!filterButtons.isEmpty()
                    && (selectedTagButtons > 0));
        CommonTagsPostFilterGui.this.resetButton.setEnabled( /*!availableTagIds.isEmpty()
                                                              *&& */!filterButtons.isEmpty()
                    && (selectedTagButtons < filterButtons.size()));
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<Tag> getFilterTags() {
        final ArrayList<Tag> filterTags = new ArrayList<Tag>(this.filterButtons.keySet().size());
        for (final Tag tag : this.filterButtons.keySet()) {
            try {
                filterTags.add(tag.clone());
            } catch (CloneNotSupportedException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }

        return filterTags;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected Collection<Integer> getFilterTagIds() {
        final ArrayList<Integer> filterTagIds = new ArrayList<Integer>();
        for (final Tag tag : filterButtons.keySet()) {
            if (tag.isSelected()) {
                filterTagIds.add((int)tag.getId());
            }
        }

        return filterTagIds;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    protected static class TagButtonModel extends JToggleButton.ToggleButtonModel {

        //~ Instance fields ----------------------------------------------------

        final Tag tag;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new TagButtonModel object.
         *
         * @param  tag  DOCUMENT ME!
         */
        protected TagButtonModel(final Tag tag) {
            this.tag = tag;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public void setSelected(final boolean b) {
            super.setSelected(b);
            this.tag.setSelected(b);
        }
    }
}
