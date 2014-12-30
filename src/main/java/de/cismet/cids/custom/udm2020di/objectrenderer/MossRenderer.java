/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.ELProperty;

import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.cismet.cids.custom.udm2020di.AbstractCidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class MossRenderer extends AbstractCidsBeanRenderer {

    //~ Instance fields --------------------------------------------------------

    private final NumberFormatter nf;

    private final ImageIcon abietinellaAbietina128;
    private final ImageIcon hylocomiumSplendens128;
    private final ImageIcon hypnumCupressiforme128;
    private final ImageIcon pleuroziumSchreberi128;
    private final ImageIcon scleropodiumPurum128;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel14;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JLabel lblAl;
    private JLabel lblAlValue;
    private JLabel lblAs;
    private JLabel lblAsValue;
    private JLabel lblCd;
    private JLabel lblCdValue;
    private JLabel lblCo;
    private JLabel lblCoValue;
    private JLabel lblCr;
    private JLabel lblCrValue;
    private JLabel lblCu;
    private JLabel lblCuValue;
    private JLabel lblFe;
    private JLabel lblFeValue;
    private JLabel lblHg;
    private JLabel lblHgValue;
    private JLabel lblLabNo;
    private JLabel lblLabNoValue;
    private JLabel lblMo;
    private JLabel lblMoValue;
    private JLabel lblMossIcon;
    private JLabel lblMossTypeValue;
    private JLabel lblNTotal;
    private JLabel lblNTotalValue;
    private JLabel lblNi;
    private JLabel lblNiValue;
    private JLabel lblPb;
    private JLabel lblPbValue;
    private JLabel lblS;
    private JLabel lblSValue;
    private JLabel lblSampleId;
    private JLabel lblSampleIdValue;
    private JLabel lblSb;
    private JLabel lblSbValue;
    private JLabel lblV;
    private JLabel lblVValue;
    private JLabel lblZn;
    private JLabel lblZnValue;
    private BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MossRenderer.
     */
    public MossRenderer() {
        nf = new NumberFormatter();

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
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        bindingGroup = new BindingGroup();

        jPanel1 = new JPanel();
        jPanel4 = new JPanel();
        lblAl = new JLabel();
        lblAlValue = new JLabel();
        jPanel5 = new JPanel();
        lblAs = new JLabel();
        lblAsValue = new JLabel();
        jPanel6 = new JPanel();
        lblCd = new JLabel();
        lblCdValue = new JLabel();
        jPanel7 = new JPanel();
        lblCo = new JLabel();
        lblCoValue = new JLabel();
        jPanel8 = new JPanel();
        lblCr = new JLabel();
        lblCrValue = new JLabel();
        jPanel9 = new JPanel();
        lblCu = new JLabel();
        lblCuValue = new JLabel();
        jPanel10 = new JPanel();
        lblFe = new JLabel();
        lblFeValue = new JLabel();
        jPanel11 = new JPanel();
        lblMo = new JLabel();
        lblMoValue = new JLabel();
        jPanel12 = new JPanel();
        lblNi = new JLabel();
        lblNiValue = new JLabel();
        jPanel13 = new JPanel();
        lblPb = new JLabel();
        lblPbValue = new JLabel();
        jPanel14 = new JPanel();
        lblS = new JLabel();
        lblSValue = new JLabel();
        jPanel15 = new JPanel();
        lblV = new JLabel();
        lblVValue = new JLabel();
        jPanel16 = new JPanel();
        lblSb = new JLabel();
        lblSbValue = new JLabel();
        jPanel17 = new JPanel();
        lblZn = new JLabel();
        lblZnValue = new JLabel();
        jPanel18 = new JPanel();
        lblHg = new JLabel();
        lblHgValue = new JLabel();
        jPanel2 = new JPanel();
        jPanel19 = new JPanel();
        lblSampleId = new JLabel();
        lblSampleIdValue = new JLabel();
        jPanel20 = new JPanel();
        lblLabNo = new JLabel();
        lblLabNoValue = new JLabel();
        jPanel21 = new JPanel();
        lblNTotal = new JLabel();
        lblNTotalValue = new JLabel();
        jPanel22 = new JPanel();
        lblMossTypeValue = new JLabel();
        jPanel3 = new JPanel();
        lblMossIcon = new JLabel();

        setOpaque(false);
        setLayout(new GridBagLayout());

        jPanel1.setBorder(BorderFactory.createTitledBorder(
                NbBundle.getMessage(MossRenderer.class, "MossRenderer.jPanel1.border.title"))); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new GridLayout(5, 3, 10, 10));

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new GridBagLayout());

        lblAl.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblAl.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel4.add(lblAl, gridBagConstraints);

        lblAlValue.setHorizontalAlignment(SwingConstants.TRAILING);

        Binding binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.al_conv}"),
                lblAlValue,
                BeanProperty.create("text"),
                "al_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel4.add(lblAlValue, gridBagConstraints);

        jPanel1.add(jPanel4);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new GridBagLayout());

        lblAs.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblAs.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel5.add(lblAs, gridBagConstraints);

        lblAsValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.as_conv}"),
                lblAsValue,
                BeanProperty.create("text"),
                "as_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel5.add(lblAsValue, gridBagConstraints);

        jPanel1.add(jPanel5);

        jPanel6.setOpaque(false);
        jPanel6.setLayout(new GridBagLayout());

        lblCd.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblCd.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel6.add(lblCd, gridBagConstraints);

        lblCdValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.cd_conv}"),
                lblCdValue,
                BeanProperty.create("text"),
                "cd_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel6.add(lblCdValue, gridBagConstraints);

        jPanel1.add(jPanel6);

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new GridBagLayout());

        lblCo.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblCo.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel7.add(lblCo, gridBagConstraints);

        lblCoValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.co_conv}"),
                lblCoValue,
                BeanProperty.create("text"),
                "co_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel7.add(lblCoValue, gridBagConstraints);

        jPanel1.add(jPanel7);

        jPanel8.setOpaque(false);
        jPanel8.setLayout(new GridBagLayout());

        lblCr.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblCr.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel8.add(lblCr, gridBagConstraints);

        lblCrValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.cr_conv}"),
                lblCrValue,
                BeanProperty.create("text"),
                "cr_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel8.add(lblCrValue, gridBagConstraints);

        jPanel1.add(jPanel8);

        jPanel9.setOpaque(false);
        jPanel9.setLayout(new GridBagLayout());

        lblCu.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblCu.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel9.add(lblCu, gridBagConstraints);

        lblCuValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.cu_conv}"),
                lblCuValue,
                BeanProperty.create("text"),
                "cu_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel9.add(lblCuValue, gridBagConstraints);

        jPanel1.add(jPanel9);

        jPanel10.setOpaque(false);
        jPanel10.setLayout(new GridBagLayout());

        lblFe.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblFe.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel10.add(lblFe, gridBagConstraints);

        lblFeValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.fe_conv}"),
                lblFeValue,
                BeanProperty.create("text"),
                "fe_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel10.add(lblFeValue, gridBagConstraints);

        jPanel1.add(jPanel10);

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new GridBagLayout());

        lblMo.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblMo.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel11.add(lblMo, gridBagConstraints);

        lblMoValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.mo_conv}"),
                lblMoValue,
                BeanProperty.create("text"),
                "mo_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel11.add(lblMoValue, gridBagConstraints);

        jPanel1.add(jPanel11);

        jPanel12.setOpaque(false);
        jPanel12.setLayout(new GridBagLayout());

        lblNi.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblNi.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel12.add(lblNi, gridBagConstraints);

        lblNiValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.ni_conv}"),
                lblNiValue,
                BeanProperty.create("text"),
                "ni_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel12.add(lblNiValue, gridBagConstraints);

        jPanel1.add(jPanel12);

        jPanel13.setOpaque(false);
        jPanel13.setLayout(new GridBagLayout());

        lblPb.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblPb.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel13.add(lblPb, gridBagConstraints);

        lblPbValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.pb_conv}"),
                lblPbValue,
                BeanProperty.create("text"),
                "pb_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel13.add(lblPbValue, gridBagConstraints);

        jPanel1.add(jPanel13);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(new GridBagLayout());

        lblS.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblS.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel14.add(lblS, gridBagConstraints);

        lblSValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.s_conv}"),
                lblSValue,
                BeanProperty.create("text"),
                "s_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel14.add(lblSValue, gridBagConstraints);

        jPanel1.add(jPanel14);

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new GridBagLayout());

        lblV.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblV.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel15.add(lblV, gridBagConstraints);

        lblVValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.v_conv}"),
                lblVValue,
                BeanProperty.create("text"),
                "v_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel15.add(lblVValue, gridBagConstraints);

        jPanel1.add(jPanel15);

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new GridBagLayout());

        lblSb.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblSb.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel16.add(lblSb, gridBagConstraints);

        lblSbValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.sb_conv}"),
                lblSbValue,
                BeanProperty.create("text"),
                "sb_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel16.add(lblSbValue, gridBagConstraints);

        jPanel1.add(jPanel16);

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new GridBagLayout());

        lblZn.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblZn.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel17.add(lblZn, gridBagConstraints);

        lblZnValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.zn_conv}"),
                lblZnValue,
                BeanProperty.create("text"),
                "zn_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel17.add(lblZnValue, gridBagConstraints);

        jPanel1.add(jPanel17);

        jPanel18.setOpaque(false);
        jPanel18.setLayout(new GridBagLayout());

        lblHg.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblHg.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel18.add(lblHg, gridBagConstraints);

        lblHgValue.setHorizontalAlignment(SwingConstants.TRAILING);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.hg_conv}"),
                lblHgValue,
                BeanProperty.create("text"),
                "hg_conv");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel18.add(lblHgValue, gridBagConstraints);

        jPanel1.add(jPanel18);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        add(jPanel1, gridBagConstraints);

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new GridLayout(1, 4, 5, 5));

        jPanel19.setOpaque(false);
        jPanel19.setLayout(new GridBagLayout());

        lblSampleId.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblSampleId.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel19.add(lblSampleId, gridBagConstraints);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.sample_id}"),
                lblSampleIdValue,
                BeanProperty.create("text"),
                "sample_id");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel19.add(lblSampleIdValue, gridBagConstraints);

        jPanel2.add(jPanel19);

        jPanel20.setOpaque(false);
        jPanel20.setLayout(new GridBagLayout());

        lblLabNo.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblLabNo.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel20.add(lblLabNo, gridBagConstraints);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.lab_no}"),
                lblLabNoValue,
                BeanProperty.create("text"),
                "lab_no");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel20.add(lblLabNoValue, gridBagConstraints);

        jPanel2.add(jPanel20);

        jPanel21.setOpaque(false);
        jPanel21.setLayout(new GridBagLayout());

        lblNTotal.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblNTotal.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel21.add(lblNTotal, gridBagConstraints);

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.n_total}"),
                lblNTotalValue,
                BeanProperty.create("text"),
                "n_total");
        binding.setConverter(nf);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel21.add(lblNTotalValue, gridBagConstraints);

        jPanel2.add(jPanel21);

        jPanel22.setBorder(BorderFactory.createTitledBorder(
                NbBundle.getMessage(MossRenderer.class, "MossRenderer.jPanel22.border.title"))); // NOI18N
        jPanel22.setOpaque(false);
        jPanel22.setPreferredSize(new Dimension(217, 170));
        jPanel22.setLayout(new GridBagLayout());

        binding = Bindings.createAutoBinding(
                UpdateStrategy.READ_WRITE,
                this,
                ELProperty.create("${cidsBean.moss_type.type}"),
                lblMossTypeValue,
                BeanProperty.create("text"),
                "moss_type");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        jPanel22.add(lblMossTypeValue, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new BorderLayout());

        lblMossIcon.setIcon(new ImageIcon(
                getClass().getResource(
                    "/de/cismet/cids/custom/udm2020di/objectrenderer/hylocomium_splendens_128.png"))); // NOI18N
        lblMossIcon.setText(NbBundle.getMessage(MossRenderer.class, "MossRenderer.lblMossIcon.text")); // NOI18N
        jPanel3.add(lblMossIcon, BorderLayout.CENTER);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        jPanel22.add(jPanel3, gridBagConstraints);

        jPanel2.add(jPanel22);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(jPanel2, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    @Override
    protected void init() {
        bindingGroup.unbind();
        bindingGroup.bind();

        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    final String mossType = (String)cidsBean.getProperty("moss_type.type");
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
                }
            };

        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class NumberFormatter extends Converter<Number, String> {

        //~ Instance fields ----------------------------------------------------

        private final DecimalFormat format;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new NumberFormatter object.
         */
        private NumberFormatter() {
            format = new DecimalFormat("0.00000 mg/kg"); // NOI18N
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final Number s) {
            if (s == null) {
                return "<null> mg/kg";
            } else {
                return format.format(s);
            }
        }

        @Override
        public Number convertReverse(final String t) {
            throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
                                                                           // choose Tools | Templates.
        }
    }
}
