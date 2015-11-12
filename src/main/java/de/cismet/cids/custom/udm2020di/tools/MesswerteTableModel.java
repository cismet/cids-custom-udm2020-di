/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import de.cismet.cids.custom.udm2020di.types.AggregationValue;
import de.cismet.cids.custom.udm2020di.widgets.MesswerteTable;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MesswerteTableModel extends AbstractTableModel {

    //~ Static fields/initializers ---------------------------------------------

    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    protected static final Logger LOGGER = Logger.getLogger(MesswerteTableModel.class);
    public static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.getDefault());

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(6);
        NUMBER_FORMAT.setMinimumFractionDigits(0);
        NUMBER_FORMAT.setMinimumIntegerDigits(1);
    }

    //~ Instance fields --------------------------------------------------------

    private final AggregationValue[] aggregationValues;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MesswerteTableModel object.
     *
     * @param  aggregationValues  DOCUMENT ME!
     */
    public MesswerteTableModel(final AggregationValue[] aggregationValues) {
        this.aggregationValues = aggregationValues;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public int getRowCount() {
        return (aggregationValues != null) ? aggregationValues.length : 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public int getColumnCount() {
        return 6;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rowIndex     DOCUMENT ME!
     * @param   columnIndex  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        if ((aggregationValues != null)
                    && (rowIndex < aggregationValues.length)) {
            switch (columnIndex) {
                case 0: {
                    return aggregationValues[rowIndex].getPlainName();
                }
                case 1: {
                    return NUMBER_FORMAT.format(aggregationValues[rowIndex].getMaxValue());
                }
                case 2: {
                    return DATE_FORMAT.format(aggregationValues[rowIndex].getMaxDate());
                }
                case 3: {
                    return NUMBER_FORMAT.format(aggregationValues[rowIndex].getMinValue());
                }
                case 4: {
                    return DATE_FORMAT.format(aggregationValues[rowIndex].getMinDate());
                }
                case 5: {
                    return aggregationValues[rowIndex].getUnit();
                }
                default: {
                    return null;
                }
            }
        } else {
            LOGGER.warn("invalid row index: " + rowIndex);
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   column  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getColumnName(final int column) {
        switch (column) {
            case 0: {
                return NbBundle.getMessage(MesswerteTable.class,
                        "MesswerteTable.MesswerteTableModel.column_0");
            }
            case 1: {
                return NbBundle.getMessage(MesswerteTable.class,
                        "MesswerteTable.MesswerteTableModel.column_1");
            }
            case 2: {
                return NbBundle.getMessage(MesswerteTable.class,
                        "MesswerteTable.MesswerteTableModel.column_2");
            }
            case 3: {
                return NbBundle.getMessage(MesswerteTable.class,
                        "MesswerteTable.MesswerteTableModel.column_3");
            }
            case 4: {
                return NbBundle.getMessage(MesswerteTable.class,
                        "MesswerteTable.MesswerteTableModel.column_4");
            }
            case 5: {
                return NbBundle.getMessage(MesswerteTable.class,
                        "MesswerteTable.MesswerteTableModel.column_5");
            }
            default: {
                return "unbekannt";
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   columnIndex  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return String.class;
            }
            case 1: {
                return String.class;
            }
            case 2: {
                return String.class;
            }
            case 3: {
                return String.class;
            }
            case 4: {
                return String.class;
            }
            case 5: {
                return String.class;
            }
            default: {
                return String.class;
            }
        }
    }
}
