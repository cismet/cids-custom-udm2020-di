/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;

import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.custom.udm2020di.AbstractCidsBeanRenderer;
import de.cismet.cids.custom.udm2020di.actions.remote.MossExportAction;
import de.cismet.cids.custom.udm2020di.actions.remote.MossVisualisationAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.tools.DefaultRendererConfigurationHelper;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.moss.Moss;
import de.cismet.cids.custom.udm2020di.widgets.MesswerteTable;
import de.cismet.cids.custom.udm2020di.widgets.ParameterPanel;
import de.cismet.cids.custom.udm2020di.widgets.VisualisationPanel;
import de.cismet.cids.custom.udm2020di.widgets.moss.MossParameterSelectionPanel;

import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class MossRenderer extends AbstractCidsBeanRenderer implements ConfigurableRenderer {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(BorisSiteRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private final ImageIcon abietinellaAbietina128;
    private final ImageIcon hylocomiumSplendens128;
    private final ImageIcon hypnumCupressiforme128;
    private final ImageIcon pleuroziumSchreberi128;
    private final ImageIcon scleropodiumPurum128;

    private Moss moss;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel exportPanel;
    private Box.Filler filler;
    private JPanel infoPanel;
    private JTabbedPane jTabbedPane;
    private JLabel lblLabNo;
    private JLabel lblLabNoValue;
    private JLabel lblMossIcon;
    private JLabel lblSampleId;
    private JLabel lblSampleIdValue;
    private MesswerteTable messwerteTable;
    private JPanel mossPanel;
    private JPanel mossTypePanel;
    private ParameterPanel parameterPanel;
    private MossParameterSelectionPanel parameterSelectionPanel;
    private VisualisationPanel visualisationPanel;
    private BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MossRenderer.
     */
    public MossRenderer() {
        abietinellaAbietina128 = ImageUtilities.loadImageIcon(MossRenderer.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/") + "/abietinella_abietina_128.png",
                false);
        hylocomiumSplendens128 = ImageUtilities.loadImageIcon(MossRenderer.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/") + "/hylocomium_splendens_128.png",
                false);
        hypnumCupressiforme128 = ImageUtilities.loadImageIcon(MossRenderer.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/") + "/hypnum_cupressiforme_128.png",
                false);
        pleuroziumSchreberi128 = ImageUtilities.loadImageIcon(MossRenderer.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/") + "/pleurozium_schreberi_128.png",
                false);
        scleropodiumPurum128 = ImageUtilities.loadImageIcon(MossRenderer.class.getPackage().getName().replaceAll(
                    "\\.",
                    "/") + "/scleropodium_purum_128.png",
                false);

        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of moss.
     *
     * @return  the value of moss
     */
    public Moss getMoss() {
        return moss;
    }

    /**
     * Set the value of moss.
     *
     * @param  moss  new value of moss
     */
    public void setMoss(final Moss moss) {
        this.moss = moss;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        bindingGroup = new BindingGroup();

        jTabbedPane = new JTabbedPane();
        infoPanel = new JPanel();
        mossPanel = new JPanel();
        mossTypePanel = new JPanel();
        lblMossIcon = new JLabel();
        lblSampleId = new JLabel();
        lblSampleIdValue = new JLabel();
        lblLabNo = new JLabel();
        lblLabNoValue = new JLabel();
        parameterPanel = new ParameterPanel();
        messwerteTable = new MesswerteTable();
        exportPanel = new JPanel();
        parameterSelectionPanel = new MossParameterSelectionPanel();
        filler = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(32767, 32767));
        visualisationPanel = new VisualisationPanel();

        setLayout(new BorderLayout());

        jTabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        infoPanel.setLayout(new BorderLayout());

        mossPanel.setOpaque(false);
        mossPanel.setLayout(new GridBagLayout());

        mossTypePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                    NbBundle.getMessage(MossRenderer.class, "MossRenderer.mossTypePanel.border.outsideBorder.title")),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // NOI18N
        mossTypePanel.setOpaque(false);
        mossTypePanel.setLayout(new BorderLayout());

        lblMossIcon.setIcon(new ImageIcon(
                getClass().getResource(
                    "/de/cismet/cids/custom/udm2020di/objectrenderer/hylocomium_splendens_128.png"))); // NOI18N
        lblMossIcon.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblMossIcon.text")); // NOI18N
        lblMossIcon.setRequestFocusEnabled(false);
        mossTypePanel.add(lblMossIcon, BorderLayout.CENTER);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        mossPanel.add(mossTypePanel, gridBagConstraints);

        lblSampleId.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblSampleId.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 15, 5, 5);
        mossPanel.add(lblSampleId, gridBagConstraints);

        Binding binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_ONCE,
                this,
                ELProperty.create("${moss.sampleId}"),
                lblSampleIdValue,
                BeanProperty.create("text"),
                "sample_id");
        binding.setSourceNullValue(" ");
        binding.setSourceUnreadableValue(" ");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(0, 5, 5, 5);
        mossPanel.add(lblSampleIdValue, gridBagConstraints);

        lblLabNo.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblLabNo.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 15, 5, 5);
        mossPanel.add(lblLabNo, gridBagConstraints);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_ONCE,
                this,
                ELProperty.create("${moss.labNo}"),
                lblLabNoValue,
                BeanProperty.create("text"),
                "lab_no");
        binding.setSourceNullValue(" ");
        binding.setSourceUnreadableValue(" ");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        mossPanel.add(lblLabNoValue, gridBagConstraints);

        infoPanel.add(mossPanel, BorderLayout.CENTER);

        parameterPanel.setMaximumSize(new Dimension(250, 2147483647));
        parameterPanel.setMinimumSize(new Dimension(250, 300));
        parameterPanel.setPreferredSize(new Dimension(250, 300));
        infoPanel.add(parameterPanel, BorderLayout.EAST);

        jTabbedPane.addTab(NbBundle.getMessage(MossRenderer.class, "MossRenderer.infoPanel.TabConstraints.tabTitle"),
            infoPanel); // NOI18N
        jTabbedPane.addTab("Aggregierte Messwerte", messwerteTable);

        exportPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        exportPanel.add(parameterSelectionPanel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        exportPanel.add(filler, gridBagConstraints);

        jTabbedPane.addTab(NbBundle.getMessage(MossRenderer.class, "MossRenderer.exportPanel.TabConstraints.tabTitle"),
            exportPanel); // NOI18N
        jTabbedPane.addTab("Datenvisualisierung", visualisationPanel);

        add(jTabbedPane, BorderLayout.CENTER);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     */
    @Override
    protected void init() {
        bindingGroup.unbind();

        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    if (moss == null) {
                        try {
                            moss = OracleImport.JSON_MAPPER.readValue(
                                    getCidsBean().getProperty("src_content").toString(),
                                    Moss.class);
                        } catch (Exception ex) {
                            LOGGER.error("could not deserialize boris Standort JSON: " + ex.getMessage(), ex);
                            return;
                        }
                    }

                    final Collection<Parameter> parameters = new ArrayList<Parameter>(
                            moss.getProbenparameter());

                    final String mossType = moss.getType();
                    mossTypePanel.setBorder(
                        BorderFactory.createCompoundBorder(
                            BorderFactory.createTitledBorder(mossType),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                    switch (mossType) {
                        case "Abietinella abietina": {
                            lblMossIcon.setIcon(abietinellaAbietina128);
                            break;
                        }
                        case "Hylocomium splendens": {
                            lblMossIcon.setIcon(hylocomiumSplendens128);
                            break;
                        }
                        case "Hypnum cupressiforme": {
                            lblMossIcon.setIcon(hypnumCupressiforme128);
                            break;
                        }
                        case "Pleurozium schreberi": {
                            lblMossIcon.setIcon(pleuroziumSchreberi128);
                            break;
                        }
                        case "Scleropodium purum": {
                            lblMossIcon.setIcon(scleropodiumPurum128);
                            break;
                        }
                        default: {
                            lblMossIcon.setIcon(null);
                        }
                    }

                    // ParameterPanel ------------------------------------------
                    final ArrayList<String> parameterNames = new ArrayList<String>(
                            parameters.size());
                    for (final Parameter probenparameter : parameters) {
                        parameterNames.add(probenparameter.getParameterName());
                    }
                    parameterPanel.setParameterNames(parameterNames);

                    // AggregationTable ----------------------------------------
                    messwerteTable.setAggregationValues(
                        moss.getAggregationValues().toArray(
                            new AggregationValue[0]));

                    // ParameterSelection (EXPORT) -----------------------------
                    parameterSelectionPanel.setParameters(parameters);
                    final MossExportAction exportAction = new MossExportAction(
                            parameterSelectionPanel.getSelectedParameters(),
                            Arrays.asList(new Long[] { getCidsBean().getPrimaryKeyValue().longValue() }),
                            Arrays.asList(new String[] { moss.getSampleId() }));
                    parameterSelectionPanel.setExportAction(exportAction);

                    // Visualisation -------------------------------------------
                    visualisationPanel.setParameters(parameters);
                    final MossVisualisationAction visualisationAction = new MossVisualisationAction(
                            Arrays.asList(new Moss[] { moss }),
                            visualisationPanel.getSelectedParameters(),
                            visualisationPanel);
                    visualisationPanel.setVisualisationAction(visualisationAction);

                    // Saved Configuration: Restore Export Parameters ----------
                    DefaultRendererConfigurationHelper.getInstance()
                            .restoreExportSettings(
                                MossRenderer.this,
                                jTabbedPane,
                                parameterSelectionPanel,
                                exportPanel,
                                LOGGER);

                    // Saved Configuration: Restore selected Tab ---------------
                    DefaultRendererConfigurationHelper.getInstance()
                            .restoreSelectedTab(
                                MossRenderer.class,
                                jTabbedPane,
                                LOGGER);

                    bindingGroup.bind();
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
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();
            final Moss moss = OracleImport.JSON_MAPPER.readValue(
                    MossRenderer.class.getResourceAsStream(
                        "/de/cismet/cids/custom/udm2020di/testing/Moss.json"),
                    Moss.class);

            final MossRenderer mossRenderer = new MossRenderer();
            mossRenderer.setMoss(moss);
            mossRenderer.init();

            final JFrame frame = new JFrame("MossRenderer");

            frame.getContentPane().add(mossRenderer);
            frame.getContentPane().setPreferredSize(new Dimension(600, 400));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(MossRenderer.class).fatal(ex.getMessage(), ex);
            System.exit(1);
        }
    }

    @Override
    public void showExportPanel(final Collection<Parameter> selectedParameters) {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    parameterSelectionPanel.setSelectedParameters(selectedParameters);
                    jTabbedPane.setSelectedComponent(exportPanel);
                }
            });
    }
}
