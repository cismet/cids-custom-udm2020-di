/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.awt.EventQueue;

import java.text.SimpleDateFormat;

import javax.swing.table.AbstractTableModel;

import de.cismet.cids.custom.udm2020di.tools.MesswerteTableModel;
import de.cismet.cids.custom.udm2020di.types.AggregationValue;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MesswerteTable extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MesswerteTable.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTable messwerteTable;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MesswerteTable.
     */
    public MesswerteTable() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public void setAggregationValues(final AggregationValue[] aggregationValues) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Initializing MesswerteTableModel with "
                        + aggregationValues.length + " aggregation Values");
        }

        final MesswerteTableModel messwerteTableModel = new MesswerteTableModel(aggregationValues);
        this.setModel(messwerteTableModel);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  messwerteTableModel  DOCUMENT ME!
     */
    public void setModel(final MesswerteTableModel messwerteTableModel) {
        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    messwerteTable.setModel(messwerteTableModel);
                }
            };

        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane = new javax.swing.JScrollPane();
        messwerteTable = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        messwerteTable.setBorder(javax.swing.BorderFactory.createLineBorder(
                javax.swing.UIManager.getDefaults().getColor("Table.dropLineColor")));
        messwerteTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {
                    "Parametername",
                    "Maximalwert",
                    "gemessen am",
                    "Minimalwert",
                    "gemessen am",
                    "Einheit"
                }) {

                Class[] types = new Class[] {
                        java.lang.String.class,
                        java.lang.Float.class,
                        java.lang.String.class,
                        java.lang.Float.class,
                        java.lang.String.class,
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
        messwerteTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(messwerteTable);

        add(jScrollPane, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents
}