/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Converter;

import java.util.regex.Pattern;

import de.cismet.cids.custom.udm2020di.types.AggregationValue;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MaxValuePanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MaxValuePanel.class);

    //~ Instance fields --------------------------------------------------------

    public final Pattern UNIT_REGEX = Pattern.compile("(?<=\\[)[a-zA-Z0-9]*(?=\\])");
    protected AggregationValue aggregationValue = new AggregationValue();
    protected float factor = 1.0f;
    protected final FactorConverter factorConverter = new FactorConverter();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblUnit;
    private javax.swing.JLabel lblValue;
    private javax.swing.JSlider sldrMaxValue;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MaxValuePanel object.
     */
    public MaxValuePanel() {
        initComponents();
    }

    /**
     * Creates new form MaxValuePanel.
     *
     * @param  aggregationValue  DOCUMENT ME!
     */
    public MaxValuePanel(final AggregationValue aggregationValue) {
        this.aggregationValue = aggregationValue;
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public FactorConverter getFactorConverter() {
        return factorConverter;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public float getFactor() {
        return factor;
    }
    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public AggregationValue getAggregationValue() {
        return aggregationValue;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationValue  DOCUMENT ME!
     */
    public void setAggregationValue(final AggregationValue aggregationValue) {
        if (aggregationValue.getMaxValue() <= 1.0E-6f) {
            this.factor = 100000000.0f;
        } else if (aggregationValue.getMaxValue() <= 1.0E-5f) {
            this.factor = 10000000.0f;
        } else if (aggregationValue.getMaxValue() <= 1.0E-4f) {
            this.factor = 1000000.0f;
        } else if (aggregationValue.getMaxValue() <= 1.0E-3f) {
            this.factor = 100000.0f;
        } else if (aggregationValue.getMaxValue() <= 0.001f) {
            this.factor = 100000.0f;
        } else if (aggregationValue.getMaxValue() <= 0.01f) {
            this.factor = 10000.0f;
        } else if (aggregationValue.getMaxValue() <= 0.1f) {
            this.factor = 1000.0f;
        } else if (aggregationValue.getMaxValue() <= 1f) {
            this.factor = 100.0f;
        } else if (aggregationValue.getMaxValue() <= 100f) {
            this.factor = 10.0f;
        } else if (aggregationValue.getMaxValue() <= 1000f) {
            this.factor = 1f;
        } else if (aggregationValue.getMaxValue() <= 10000f) {
            this.factor = 0.01f;
        } else if (aggregationValue.getMaxValue() <= 100000f) {
            this.factor = 0.001f;
        } else {
            this.factor = 0.0001f;
        }

        this.bindingGroup.unbind();
        this.aggregationValue = aggregationValue;
        this.bindingGroup.bind();
        this.sldrMaxValue.setValue(this.getMinThreshold());
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getMaxThreshold() {
        return (int)Math.floor(aggregationValue.getMaxValue() * factor);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public int getMinThreshold() {
        return (int)Math.ceil(aggregationValue.getMinValue() * factor);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getParameterName() {
        if (this.aggregationValue != null) {
            this.aggregationValue.getPlainName();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getParameterUnit() {
        if (this.aggregationValue != null) {
            return this.aggregationValue.getUnit();
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public float getValue() {
        return this.sldrMaxValue.getValue() / this.factor;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value  DOCUMENT ME!
     */
    public void setValue(final float value) {
        final int convertedValue = Math.round(value * this.factor);

        if (convertedValue < this.getMinThreshold()) {
            LOGGER.warn("new value " + convertedValue + " (" + value + " x " + this.factor
                        + ") exceeds minimum threshold " + this.getMinThreshold() + "!");
        } else if (convertedValue > this.getMaxThreshold()) {
            LOGGER.warn("new value " + convertedValue + " (" + value + " x " + this.factor
                        + ") exceeds maximum threshold " + this.getMaxThreshold() + "!");
        } else {
            this.sldrMaxValue.setValue(convertedValue);
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.sldrMaxValue.setEnabled(enabled);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        sldrMaxValue = new javax.swing.JSlider();
        lblValue = new javax.swing.JLabel();
        lblUnit = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        sldrMaxValue.setPaintTicks(true);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_ONCE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${minThreshold}"),
                sldrMaxValue,
                org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_ONCE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${maxThreshold}"),
                sldrMaxValue,
                org.jdesktop.beansbinding.BeanProperty.create("maximum"));
        binding.setSourceNullValue(10);
        binding.setSourceUnreadableValue(10);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_ONCE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${minThreshold}"),
                sldrMaxValue,
                org.jdesktop.beansbinding.BeanProperty.create("minimum"));
        binding.setSourceNullValue(0);
        binding.setSourceUnreadableValue(0);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(sldrMaxValue, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                sldrMaxValue,
                org.jdesktop.beansbinding.ELProperty.create("${value}"),
                lblValue,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("0");       // NOI18N
        binding.setSourceUnreadableValue("0"); // NOI18N
        binding.setConverter(this.factorConverter);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblValue, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${parameterUnit}"),
                lblUnit,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        add(lblUnit, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    protected class FactorConverter extends Converter {

        //~ Methods ------------------------------------------------------------

        @Override
        public Object convertForward(final Object s) {
            return String.valueOf(MaxValuePanel.this.getValue());
        }

        @Override
        public Object convertReverse(final Object t) {
            return (int)MaxValuePanel.this.getValue();
        }
    }
}
