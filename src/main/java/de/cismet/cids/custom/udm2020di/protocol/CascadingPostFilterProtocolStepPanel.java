/**
 * *************************************************
 *
 * cismet GmbH, Saarbruecken, Germany
 *
 *              ... and it just works.
 *
 ***************************************************
 */
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

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * DOCUMENT ME!
 *
 * @author Pascal Dihé <pascal.dihe@cismet.de>
 * @version $Revision$, $Date$
 */
public class CascadingPostFilterProtocolStepPanel extends AbstractProtocolStepPanel {

    //~ Static fields/initializers ---------------------------------------------
    protected static final Logger LOGGER = Logger.getLogger(CascadingPostFilterProtocolStepPanel.class);

    //~ Instance fields --------------------------------------------------------
    protected CascadingPostFilterProtocolStep protocolStep;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filterSettingsPanel;
    private javax.swing.JLabel filteredSearchResultsLabel;
    private javax.swing.JLabel iconLabel;
    private org.jdesktop.swingx.JXHyperlink restorePostFilterHyperlink;
    private javax.swing.JLabel searchResultsLabel;
    private org.jdesktop.swingx.JXHyperlink showPostFilterHyperlink;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------
    /**
     * Creates new form ExportActionProtocolStepPanel.
     *
     * @param protocolStep exportAction DOCUMENT ME!
     */
    public CascadingPostFilterProtocolStepPanel(final CascadingPostFilterProtocolStep protocolStep) {
        initComponents();
        this.setProtocolStep(protocolStep);
        this.filterSettingsPanel.setVisible(false);

        // ComponentRegistry.getRegistry().getDescriptionPane().gotoMetaObjects(to, TOOL_TIP_TEXT_KEY);
    }

    //~ Methods ----------------------------------------------------------------
    /**
     * DOCUMENT ME!
     *
     * @param protocolStep exportAction DOCUMENT ME!
     */
    protected final void setProtocolStep(final CascadingPostFilterProtocolStep protocolStep) {
        this.protocolStep = protocolStep;

        final Runnable r = new Runnable() {

            @Override
            public void run() {
                Mnemonics.setLocalizedText(
                        searchResultsLabel,
                        NbBundle.getMessage(
                                CascadingPostFilterProtocolStepPanel.class,
                                "CascadingPostFilterProtocolStepPanel.searchResultsLabel.text",
                                String.valueOf(protocolStep.getResultNodes().size())));

                Mnemonics.setLocalizedText(
                        filteredSearchResultsLabel,
                        NbBundle.getMessage(
                                CascadingPostFilterProtocolStepPanel.class,
                                "CascadingPostFilterProtocolStepPanel.filteredSearchResultsLabel.text",
                                String.valueOf(protocolStep.getFilteredNodes().size())));

                Mnemonics.setLocalizedText(
                        showPostFilterHyperlink,
                        NbBundle.getMessage(
                                CascadingPostFilterProtocolStepPanel.class,
                                "CascadingPostFilterProtocolStepPanel.showPostFilterHyperlink.text",
                                String.valueOf(protocolStep.getProtocolSteps().size())));

                for (final CommonPostFilterProtocolStep subProtocolStep : protocolStep.getProtocolSteps().values()) {
                    
                    final JPanel panel = new JPanel(new BorderLayout());
                    final JLabel customFilterLabel = new JLabel();
                    customFilterLabel.setIcon(subProtocolStep.getIcon()); // NOI18N
                   customFilterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    Mnemonics.setLocalizedText(customFilterLabel, 
                            NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, 
                                    "CascadingPostFilterProtocolStepPanel.customFilterLabel.text", 
                                    String.valueOf(subProtocolStep.appliedFilters()),
                            subProtocolStep.getTitle())); // NOI18N
                    panel.add(customFilterLabel, BorderLayout.NORTH);

                    final CommonPostFilterProtocolStepPanel protocolStepPanel = (CommonPostFilterProtocolStepPanel) subProtocolStep.visualize();
                    panel.add(protocolStepPanel.getFilterSettingsPanel(), BorderLayout.CENTER);
                    filterSettingsPanel.add(panel);
                }

                validate();
            }
        };

        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        iconLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        searchResultsLabel = new javax.swing.JLabel();
        filteredSearchResultsLabel = new javax.swing.JLabel();
        showPostFilterHyperlink = new org.jdesktop.swingx.JXHyperlink();
        restorePostFilterHyperlink = new org.jdesktop.swingx.JXHyperlink();
        filterSettingsPanel = new javax.swing.JPanel();

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/udm2020di/protocol/filter_20.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(iconLabel, org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.iconLabel.text")); // NOI18N
        iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.openide.awt.Mnemonics.setLocalizedText(titleLabel, org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.titleLabel.text")); // NOI18N

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(searchResultsLabel, org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.searchResultsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(searchResultsLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(filteredSearchResultsLabel, org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.filteredSearchResultsLabel.text")); // NOI18N
        filteredSearchResultsLabel.setToolTipText(org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.filteredSearchResultsLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(filteredSearchResultsLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showPostFilterHyperlink, org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.showPostFilterHyperlink.text")); // NOI18N
        showPostFilterHyperlink.setActionCommand(org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.showPostFilterHyperlink.actionCommand")); // NOI18N
        showPostFilterHyperlink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPostFilterHyperlinkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(showPostFilterHyperlink, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(restorePostFilterHyperlink, org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.restorePostFilterHyperlink.text")); // NOI18N
        restorePostFilterHyperlink.setActionCommand(org.openide.util.NbBundle.getMessage(CascadingPostFilterProtocolStepPanel.class, "CascadingPostFilterProtocolStepPanel.restorePostFilterHyperlink.actionCommand")); // NOI18N
        restorePostFilterHyperlink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restorePostFilterHyperlinkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(restorePostFilterHyperlink, gridBagConstraints);

        filterSettingsPanel.setFocusable(false);
        filterSettingsPanel.setLayout(new javax.swing.BoxLayout(filterSettingsPanel, javax.swing.BoxLayout.PAGE_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(filterSettingsPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param evt DOCUMENT ME!
     */
    private void restorePostFilterHyperlinkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restorePostFilterHyperlinkActionPerformed
        if (!this.protocolStep.getProtocolSteps().isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring " + this.protocolStep.getProtocolSteps().size()
                        + " protocol steps for master post filter '"
                        + this.protocolStep.getMasterPostFilter() + "'");
            }

            final SearchResultsTree searchResultsTree = ComponentRegistry.getRegistry().getSearchResultsTree();
            if ((searchResultsTree != null) && (searchResultsTree instanceof PostfilterEnabledSearchResultsTree)) {
                PostfilterProtocolRegistry.getInstance().restoreCascadingProtocolStep(
                        this.protocolStep);

                if (!this.protocolStep.getResultNodes().isEmpty()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("restoring " + this.protocolStep.getResultNodes().size()
                                + " result nodes and " + this.protocolStep.getFilteredNodes().size()
                                + " filtered nodes from  protocol of master post filter '"
                                + this.protocolStep.getMasterPostFilter() + "'");
                    }

                    ((PostfilterEnabledSearchResultsTree) searchResultsTree).setFilteredResultNodes(
                            this.protocolStep.getResultNodes().toArray(
                                    new Node[this.protocolStep.getFilteredNodes().size()]),
                            this.protocolStep.getFilteredNodes().toArray(
                                    new Node[this.protocolStep.getFilteredNodes().size()]));
                } else {
                    LOGGER.error(
                            "result nodes list is empty, cannot restore search result from  protocol of master post filter '"
                            + this.protocolStep.getMasterPostFilter()
                            + "'");
                }
            } else {
                LOGGER.error("result nodes cannot be restored, no PostfilterEnabledSearchResultsTree available!");
            }
        } else {
            LOGGER.error("selected tags list is empty, cannot filter settings from protocol of master post filter '"
                    + this.protocolStep.getMasterPostFilter() + "'");
        }
    }//GEN-LAST:event_restorePostFilterHyperlinkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param evt DOCUMENT ME!
     */
    private void showPostFilterHyperlinkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPostFilterHyperlinkActionPerformed
        toggleFilterSettingsPanelVisibility();
    }//GEN-LAST:event_showPostFilterHyperlinkActionPerformed

    @Override
    public Component getIconComponent() {
        return this.iconLabel;
    }

    @Override
    public Component getTitleComponent() {
        return this.titleLabel;
    }

    /**
     * DOCUMENT ME!
     */
    protected void toggleFilterSettingsPanelVisibility() {
        setFilterSettingsPanelVisible(!this.filterSettingsPanel.isVisible());
    }

    /**
     * DOCUMENT ME!
     *
     * @param visible DOCUMENT ME!
     */
    protected void setFilterSettingsPanelVisible(final boolean visible) {
        this.filterSettingsPanel.setVisible(visible);
        revalidate();
    }
}
