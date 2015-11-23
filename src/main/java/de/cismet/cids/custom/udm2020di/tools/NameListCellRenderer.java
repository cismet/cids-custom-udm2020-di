/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public final class NameListCellRenderer extends DefaultListCellRenderer {

    //~ Methods ----------------------------------------------------------------

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

        if ((comp instanceof JLabel) && (value instanceof CidsBean)) {
            final JLabel label = (JLabel)comp;
            final CidsBean obj = (CidsBean)value;
            final String name = (String)obj.getProperty("name"); // NOI18N
            label.setText(name);
        }

        return comp;
    }
}
