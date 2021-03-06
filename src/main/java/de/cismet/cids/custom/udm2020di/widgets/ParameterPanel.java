/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JLabel;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class ParameterPanel extends javax.swing.JPanel {

    //~ Instance fields --------------------------------------------------------

    protected transient SortedSet<String> parameterNames = new TreeSet<String>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form ParameterPanel.
     */
    public ParameterPanel() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of parameterNames.
     *
     * @return  the value of parameterNames
     */
    public Collection<String> getParameterNames() {
        return parameterNames;
    }

    /**
     * Set the value of parameterNames.
     *
     * @param  parameterNames  new value of parameterNames
     */
    public void setParameterNames(final Collection<String> parameterNames) {
        this.parameterNames.addAll(parameterNames);

        removeAll();
        if ((parameterNames != null) && !parameterNames.isEmpty()) {
            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 2);
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weighty = 0.0;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            int i = 0;
            for (final String parameterName : parameterNames) {
                gridBagConstraints.gridy = i;
                if (i == (parameterNames.size() - 1)) {
                    gridBagConstraints.weighty = 1.0;
                }
                final JLabel paramLabel = new JLabel("<html>" + parameterName + "</html>");
                // paramLabel.setMaximumSize(new Dimension(175, Integer.MAX_VALUE));
                // paramLabel.setMinimumSize(new Dimension(175, 21));
                add(paramLabel, gridBagConstraints);
                i++;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5),
                javax.swing.BorderFactory.createTitledBorder(
                    org.openide.util.NbBundle.getMessage(
                        ParameterPanel.class,
                        "ParameterPanel.border.insideBorder.title")))); // NOI18N
        setLayout(new java.awt.GridBagLayout());
    }                                                                   // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
