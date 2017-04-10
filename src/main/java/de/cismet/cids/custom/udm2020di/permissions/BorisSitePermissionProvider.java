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
public class BorisSitePermissionProvider extends DefaultGeometryFromCidsObjectPermissionProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisSitePermissionProvider object.
     */
    public BorisSitePermissionProvider() {
        LOGGER = org.apache.log4j.Logger.getLogger(
                BorisSitePermissionProvider.class);
    }
}
