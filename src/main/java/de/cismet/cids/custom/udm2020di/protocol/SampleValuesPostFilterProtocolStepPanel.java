/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.PostfilterEnabledSearchResultsTree;
import Sirius.navigator.ui.tree.SearchResultsTree;

import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.EventQueue;

import java.util.Arrays;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.widgets.MaxParameterValuePanel;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class SampleValuesPostFilterProtocolStepPanel extends AbstractProtocolStepPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(SampleValuesPostFilterProtocolStepPanel.class);

    //~ Instance fields --------------------------------------------------------

    protected SampleValuesPostFilterProtocolStep protocolStep;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel additionalFiltersLabel;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JPanel parameterPanel;
    private org.jdesktop.swingx.JXHyperlink restorePostFilterHyperlink;
    private org.jdesktop.swingx.JXHyperlink restoreSearchResultsHyperlink;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ExportActionProtocolStepPanel.
     *
     * @param  protocolStep  exportAction DOCUMENT ME!
     */
    public SampleValuesPostFilterProtocolStepPanel(final SampleValuesPostFilterProtocolStep protocolStep) {
        initComponents();
        this.setProtocolStep(protocolStep);

        // ComponentRegistry.getRegistry().getDescriptionPane().gotoMetaObjects(to, TOOL_TIP_TEXT_KEY);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  protocolStep  exportAction DOCUMENT ME!
     */
    protected final void setProtocolStep(final SampleValuesPostFilterProtocolStep protocolStep) {
        this.protocolStep = protocolStep;

        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    iconLabel.setIcon(protocolStep.getIcon());
                    titleLabel.setText(NbBundle.getMessage(
                            SampleValuesPostFilterProtocolStepPanel.class,
                            protocolStep.getPostFilter()));

                    Mnemonics.setLocalizedText(
                        restoreSearchResultsHyperlink,
                        NbBundle.getMessage(
                            SampleValuesPostFilterProtocolStepPanel.class,
                            "SampleValuesPostFilterProtocolStepPanel.restoreSearchResultsHyperlink.text",
                            String.valueOf(protocolStep.getResultNodes().size())));

                    Mnemonics.setLocalizedText(
                        additionalFiltersLabel,
                        NbBundle.getMessage(
                            SampleValuesPostFilterProtocolStepPanel.class,
                            "SampleValuesPostFilterProtocolStepPanel.additionalFiltersLabel.text",
                            String.valueOf(protocolStep.getCascadingProtocolStep().getProtocolSteps().size())));

                    Mnemonics.setLocalizedText(
                        restorePostFilterHyperlink,
                        NbBundle.getMessage(
                            SampleValuesPostFilterProtocolStepPanel.class,
                            "SampleValuesPostFilterProtocolStepPanel.restorePostFilterHyperlink.text",
                            String.valueOf(protocolStep.getSelectedValues().size()),
                            protocolStep.getTitle()));
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("visualising " + protocolStep.getSelectedValues().size()
                                    + " selected parameter values");
                    }
                    for (final Map.Entry<String, Float> selectedValue : protocolStep.getSelectedValues().entrySet()) {
                        final MaxParameterValuePanel maxParameterValuePanel = new MaxParameterValuePanel();
                        maxParameterValuePanel.setEnabled(false);
                        maxParameterValuePanel.setAggregationValues(
                            Arrays.asList(
                                new AggregationValue[] { protocolStep.getAggregationValue(selectedValue.getKey()) }));
                        maxParameterValuePanel.setValue(selectedValue);

                        parameterPanel.add(maxParameterValuePanel);
                    }
                }
            };

        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        iconLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        restoreSearchResultsHyperlink = new org.jdesktop.swingx.JXHyperlink();
        additionalFiltersLabel = new javax.swing.JLabel();
        restorePostFilterHyperlink = new org.jdesktop.swingx.JXHyperlink();
        parameterPanel = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(
            iconLabel,
            org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.iconLabel.text")); // NOI18N
        iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.openide.awt.Mnemonics.setLocalizedText(
            titleLabel,
            org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.titleLabel.text")); // NOI18N

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            restoreSearchResultsHyperlink,
            org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.restoreSearchResultsHyperlink.text"));          // NOI18N
        restoreSearchResultsHyperlink.setActionCommand(org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.restoreSearchResultsHyperlink.actionCommand")); // NOI18N
        restoreSearchResultsHyperlink.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    restoreSearchResultsHyperlinkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(restoreSearchResultsHyperlink, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            additionalFiltersLabel,
            org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.additionalFiltersLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(additionalFiltersLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            restorePostFilterHyperlink,
            org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.restorePostFilterHyperlink.text"));          // NOI18N
        restorePostFilterHyperlink.setActionCommand(org.openide.util.NbBundle.getMessage(
                SampleValuesPostFilterProtocolStepPanel.class,
                "SampleValuesPostFilterProtocolStepPanel.restorePostFilterHyperlink.actionCommand")); // NOI18N
        restorePostFilterHyperlink.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    restorePostFilterHyperlinkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(restorePostFilterHyperlink, gridBagConstraints);

        parameterPanel.setLayout(new javax.swing.BoxLayout(parameterPanel, javax.swing.BoxLayout.PAGE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(parameterPanel, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void restoreSearchResultsHyperlinkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_restoreSearchResultsHyperlinkActionPerformed

        if (!this.protocolStep.getResultNodes().isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring " + this.protocolStep.getCascadingProtocolStep().getResultNodes().size()
                            + " search results from  protocol of post filter '"
                            + this.protocolStep.getPostFilter() + "'");
            }

            ComponentRegistry.getRegistry()
                    .getSearchResultsTree()
                    .setResultNodes(
                        this.protocolStep.getResultNodes().toArray(
                            new Node[this.protocolStep.getResultNodes().size()]));
        } else {
            LOGGER.error("result nodes list is empty, cannot restore search result from  protocol of post filter '"
                        + this.protocolStep.getPostFilter() + "'");
        }
    } //GEN-LAST:event_restoreSearchResultsHyperlinkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void restorePostFilterHyperlinkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_restorePostFilterHyperlinkActionPerformed
        if (!this.protocolStep.getSelectedValues().isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring " + this.protocolStep.getSelectedValues().size()
                            + " selected sample values from protocol of post filter '"
                            + this.protocolStep.getPostFilter() + "'");
            }

            final SearchResultsTree searchResultsTree = ComponentRegistry.getRegistry().getSearchResultsTree();
            if ((searchResultsTree != null) && (searchResultsTree instanceof PostfilterEnabledSearchResultsTree)) {
                PostfilterProtocolRegistry.getInstance()
                        .restoreCascadingProtocolStep(
                            this.protocolStep.getCascadingProtocolStep());

                if (!this.protocolStep.getCascadingProtocolStep().getResultNodes().isEmpty()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("restoring " + this.protocolStep.getResultNodes().size()
                                    + " result nodes and " + this.protocolStep.getFilteredNodes().size()
                                    + " filtered nodes from  protocol of post filter '"
                                    + this.protocolStep.getPostFilter() + "'");
                    }

                    ((PostfilterEnabledSearchResultsTree)searchResultsTree).setFilteredResultNodes(
                        this.protocolStep.getResultNodes().toArray(
                            new Node[this.protocolStep.getFilteredNodes().size()]),
                        this.protocolStep.getFilteredNodes().toArray(
                            new Node[this.protocolStep.getFilteredNodes().size()]));
                } else {
                    LOGGER.error(
                        "result nodes list is empty, cannot restore search result from  protocol of post filter '"
                                + this.protocolStep.getPostFilter()
                                + "'");
                }
            } else {
                LOGGER.error("result nodes cannot be restored, no PostfilterEnabledSearchResultsTree available!");
            }
        } else {
            LOGGER.error("selected tags list is empty, cannot filter settings from protocol of post filter '"
                        + this.protocolStep.getPostFilter() + "'");
        }
    } //GEN-LAST:event_restorePostFilterHyperlinkActionPerformed

    @Override
    public Component getIconComponent() {
        return this.iconLabel;
    }

    @Override
    public Component getTitleComponent() {
        return this.titleLabel;
    }
}
