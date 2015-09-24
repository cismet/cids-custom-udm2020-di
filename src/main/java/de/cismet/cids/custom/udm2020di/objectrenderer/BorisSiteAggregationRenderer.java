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

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import de.cismet.cids.custom.udm2020di.actions.remote.BorisExportAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.boris.Probenparameter;
import de.cismet.cids.custom.udm2020di.types.boris.Standort;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRendererPanel;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public class BorisSiteAggregationRenderer extends CidsBeanAggregationRendererPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger logger = Logger.getLogger(BorisSiteAggregationRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private transient Collection<CidsBean> cidsBeans;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exportPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private de.cismet.cids.custom.udm2020di.widgets.MapPanel mapPanel;
    private javax.swing.JPanel messwertePanel;
    private javax.swing.JTable messwerteTable;
    private de.cismet.cids.custom.udm2020di.widgets.ParameterPanel parameterPanel;
    private de.cismet.cids.custom.udm2020di.widgets.ParameterSelectionPanel parameterSelectionPanel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form BorisSiteRenderer.
     */
    public BorisSiteAggregationRenderer() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of cidsBeans.
     *
     * @return  the value of cidsBeans
     */
    @Override
    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    /**
     * Set the value of cidsBeans.
     *
     * @param  cidsBeans  new value of cidsBeans
     */
    @Override
    public void setCidsBeans(final Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;
        this.init();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        messwertePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messwerteTable = new javax.swing.JTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));
        jTabbedPane1 = new javax.swing.JTabbedPane();
        infoPanel = new javax.swing.JPanel();
        parameterPanel = new de.cismet.cids.custom.udm2020di.widgets.ParameterPanel();
        mapPanel = new de.cismet.cids.custom.udm2020di.widgets.MapPanel();
        exportPanel = new javax.swing.JPanel();
        parameterSelectionPanel = new de.cismet.cids.custom.udm2020di.widgets.ParameterSelectionPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));

        messwertePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        messwertePanel.setLayout(new java.awt.GridBagLayout());

        messwerteTable.setBorder(javax.swing.BorderFactory.createLineBorder(
                javax.swing.UIManager.getDefaults().getColor("Table.dropLineColor")));
        messwerteTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] { "Parametername", "Datum der Probe", "Maximalwert", "Minimalwert" }) {

                Class[] types = new Class[] {
                        java.lang.String.class,
                        java.lang.Object.class,
                        java.lang.Float.class,
                        java.lang.Float.class
                    };
                boolean[] canEdit = new boolean[] { false, false, false, false };

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
        jScrollPane1.setViewportView(messwerteTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        messwertePanel.add(jScrollPane1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        messwertePanel.add(filler1, gridBagConstraints);

        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        infoPanel.setLayout(new java.awt.BorderLayout());

        parameterPanel.setMinimumSize(new java.awt.Dimension(200, 200));
        parameterPanel.setPreferredSize(new java.awt.Dimension(200, 300));
        infoPanel.add(parameterPanel, java.awt.BorderLayout.EAST);

        mapPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5),
                javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createTitledBorder(
                        org.openide.util.NbBundle.getMessage(
                            BorisSiteAggregationRenderer.class,
                            "BorisSiteAggregationRenderer.mapPanel.border.insideBorder.outsideBorder.title")),
                    javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)))); // NOI18N
        infoPanel.add(mapPanel, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                BorisSiteAggregationRenderer.class,
                "BorisSiteAggregationRenderer.infoPanel.TabConstraints.tabTitle_1"),
            infoPanel); // NOI18N

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

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(
                BorisSiteAggregationRenderer.class,
                "BorisSiteAggregationRenderer.exportPanel.TabConstraints.tabTitle_1_1"),
            exportPanel); // NOI18N

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    protected void init() {
        if ((cidsBeans != null) && !cidsBeans.isEmpty()) {
            logger.info("processing " + cidsBeans.size() + "cids beans");
            final Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        mapPanel.setCidsBeans(cidsBeans);

                        final TreeSet<String> parameterNamesSet = new TreeSet<String>();
                        final TreeSet<Parameter> parametersSet = new TreeSet<Parameter>();
                        final TreeSet<String> standortPks = new TreeSet<String>();

                        for (final CidsBean cidsBean : cidsBeans) {
                            try {
                                final Standort borisStandort = OracleImport.JSON_MAPPER.readValue(
                                        cidsBean.getProperty("src_content").toString(),
                                        Standort.class);

                                final ArrayList<String> parameterNames = new ArrayList<String>(
                                        borisStandort.getProbenparameter().size());
                                for (final Probenparameter probenparameter : borisStandort.getProbenparameter()) {
                                    parameterNames.add(probenparameter.getParameterName());
                                }

                                standortPks.add(borisStandort.getPk());
                                parameterNamesSet.addAll(parameterNames);
                                parametersSet.addAll(borisStandort.getProbenparameter());
                            } catch (Exception ex) {
                                logger.error("could not deserialize boris Standort JSON: " + ex.getMessage(), ex);
                                continue;
                            }
                        }

                        parameterPanel.setParameterNames(parameterNamesSet);
                        parameterSelectionPanel.setParameters(parametersSet);

                        final BorisExportAction borisExportAction = new BorisExportAction(
                                standortPks,
                                parameterSelectionPanel.getSelectedParameters());
                        parameterSelectionPanel.setExportAction(borisExportAction);
                    }
                };

            if (EventQueue.isDispatchThread()) {
                r.run();
            } else {
                EventQueue.invokeLater(r);
            }
        }
    }

    @Override
    public void dispose() {
        // mappingComponent.dispose();
    }

    @Override
    public String getTitle() {
        String desc = "";
        final Collection<CidsBean> beans = cidsBeans;
        if ((beans != null) && (beans.size() > 0)) {
            desc += beans.size() + " Boris Standorte ausgewählt";
        }
        return desc;
    }

    @Override
    public void setTitle(final String title) {
    }
}