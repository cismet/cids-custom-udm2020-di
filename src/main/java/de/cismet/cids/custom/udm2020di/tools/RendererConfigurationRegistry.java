/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRenderer;
import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class RendererConfigurationRegistry {

    //~ Static fields/initializers ---------------------------------------------

    private static final int STACK_SIZE = 5;
    private static final Logger LOGGER = Logger.getLogger(RendererConfigurationRegistry.class);
    private static final RendererConfigurationRegistry INSTANCE = new RendererConfigurationRegistry();

    //~ Instance fields --------------------------------------------------------

    private final ConcurrentHashMap<Integer, Map<String, Object>> configurationMap =
        new ConcurrentHashMap<Integer, Map<String, Object>>();

    private final TreeSet<Integer> configurationStack = new TreeSet<Integer>();

    private final ConcurrentHashMap<Integer, Map<String, Object>> persitentConfigurationMap =
        new ConcurrentHashMap<Integer, Map<String, Object>>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new RendererConfigurationRegistry object.
     */
    private RendererConfigurationRegistry() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static final RendererConfigurationRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rendererClass  DOCUMENT ME!
     * @param  settings       DOCUMENT ME!
     */
    public void setSettings(final Class rendererClass, final Map<String, Object> settings) {
        final int hashCode = rendererClass.hashCode();
        persitentConfigurationMap.put(hashCode, settings);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("persistent configuration settings '" + hashCode + "' saved");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  renderer  DOCUMENT ME!
     * @param  settings  DOCUMENT ME!
     */
    public void pushSettings(final CidsBeanRenderer renderer, final Map<String, Object> settings) {
        final int hashCode = renderer.getCidsBean().hashCode();
        configurationMap.put(hashCode, settings);
        stack(hashCode);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("configuration settings '" + hashCode + "' saved");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationRenderer  DOCUMENT ME!
     * @param  settings             DOCUMENT ME!
     */
    public void pushSettings(final CidsBeanAggregationRenderer aggregationRenderer,
            final Map<String, Object> settings) {
        final int hashCode = aggregationRenderer.getCidsBeans().hashCode();
        configurationMap.put(hashCode, settings);
        stack(hashCode);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("configuration settings '" + hashCode + "' saved");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  domain       DOCUMENT ME!
     * @param  metaClassId  DOCUMENT ME!
     * @param  objectIds    DOCUMENT ME!
     * @param  settings     DOCUMENT ME!
     */
    public void pushSettings(final String domain,
            final int metaClassId,
            final Collection<Integer> objectIds,
            final Map<String, Object> settings) {
        final int hashCode = hashCode(domain, metaClassId, objectIds);
        configurationMap.put(hashCode, settings);
        stack(hashCode);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("configuration settings '" + hashCode + "' saved");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  domain       DOCUMENT ME!
     * @param  metaClassId  DOCUMENT ME!
     * @param  objectId     DOCUMENT ME!
     * @param  settings     DOCUMENT ME!
     */
    public void pushSettings(final String domain,
            final int metaClassId,
            final int objectId,
            final Map<String, Object> settings) {
        final int hashCode = hashCode(domain, metaClassId, objectId);
        configurationMap.put(hashCode, settings);
        stack(hashCode);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("configuration settings '" + hashCode + "' saved");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   domain       DOCUMENT ME!
     * @param   metaClassId  DOCUMENT ME!
     * @param   objectIds    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Object> popSettings(final String domain,
            final int metaClassId,
            final Collection<Integer> objectIds) {
        final int hashCode = hashCode(domain, metaClassId, objectIds);
        final Map<String, Object> settings = configurationMap.remove(hashCode);
        configurationStack.remove(hashCode);
        if (settings != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("configuration settings '" + hashCode + "' removed");
            }
        } else {
            LOGGER.warn("configuration settings '" + hashCode + "' not found!");
        }
        return settings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   renderer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Object> popSettings(final CidsBeanRenderer renderer) {
        final int hashCode = renderer.getCidsBean().hashCode();
        final Map<String, Object> settings = configurationMap.remove(hashCode);
        configurationStack.remove(hashCode);
        if (settings != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("configuration settings '" + hashCode + "' removed");
            }
        } else {
            LOGGER.warn("configuration settings '" + hashCode + "' not found!");
        }
        return settings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   aggregationRenderer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Object> popSettings(final CidsBeanAggregationRenderer aggregationRenderer) {
        final int hashCode = aggregationRenderer.getCidsBeans().hashCode();
        final Map<String, Object> settings = configurationMap.remove(hashCode);
        configurationStack.remove(hashCode);
        if (settings != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("configuration settings '" + hashCode + "' removed");
            }
        } else {
            LOGGER.warn("configuration settings '" + hashCode + "' not found!");
        }
        return settings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   rendererClass  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Object> getSettings(final Class rendererClass) {
        final int hashCode = rendererClass.hashCode();
        final Map<String, Object> settings = persitentConfigurationMap.get(hashCode);
        return settings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   domain       DOCUMENT ME!
     * @param   metaClassId  DOCUMENT ME!
     * @param   objectId     DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int hashCode(final String domain,
            final int metaClassId,
            final int objectId) {
        int hashCode = 1;
        final String s = objectId + "." + metaClassId + "." + domain; // NOI18N
        hashCode = (31 * hashCode) + ((s == null) ? 0 : s.hashCode());
        return hashCode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   domain       DOCUMENT ME!
     * @param   metaClassId  DOCUMENT ME!
     * @param   objectIds    DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private int hashCode(final String domain,
            final int metaClassId,
            final Collection<Integer> objectIds) {
        int hashCode = 1;
        for (final int objectId : objectIds) {
            final String s = objectId + "." + metaClassId + "." + domain; // NOI18N
            hashCode = (31 * hashCode) + ((s == null) ? 0 : s.hashCode());
        }

        return hashCode;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  hashCode  DOCUMENT ME!
     */
    private void stack(final int hashCode) {
        this.configurationStack.add(hashCode);
        if (this.configurationStack.size() > STACK_SIZE) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("stack is full, removing oldest configuration element " + hashCode);
            }
            final int oldestHashCode = this.configurationStack.pollFirst();
            this.configurationMap.remove(oldestHashCode);
        }
    }
}
