/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.permissions;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.resource.PropertyManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.newuser.User;

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

    public static final String GEOMETRY_PERMISSION_ATTR =
        "de.cismet.cids.custom.udm2020di.permissions.GeometryFromCidsObjectPermission";

    //~ Methods ----------------------------------------------------------------

    @Override
    public void applicationStarted() {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CidsRestrictionGeometryStore initialization started");
            }

            // for perfomance reasons, active only for usergroups with geo restriction attribute!
            final User user = SessionManager.getSession().getUser();
            if (SessionManager.getProxy().hasConfigAttr(user, GEOMETRY_PERMISSION_ATTR)) {
                final String geometryPermission = SessionManager.getProxy()
                            .getConfigAttr(user, GEOMETRY_PERMISSION_ATTR);
                if ((geometryPermission != null) && !geometryPermission.isEmpty()) {
                    LOGGER.info("loading restriction feature '" + geometryPermission
                                + "' for usergroup '" + user.getUserGroup().getName() + "'.");

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
                        final CidsFeature feature = new CidsFeature(mo);
                        if ((feature != null) && (feature.getGeometry() != null)) {
                            STRtree restrictionfeatures = restrictions.get(name);
                            if (restrictionfeatures == null) {
                                restrictionfeatures = new STRtree();
                                restrictions.put(name, restrictionfeatures);
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug("new geometry restriction for '" + name + "' added");
                                }
                            }
                            restrictionfeatures.insert(feature.getGeometry().getEnvelopeInternal(), feature);
                        }
                    }

                    if (!restrictions.isEmpty()) {
                        PropertyManager.USE_CUSTOM_BEAN_PERMISSION_PROVIDER_FOR_SEARCH = true;
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.info("CidsRestrictionGeometryStore initialized with " + metaObjects.length
                                        + " Object"
                                        + ((metaObjects.length == 1) ? "" : "s") + " for user '"
                                        + user.getKey() + ", enforcement of geo permissions is acivated!");
                        }
                    } else {
                        LOGGER.warn("no matching restriction features for '" + geometryPermission + "' found for user '"
                                    + user.getKey() + ", enforcement of geo permissions is deacivated.");
                        PropertyManager.USE_CUSTOM_BEAN_PERMISSION_PROVIDER_FOR_SEARCH = false;
                    }
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Config attribute '" + GEOMETRY_PERMISSION_ATTR + "' empty for user '"
                                    + user.getKey() + ", enforcement of geo permissions is deacivated.");
                    }
                    PropertyManager.USE_CUSTOM_BEAN_PERMISSION_PROVIDER_FOR_SEARCH = false;
                }
            } else {
                LOGGER.info("Config attribute '" + GEOMETRY_PERMISSION_ATTR + "' not set for user '"
                            + user.getKey() + ", enforcement of geo permissions is deacivated.");
                PropertyManager.USE_CUSTOM_BEAN_PERMISSION_PROVIDER_FOR_SEARCH = false;
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
