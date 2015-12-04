/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

import java.util.Comparator;

import de.cismet.cids.custom.udm2020di.types.Tag;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class TagKeyComparator implements Comparator<Tag> {

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final Tag tag1, final Tag tag2) {
        return tag1.getKey().compareTo(tag2.getKey());
    }
}
