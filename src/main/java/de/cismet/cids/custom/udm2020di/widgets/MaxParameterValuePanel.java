/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.event.ItemEvent;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import de.cismet.cids.custom.udm2020di.types.AggregationValue;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MaxParameterValuePanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MaxParameterValuePanel.class);

    //~ Instance fields --------------------------------------------------------

    protected transient Collection<AggregationValue> aggregationValues;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbParameters;
    private de.cismet.cids.custom.udm2020di.widgets.MaxValuePanel maxValuePanel;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MaxParameterValuePanel.
     */
    public MaxParameterValuePanel() {
        initComponents();
    }

    /**
     * Creates a new MaxParameterValuePanel object.
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public MaxParameterValuePanel(final Collection<AggregationValue> aggregationValues) {
        initComponents();
        this.setAggregationValues(aggregationValues);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<AggregationValue> getAggregationValues() {
        return aggregationValues;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        cbParameters = new javax.swing.JComboBox();
        maxValuePanel = new de.cismet.cids.custom.udm2020di.widgets.MaxValuePanel();

        setLayout(new java.awt.GridBagLayout());

        cbParameters.setModel(new javax.swing.DefaultComboBoxModel(
                new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbParameters.setRenderer(new NameRenderer());
        cbParameters.addItemListener(new java.awt.event.ItemListener() {

                @Override
                public void itemStateChanged(final java.awt.event.ItemEvent evt) {
                    cbParametersItemStateChanged(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(cbParameters, gridBagConstraints);

        maxValuePanel.setBorder(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(maxValuePanel, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbParametersItemStateChanged(final java.awt.event.ItemEvent evt) { //GEN-FIRST:event_cbParametersItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            this.maxValuePanel.setAggregationValue((AggregationValue)evt.getItem());
        }
    }                                                                               //GEN-LAST:event_cbParametersItemStateChanged

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public final void setAggregationValues(final Collection<AggregationValue> aggregationValues) {
        this.aggregationValues = aggregationValues;
        final DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        for (final AggregationValue aggregationValue : this.aggregationValues) {
            comboBoxModel.addElement(aggregationValue);
        }

        this.cbParameters.setModel(comboBoxModel);
        this.cbParameters.setSelectedIndex(-1);
        this.cbParameters.setSelectedIndex(0);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map.Entry<String, Float> getValue() {
        if (cbParameters.getSelectedIndex() > -1) {
            return new SimpleEntry<String, Float>(
                    ((AggregationValue)this.cbParameters.getSelectedItem()).getPollutantKey(),
                    this.maxValuePanel.getValue());
        }

        return null;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.cbParameters.setEnabled(enabled);
        this.maxValuePanel.setEnabled(enabled);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  value  DOCUMENT ME!
     */
    public void setValue(final Map.Entry<String, Float> value) {
        if ((this.aggregationValues != null) && !this.aggregationValues.isEmpty()) {
            boolean selected = false;
            for (final AggregationValue aggregationValue : this.aggregationValues) {
                if (aggregationValue.getPollutantKey().equals(value.getKey())) {
                    this.cbParameters.setSelectedItem(aggregationValue);
                    this.maxValuePanel.setAggregationValue(aggregationValue);
                    this.maxValuePanel.setValue(value.getValue());
                    selected = true;
                    break;
                }
            }

            if (!selected) {
                LOGGER.warn("could not set value " + value.getValue() + " of parameter '" + value.getKey()
                            + "': parameter not found in list of "
                            + this.aggregationValues.size() + " aggregation values!");
            }
        } else {
            LOGGER.warn("could not select value " + value.getValue() + " of parameter '" + value.getKey()
                        + "': aggregation values list is empty!");
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    public static final class NameRenderer extends DefaultListCellRenderer {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   list          DOCUMENT ME!
         * @param   value         DOCUMENT ME!
         * @param   index         DOCUMENT ME!
         * @param   isSelected    DOCUMENT ME!
         * @param   cellHasFocus  DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component comp = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if ((comp instanceof JLabel) && (value instanceof AggregationValue)) {
                final JLabel label = (JLabel)comp;
                final AggregationValue obj = (AggregationValue)value;
                label.setText(obj.getPlainName());
            }

            return comp;
        }
    }
}
