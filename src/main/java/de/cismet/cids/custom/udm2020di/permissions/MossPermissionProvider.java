/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.permissions;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class MossPermissionProvider extends DefaultGeometryFromCidsObjectPermissionProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MossPermissionProvider object.
     */
    public MossPermissionProvider() {
        LOGGER = org.apache.log4j.Logger.getLogger(
                MossPermissionProvider.class);
    }
}
