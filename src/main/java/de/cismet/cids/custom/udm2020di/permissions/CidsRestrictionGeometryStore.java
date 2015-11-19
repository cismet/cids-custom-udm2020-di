/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.permissions;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import com.vividsolutions.jts.index.strtree.STRtree;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.util.HashMap;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.configuration.StartupHook;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = StartupHook.class)
public class CidsRestrictionGeometryStore implements StartupHook {

    //~ Static fields/initializers ---------------------------------------------

    public static final String DOMAIN = "UDM2020-DI";
    public static final String TABLE = "NAMED_AREA";

    protected static final transient Logger LOGGER = Logger.getLogger(CidsRestrictionGeometryStore.class);
    protected static final HashMap<String, STRtree> restrictions = new HashMap<String, STRtree>();

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CidsRestrictionGeometryStore initialization started");
            }
            final MetaClass mc = ClassCacheMultiple.getMetaClass(DOMAIN, TABLE);
            final MetaObject[] metaObjects = SessionManager.getConnection()
                        .getMetaObjectByQuery(SessionManager.getSession().getUser(),
                            "SELECT "
                            + mc.getID()
                            + ", "
                            + mc.getPrimaryKey()
                            + " FROM "
                            + mc.getTableName()
                            // restrict to Niederösterreich to minimise  startup time
                            + " WHERE "
                            + mc.getTableName()
                            + ".name = 'Niederösterreich'");

            for (final MetaObject mo : metaObjects) {
                final CidsBean cb = mo.getBean();

                final String name = (String)cb.getProperty("name");
                STRtree restrictionfeatures = restrictions.get(name);

                if (restrictionfeatures == null) {
                    restrictionfeatures = new STRtree();
                    restrictions.put(name, restrictionfeatures);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("new geometry restriction for '" + name + "' added");
                    }
                }

                final CidsFeature feature = new CidsFeature(mo);
                if ((feature != null) && (feature.getGeometry() != null)) {
                    restrictionfeatures.insert(feature.getGeometry().getEnvelopeInternal(), feature);
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CidsRestrictionGeometryStore initialized with " + metaObjects.length + " Object"
                            + ((metaObjects.length == 1) ? "" : "s") + ".");
            }
        } catch (Exception e) {
            LOGGER.warn("Error during initialization of restriction objects.", e);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static HashMap<String, STRtree> getRestrictions() {
        return restrictions;
    }
}
