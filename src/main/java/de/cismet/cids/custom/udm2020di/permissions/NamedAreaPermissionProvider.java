/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.permissions;

import com.vividsolutions.jts.geom.Geometry;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class NamedAreaPermissionProvider extends BasicGeometryFromCidsObjectPermissionProvider {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new NamedAreaPermissionProvider object.
     */
    public NamedAreaPermissionProvider() {
        LOGGER = org.apache.log4j.Logger.getLogger(
                WagwStationPermissionProvider.class);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Geometry getGeometry() {
        return (Geometry)cidsBean.getProperty("area.geo_field");
    }
}
