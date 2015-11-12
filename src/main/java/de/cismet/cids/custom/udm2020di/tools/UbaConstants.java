/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public class UbaConstants {

    //~ Static fields/initializers ---------------------------------------------

    public static final String EPSG_WGS84 = "EPSG:4326";
    public static final String EPSG_UBA = EPSG_WGS84;
    public static final String WMS_DEMIS_WORLDMAP_GETMAP_TEMPLATE =
        "http://www2.demis.nl/WMS/wms.ashx?wms=WorldMap&&VERSION=1.1.0&REQUEST=GetMap&BBOX=<cismap:boundingBox>&WIDTH=<cismap:width>&HEIGHT=<cismap:height>&SRS=EPSG:4326&FORMAT=image/png&TRANSPARENT=TRUE&BGCOLOR=0xF0F0F0&EXCEPTIONS=application/vnd.ogc.se_xml&LAYERS=Bathymetry,Countries,Topography,Hillshading,Builtup%20areas,Coastlines,Waterbodies,Inundated,Rivers,Streams,Railroads,Highways,Roads,Trails,Borders,Cities,Settlements,Spot%20elevations,Airports,Ocean%20features&STYLES";
    public static final String WMS_BASEMAP_AT_GETMAP_TEMPLATE = UbaConstants.WMS_DEMIS_WORLDMAP_GETMAP_TEMPLATE;
}
