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
public class GwkPermissionProvider extends DefaultGeometryFromCidsObjectPermissionProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new GwkPermissionProvider object.
     */
    public GwkPermissionProvider() {
        LOGGER = org.apache.log4j.Logger.getLogger(GwkPermissionProvider.class);
    }
}
