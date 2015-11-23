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
public class DefaultGeometryFromCidsObjectPermissionProvider extends BasicGeometryFromCidsObjectPermissionProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Geometry getGeometry() {
        return (Geometry)cidsBean.getProperty("geometry.geo_field");
    }
}
