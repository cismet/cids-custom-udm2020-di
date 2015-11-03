/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import org.openide.util.WeakListeners;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import de.cismet.cids.custom.udm2020di.AbstractCidsBeanRenderer;
import de.cismet.cids.custom.udm2020di.actions.remote.EprtrExportAction;
import de.cismet.cids.custom.udm2020di.actions.remote.EprtrVisualisationAction;
import de.cismet.cids.custom.udm2020di.actions.remote.VisualisationAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.eprtr.Activity;
import de.cismet.cids.custom.udm2020di.types.eprtr.Address;
import de.cismet.cids.custom.udm2020di.types.eprtr.Installation;
import de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel;
import de.cismet.cids.custom.udm2020di.widgets.MaxParameterValueSelectionPanel;
import de.cismet.cids.custom.udm2020di.widgets.VisualisationParameterSelectionPanel;
import de.cismet.cids.custom.udm2020di.widgets.eprtr.ActivitiesPanel;
import de.cismet.cids.custom.udm2020di.widgets.eprtr.AddressPanel;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class EprtrInstallationRenderer extends AbstractCidsBeanRenderer {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(EprtrInstallationRenderer.class);
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY");
    protected static int SELECTED_TAB = 0;

    //~ Instance fields --------------------------------------------------------

    private Installation eprtrInstallation;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel activitiesPanel;
    private javax.swing.JScrollPane activitiesScrollPane;
    private javax.swing.JPanel exportPanel;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JPanel instPanel;
    private de.cismet.cids.custom.udm2020di.widgets.eprtr.InstallationPanel installationPanel;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JScrollPane messwerteScrollPane;
    private javax.swing.JTable messwerteTable;
    private javax.swing.JPanel notifPanel;
    private de.cismet.cids.custom.udm2020di.widgets.eprtr.NotificationsPanel notificationsPanel;
    private de.cismet.cids.custom.udm2020di.widgets.ParameterPanel parameterPanel;
    private de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel parameterSelectionPanel;
    private de.cismet.cids.custom.udm2020di.widgets.eprtr.EprtrVisualisationPanel visualisationPanel;
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

        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    // Installations -------------------------------------------
                    EprtrInstallationRenderer.this.installationPanel.setInstallation(
                        eprtrInstallation);

                    final Collection<Parameter> parameters = new ArrayList<Parameter>(
                            eprtrInstallation.getReleaseParameters());

                    // Addresses -----------------------------------------------
                    if (eprtrInstallation.getAddresses() != null) {
                        for (final Address address : eprtrInstallation.getAddresses()) {
                            final AddressPanel addressPanel = new AddressPanel();
                            addressPanel.setAddress(address);
                            EprtrInstallationRenderer.this.instPanel.add(addressPanel);
                        }
                    }
                    EprtrInstallationRenderer.this.instPanel.add(Box.createGlue());

                    // Activities
                    if (eprtrInstallation.getActivities() != null) {
                        final Map<String, Collection<Activity>> activitiesMap =
                            new TreeMap<String, Collection<Activity>>();

                        for (final Activity activity : eprtrInstallation.getActivities()) {
                            if (!activitiesMap.containsKey(activity.getNotificationPeriod())) {
                                activitiesMap.put(activity.getNotificationPeriod(),
                                    new ArrayList<Activity>());
                            }
                            activitiesMap.get(activity.getNotificationPeriod()).add(activity);
                        }

                        for (final String notificationPeriod : activitiesMap.keySet()) {
                            final ActivitiesPanel activitiesPanel = new ActivitiesPanel();
                            activitiesPanel.setNotificationPeriod(notificationPeriod);
                            activitiesPanel.setActivities(activitiesMap.get(notificationPeriod));
                            EprtrInstallationRenderer.this.activitiesPanel.add(activitiesPanel);
                        }
                    }
                    EprtrInstallationRenderer.this.activitiesPanel.add(Box.createGlue());

                    // Notifications
                    EprtrInstallationRenderer.this.notificationsPanel.setNotifications(
                        EprtrInstallationRenderer.this.getEprtrInstallation().getNotifications());

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
                                DATE_FORMAT.format(aggregationValue.getMinDate()),
                                aggregationValue.getMaxValue(),
                                DATE_FORMAT.format(aggregationValue.getMaxDate()),
                            };
                        tableModel.addRow(rowData);
                    }

                    // ParameterSelection (EXPORT) -----------------------------
                    parameterSelectionPanel.setParameters(parameters);
                    final EprtrExportAction eprtrExportAction = new EprtrExportAction(
                            Arrays.asList(new Long[] { eprtrInstallation.getErasId() }),
                            parameterSelectionPanel.getSelectedParameters());
                    parameterSelectionPanel.setExportAction(eprtrExportAction);

                    // Visualisation -------------------------------------------
                    visualisationPanel.setParameters(parameters);
                    final VisualisationAction visualisationAction = new EprtrVisualisationAction(
                            Arrays.asList(new Installation[] { eprtrInstallation }),
                            visualisationPanel.getSelectedParameters(),
                            visualisationPanel);
                    visualisationPanel.setVisualisationAction(visualisationAction);

                    // Saved TAB -----------------------------------------------
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("restoring selected tab index: " + SELECTED_TAB);
                    }
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

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void init() {
        try {
            final Installation installation = OracleImport.JSON_MAPPER.readValue(
                    this.getCidsBean().getProperty("src_content").toString(),
                    Installation.class);
            if (PropertyUtils.isReadable(this.getCidsBean(), "src_content_confidential")) {
                final Object src_content_confidential = this.getCidsBean().getProperty("src_content_confidential");
                if (src_content_confidential != null) {
                    final Installation confidentialInstallation = OracleImport.JSON_MAPPER.readValue(
                            this.getCidsBean().getProperty("src_content_confidential").toString(),
                            Installation.class);

                    LOGGER.info("showing confidential activity information");
                    if (confidentialInstallation.getActivities() != null) {
                        installation.setActivities(confidentialInstallation.getActivities());
                    }
                }
            }
            EprtrInstallationRenderer.this.setEprtrInstallation(installation);

            jTabbedPane.setSelectedIndex(SELECTED_TAB);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("setting saved tab: " + SELECTED_TAB);
            }

            jTabbedPane.addChangeListener(WeakListeners.create(
                    ChangeListener.class,
                    new ChangeListener() {

                        @Override
                        public void stateChanged(final ChangeEvent evt) {
                            SELECTED_TAB = jTabbedPane.getSelectedIndex();
                        }
                    },
                    jTabbedPane));
        } catch (Exception ex) {
            LOGGER.error("could not deserialize EPRTR Installation JSON: " + ex.getMessage(), ex);
            return;
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
        parameterPanel = new de.cismet.cids.custom.udm2020di.widgets.ParameterPanel();
        instPanel = new javax.swing.JPanel();
        installationPanel = new de.cismet.cids.custom.udm2020di.widgets.eprtr.InstallationPanel();
        notifPanel = new javax.swing.JPanel();
        notificationsPanel = new de.cismet.cids.custom.udm2020di.widgets.eprtr.NotificationsPanel();
        activitiesScrollPane = new javax.swing.JScrollPane();
        activitiesPanel = new javax.swing.JPanel();
        messwerteScrollPane = new javax.swing.JScrollPane();
        messwerteTable = new javax.swing.JTable();
        exportPanel = new javax.swing.JPanel();
        parameterSelectionPanel = new ExportParameterSelectionPanel(true);
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0),
                new java.awt.Dimension(0, 0),
                new java.awt.Dimension(32767, 32767));
        visualisationPanel = new de.cismet.cids.custom.udm2020di.widgets.eprtr.EprtrVisualisationPanel();

        setLayout(new java.awt.BorderLayout());

        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        infoPanel.setLayout(new java.awt.BorderLayout());

        parameterPanel.setMaximumSize(new java.awt.Dimension(175, 2147483647));
        parameterPanel.setPreferredSize(new java.awt.Dimension(175, 300));
        infoPanel.add(parameterPanel, java.awt.BorderLayout.EAST);

        instPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        instPanel.setLayout(new javax.swing.BoxLayout(instPanel, javax.swing.BoxLayout.PAGE_AXIS));
        instPanel.add(installationPanel);

        infoPanel.add(instPanel, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.infoPanel.TabConstraints.tabTitle"),
            infoPanel); // NOI18N

        notifPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        notifPanel.setLayout(new java.awt.BorderLayout());

        notificationsPanel.setLayout(new javax.swing.BoxLayout(notificationsPanel, javax.swing.BoxLayout.PAGE_AXIS));
        notifPanel.add(notificationsPanel, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.notifPanel.TabConstraints.tabTitle"),
            notifPanel); // NOI18N

        activitiesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        activitiesScrollPane.setPreferredSize(new java.awt.Dimension(400, 600));

        activitiesPanel.setLayout(new javax.swing.BoxLayout(activitiesPanel, javax.swing.BoxLayout.PAGE_AXIS));
        activitiesScrollPane.setViewportView(activitiesPanel);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.activitiesScrollPane.TabConstraints.tabTitle"),
            activitiesScrollPane); // NOI18N

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
        messwerteScrollPane.setViewportView(messwerteTable);

        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.messwerteScrollPane.TabConstraints.tabTitle"),
            messwerteScrollPane); // NOI18N

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

        jTabbedPane.addTab("Originaldatenexport", exportPanel);
        jTabbedPane.addTab(org.openide.util.NbBundle.getMessage(
                EprtrInstallationRenderer.class,
                "EprtrInstallationRenderer.visualisationPanel.TabConstraints.tabTitle"),
            visualisationPanel); // NOI18N

        add(jTabbedPane, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            BasicConfigurator.configure();
            final Installation eprtrInstallation = OracleImport.JSON_MAPPER.readValue(
                    MaxParameterValueSelectionPanel.class.getResourceAsStream(
                        "/de/cismet/cids/custom/udm2020di/testing/EprtrInstallation.json"),
                    Installation.class);

            final EprtrInstallationRenderer eprtrInstallationRenderer = new EprtrInstallationRenderer();
            eprtrInstallationRenderer.setEprtrInstallation(eprtrInstallation);
            // eprtrInstallationRenderer.init();

            final JFrame frame = new JFrame("EprtrInstallationRenderer");

            frame.getContentPane().add(eprtrInstallationRenderer);
            frame.getContentPane().setPreferredSize(new Dimension(600, 400));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(EprtrInstallationRenderer.class).fatal(ex.getMessage(), ex);
            System.exit(1);
        }
    }
}
