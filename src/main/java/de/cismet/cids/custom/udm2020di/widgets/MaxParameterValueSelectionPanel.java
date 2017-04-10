/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Validator;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.types.AggregationValues;
import de.cismet.cids.custom.udm2020di.types.boris.Standort;

import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MaxParameterValueSelectionPanel extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(MaxParameterValueSelectionPanel.class);

    public static final String PROP_SELECTEDVALUES = "selectedValues";

    public static final String PROP_MINDATE = "minDate";

    public static final String PROP_MAXDATE = "maxDate";

    //~ Instance fields --------------------------------------------------------

    protected final transient Collection<MaxParameterValuePanel> parameterValuePanels =
        new ArrayList<MaxParameterValuePanel>();

    protected transient AggregationValues aggregationValues = new AggregationValues();

    private int selectedValues = 0;

    private final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    // private final MaxDateValidator maxDateValidator = new MaxDateValidator();

    private Date minDate;

    private Date maxDate;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMeButton;
    private javax.swing.JPanel datePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.toedter.calendar.JDateChooser jdcEndDate;
    private com.toedter.calendar.JDateChooser jdcStartDate;
    private javax.swing.JPanel parametersPanel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MaxValueSelectionPanel.
     */
    public MaxParameterValueSelectionPanel() {
        initComponents();
        datePanel.setVisible(false);
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
     * @param  minDate  DOCUMENT ME!
     * @param  maxDate  DOCUMENT ME!
     */
    protected void initDate(final Date minDate, final Date maxDate) {
        final boolean dateEnabled = (((minDate
                                != null)
                            && (maxDate
                                != null))
                        && (minDate.compareTo(
                                maxDate)
                            != 0));
        // this.addParameterValuePanel();
        this.setMinDate(
            minDate);
        this.setMaxDate(
            maxDate);
        if (!dateEnabled) {
            final String message = "no valid / distinct aggregation values start and end dates provided: \n"
                        + " startDate = "
                        + minDate + " \n"
                        + " endDate = "
                        + maxDate;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(message);
            }
            this.jdcStartDate.setEnabled(false);
            this.jdcEndDate.setEnabled(false);
            this.datePanel.setVisible(false);
            this.validate();
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("showing date chooser with \n"
                            + " startDate = "
                            + minDate + " \n"
                            + " endDate = "
                            + maxDate);
            }
            this.jdcStartDate.setMinSelectableDate(minDate);
            this.jdcStartDate.setMaxSelectableDate(maxDate);
            this.jdcEndDate.setMinSelectableDate(minDate);
            this.jdcEndDate.setMaxSelectableDate(maxDate);
            this.jdcStartDate.setEnabled(true);
            this.jdcEndDate.setEnabled(true);
            this.datePanel.setVisible(true);
            this.validate();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationValueCollection  DOCUMENT ME!
     */
    public final void setAggregationValues(final Collection<AggregationValue> aggregationValueCollection) {
        final AggregationValues tmpAggregationValues = new AggregationValues();
        tmpAggregationValues.addAll(aggregationValueCollection);
        this.setAggregationValues(tmpAggregationValues);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public final void setAggregationValues(final AggregationValues aggregationValues) {
        if (!SwingUtilities.isEventDispatchThread()) {
            LOGGER.warn("setAggregationValues not called from EDT!");
        }

        this.aggregationValues = aggregationValues;
        if ((this.aggregationValues != null)
                    && !this.aggregationValues.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("generating parameter selection panels for " + this.aggregationValues.size()
                            + " parameters");
            }

            this.addMeButton.setEnabled(true);
            this.initDate(aggregationValues.getMinDate(), aggregationValues.getMaxDate());
        } else {
            this.addMeButton.setEnabled(false);
            this.jdcStartDate.setEnabled(false);
            this.jdcEndDate.setEnabled(false);
            this.datePanel.setVisible(false);
            this.validate();
            LOGGER.warn("no valid aggregation values provided");
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        parametersPanel = new javax.swing.JPanel();
        addMeButton = new javax.swing.JButton();
        datePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jdcStartDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jdcEndDate = new com.toedter.calendar.JDateChooser();

        setLayout(new java.awt.BorderLayout());

        parametersPanel.setLayout(new java.awt.GridBagLayout());

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
        parametersPanel.add(addMeButton, gridBagConstraints);

        add(parametersPanel, java.awt.BorderLayout.CENTER);

        datePanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel1,
            org.openide.util.NbBundle.getMessage(
                MaxParameterValueSelectionPanel.class,
                "MaxParameterValueSelectionPanel.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        datePanel.add(jLabel1, gridBagConstraints);

        jdcStartDate.setDateFormatString(org.openide.util.NbBundle.getMessage(
                MaxParameterValueSelectionPanel.class,
                "MaxParameterValueSelectionPanel.jdcStartDate.dateFormatString")); // NOI18N
        jdcStartDate.setMinimumSize(new java.awt.Dimension(100, 20));
        jdcStartDate.setOpaque(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${minDate}"),
                jdcStartDate,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${maxDate}"),
                jdcStartDate,
                org.jdesktop.beansbinding.BeanProperty.create("maxSelectableDate"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        datePanel.add(jdcStartDate, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(
            jLabel2,
            org.openide.util.NbBundle.getMessage(
                MaxParameterValueSelectionPanel.class,
                "MaxParameterValueSelectionPanel.jLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        datePanel.add(jLabel2, gridBagConstraints);

        jdcEndDate.setDateFormatString(org.openide.util.NbBundle.getMessage(
                MaxParameterValueSelectionPanel.class,
                "MaxParameterValueSelectionPanel.jdcEndDate.dateFormatString")); // NOI18N
        jdcEndDate.setMinimumSize(new java.awt.Dimension(100, 20));
        jdcEndDate.setOpaque(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${maxDate}"),
                jdcEndDate,
                org.jdesktop.beansbinding.BeanProperty.create("date"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${minDate}"),
                jdcEndDate,
                org.jdesktop.beansbinding.BeanProperty.create("minSelectableDate"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        datePanel.add(jdcEndDate, gridBagConstraints);

        add(datePanel, java.awt.BorderLayout.SOUTH);

        bindingGroup.bind();
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
        this.parametersPanel.removeAll();
        final GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        parametersPanel.add(addMeButton, gridBagConstraints);
        this.addMeButton.setEnabled((this.aggregationValues != null) && !this.aggregationValues.isEmpty());
        this.initDate(
            (this.aggregationValues != null) ? this.aggregationValues.getMinDate() : null,
            (this.aggregationValues != null) ? this.aggregationValues.getMaxDate() : null);
        setSelectedValues(0);
        this.validate();
    }

    /**
     * DOCUMENT ME!
     */
    protected void addParameterValuePanel() {
        this.parametersPanel.remove(addMeButton);

        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;

        final MaxParameterValuePanel parameterValuePanel = new MaxParameterValuePanel(aggregationValues);
        this.parametersPanel.add(parameterValuePanel, gridBagConstraints);
        this.parameterValuePanels.add(parameterValuePanel);

        final JButton removeMeButton = new javax.swing.JButton("-");
        removeMeButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        removeMeButton.setMargin(new java.awt.Insets(2, 8, 2, 8));
        if (this.parameterValuePanels.size() > 0) {
            removeMeButton.addActionListener(new java.awt.event.ActionListener() {

                    @Override
                    public void actionPerformed(final java.awt.event.ActionEvent evt) {
                        parameterValuePanels.remove(parameterValuePanel);
                        parametersPanel.remove(parameterValuePanel);
                        parametersPanel.remove(removeMeButton);
                        removeMeButton.removeActionListener(this);
                        setSelectedValues(parameterValuePanels.size());
                        parametersPanel.validate();
                        parametersPanel.repaint();
                    }
                });
        } else {
            removeMeButton.setEnabled(false);
        }

        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.0;
        this.parametersPanel.add(removeMeButton, gridBagConstraints);

        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        this.parametersPanel.add(addMeButton, gridBagConstraints);
        setSelectedValues(parameterValuePanels.size());

        validate();
        repaint();
    }

    /**
     * Inits the panel with pre-selected values.
     *
     * @param  values   DOCUMENT ME!
     * @param  minDate  DOCUMENT ME!
     * @param  maxDate  DOCUMENT ME!
     */
    public void setValues(
            final Map<String, Float> values,
            final Date minDate,
            final Date maxDate) {
        if ((this.aggregationValues != null) && !this.aggregationValues.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("selecting " + values.size() + " values with minDate: "
                            + minDate + " and maxDate:" + maxDate);
            }

            this.parametersPanel.removeAll();
            final SortedSet<Map.Entry<String, Float>> sortedValues = new TreeSet<Map.Entry<String, Float>>(
                    new Comparator<Map.Entry<String, Float>>() {

                        @Override
                        public int compare(final Map.Entry<String, Float> o1, final Map.Entry<String, Float> o2) {
                            return o1.getKey().compareTo(o2.getKey());
                        }
                    });

            sortedValues.addAll(values.entrySet());

            for (final Map.Entry<String, Float> value : sortedValues) {
                final GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.weightx = 1.0;
                gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = GridBagConstraints.RELATIVE;

                final MaxParameterValuePanel parameterValuePanel = new MaxParameterValuePanel(aggregationValues);
                parameterValuePanel.setValue(value);
                this.parametersPanel.add(parameterValuePanel, gridBagConstraints);
                this.parameterValuePanels.add(parameterValuePanel);

                final JButton removeMeButton = new javax.swing.JButton("-");
                removeMeButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
                removeMeButton.setMargin(new java.awt.Insets(2, 8, 2, 8));
                if (this.parameterValuePanels.size() > 0) {
                    removeMeButton.addActionListener(new java.awt.event.ActionListener() {

                            @Override
                            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                                parameterValuePanels.remove(parameterValuePanel);
                                parametersPanel.remove(parameterValuePanel);
                                parametersPanel.remove(removeMeButton);
                                removeMeButton.removeActionListener(this);
                                setSelectedValues(parameterValuePanels.size());
                                parametersPanel.validate();
                                parametersPanel.repaint();
                            }
                        });
                } else {
                    removeMeButton.setEnabled(false);
                }

                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                gridBagConstraints.fill = GridBagConstraints.NONE;
                gridBagConstraints.gridx = 1;
                gridBagConstraints.weightx = 0.0;
                this.parametersPanel.add(removeMeButton, gridBagConstraints);
            }

            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 0.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
            this.parametersPanel.add(addMeButton, gridBagConstraints);
            setSelectedValues(parameterValuePanels.size());

            validate();
            repaint();

            if (minDate != null) {
                this.setMinDate(minDate);
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("could not set minDate, date is null");
                }
            }

            if (maxDate != null) {
                this.setMaxDate(maxDate);
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("could not set maxDate, date is null");
                }
            }
        } else {
            LOGGER.warn("could not select " + values.size() + "' values: aggregation values list is empty!");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Float> getValues() {
        final Map<String, Float> values = new HashMap<String, Float>();
        for (final MaxParameterValuePanel parameterValuePanel : this.parameterValuePanels) {
            final Map.Entry<String, Float> value = parameterValuePanel.getValue();
            values.put(value.getKey(), value.getValue());
        }

        return values;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public AggregationValues getAggregationValues() {
        return aggregationValues;
    }

    /**
     * Get the value of minDate.
     *
     * @return  the value of minDate
     */
    public Date getMinDate() {
        return minDate;
    }

    /**
     * Set the value of minDate.
     *
     * @param  minDate  new value of minDate
     */
    public void setMinDate(final Date minDate) {
        final Date oldMinDate = this.minDate;
        this.minDate = minDate;
        propertyChangeSupport.firePropertyChange(PROP_MINDATE, oldMinDate, minDate);
    }

    /**
     * Get the value of maxDate.
     *
     * @return  the value of maxDate
     */
    public Date getMaxDate() {
        return maxDate;
    }

    /**
     * Set the value of maxDate.
     *
     * @param  maxDate  new value of maxDate
     */
    public void setMaxDate(final Date maxDate) {
        final Date oldMaxDate = this.maxDate;
        this.maxDate = maxDate;
        propertyChangeSupport.firePropertyChange(PROP_MAXDATE, oldMaxDate, maxDate);
    }
    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();
            final Standort borisStandort = OracleImport.JSON_MAPPER.readValue(
                    MaxParameterValueSelectionPanel.class.getResourceAsStream(
                        "/de/cismet/cids/custom/udm2020di/testing/BorisStandort.json"),
                    Standort.class);

            final AggregationValues aggregationValues = new AggregationValues(borisStandort.getAggregationValues());
            final MaxParameterValueSelectionPanel panel = new MaxParameterValueSelectionPanel();

            aggregationValues.addAll(borisStandort.getAggregationValues());
            panel.setAggregationValues(aggregationValues);

            final Map<String, Float> values = new HashMap<String, Float>();
            values.put("As", 0.029f);
            // values.put("Pb", 59f);
            // values.put("Zn", 5f);
            values.put("Cr", 30.0E-5f);
            panel.setValues(values, null, null);

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

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class MaxDateValidator extends Validator<Date> {

        //~ Methods ------------------------------------------------------------

        @Override
        public Result validate(final Date selectedMaxDate) {
            final boolean validMaxDate = (selectedMaxDate.compareTo(aggregationValues.getMaxDate()) <= 0);
            final boolean validMinDate = (selectedMaxDate.compareTo(aggregationValues.getMinDate()) >= 0);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("selectedMaxDate: " + selectedMaxDate
                            + " \n maxDate: " + aggregationValues.getMaxDate()
                            + " \n minDate: " + aggregationValues.getMinDate()
                            + " \n selectedMaxDate <= maxDate: " + validMaxDate
                            + " \n selectedMaxDate >= minDate: " + validMinDate);
            }

            if (!validMaxDate) {
                final String msg = "selectedMaxDate (" + selectedMaxDate + ") > maxDate ("
                            + aggregationValues.getMaxDate() + ")";
                LOGGER.warn(msg);
                System.err.println(msg);
                return new Result(null, msg);
            }

            if (!validMinDate) {
                final String msg = "selectedMaxDate (" + selectedMaxDate + ") < minDate ("
                            + aggregationValues.getMinDate() + ")";
                LOGGER.warn(msg);
                System.err.println(msg);
                return new Result(null, msg);
            }

            return null;
        }
    }
}
