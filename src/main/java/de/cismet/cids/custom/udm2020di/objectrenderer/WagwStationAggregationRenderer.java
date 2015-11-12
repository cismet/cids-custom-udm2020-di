/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.apache.log4j.Logger;

import org.openide.util.WeakListeners;

import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.custom.udm2020di.actions.remote.WaExportAction;
import de.cismet.cids.custom.udm2020di.actions.remote.WaVisualisationAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.tools.NameRenderer;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.AggregationValues;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.wa.Messstelle;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRendererPanel;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class WagwStationAggregationRenderer extends CidsBeanAggregationRendererPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static int SELECTED_TAB = 0;

    //~ Instance fields --------------------------------------------------------

    protected Logger logger = Logger.getLogger(WagwStationAggregationRenderer.class);

    protected String stationType = WaExportAction.WAGW;

    private transient Collection<CidsBean> cidsBeans;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel exportPanel;
    private javax.swing.JPanel featureSelectionPanel;
    private javax.swing.JList featuresList;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane;
    private de.cismet.cids.custom.udm2020di.widgets.MapPanel mapPanel;
    private de.cismet.cids.custom.udm2020di.widgets.MesswerteTable messwerteTable;
    private de.cismet.cids.custom.udm2020di.widgets.ParameterPanel parameterPanel;
    private de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel parameterSelectionPanel;
    private de.cismet.cids.custom.udm2020di.widgets.VisualisationPanel visualisationPanel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form WagwSiteRenderer.
     */
    public WagwStationAggregationRenderer() {
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

        parameterPanel = new de.cismet.cids.custom.udm2020di.widgets.ParameterPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        infoPanel = new javax.swing.JPanel();
        mapPanel = new de.cismet.cids.custom.udm2020di.widgets.MapPanel();
        featureSelectionPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        featuresList = new javax.swing.JList();
        messwerteTable = new de.cismet.cids.custom.udm2020di.widgets.MesswerteTable();
        exportPanel = new javax.swing.JPanel();
        parameterSelectionPanel = new de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));
        visualisationPanel = new de.cismet.cids.custom.udm2020di.widgets.VisualisationPanel();

        parameterPanel.setMinimumSize(new java.awt.Dimension(200, 300));

        setLayout(new java.awt.BorderLayout());

        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        infoPanel.setLayout(new java.awt.GridBagLayout());

        mapPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5),
                javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createTitledBorder(
                        org.openide.util.NbBundle.getMessage(
                            WagwStationAggregationRenderer.class,
                            "WagwStationAggregationRenderer.mapPanel.border.insideBorder.outsideBorder.title")),
                    javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)))); // NOI18N
        mapPanel.setMinimumSize(new java.awt.Dimension(300, 500));
        mapPanel.setPreferredSize(new java.awt.Dimension(300, 500));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1.0;
        infoPanel.add(mapPanel, gridBagConstraints);

        featureSelectionPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5),
                javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createTitledBorder(
                        org.openide.util.NbBundle.getMessage(
                            WagwStationAggregationRenderer.class,
                            "WagwStationAggregationRenderer.featureSelectionPanel.border.insideBorder.outsideBorder.title")),
                    javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)))); // NOI18N
        featureSelectionPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        featureSelectionPanel.setLayout(new java.awt.GridLayout(1, 0));

        featuresList.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        featuresList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        featuresList.setCellRenderer(new NameRenderer());
        featuresList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

                @Override
                public void valueChanged(final javax.swing.event.ListSelectionEvent evt) {
                    featuresListValueChanged(evt);
                }
            });
        jScrollPane2.setViewportView(featuresList);

        featureSelectionPanel.add(jScrollPane2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        infoPanel.add(featureSelectionPanel, gridBagConstraints);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                WagwStationAggregationRenderer.class,
                "WagwStationAggregationRenderer.infoPanel.TabConstraints.tabTitle_1"),
            infoPanel);      // NOI18N
        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                WagwStationAggregationRenderer.class,
                "WagwStationAggregationRenderer.messwerteTable.TabConstraints.tabTitle"),
            messwerteTable); // NOI18N

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
                WagwStationAggregationRenderer.class,
                "WagwStationAggregationRenderer.exportPanel.TabConstraints.tabTitle_1_1"),
            exportPanel); // NOI18N
        jTabbedPane.addTab("Datenvisualisierung", visualisationPanel);

        add(jTabbedPane, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void featuresListValueChanged(final javax.swing.event.ListSelectionEvent evt) { //GEN-FIRST:event_featuresListValueChanged
        if (!evt.getValueIsAdjusting()) {
            this.mapPanel.gotoCidsBean((CidsBean)this.featuresList.getSelectedValue());
        }
    }                                                                                       //GEN-LAST:event_featuresListValueChanged

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

                        final Collection<Messstelle> stations = new ArrayList<Messstelle>();
                        final TreeSet<Parameter> parametersSet = new TreeSet<Parameter>();
                        final TreeSet<String> messstellenPks = new TreeSet<String>();
                        final DefaultListModel listModel = new DefaultListModel();
                        final AggregationValues aggregationValues = new AggregationValues();

                        for (final CidsBean cidsBean : cidsBeans) {
                            listModel.addElement(cidsBean);

                            try {
                                final Messstelle messstelle = OracleImport.JSON_MAPPER.readValue(
                                        cidsBean.getProperty("src_content").toString(),
                                        Messstelle.class);
                                stations.add(messstelle);

                                final ArrayList<String> parameterNames = new ArrayList<String>(
                                        messstelle.getProbenparameter().size());
                                for (final Parameter probenparameter : messstelle.getProbenparameter()) {
                                    parameterNames.add(probenparameter.getParameterName());
                                }

                                messstellenPks.add(messstelle.getPk());

                                parametersSet.addAll(messstelle.getProbenparameter());

                                aggregationValues.addAll(messstelle.getAggregationValues());
                            } catch (Exception ex) {
                                logger.error("could not deserialize wagw Messstelle JSON: " + ex.getMessage(), ex);
                            }
                        }

                        featuresList.setModel(listModel);
                        // parameterPanel.setParameterNames(parameterNamesSet);

                        // Messwerte Tab -------------------------------
                        messwerteTable.setAggregationValues(
                            aggregationValues.toArray(
                                new AggregationValue[0]));

                        // Export Tab ------------------------------------------
                        parameterSelectionPanel.setParameters(parametersSet);
                        final WaExportAction waExportAction = new WaExportAction(
                                stationType,
                                messstellenPks,
                                parameterSelectionPanel.getSelectedParameters());
                        parameterSelectionPanel.setExportAction(waExportAction);

                        // Visualisation -------------------------------------------
                        visualisationPanel.setParameters(parametersSet);
                        final WaVisualisationAction visualisationAction = new WaVisualisationAction(
                                stationType,
                                stations,
                                visualisationPanel.getSelectedParameters(),
                                visualisationPanel);
                        visualisationPanel.setVisualisationAction(visualisationAction);

                        // selected TAB ----------------------------------------
                        jTabbedPane.setSelectedIndex(SELECTED_TAB);
                        jTabbedPane.addChangeListener(WeakListeners.create(
                                ChangeListener.class,
                                new ChangeListener() {

                                    @Override
                                    public void stateChanged(final ChangeEvent evt) {
                                        SELECTED_TAB = jTabbedPane.getSelectedIndex();
                                    }
                                },
                                jTabbedPane));
                    }
                };

            if (EventQueue.isDispatchThread()) {
                r.run();
            } else {
                EventQueue.invokeLater(r);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        // mappingComponent.dispose();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getTitle() {
        String desc = "";
        final Collection<CidsBean> beans = cidsBeans;
        if ((beans != null) && (beans.size() > 0)) {
            desc += beans.size() + " Grundwasser Messstellen ausgewählt";
        }
        return desc;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  title  DOCUMENT ME!
     */
    @Override
    public void setTitle(final String title) {
    }
}
