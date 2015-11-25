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
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.udm2020di.serversearch.CustomMaxValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.PostFilterAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.tools.PostfilterConfigurationRegistry;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.AggregationValues;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public abstract class CommonSampleValuesPostFilterGui extends AbstractPostFilterGUI {

    //~ Static fields/initializers ---------------------------------------------

    public static final String SELECTED_VALUES = "SELECTED_VALUES";
    public static final String MIN_DATE = "MIN_DATE";
    public static final String MAX_DATE = "MIN_DATE";

    protected static Logger LOGGER = Logger.getLogger(CommonSampleValuesPostFilterGui.class);

    //~ Instance fields --------------------------------------------------------

    protected final PostFilterAggregationValuesSearch postFilterAggregationValuesSearch;
    protected final CustomMaxValuesSearch customMaxValuesSearch;
    protected boolean active = false;
    protected final MetaClass metaClass;
    protected final ImageIcon icon;

    protected final PostFilter postFilter = new PostFilter() {

            @Override
            public Integer getFilterChainOrderKeyPrio() {
                return getDisplayOrderKeyPrio();
            }

            @Override
            public Collection<Node> filter(final Collection<Node> input) {
                final Map<String, Float> maxParameterValues = maxParameterValueSelectionPanel.getValues();

                if (!maxParameterValues.isEmpty()) {
                    EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                disableButtons();
                            }
                        });

                    final List<MetaObjectNode> preFilteredNodes = preFilterNodes(input);
                    final Collection<Node> postFilteredNodes = new ArrayList<Node>(input);
                    postFilteredNodes.removeAll(preFilteredNodes);

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.info("PostFilter: filtering " + preFilteredNodes.size() + " pre-filtered nodes of "
                                    + input.size() + " available nodes");
                    }

                    final ArrayList objectIds = new ArrayList<Integer>();
                    for (final MetaObjectNode node : preFilteredNodes) {
                        objectIds.add(node.getObjectId());
                    }

                    customMaxValuesSearch.setObjectIds(objectIds);
                    customMaxValuesSearch.setClassId(metaClass.getID());
                    customMaxValuesSearch.setMaxValues(maxParameterValues);
                    customMaxValuesSearch.setMinDate(maxParameterValueSelectionPanel.getMinDate());
                    customMaxValuesSearch.setMaxDate(maxParameterValueSelectionPanel.getMaxDate());

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("filtering " + input.size() + " nodes with "
                                    + maxParameterValues.size() + " max param value filters");
                    }

                    try {
                        final Collection<Node> filteredNodes = SessionManager.getProxy()
                                    .customServerSearch(customMaxValuesSearch);

                        postFilteredNodes.addAll(filteredNodes);
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(postFilteredNodes.size() + " of " + input.size()
                                        + " nodes remaining after applying "
                                        + maxParameterValues.size() + " max param value filters to "
                                        + preFilteredNodes.size()
                                        + " pre-filtered nodes (" + filteredNodes + " actually filtered nodes)");
                        }

                        return postFilteredNodes;
                    } catch (Exception e) {
                        LOGGER.error("could not apply max param value filters for '" + input.size() + " nodes!", e);
                        return input;
                    }
                } else {
                    return input;
                }
            }
        };

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton applyButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private de.cismet.cids.custom.udm2020di.widgets.MaxParameterValueSelectionPanel maxParameterValueSelectionPanel;
    private javax.swing.JPanel parameterPanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton resetButton;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form SampleValuesPostFilterGui.
     *
     * @param  postFilterAggregationValuesSearch  DOCUMENT ME!
     * @param  customMaxValuesSearch              DOCUMENT ME!
     * @param  icon                               DOCUMENT ME!
     * @param  className                          DOCUMENT ME!
     */
    public CommonSampleValuesPostFilterGui(
            final PostFilterAggregationValuesSearch postFilterAggregationValuesSearch,
            final CustomMaxValuesSearch customMaxValuesSearch,
            final ImageIcon icon,
            final String className) {
        this.postFilterAggregationValuesSearch = postFilterAggregationValuesSearch;
        this.customMaxValuesSearch = customMaxValuesSearch;
        this.icon = icon;

        metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", className);
        if (metaClass == null) {
            LOGGER.warn("could not retrieve " + className + " class from UDM2020-DI, "
                        + "filter is disabled!");
            this.active = false;
        }

        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean isActive() {
        return this.active && (this.metaClass != null);
    }

    @Override
    public boolean canHandle(final Collection<Node> nodes) {
        final boolean canHandle = !this.preFilterNodes(nodes).isEmpty();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("filter can handle " + nodes.size() + " nodes:" + canHandle);
        }
        return canHandle;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   input  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected List<MetaObjectNode> preFilterNodes(final Collection<Node> input) {
        final List<MetaObjectNode> nodes = new ArrayList<MetaObjectNode>();
        if (this.metaClass != null) {
            for (final Node node : input) {
                if ((node.getClassId() == this.metaClass.getId())
                            && (node instanceof MetaObjectNode)) {
                    nodes.add((MetaObjectNode)node);
                }
            }
        }

        return nodes;
    }

    @Override
    public String getTitle() {
        return org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.title");
    }

    /**
     * DOCUMENT ME!
     */
    protected void disableButtons() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("disable buttons");
        }
        applyButton.setEnabled(false);
        resetButton.setEnabled(false);
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public void initializeFilter(final Collection<Node> nodes) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("initialize Filter with " + nodes.size() + " nodes");
        }

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    parameterPanel.removeAll();
                    parameterPanel.setLayout(new FlowLayout());
                    parameterPanel.add(progressBar);
                    maxParameterValueSelectionPanel.reset();
                    disableButtons();
                    parameterPanel.validate();
                    parameterPanel.repaint();
                }
            });

        final List<MetaObjectNode> preFilteredNodes = preFilterNodes(nodes);
        if (!preFilteredNodes.isEmpty()) {
            final SwingWorker<Collection<AggregationValue>, Void> worker =
                new SwingWorker<Collection<AggregationValue>, Void>() {

                    @Override
                    protected Collection<AggregationValue> doInBackground() throws Exception {
                        final ArrayList objectIds = new ArrayList<Integer>();
                        for (final MetaObjectNode node : preFilteredNodes) {
                            objectIds.add(node.getObjectId());
                        }

                        postFilterAggregationValuesSearch.setObjectIds(objectIds);

                        try {
                            final Collection<AggregationValue> aggregationValues = SessionManager.getProxy()
                                        .customServerSearch(postFilterAggregationValuesSearch);

                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug(aggregationValues.size() + " aggregation values for "
                                            + objectIds.size() + " nodes retrieved.");
                            }

                            return aggregationValues;
                        } catch (Exception e) {
                            LOGGER.error("could not retrieve aggregation values  for " + objectIds.size() + " nodes!",
                                e);
                        }

                        return new ArrayList<AggregationValue>(0);
                    }

                    @Override
                    protected void done() {
                        try {
                            final Collection<AggregationValue> aggregationValues = this.get();

                            parameterPanel.removeAll();
                            parameterPanel.setLayout(new BorderLayout());
                            parameterPanel.add(maxParameterValueSelectionPanel, BorderLayout.CENTER);
                            if (!aggregationValues.isEmpty()) {
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("setting " + aggregationValues.size() + " aggregation values");
                                }
                                if (AggregationValues.class.isAssignableFrom(aggregationValues.getClass())) {
                                    maxParameterValueSelectionPanel.setAggregationValues((AggregationValues)
                                        aggregationValues);
                                } else {
                                    LOGGER.warn("search did not return AggregationValues.class object!");
                                    maxParameterValueSelectionPanel.setAggregationValues(aggregationValues);
                                }

                                final Class runtimeClass = CommonSampleValuesPostFilterGui.this.getClass();

                                if (PostfilterConfigurationRegistry.getInstance().hasSetting(
                                                runtimeClass,
                                                SELECTED_VALUES)) {
                                    final Map<String, Float> selectedValues = (Map<String, Float>)
                                        PostfilterConfigurationRegistry.getInstance()
                                                .popSetting(
                                                        runtimeClass,
                                                        SELECTED_VALUES);

                                    final Date minDate =
                                        PostfilterConfigurationRegistry
                                                .getInstance().hasSetting(runtimeClass, MIN_DATE)
                                        ? (Date)PostfilterConfigurationRegistry.getInstance()
                                                .popSetting(runtimeClass, MIN_DATE) : null;

                                    final Date maxDate =
                                        PostfilterConfigurationRegistry
                                                .getInstance().hasSetting(runtimeClass, MAX_DATE)
                                        ? (Date)PostfilterConfigurationRegistry.getInstance()
                                                .popSetting(runtimeClass, MAX_DATE) : null;

                                    LOGGER.info("restoring " + selectedValues.size()
                                                + " selected values from saved configuration!");
                                    maxParameterValueSelectionPanel.setValues(selectedValues, minDate, maxDate);
                                }

                                enableButtons();
                            } else {
                                LOGGER.warn("no aggregation values found!");
                            }
                            parameterPanel.validate();
                            parameterPanel.repaint();
                        } catch (Exception ex) {
                            LOGGER.error(ex.getMessage(), ex);
                        }
                    }
                };
            worker.execute();
        } else {
            LOGGER.warn("no (valid) nodes provided, filter disabled!");
        }
    }

    @Override
    public void adjustFilter(final Collection<Node> nodes) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("adjust Filter with " + nodes.size() + " nodes");
        }

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    enableButtons();
                }
            });
    }

    @Override
    public PostFilter getFilter() {
        return this.postFilter;
    }

    /**
     * DOCUMENT ME!
     */
    protected void enableButtons() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("enable buttons: " + maxParameterValueSelectionPanel.getSelectedValues());
        }
        applyButton.setEnabled(maxParameterValueSelectionPanel.getSelectedValues() > 0);
        resetButton.setEnabled(maxParameterValueSelectionPanel.getSelectedValues() > 0);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        progressBar = new javax.swing.JProgressBar();
        parameterPanel = new javax.swing.JPanel();
        maxParameterValueSelectionPanel = new de.cismet.cids.custom.udm2020di.widgets.MaxParameterValueSelectionPanel();
        actionPanel = new javax.swing.JPanel();
        applyButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5),
                new java.awt.Dimension(0, 5),
                new java.awt.Dimension(32767, 5));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5),
                new java.awt.Dimension(0, 5),
                new java.awt.Dimension(32767, 5));
        resetButton = new javax.swing.JButton();

        progressBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        progressBar.setIndeterminate(true);
        progressBar.setMinimumSize(new java.awt.Dimension(150, 14));
        progressBar.setOpaque(true);
        progressBar.setPreferredSize(new java.awt.Dimension(250, 14));

        setLayout(new java.awt.BorderLayout());

        parameterPanel.setLayout(new java.awt.BorderLayout());

        maxParameterValueSelectionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        maxParameterValueSelectionPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(final java.beans.PropertyChangeEvent evt) {
                    maxParameterValueSelectionPanelPropertyChange(evt);
                }
            });
        parameterPanel.add(maxParameterValueSelectionPanel, java.awt.BorderLayout.CENTER);

        add(parameterPanel, java.awt.BorderLayout.CENTER);

        actionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        actionPanel.setLayout(new javax.swing.BoxLayout(actionPanel, javax.swing.BoxLayout.PAGE_AXIS));

        applyButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/udm2020di/postfilter/funnel-arrow-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            applyButton,
            org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.applyButton.text"));                                          // NOI18N
        applyButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.applyButton.toolTipText"));                                   // NOI18N
        applyButton.setActionCommand(org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.applyButton.actionCommand"));                                 // NOI18N
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
        actionPanel.add(filler2);

        resetButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/udm2020di/postfilter/funnel-minus-icon.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(
            resetButton,
            org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.resetButton.text"));                                          // NOI18N
        resetButton.setToolTipText(org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.resetButton.toolTipText"));                                   // NOI18N
        resetButton.setActionCommand(org.openide.util.NbBundle.getMessage(
                CommonSampleValuesPostFilterGui.class,
                "CommonSampleValuesPostFilterGui.resetButton.actionCommand"));                                 // NOI18N
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
        this.maxParameterValueSelectionPanel.reset();
        this.validateTree();
        this.enableButtons();
    }                                                                               //GEN-LAST:event_resetButtonActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void maxParameterValueSelectionPanelPropertyChange(final java.beans.PropertyChangeEvent evt) { //GEN-FIRST:event_maxParameterValueSelectionPanelPropertyChange
        this.enableButtons();
    }                                                                                                      //GEN-LAST:event_maxParameterValueSelectionPanelPropertyChange

    @Override
    public Integer getDisplayOrderKeyPrio() {
        return 2000;
    }
}
