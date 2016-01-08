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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.tools.UbaConstants;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.cismap.commons.Crs;
import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import static de.cismet.cids.custom.udm2020di.tools.UbaConstants.WMS_BASEMAP_AT_GETMAP_TEMPLATE;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MapPanel extends javax.swing.JPanel implements CidsBeanCollectionStore {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MapPanel.class);

    private static final Double GEOMETRY_BUFFER = 0.1d;

    private static final Double GEOMETRY_BUFFER_MULTIPLIER = 0.05d;

    //~ Instance fields --------------------------------------------------------

    private final transient Map<CidsBean, CidsFeature> cidsFeatures = new HashMap<CidsBean, CidsFeature>();

    private transient Collection<CidsBean> cidsBeans;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cismap.commons.gui.MappingComponent mappingComponent;
    // End of variables declaration//GEN-END:variables

    private double geometryBuffer = GEOMETRY_BUFFER;

    private double geometryBufferMultiplier = GEOMETRY_BUFFER_MULTIPLIER;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form MapPanel.
     */
    public MapPanel() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<CidsBean, CidsFeature> getFeatures() {
        return cidsFeatures;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public MappingComponent getMappingComponent() {
        return mappingComponent;
    }

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
     * DOCUMENT ME!
     *
     * @param   selectedBean  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean gotoCidsBean(final CidsBean selectedBean) {
        if (cidsFeatures.containsKey(selectedBean)) {
            final XBoundingBox boxToGoto = new XBoundingBox(cidsFeatures.get(selectedBean).getGeometry().getEnvelope()
                            .buffer(GEOMETRY_BUFFER));
            boxToGoto.setX1(boxToGoto.getX1()
                        - (this.getGeometryBufferMultiplier() * boxToGoto.getWidth()));
            boxToGoto.setX2(boxToGoto.getX2()
                        + (this.getGeometryBufferMultiplier() * boxToGoto.getWidth()));
            boxToGoto.setY1(boxToGoto.getY1()
                        - (this.getGeometryBufferMultiplier() * boxToGoto.getHeight()));
            boxToGoto.setY2(boxToGoto.getY2()
                        + (this.getGeometryBufferMultiplier() * boxToGoto.getHeight()));
            mappingComponent.gotoBoundingBox(boxToGoto, false, true, 500);
            return true;
        } else {
            LOGGER.warn("selected cids bean not found in map of cids features!");
            return false;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean) {
        this.setCidsBeans(Arrays.asList(new CidsBean[] { cidsBean }));
    }

    /**
     * Set the value of cidsBeans.
     *
     * @param  cidsBeans  new value of cidsBeans
     */
    @Override
    public void setCidsBeans(final Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;
        this.cidsFeatures.clear();

        if ((this.cidsBeans != null) && !this.cidsBeans.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("showing " + this.cidsBeans.size() + " objects on map");
            }
            final Runnable objectInitialisationThread = new Runnable() {

                    @Override
                    public void run() {
                        for (final CidsBean cidsBean : cidsBeans) {
                            final CidsFeature feature = new CidsFeature(cidsBean.getMetaObject());
                            cidsFeatures.put(cidsBean, feature);
                        }

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("adding " + cidsBeans.size() + " features to map");
                        }

                        initMap();
                    }
                };

            new Thread(objectInitialisationThread).start();
        } else {
            LOGGER.warn("no cids beans provided - no objects shown on map!");
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
                final Geometry geom = new CidsFeature(deltaSurface.getMetaObject()).getGeometry();
                final Geometry geomUba = CrsTransformer.transformToGivenCrs(geom.getEnvelope(), UbaConstants.EPSG_UBA);
                geometries.add(geomUba);
            } catch (Exception ex) {
                LOGGER.warn(ex, ex);
            }
        }

        final GeometryCollection geoCollection = new GeometryCollection(geometries.toArray(
                    new Geometry[geometries.size()]),
                new GeometryFactory());

        // TODO Buffer sollte nicht konstant sein!
        return new XBoundingBox(geoCollection.getEnvelope().buffer(this.getGeometryBuffer()));
    }

    /**
     * DOCUMENT ME!
     */
    protected void initMap() {
        try {
            final XBoundingBox box = boundingBoxFromPointList(cidsBeans);

            final ActiveLayerModel mappingModel = new ActiveLayerModel();
            mappingModel.setSrs(new Crs(
                    UbaConstants.EPSG_UBA,
                    UbaConstants.EPSG_UBA,
                    UbaConstants.EPSG_UBA,
                    false,
                    true));
            mappingModel.addHome(new XBoundingBox(
                    box.getX1(),
                    box.getY1(),
                    box.getX2(),
                    box.getY2(),
                    UbaConstants.EPSG_UBA,
                    false));

            final SimpleWMS basemap = new SimpleWMS(new SimpleWmsGetMapUrl(WMS_BASEMAP_AT_GETMAP_TEMPLATE));

            basemap.setName("Worldmap"); // NOI18N
            basemap.setTranslucency(0.25f);

            final Runnable guiInitialisationThread = new Runnable() {

                    @Override
                    public void run() {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("performing GUI inititialisation");
                        }
                        mappingModel.addLayer(basemap);

                        mappingComponent.setMappingModel(mappingModel);

                        mappingComponent.gotoInitialBoundingBox();

                        mappingComponent.setInteractionMode(MappingComponent.ZOOM);
                        mappingComponent.unlock();

                        mappingComponent.getFeatureCollection().addFeatures(cidsFeatures.values());
                    }
                };

            EventQueue.invokeLater(guiInitialisationThread);
        } catch (Exception e) {
            LOGGER.error("cannot initialise mapping component", e);
        }
    }

    /**
     * Get the value of geometryBuffer.
     *
     * @return  the value of geometryBuffer
     */
    public double getGeometryBuffer() {
        return geometryBuffer;
    }

    /**
     * Set the value of geometryBuffer.
     *
     * @param  geometryBuffer  new value of geometryBuffer
     */
    public void setGeometryBuffer(final double geometryBuffer) {
        this.geometryBuffer = geometryBuffer;
    }

    /**
     * Get the value of geometryBufferMultiplier.
     *
     * @return  the value of geometryBufferMultiplier
     */
    public double getGeometryBufferMultiplier() {
        return geometryBufferMultiplier;
    }

    /**
     * Set the value of geometryBufferMultiplier.
     *
     * @param  geometryBufferMultiplier  new value of geometryBufferMultiplier
     */
    public void setGeometryBufferMultiplier(final double geometryBufferMultiplier) {
        this.geometryBufferMultiplier = geometryBufferMultiplier;
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
