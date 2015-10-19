/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.udm2020di.widgets;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.AggregationValues;
import de.cismet.cids.custom.udm2020di.types.boris.Standort;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public class MaxParameterValueSelectionPanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PROP_SELECTEDVALUES = "selectedValues";

    //~ Instance fields --------------------------------------------------------

    protected final transient Collection<MaxParameterValuePanel> parameterValuePanels =
        new ArrayList<MaxParameterValuePanel>();

    protected final transient Collection<AggregationValue> aggregationValues = new ArrayList<AggregationValue>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMeButton;
    // End of variables declaration//GEN-END:variables

    private int selectedValues;

    private final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MaxValueSelectionPanel.
     */
    public MaxParameterValueSelectionPanel() {
        initComponents();
    }

    /**
     * Creates a new MaxParameterValueSelectionPanel object.
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public MaxParameterValueSelectionPanel(final Collection<AggregationValue> aggregationValues) {
        this();
        this.setAggregationValues(aggregationValues);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of selectedValues.
     *
     * @return  the value of selectedValues
     */
    public int getSelectedValues() {
        return selectedValues;
    }

    /**
     * Set the value of selectedValues.
     *
     * @param  selectedValues  new value of selectedValues
     */
    protected void setSelectedValues(final int selectedValues) {
        final int oldSelectedValues = this.selectedValues;
        this.selectedValues = selectedValues;
        propertyChangeSupport.firePropertyChange(PROP_SELECTEDVALUES, oldSelectedValues, selectedValues);
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param  listener  DOCUMENT ME!
     */
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public final void setAggregationValues(final Collection<AggregationValue> aggregationValues) {
        this.aggregationValues.clear();
        // ignore all aggregation values that do not map to a concrete pollutant
        for (final AggregationValue aggregationValue : aggregationValues) {
            if (!aggregationValue.getPollutantKey().equalsIgnoreCase("METPlus")
                        && !aggregationValue.getPollutantKey().equalsIgnoreCase("KWSplus")
                        && !aggregationValue.getPollutantKey().equalsIgnoreCase("PESTplus")
                        && !aggregationValue.getPollutantKey().equalsIgnoreCase("THGundLSSplus")
                        && !aggregationValue.getPollutantKey().equalsIgnoreCase("DNMplus")
                        && !aggregationValue.getPollutantKey().equalsIgnoreCase("SYSSplus")) {
                this.aggregationValues.add(aggregationValue);
            }
        }

        if ((this.aggregationValues != null) && !this.aggregationValues.isEmpty()) {
            // this.addParameterValuePanel();
            this.addMeButton.setEnabled(true);
        } else {
            this.addMeButton.setEnabled(false);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        final java.awt.GridBagConstraints gridBagConstraints;

        addMeButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        addMeButton.setFont(new java.awt.Font("Tahoma", 1, 14));      // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(addMeButton, "+"); // NOI18N
        addMeButton.setEnabled(false);
        addMeButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        addMeButton.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    addMeButtonActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(addMeButton, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void addMeButtonActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_addMeButtonActionPerformed
        this.addParameterValuePanel();
    }                                                                               //GEN-LAST:event_addMeButtonActionPerformed

    /**
     * DOCUMENT ME!
     */
    public void reset() {
        this.parameterValuePanels.clear();
        this.removeAll();
        this.initComponents();
        this.addMeButton.setEnabled(!this.aggregationValues.isEmpty());
        setSelectedValues(0);
    }

    /**
     * DOCUMENT ME!
     */
    protected void addParameterValuePanel() {
        remove(addMeButton);

        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;

        final MaxParameterValuePanel parameterValuePanel = new MaxParameterValuePanel(aggregationValues);
        this.add(parameterValuePanel, gridBagConstraints);
        this.parameterValuePanels.add(parameterValuePanel);

        final JButton removeMeButton = new javax.swing.JButton("-");
        removeMeButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        removeMeButton.setMargin(new java.awt.Insets(2, 8, 2, 8));
        if (this.parameterValuePanels.size() > 0) {
            removeMeButton.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(final java.awt.event.ActionEvent evt) {
                        parameterValuePanels.remove(parameterValuePanel);
                        remove(parameterValuePanel);
                        remove(removeMeButton);
                        removeMeButton.removeActionListener(this);
                        setSelectedValues(parameterValuePanels.size());
                        validate();
                        repaint();
                    }
                });
        } else {
            removeMeButton.setEnabled(false);
        }

        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.0;
        this.add(removeMeButton, gridBagConstraints);

        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        this.add(addMeButton, gridBagConstraints);
        setSelectedValues(parameterValuePanels.size());

        validate();
        repaint();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Float> getValues() {
        final Map<String, Float> values = new HashMap<String, Float>();
        for (final MaxParameterValuePanel parameterValuePanel : this.parameterValuePanels) {
            final SimpleEntry<String, Float> value = parameterValuePanel.getValue();
            values.put(value.getKey(), value.getValue());
        }

        return values;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            BasicConfigurator.configure();
            final Standort borisStandort = OracleImport.JSON_MAPPER.readValue(
                    MaxParameterValueSelectionPanel.class.getResourceAsStream(
                        "/de/cismet/cids/custom/udm2020di/testing/BorisStandort.json"),
                    Standort.class);

            final AggregationValues aggregationValues = new AggregationValues(borisStandort.getAggregationValues());

            aggregationValues.addAll(borisStandort.getAggregationValues());

            final MaxParameterValueSelectionPanel panel = new MaxParameterValueSelectionPanel(
                    borisStandort.getAggregationValues());

            panel.setAggregationValues(borisStandort.getAggregationValues());

            panel.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(final PropertyChangeEvent evt) {
                        System.out.println(evt.getNewValue() + " = " + panel.getSelectedValues());
                    }
                });

            final JFrame frame = new JFrame("MaxParameterValueSelectionPanel");
            frame.getContentPane().add(panel);
            frame.getContentPane().setPreferredSize(new Dimension(600, 400));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(MaxParameterValueSelectionPanel.class).fatal(ex.getMessage(), ex);
            System.exit(1);
        }
    }
}
