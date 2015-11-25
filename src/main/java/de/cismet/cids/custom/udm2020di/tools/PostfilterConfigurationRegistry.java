/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class PostfilterConfigurationRegistry {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(PostfilterConfigurationRegistry.class);
    private static final PostfilterConfigurationRegistry INSTANCE = new PostfilterConfigurationRegistry();

    //~ Instance fields --------------------------------------------------------

    private final ConcurrentHashMap<String, Map<String, Object>> configurationMap =
        new ConcurrentHashMap<String, Map<String, Object>>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new RendererConfigurationRegistry object.
     */
    private PostfilterConfigurationRegistry() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static final PostfilterConfigurationRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  postFilterClass  rendererClass DOCUMENT ME!
     * @param  settings         DOCUMENT ME!
     */
    public void pushSettings(
            final Class<? extends PostFilterGUI> postFilterClass,
            final Map<String, Object> settings) {
        configurationMap.put(postFilterClass.getName(), settings);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("persistent configuration settings '" + postFilterClass.getCanonicalName()
                        + "' saved");
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterClass  rendererClass DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, Object> popSettings(final Class<? extends PostFilterGUI> postFilterClass) {
        final Map<String, Object> settings = configurationMap.remove(postFilterClass.getCanonicalName());
        return settings;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterClass  DOCUMENT ME!
     * @param   settingsKey      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Object popSetting(final Class<? extends PostFilterGUI> postFilterClass,
            final String settingsKey) {
        final Map<String, Object> settings = configurationMap.remove(postFilterClass.getName());
        if ((settings != null) && !settings.isEmpty()) {
            return settings.remove(settingsKey);
        } else {
            LOGGER.warn("settings '" + settingsKey + "' for post filter '"
                        + postFilterClass.getSimpleName() + "' not found!");
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterClass  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasSettings(final Class<? extends PostFilterGUI> postFilterClass) {
        return configurationMap.containsKey(postFilterClass.getCanonicalName());
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterClass  DOCUMENT ME!
     * @param   settingsKey      DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasSetting(
            final Class<? extends PostFilterGUI> postFilterClass,
            final String settingsKey) {
        if (this.hasSettings(postFilterClass)) {
            return configurationMap.get(postFilterClass.getCanonicalName()).containsKey(settingsKey);
        }

        return false;
    }
}
