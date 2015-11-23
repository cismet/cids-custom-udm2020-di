/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets.moss;

import org.apache.log4j.Logger;

import java.awt.GridBagConstraints;

import java.util.Collection;

import javax.swing.JRadioButton;

import de.cismet.cids.custom.udm2020di.actions.remote.ExportAction;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MossParameterSelectionPanel extends ExportParameterSelectionPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MossParameterSelectionPanel.class);

    //~ Instance fields --------------------------------------------------------

    protected final JRadioButton rbtnXls = new JRadioButton();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ParameterSelectionPanel.
     */
    public MossParameterSelectionPanel() {
        super();
        this.initMossComponents();
    }

    /**
     * Creates a new ParameterSelectionPanel object.
     *
     * @param  parameters  DOCUMENT ME!
     */
    public MossParameterSelectionPanel(final Collection<Parameter> parameters) {
        super(parameters);
        this.initMossComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void initMossComponents() {
        exportButtonGroup.add(rbtnXls);
        rbtnXls.setText(org.openide.util.NbBundle.getMessage(
                MossParameterSelectionPanel.class,
                "MossParameterSelectionPanel.rbtnXls.text"));          // NOI18N
        rbtnXls.setToolTipText(org.openide.util.NbBundle.getMessage(
                MossParameterSelectionPanel.class,
                "MossParameterSelectionPanel.rbtnXls.toolTipText"));   // NOI18N
        rbtnXls.setActionCommand(org.openide.util.NbBundle.getMessage(
                MossParameterSelectionPanel.class,
                "MossParameterSelectionPanel.rbtnXls.actionCommand")); // NOI18N
        rbtnXls.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    final ExportAction exportAction = getExportAction();
                    if (exportAction != null) {
                        exportAction.setExportFormat(
                            de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_XLS);
                    }
                }
            });

        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        actionPanel.add(rbtnXls, gridBagConstraints);
    }

    @Override
    public void setExportFormat(final String exportFormat) {
        if (exportFormat.equals(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_XLS)) {
            this.rbtnXls.setSelected(true);
        } else {
            super.setExportFormat(exportFormat);
        }
    }

    @Override
    public void setExportFormatEnabled(final String exportFormat, final boolean enabled) {
        if (exportFormat.equals(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_XLS)) {
            this.rbtnXls.setEnabled(enabled);
        } else {
            super.setExportFormatEnabled(exportFormat, enabled);
        }
    }
}
