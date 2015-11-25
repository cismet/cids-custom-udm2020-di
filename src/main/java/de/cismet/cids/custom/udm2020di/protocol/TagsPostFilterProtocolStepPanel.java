/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;
import Sirius.navigator.ui.ComponentRegistry;

import org.apache.log4j.Logger;

import org.openide.awt.Mnemonics;
import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.EventQueue;

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
public class TagsPostFilterProtocolStepPanel extends AbstractProtocolStepPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(TagsPostFilterProtocolStepPanel.class);

    //~ Instance fields --------------------------------------------------------

    protected TagsPostFilterProtocolStep protocolStep;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXHyperlink exportActionHyperlink;
    private org.jdesktop.swingx.JXHyperlink exportPanelHyperlink;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel objectInfoLabel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ExportActionProtocolStepPanel.
     *
     * @param  protocolStep  exportAction DOCUMENT ME!
     */
    public TagsPostFilterProtocolStepPanel(final TagsPostFilterProtocolStep protocolStep) {
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
    protected final void setProtocolStep(final TagsPostFilterProtocolStep protocolStep) {
        this.protocolStep = protocolStep;

        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    iconLabel.setIcon(TagsPostFilterProtocolStepPanel.this.protocolStep.getIcon());
                    titleLabel.setText(TagsPostFilterProtocolStepPanel.this.protocolStep.getTitle());

                    // overwrite title and icon provided by action object!
                    Mnemonics.setLocalizedText(
                        exportActionHyperlink,
                        NbBundle.getMessage(
                            TagsPostFilterProtocolStepPanel.class,
                            "TagsPostFilterProtocolStepPanel.exportActionHyperlink.text"));

//                    exportActionHyperlink.setIcon(protocolStep.getIcon()());
//                    exportActionHyperlink.setToolTipText(NbBundle.getMessage(TagsPostFilterProtocolStepPanel.class,
//                            "ExportActionProtocolStepPanel.exportActionHyperlink.toolTipText",
//                            TagsPostFilterProtocolStepPanel.this.exportAction.getExportFormat())); // NOI18N
//
//                    final String title = TagsPostFilterProtocolStepPanel.this.exportAction.getTitle();
//                    final String suffix;
//                    if (title.charAt(title.length() - 1) == 's') {
//                        suffix = "e";
//                    } else if (title.charAt(title.length() - 1) == 't') {
//                        suffix = "en";
//                    } else {
//                        suffix = "n";
//                    }
//
//                    objectInfoLabel.setText(String.valueOf(TagsPostFilterProtocolStepPanel.this.exportAction.getObjectIds().size())
//                                + ' '
//                                + title
//                                + ((TagsPostFilterProtocolStepPanel.this.exportAction.getObjectIds().size() > 1) ? suffix
//                                                                                                               : ""));
//                    Mnemonics.setLocalizedText(exportPanelHyperlink,
//                        NbBundle.getMessage(TagsPostFilterProtocolStepPanel.class,
//                            "ExportActionProtocolStepPanel.exportPanelHyperlink.text",
//                            String.valueOf(TagsPostFilterProtocolStepPanel.this.exportAction.getParameters().size()))); // NOI18N
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
        objectInfoLabel = new javax.swing.JLabel();
        exportActionHyperlink = new org.jdesktop.swingx.JXHyperlink();
        exportPanelHyperlink = new org.jdesktop.swingx.JXHyperlink();

        org.openide.awt.Mnemonics.setLocalizedText(
            iconLabel,
            org.openide.util.NbBundle.getMessage(
                TagsPostFilterProtocolStepPanel.class,
                "TagsPostFilterProtocolStepPanel.iconLabel.text")); // NOI18N
        iconLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.openide.awt.Mnemonics.setLocalizedText(
            titleLabel,
            org.openide.util.NbBundle.getMessage(
                TagsPostFilterProtocolStepPanel.class,
                "TagsPostFilterProtocolStepPanel.titleLabel.text")); // NOI18N

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            objectInfoLabel,
            org.openide.util.NbBundle.getMessage(
                TagsPostFilterProtocolStepPanel.class,
                "TagsPostFilterProtocolStepPanel.objectInfoLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(objectInfoLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            exportActionHyperlink,
            org.openide.util.NbBundle.getMessage(
                TagsPostFilterProtocolStepPanel.class,
                "TagsPostFilterProtocolStepPanel.exportActionHyperlink.text_1"));      // NOI18N
        exportActionHyperlink.setToolTipText(org.openide.util.NbBundle.getMessage(
                TagsPostFilterProtocolStepPanel.class,
                "TagsPostFilterProtocolStepPanel.exportActionHyperlink.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(exportActionHyperlink, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            exportPanelHyperlink,
            org.openide.util.NbBundle.getMessage(
                TagsPostFilterProtocolStepPanel.class,
                "TagsPostFilterProtocolStepPanel.exportPanelHyperlink.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        add(exportPanelHyperlink, gridBagConstraints);
    }                                                                          // </editor-fold>//GEN-END:initComponents

    @Override
    public Component getIconComponent() {
        return this.iconLabel;
    }

    @Override
    public Component getTitleComponent() {
        return this.titleLabel;
    }

//     try {
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("restoring export panel for " + this.exportAction.getObjectIds().size() + " objects");
//            }
//
//            final Map<String, Object> settings = new HashMap<String, Object>();
//            settings.put(PARAMETER_SETTINGS, this.exportAction.getParameters());
//            settings.put(EXPORT_FORMAT_SETTINGS, this.exportAction.getExportFormat());
//
//
//            RendererConfigurationRegistry.getInstance()
//                    .pushSettings(
//                        SessionManager.getSession().getConnectionInfo().getUserDomain(),
//                        this.exportAction.getClassId(),
//                        this.exportAction.getObjectIds().iterator().next().intValue(),
//                        settings);
//
//
//            ComponentRegistry.getRegistry()
//                    .getDescriptionPane()
//                    .gotoMetaObject(
//                        SessionManager.getProxy().getMetaClass(
//                            this.exportAction.getClassId(),
//                            SessionManager.getSession().getConnectionInfo().getUserDomain()),
//                        this.exportAction.getObjectIds().iterator().next().intValue(),
//                        null);
//
//            ComponentRegistry.getRegistry().showComponent(ComponentRegistry.DESCRIPTION_PANE);
//        } catch (ConnectionException ex) {
//            LOGGER.error(ex.getMessage(), ex);
//        }
}
