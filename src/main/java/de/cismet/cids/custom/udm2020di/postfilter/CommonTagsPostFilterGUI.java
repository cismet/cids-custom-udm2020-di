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
import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import org.openide.util.WeakListeners;
import org.openide.util.lookup.ServiceProvider;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JToggleButton;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.udm2020di.serversearches.FilterByTagsSearch;
import de.cismet.cids.custom.udm2020di.serversearches.PostFilterTagsSearch;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class CommonTagsPostFilterGUI extends AbstractPostFilterGUI implements ActionListener {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger logger = Logger.getLogger(CommonTagsPostFilterGUI.class);

    //~ Instance fields --------------------------------------------------------

    protected final PostFilterTagsSearch postfilterTagsSearch;
    protected final FilterByTagsSearch filterByTagsSearch;
    protected final boolean active;
    protected final Map<Integer, JToggleButton> filterButtons = new HashMap<Integer, JToggleButton>();

    protected final PostFilter postFilter = new PostFilter() {

            @Override
            public Integer getFilterChainOrderKeyPrio() {
                return 100;
            }

            @Override
            public Collection<Node> filter(final Collection<Node> input) {
                if (logger.isDebugEnabled()) {
                    logger.info("PostFilter: filtering " + input.size() + " nodes");
                }

                final List<Node> nodes;
                if (List.class.isAssignableFrom(input.getClass())) {
                    nodes = (List<Node>)input;
                } else {
                    nodes = new ArrayList<Node>(input);
                }

                filterByTagsSearch.setNodes(nodes);
                final ArrayList filterTagIds = new ArrayList<Integer>();
                for (final Integer tagId : filterButtons.keySet()) {
                    if (!filterButtons.get(tagId).isSelected()) {
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
                    if (logger.isDebugEnabled()) {
                        logger.debug(filteredNodes.size() + " of " + input.size() + " nodes remaining after applying "
                                    + filterTagIds.size() + " filter tags");
                    }

                    return filteredNodes;
                } catch (Exception e) {
                    logger.error("could not apply filter tags for '" + input.size() + " nodes!", e);
                    return input;
                }
            }
        };

    // Variables declaration - do not modify
    private javax.swing.JButton applyButton;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CommonTagsPostfilter.
     */
    public CommonTagsPostFilterGUI() {
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
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        applyButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(
            applyButton,
            org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGUI.class,
                "CommonTagsPostFilterGUI.applyButton.text"));          // NOI18N
        applyButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGUI.class,
                "CommonTagsPostFilterGUI.applyButton.toolTipText"));   // NOI18N
        applyButton.setActionCommand(org.openide.util.NbBundle.getMessage(
                CommonTagsPostFilterGUI.class,
                "CommonTagsPostFilterGUI.applyButton.actionCommand")); // NOI18N
        applyButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    applyButtonActionPerformed(evt);
                }
            });
        add(applyButton);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void applyButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_applyButtonActionPerformed
        this.firePostFilterChanged();
    }                                                                               //GEN-LAST:event_applyButtonActionPerformed
    // End of variables declaration
    @Override
    public void initializeFilter(final Collection<Node> nodes) {
        if (logger.isDebugEnabled()) {
            logger.debug("initialize Filter with " + nodes.size() + " nodes");
        }
        this.filterButtons.clear();

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    removeAll();
                }
            });

        final SwingWorker<Collection<JToggleButton>, Void> worker = new SwingWorker<Collection<JToggleButton>, Void>() {

                @Override
                protected Collection<JToggleButton> doInBackground() throws Exception {
                    final Collection<JToggleButton> tagButtons = new ArrayList<JToggleButton>();
                    final Collection<CidsBean> cidsBeans = retrieveFilterTags(nodes);

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
                        for (final JToggleButton tagButton : tagButtons) {
                            add(tagButton);
                        }
                        add(applyButton);
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
//        tagButton.addActionListener(WeakListeners.create(
//                ActionListener.class,
//                CommonTagsPostFilterGUI.this,
//                tagButton));

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
    protected Collection<CidsBean> retrieveFilterTags(final Collection<Node> nodes) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("retrieving tags for '" + nodes.size() + " nodes.");
        }
        final Collection<CidsBean> cidsBeans = new ArrayList<CidsBean>();
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
            CommonTagsPostFilterGUI.this.postfilterTagsSearch.setObjectIdMap(objectIdMap);
            final Collection<MetaObject> metaObjects = SessionManager.getProxy()
                        .customServerSearch(postfilterTagsSearch);
            for (final MetaObject metaObject : metaObjects) {
                final CidsBean cidsBean = metaObject.getBean();
                cidsBeans.add(cidsBean);
            }
        } catch (Exception e) {
            logger.error("could not retrieve tags for '" + nodes.size() + " nodes!", e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug(cidsBeans.size() + " tags for '" + nodes.size() + " nodes retrieved.");
        }

        return cidsBeans;
    }

    @Override
    public void adjustFilter(final Collection<Node> nodes) {
        if (logger.isDebugEnabled()) {
            logger.debug("adjust Filter with " + nodes.size() + " nodes");
        }
        final SwingWorker<Collection<CidsBean>, Void> worker = new SwingWorker<Collection<CidsBean>, Void>() {

                @Override
                protected Collection<CidsBean> doInBackground() throws Exception {
                    return retrieveFilterTags(nodes);
                }

                @Override
                protected void done() {
                    try {
                        final Collection<CidsBean> cidsBeans = this.get();

                        for (final CidsBean cidsBean : cidsBeans) {
                            final int tagId = (int)cidsBean.getProperty("id");
                            if (filterButtons.containsKey(tagId)) {
                                filterButtons.get(tagId).setSelected(true);
                            }
                        }
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
        return "TAGS";
    }

    @Override
    public PostFilter getFilter() {
        return this.postFilter;
    }

    @Override
    public Integer getDisplayOrderKeyPrio() {
        return this.postFilter.getFilterChainOrderKeyPrio();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JToggleButton tagButton = (JToggleButton)e.getSource();
//        final Integer tagId = Integer.valueOf(e.getActionCommand());
//        if (tagButton.isSelected()) {
//            this.filterTags.add(tagId);
//        } else {
//            this.filterTags.remove(tagId);
//        }

        // this.firePostFilterChanged();
    }
}
