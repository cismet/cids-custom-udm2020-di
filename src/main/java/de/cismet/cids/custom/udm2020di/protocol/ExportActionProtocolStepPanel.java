/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.DescriptionPane;

import Sirius.server.middleware.types.MetaObjectNode;

import lombok.Getter;

import org.apache.log4j.Logger;

import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.actions.remote.ExportAction;
import de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction;
import de.cismet.cids.custom.udm2020di.tools.RendererConfigurationRegistry;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;

import static de.cismet.cids.custom.udm2020di.actions.remote.ExportAction.EXPORT_FORMAT_SETTINGS;
import static de.cismet.cids.custom.udm2020di.actions.remote.ExportAction.PARAMETER_SETTINGS;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class ExportActionProtocolStepPanel extends AbstractProtocolStepPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(ExportActionProtocolStepPanel.class);

    //~ Instance fields --------------------------------------------------------

    @Getter
    protected ExportAction exportAction;
    @Getter
    protected final ArrayList<MetaObjectNode> metaObjectNodes = new ArrayList<MetaObjectNode>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXHyperlink exportActionHyperlink;
    private org.jdesktop.swingx.JXHyperlink exportObjectsHyperlink;
    private org.jdesktop.swingx.JXHyperlink exportParameterHyperlink;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JPanel objectsPanel;
    private Sirius.navigator.search.CidsServerSearchProtocolStepResultsTree protocolResultsTree;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ExportActionProtocolStepPanel.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    public ExportActionProtocolStepPanel(final ExportAction exportAction) {
        initComponents();
        this.objectsPanel.setVisible(false);
        this.setExportAction(exportAction);

        // ComponentRegistry.getRegistry().getDescriptionPane().gotoMetaObjects(to, TOOL_TIP_TEXT_KEY);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  exportAction  DOCUMENT ME!
     */
    protected final void setExportAction(final ExportAction exportAction) {
        this.exportAction = exportAction;

        final String domain = SessionManager.getSession().getConnectionInfo().getUserDomain();
        for (final Map.Entry<Long, String> object : this.exportAction.getObjects().entrySet()) {
            final MetaObjectNode metaObjectNode = new MetaObjectNode(
                    domain,
                    object.getKey().intValue(),
                    this.exportAction.getClassId(),
                    object.getValue());

            metaObjectNodes.add(metaObjectNode);
        }

        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    final String title = ExportActionProtocolStepPanel.this.exportAction.getTitle();
                    final String suffix;
                    if (title.charAt(title.length() - 1) == 's') {
                        suffix = "e";
                    } else if (title.charAt(title.length() - 1) == 't') {
                        suffix = "en";
                    } else {
                        suffix = "n";
                    }

                    // iconLabel.setIcon(ExportActionProtocolStepPanel.this.exportAction.getIcon());
                    titleLabel.setText(title + suffix
                                + NbBundle.getMessage(
                                    ExportActionProtocolStepPanel.class,
                                    "ExportActionProtocolStepPanel.title"));
                    exportActionHyperlink.setAction(ExportActionProtocolStepPanel.this.exportAction);

                    // overwrite title and icon provided by action object!
                    Mnemonics.setLocalizedText(
                        exportActionHyperlink,
                        NbBundle.getMessage(
                            ExportActionProtocolStepPanel.class,
                            "ExportActionProtocolStepPanel.exportActionHyperlink.text"));
                    exportActionHyperlink.setIcon(getFileIcon());
                    exportActionHyperlink.setToolTipText(NbBundle.getMessage(
                            ExportActionProtocolStepPanel.class,
                            "ExportActionProtocolStepPanel.exportActionHyperlink.toolTipText",
                            ExportActionProtocolStepPanel.this.exportAction.getExportFormat())); // NOI18N

                    exportObjectsHyperlink.setText(
                        String.valueOf(ExportActionProtocolStepPanel.this.exportAction.getObjects().size())
                                + ' '
                                + title
                                + ((ExportActionProtocolStepPanel.this.exportAction.getObjects().size() > 1) ? suffix
                                                                                                             : ""));
                    Mnemonics.setLocalizedText(
                        exportParameterHyperlink,
                        NbBundle.getMessage(
                            ExportActionProtocolStepPanel.class,
                            "ExportActionProtocolStepPanel.exportParameterHyperlink.text",
                            String.valueOf(ExportActionProtocolStepPanel.this.exportAction.getParameters().size()))); // NOI18N

                    protocolResultsTree.setResultNodes(metaObjectNodes.toArray(
                            new MetaObjectNode[metaObjectNodes.size()]));
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
        exportObjectsHyperlink = new org.jdesktop.swingx.JXHyperlink();
        exportParameterHyperlink = new org.jdesktop.swingx.JXHyperlink();
        exportActionHyperlink = new org.jdesktop.swingx.JXHyperlink();
        objectsPanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        try {
            protocolResultsTree = new Sirius.navigator.search.CidsServerSearchProtocolStepResultsTree();
        } catch (java.lang.Exception e1) {
            e1.printStackTrace();
        }

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/udm2020di/protocol/table_export_20.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(iconLabel, org.openide.util.NbBundle.getMessage(ExportActionProtocolStepPanel.class, "ExportActionProtocolStepPanel.iconLabel.text")); // NOI18N
        iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.openide.awt.Mnemonics.setLocalizedText(titleLabel, org.openide.util.NbBundle.getMessage(ExportActionProtocolStepPanel.class, "ExportActionProtocolStepPanel.titleLabel.text")); // NOI18N

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(exportObjectsHyperlink, org.openide.util.NbBundle.getMessage(ExportActionProtocolStepPanel.class, "ExportActionProtocolStepPanel.exportObjectsHyperlink.text")); // NOI18N
        exportObjectsHyperlink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportObjectsHyperlinkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(exportObjectsHyperlink, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(exportParameterHyperlink, org.openide.util.NbBundle.getMessage(ExportActionProtocolStepPanel.class, "ExportActionProtocolStepPanel.exportParameterHyperlink.text")); // NOI18N
        exportParameterHyperlink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportParameterHyperlinkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(exportParameterHyperlink, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(exportActionHyperlink, org.openide.util.NbBundle.getMessage(ExportActionProtocolStepPanel.class, "ExportActionProtocolStepPanel.exportActionHyperlink.text_1")); // NOI18N
        exportActionHyperlink.setToolTipText(org.openide.util.NbBundle.getMessage(ExportActionProtocolStepPanel.class, "ExportActionProtocolStepPanel.exportActionHyperlink.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(exportActionHyperlink, gridBagConstraints);

        objectsPanel.setPreferredSize(new java.awt.Dimension(2, 75));
        objectsPanel.setLayout(new java.awt.GridBagLayout());

        jScrollPane.setViewportView(protocolResultsTree);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        objectsPanel.add(jScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(objectsPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void exportParameterHyperlinkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportParameterHyperlinkActionPerformed

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("restoring export panel for " + this.exportAction.getObjects().size() + " objects");
        }

        final Map<String, Object> settings = new HashMap<String, Object>();
        settings.put(PARAMETER_SETTINGS, this.exportAction.getParameters());
        settings.put(EXPORT_FORMAT_SETTINGS, this.exportAction.getExportFormat());

        // Long cannot be converted to Integer!
        final ArrayList<Integer> objectIds = new ArrayList<Integer>(this.exportAction.getObjects().size());

        for (final Map.Entry<Long, String> object : this.exportAction.getObjects().entrySet()) {
            objectIds.add(object.getKey().intValue());
        }

        if (objectIds.size() == 1) {
            RendererConfigurationRegistry.getInstance()
                    .pushSettings(
                        SessionManager.getSession().getConnectionInfo().getUserDomain(),
                        this.exportAction.getClassId(),
                        objectIds.get(0),
                        settings);
        } else {
            RendererConfigurationRegistry.getInstance()
                    .pushSettings(
                        SessionManager.getSession().getConnectionInfo().getUserDomain(),
                        this.exportAction.getClassId(),
                        objectIds,
                        settings);
        }

        final DescriptionPane descriptionPane = ComponentRegistry.getRegistry().getDescriptionPane();
        descriptionPane.gotoMetaObjectNodes(metaObjectNodes.toArray(new MetaObjectNode[metaObjectNodes.size()]));
        descriptionPane.clearBreadCrumb();

        ComponentRegistry.getRegistry().showComponent(ComponentRegistry.DESCRIPTION_PANE);
    }//GEN-LAST:event_exportParameterHyperlinkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void exportObjectsHyperlinkActionPerformed(final java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportObjectsHyperlinkActionPerformed
        this.toggleObjectsPanelVisibility();
    }//GEN-LAST:event_exportObjectsHyperlinkActionPerformed

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
     *
     * @return  DOCUMENT ME!
     */
    protected ImageIcon getFileIcon() {
        if (this.exportAction.getExportFormat().equalsIgnoreCase(AbstractExportAction.PARAM_EXPORTFORMAT_CSV)) {
            return new javax.swing.ImageIcon(getClass().getResource(
                        "/de/cismet/cids/custom/udm2020di/protocol/text_exports.png"));
        } else if (this.exportAction.getExportFormat().equalsIgnoreCase(AbstractExportAction.PARAM_EXPORTFORMAT_XLS)) {
            return new javax.swing.ImageIcon(getClass().getResource(
                        "/de/cismet/cids/custom/udm2020di/protocol/table_excel.png"));
        } else if (this.exportAction.getExportFormat().equalsIgnoreCase(AbstractExportAction.PARAM_EXPORTFORMAT_XLSX)) {
            return new javax.swing.ImageIcon(getClass().getResource(
                        "/de/cismet/cids/custom/udm2020di/protocol/excel_exports.png"));
        } else if (this.exportAction.getExportFormat().equalsIgnoreCase(AbstractExportAction.PARAM_EXPORTFORMAT_SHP)) {
            return new javax.swing.ImageIcon(getClass().getResource(
                        "/de/cismet/cids/custom/udm2020di/protocol/map_go.png"));
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     */
    protected void toggleObjectsPanelVisibility() {
        setObjectsPanelVisible(!this.objectsPanel.isVisible());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  visible  DOCUMENT ME!
     */
    protected void setObjectsPanelVisible(final boolean visible) {
        this.objectsPanel.setVisible(visible);
        revalidate();
    }
}
