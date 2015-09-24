/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;

import org.apache.log4j.Logger;

import java.awt.EventQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.tools.UbaUtils;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.cismap.commons.Crs;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public class MapPanel extends javax.swing.JPanel implements CidsBeanCollectionStore {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOG = Logger.getLogger(MapPanel.class);
    public static final String WMS_DEMIS_WORLDMAP_GETMAP_TEMPLATE =
        "http://www2.demis.nl/WMS/wms.ashx?wms=WorldMap&&VERSION=1.1.0&REQUEST=GetMap&BBOX=<cismap:boundingBox>&WIDTH=<cismap:width>&HEIGHT=<cismap:height>&SRS=EPSG:4326&FORMAT=image/png&TRANSPARENT=TRUE&BGCOLOR=0xF0F0F0&EXCEPTIONS=application/vnd.ogc.se_xml&LAYERS=Bathymetry,Countries,Topography,Hillshading,Builtup%20areas,Coastlines,Waterbodies,Inundated,Rivers,Streams,Railroads,Highways,Roads,Trails,Borders,Cities,Settlements,Spot%20elevations,Airports,Ocean%20features&STYLES";

    private static final Double GEOMETRY_BUFFER = 5.0d;

    //~ Instance fields --------------------------------------------------------

    // private static final Double GEOMETRY_BUFFER_MULTIPLIER = 0.8d;
    private transient Map<CidsBean, CidsFeature> features;

    private transient Collection<CidsBean> cidsBeans;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cismap.commons.gui.MappingComponent mappingComponent;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MapPanel.
     */
    public MapPanel() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of cidsBeans.
     *
     * @return  the value of cidsBeans
     */
    @Override
    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    /**
     * Set the value of cidsBeans.
     *
     * @param  cidsBeans  new value of cidsBeans
     */
    @Override
    public void setCidsBeans(final Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;

        if ((this.cidsBeans != null) && !this.cidsBeans.isEmpty()) {
            final Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("adding " + cidsBeans.size() + " features to map");
                        }
                        features = new HashMap<CidsBean, CidsFeature>(cidsBeans.size());

                        initMap();
                    }
                };

            if (EventQueue.isDispatchThread()) {
                r.run();
            } else {
                EventQueue.invokeLater(r);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   deltaSurfaces  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected XBoundingBox boundingBoxFromPointList(final Collection<CidsBean> deltaSurfaces) {
        final List<Geometry> geometries = new ArrayList<Geometry>();

        for (final CidsBean deltaSurface : deltaSurfaces) {
            try {
                final Geometry geomUba = (Geometry)deltaSurface.getProperty("geom.geo_field");
                // final Geometry geomUba = CrsTransformer.transformToGivenCrs(geom.getEnvelope(), UbaUtils.EPSG_UBA);
                geometries.add(geomUba);
            } catch (Exception ex) {
                LOG.warn(ex, ex);
            }
        }

        final GeometryCollection geoCollection = new GeometryCollection(geometries.toArray(
                    new Geometry[geometries.size()]),
                new GeometryFactory());

        // TODO Buffer sollte nicht konstant sein!
        return new XBoundingBox(geoCollection.getEnvelope().buffer(GEOMETRY_BUFFER));
    }

    /**
     * DOCUMENT ME!
     */
    protected void initMap() {
        try {
            final XBoundingBox box = boundingBoxFromPointList(cidsBeans);

            final ActiveLayerModel mappingModel = new ActiveLayerModel();
            mappingModel.setSrs(new Crs(UbaUtils.EPSG_UBA, UbaUtils.EPSG_UBA, UbaUtils.EPSG_UBA, true, true));

            mappingModel.addHome(new XBoundingBox(
                    box.getX1(),
                    box.getY1(),
                    box.getX2(),
                    box.getY2(),
                    UbaUtils.EPSG_UBA,
                    true));

            final SimpleWMS ortho = new SimpleWMS(new SimpleWmsGetMapUrl(WMS_DEMIS_WORLDMAP_GETMAP_TEMPLATE));

            ortho.setName("Wuppertal Ortophoto"); // NOI18N

            mappingModel.addLayer(ortho);

            mappingComponent.setMappingModel(mappingModel);

            mappingComponent.gotoInitialBoundingBox();

            mappingComponent.setInteractionMode(MappingComponent.ZOOM);
            mappingComponent.unlock();

            for (final CidsBean cidsBean : cidsBeans) {
                final CidsFeature feature = new CidsFeature(cidsBean.getMetaObject());
                features.put(cidsBean, feature);
            }
            mappingComponent.getFeatureCollection().addFeatures(features.values());
        } catch (Exception e) {
            LOG.error("cannot initialise mapping component", e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        mappingComponent = new de.cismet.cismap.commons.gui.MappingComponent();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new java.awt.BorderLayout());
        add(mappingComponent, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents
}
