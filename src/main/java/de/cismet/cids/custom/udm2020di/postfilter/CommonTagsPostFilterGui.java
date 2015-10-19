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

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.udm2020di.serversearch.FilterByTagsSearch;
import de.cismet.cids.custom.udm2020di.serversearch.PostFilterTagsSearch;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
//@ServiceProvider(service = PostFilterGUI.class)
public class CommonTagsPostFilterGui extends AbstractPostFilterGUI implements ActionListener {

    //~ Static fields/initializers ---------------------------------------------

    protected static final ConcurrentHashMap<Integer, LinkedBlockingDeque<Collection<MetaObject>>> QUEUE_MAP =
        new ConcurrentHashMap<Integer, LinkedBlockingDeque<Collection<MetaObject>>>();

    //~ Instance fields --------------------------------------------------------

    protected Logger logger = Logger.getLogger(CommonTagsPostFilterGui.class);

    protected final PostFilterTagsSearch postfilterTagsSearch;
    protected final FilterByTagsSearch filterByTagsSearch;
    protected final boolean active;
    protected final Map<Integer, JToggleButton> filterButtons = new Hashtable<Integer, JToggleButton>();

    protected boolean eventsEnabled = true;
    protected ImageIcon icon = new ImageIcon(getClass().getResource(
                "/de/cismet/cids/custom/udm2020di/postfilter/define_name.png"));

    protected final ArrayList<Integer> availableTagIds = new ArrayList<Integer>();

    protected final Semaphore semaphore = new Semaphore(1);

    protected final PostFilter postFilter = new PostFilter() {

            @Override
            public Integer getFilterChainOrderKeyPrio() {
                return CommonTagsPostFilterGui.this.getDisplayOrderKeyPrio();
            }

            @Override
            public Collection<Node> filter(final Collection<Node> input) {
                EventQueue.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            disableButtons();
                        }
                    });

                final List<Node> preFilteredNodes = preFilterNodes(input);
                final Collection<Node> postFilteredNodes = new ArrayList<Node>(input);
                postFilteredNodes.removeAll(preFilteredNodes);

                if (logger.isDebugEnabled()) {
                    logger.info("PostFilter: filtering " + preFilteredNodes.size() + " pre-filtered nodes of "
                                + input.size() + " available nodes");
                }

                filterByTagsSearch.setNodes(preFilteredNodes);
                final ArrayList filterTagIds = new ArrayList<Integer>();
                for (final Integer tagId : filterButtons.keySet()) {
                    if (filterButtons.get(tagId).isSelected()) {
                        filterTagIds.add(tagId);
                    }
                }
                filterByTagsSearch.setFilterTagIds(filterTagIds);
                if (logger.isDebugEnabled()) {
                    logger.debug("filtering " + input.size() + " nodes with "
                                + filterTagIds.size() + " filter tags of "
                                + filterButtons.size() + " available filter tags");
                }

                try {
                    final Collection<Node> filteredNodes = SessionManager.getProxy()
                                .customServerSearch(filterByTagsSearch);

                    postFilteredNodes.addAll(filteredNodes);
                    if (logger.isDebugEnabled()) {
                        logger.debug(postFilteredNodes.size() + " of " + input.size()
                                    + " nodes remaining after applying "
                                    + filterTagIds.size() + " filter tags to " + preFilteredNodes.size()
                                    + "pre-filtered nodes (" + filteredNodes + " actuially filtered nodes)");
                    }

                    return postFilteredNodes;
                } catch (Exception e) {
                    logger.error("could not apply filter tags for '" + input.size() + " nodes!", e);
                    return input;
                }
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
        boolean initActive = false;

        try {
            initPostFilterTagsSearch = new PostFilterTagsSearch();
            initFilterByTagsSearch = new FilterByTagsSearch();
            initActive = true;
        } catch (IOException ex) {
            logger.error("could not initialize PostFilterTagsSearch, disabling filter", ex);
        }
        this.postfilterTagsSearch = initPostFilterTagsSearch;
        this.filterByTagsSearch = initFilterByTagsSearch;
        this.active = initActive;
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
            logger.warn("prossible sychronisation problem, ignoring setEventsEnabled:"
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
    private void applyButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_applyButtonActionPerformed
        this.firePostFilterChanged();
    }                                                                               //GEN-LAST:event_applyButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void resetButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_resetButtonActionPerformed
        setEventsEnabled(false);
        for (final Integer tagId : filterButtons.keySet()) {
            filterButtons.get(tagId).setSelected(true);

            // does not work as expected
// if (!availableTagIds.contains(tagId)) {
// filterButtons.get(tagId).setSelected(false);
// } else {
// filterButtons.get(tagId).setSelected(true);
// }
        }
        this.enableButtons();
        this.tagsPanel.validate();
        setEventsEnabled(true);
    } //GEN-LAST:event_resetButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void switchButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_switchButtonActionPerformed
        setEventsEnabled(false);
        for (final JToggleButton toggleButton : this.filterButtons.values()) {
            toggleButton.setSelected(!toggleButton.isSelected());
        }
        this.enableButtons();
        this.tagsPanel.validate();
        setEventsEnabled(true);
    }                                                                                //GEN-LAST:event_switchButtonActionPerformed

    @Override
    public void initializeFilter(final Collection<Node> nodes) {
        if (logger.isDebugEnabled()) {
            logger.debug("initialize Filter with " + nodes.size() + " nodes");
        }

        final SwingWorker<Collection<JToggleButton>, Void> worker = new SwingWorker<Collection<JToggleButton>, Void>() {

                @Override
                protected Collection<JToggleButton> doInBackground() throws Exception {
                    if (logger.isDebugEnabled()) {
                        logger.debug("waiting for semaphore acquire");
                    }
                    semaphore.acquire();
                    if (logger.isDebugEnabled()) {
                        logger.debug("semaphore semaphore acquired");
                    }

                    filterButtons.clear();
                    availableTagIds.clear();

                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                CommonTagsPostFilterGui.this.tagsPanel.removeAll();
                                CommonTagsPostFilterGui.this.tagsPanel.add(progressBar);
                                CommonTagsPostFilterGui.this.tagsPanel.validate();
                                CommonTagsPostFilterGui.this.tagsPanel.repaint();
                                disableButtons();
                            }
                        });

                    final Collection<JToggleButton> tagButtons = new ArrayList<JToggleButton>();
                    final Collection<MetaObject> metaObjects = retrieveFilterTags(new ArrayList<Node>(nodes));
                    final Collection<CidsBean> cidsBeans = filterCidsBeans(metaObjects);

                    for (final CidsBean cidsBean : cidsBeans) {
                        final JToggleButton tagButton = generateTagButton(cidsBean);
                        if (tagButton != null) {
                            tagButtons.add(tagButton);
                        }
                    }

                    return tagButtons;
                }

                @Override
                protected void done() {
                    try {
                        final Collection<JToggleButton> tagButtons = this.get();
                        CommonTagsPostFilterGui.this.tagsPanel.removeAll();
                        CommonTagsPostFilterGui.this.tagsPanel.validate();
                        for (final JToggleButton tagButton : tagButtons) {
                            CommonTagsPostFilterGui.this.tagsPanel.add(tagButton);
                        }
                        CommonTagsPostFilterGui.this.tagsPanel.validate();
                        CommonTagsPostFilterGui.this.tagsPanel.repaint();
                        enableButtons();
                        semaphore.release();
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            };

        if ((nodes != null) && !nodes.isEmpty()) {
            worker.execute();
        } else {
            logger.warn("no nodes provided, filter disabled");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   cidsBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected JToggleButton generateTagButton(final CidsBean cidsBean) {
        final int tagId = (int)cidsBean.getProperty("id");
        final JToggleButton tagButton = new JToggleButton(cidsBean.getProperty("key").toString());
        tagButton.setToolTipText(cidsBean.getProperty("name").toString());
        tagButton.setSelected(true);
        tagButton.setActionCommand(String.valueOf(tagId));
        tagButton.addActionListener(WeakListeners.create(
                ActionListener.class,
                CommonTagsPostFilterGui.this,
                tagButton));

        if (!this.filterButtons.containsKey(tagId)) {
            this.filterButtons.put(tagId, tagButton);
            return tagButton;
        } else {
            logger.warn("tag '" + cidsBean.getProperty("name").toString()
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

        if (logger.isDebugEnabled()) {
            logger.debug("retrieving tags for " + nodes.size() + " nodes: " + key);
        }

        if (QUEUE_MAP.containsKey(key)) {
            if (logger.isDebugEnabled()) {
                logger.debug("retrieveFilterTags request is queued");
            }
            final LinkedBlockingDeque<Collection<MetaObject>> queue = QUEUE_MAP.get(nodes.hashCode());
            final Collection<MetaObject> metaObjects = queue.take();
            if (logger.isDebugEnabled()) {
                logger.debug(metaObjects.size() + " tag objects retrieved from queue ");
            }
            queue.put(metaObjects);
            return metaObjects;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("retrieveFilterTags request is not queued, generating new request");
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
                logger.error("could not retrieve tags for " + nodes.size() + " nodes!", e);
            }

            if (logger.isDebugEnabled()) {
                logger.debug(metaObjects.size() + " tags for " + nodes.size() + " nodes retrieved.");
            }

            queue.put(metaObjects);
            if (logger.isDebugEnabled()) {
                logger.debug(metaObjects.size() + " tag objects retrieved from server ");
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
    protected Collection<CidsBean> filterCidsBeans(
            final Collection<MetaObject> metaObjects) {
        final Collection<CidsBean> cidsBeans = new ArrayList<CidsBean>(metaObjects.size());
        availableTagIds.clear();
        for (final MetaObject metaObject : metaObjects) {
            final CidsBean cidsBean = metaObject.getBean();

            if ((getFilterTagGroup() == null)
                        || ((cidsBean.getProperty("taggroup_key") != null)
                            && cidsBean.getProperty("taggroup_key").toString().equalsIgnoreCase(getFilterTagGroup()))) {
                cidsBeans.add(cidsBean);
                final int tagId = (Integer)cidsBean.getProperty("id");
                availableTagIds.add(tagId);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(cidsBeans.size() + " of " + metaObjects.size() + " retrieved tags available for filtering");
        }
        return cidsBeans;
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
        if (List.class.isAssignableFrom(input.getClass())) {
            nodes = (List<Node>)input;
        } else {
            nodes = new ArrayList<Node>(input);
        }

        return nodes;
    }

    @Override
    public void adjustFilter(final Collection<Node> nodes) {
        if (logger.isDebugEnabled()) {
            logger.debug("adjust Filter with " + nodes.size() + " nodes");
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

    @Override
    public boolean canHandle(final Collection<Node> nodes) {
        return true;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getTitle() {
        return org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "CommonTagsPostFilterGui.title");
    }

    @Override
    public PostFilter getFilter() {
        return this.postFilter;
    }

    @Override
    public Integer getDisplayOrderKeyPrio() {
        return 1000;
    }

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

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (isEventsEnabled()) {
            this.enableButtons();
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
}
