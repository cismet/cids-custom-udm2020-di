/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.apache.log4j.Logger;

import java.awt.EventQueue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import de.cismet.cids.custom.udm2020di.AbstractCidsBeanRenderer;
import de.cismet.cids.custom.udm2020di.actions.remote.EprtrExportAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.eprtr.Installation;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class EprtrInstallationRenderer extends AbstractCidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger logger = Logger.getLogger(EprtrInstallationRenderer.class);
    protected static final DateFormat dateFormat = new SimpleDateFormat("YYYY");

    //~ Instance fields --------------------------------------------------------

    private Installation eprtrInstallation;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exportPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JPanel installationPanel;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JPanel messwertePanel;
    private javax.swing.JTable messwerteTable;
    private de.cismet.cids.custom.udm2020di.widgets.ParameterPanel parameterPanel;
    private de.cismet.cids.custom.udm2020di.widgets.ParameterSelectionPanel parameterSelectionPanel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form EprtrInstallationRenderer.
     */
    public EprtrInstallationRenderer() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Installation getEprtrInstallation() {
        return eprtrInstallation;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  eprtrInstallation  DOCUMENT ME!
     */
    public void setEprtrInstallation(final Installation eprtrInstallation) {
        this.eprtrInstallation = eprtrInstallation;
    }

    @Override
    protected void init() {
        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    try {
                        EprtrInstallationRenderer.this.eprtrInstallation = OracleImport.JSON_MAPPER.readValue(
                                EprtrInstallationRenderer.this.getCidsBean().getProperty("src_content").toString(),
                                Installation.class);
                    } catch (Exception ex) {
                        logger.error("could not deserialize EPRTR Installation JSON: " + ex.getMessage(), ex);
                        return;
                    }

                    // taskPane.setCollapsed(!firstPane);
                    // firstPane = false;
                    // taskPane.addPropertyChangeListener(new CollapseListener(taskPaneContainerDataAccess, taskPane));

// final GridBagConstraints gridBagConstraints = new GridBagConstraints();
// gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
// gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 2);
// gridBagConstraints.gridy = 0;
//
// // Standortparameter ---------------------------------------
// JLabel label;
// int i = 0;
// for (final Standortparameter standortparameter : borisStandort.getStandortparameter()) {
// if ((standortparameter.getWert() != null) && !standortparameter.getWert().isEmpty()
// && !standortparameter.getWert().equalsIgnoreCase("null")) {
// gridBagConstraints.gridy = i;
// gridBagConstraints.gridx = 0;
// gridBagConstraints.weightx = 0.0;
// label = new JLabel("<html><p>" + standortparameter.getName() + ": </p></html>");
// label.setMaximumSize(new Dimension(150, 50));
// standortdatenPanel.add(label, gridBagConstraints);
//
// if (i == (borisStandort.getStandortparameter().size() - 1)) {
// gridBagConstraints.weighty = 1.0;
// }
// gridBagConstraints.gridx = 1;
// gridBagConstraints.weightx = 1.0;
// label = new JLabel("<html><p>" + standortparameter.getWert() + "</p></html>");
// label.setMaximumSize(new Dimension(200, 50));
// standortdatenPanel.add(label, gridBagConstraints);
// i++;
// }
// }

                    // ParameterPanel ------------------------------------------
                    final ArrayList<String> parameterNames = new ArrayList<String>(
                            eprtrInstallation.getReleaseParameters().size());
                    for (final Parameter parameter : eprtrInstallation.getReleaseParameters()) {
                        parameterNames.add(parameter.getParameterName());
                    }
                    parameterPanel.setParameterNames(parameterNames);

                    // AggregationTable ----------------------------------------
                    final DefaultTableModel tableModel = (DefaultTableModel)messwerteTable.getModel();
                    for (final AggregationValue aggregationValue : eprtrInstallation.getAggregationValues()) {
                        final Object[] rowData = new Object[] {
                                aggregationValue.getReleaseType(),
                                aggregationValue.getName(),
                                aggregationValue.getMinValue(),
                                dateFormat.format(aggregationValue.getMinDate()),
                                aggregationValue.getMaxValue(),
                                dateFormat.format(aggregationValue.getMaxDate()),
                            };
                        tableModel.addRow(rowData);
                    }

                    // ParameterSelection (EXPORT) -----------------------------
                    parameterSelectionPanel.setParameters(
                        new ArrayList<Parameter>(eprtrInstallation.getReleaseParameters()));
                    final EprtrExportAction eprtrExportAction = new EprtrExportAction(
                            new long[] { eprtrInstallation.getErasId() },
                            parameterSelectionPanel.getSelectedParameters());
                    parameterSelectionPanel.setExportAction(eprtrExportAction);
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

        jTabbedPane = new javax.swing.JTabbedPane();
        infoPanel = new javax.swing.JPanel();
        installationPanel = new javax.swing.JPanel();
        parameterPanel = new de.cismet.cids.custom.udm2020di.widgets.ParameterPanel();
        messwertePanel = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        messwerteTable = new javax.swing.JTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));
        exportPanel = new javax.swing.JPanel();
        parameterSelectionPanel = new de.cismet.cids.custom.udm2020di.widgets.ParameterSelectionPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));

        setLayout(new java.awt.BorderLayout());

        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        infoPanel.setLayout(new java.awt.BorderLayout());

        installationPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        installationPanel.setLayout(new java.awt.GridLayout(2, 1, 5, 5));
        infoPanel.add(installationPanel, java.awt.BorderLayout.CENTER);

        parameterPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        infoPanel.add(parameterPanel, java.awt.BorderLayout.EAST);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.infoPanel.TabConstraints.tabTitle"),
            infoPanel); // NOI18N

        messwertePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messwertePanel.setLayout(new java.awt.GridBagLayout());

        messwerteTable.setBorder(javax.swing.BorderFactory.createLineBorder(
                javax.swing.UIManager.getDefaults().getColor("Table.dropLineColor")));
        messwerteTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                    "Art der Freisetzung",
                    "Schadstoff",
                    "Minimalwert [kg/J]",
                    "in Periode",
                    "Maximalwert [kg/J]",
                    "in Periode"
                }) {

                Class[] types = new Class[] {
                        java.lang.String.class,
                        java.lang.String.class,
                        java.lang.Float.class,
                        java.lang.String.class,
                        java.lang.Float.class,
                        java.lang.String.class
                    };
                boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

                @Override
                public Class getColumnClass(final int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
        messwerteTable.setFillsViewportHeight(true);
        messwerteTable.setPreferredSize(new java.awt.Dimension(300, 500));
        messwerteTable.setRequestFocusEnabled(false);
        jScrollPane.setViewportView(messwerteTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        messwertePanel.add(jScrollPane, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        messwertePanel.add(filler1, gridBagConstraints);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.messwertePanel.TabConstraints.tabTitle"),
            messwertePanel); // NOI18N

        exportPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        exportPanel.add(parameterSelectionPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        exportPanel.add(filler2, gridBagConstraints);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.exportPanel.TabConstraints.tabTitle"),
            exportPanel); // NOI18N

        add(jTabbedPane, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents
}
