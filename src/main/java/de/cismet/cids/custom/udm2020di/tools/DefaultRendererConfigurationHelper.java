/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.tools;

import org.apache.log4j.Logger;

import org.openide.util.WeakListeners;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.cismet.cids.custom.udm2020di.objectrenderer.BorisSiteRenderer;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRenderer;
import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import static de.cismet.cids.custom.udm2020di.actions.remote.ExportAction.EXPORT_FORMAT_SETTINGS;
import static de.cismet.cids.custom.udm2020di.actions.remote.ExportAction.PARAMETER_SETTINGS;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class DefaultRendererConfigurationHelper {

    //~ Static fields/initializers ---------------------------------------------

    protected static final DefaultRendererConfigurationHelper INSTANCE = new DefaultRendererConfigurationHelper();

    protected static final String SELECTED_TAB = "SELECTED_TAB";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DefaultRendererConfigurationHelper object.
     */
    protected DefaultRendererConfigurationHelper() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static final DefaultRendererConfigurationHelper getInstance() {
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  rendererClass  DOCUMENT ME!
     * @param  jTabbedPane    DOCUMENT ME!
     * @param  logger         DOCUMENT ME!
     */
    public void restoreSelectedTab(
            final Class rendererClass,
            final JTabbedPane jTabbedPane,
            final Logger logger) {
        // restore saved tab
        final Map<String, Object> settings = RendererConfigurationRegistry.getInstance().getSettings(rendererClass);
        if ((settings != null) && !settings.isEmpty() && settings.containsKey(SELECTED_TAB)) {
            final int selectedIndex = (int)settings.get(SELECTED_TAB);
            if (logger.isDebugEnabled()) {
                logger.debug("restoring selected tab index #" + selectedIndex);
            }
            jTabbedPane.setSelectedIndex(selectedIndex);
        } else {
            logger.warn("selected tab settings  not found!");
        }

        // register listener to save tab
        jTabbedPane.addChangeListener(WeakListeners.create(
                ChangeListener.class,
                new ChangeListener() {

                    @Override
                    public void stateChanged(final ChangeEvent evt) {
                        final Map<String, Object> settings;
                        if (RendererConfigurationRegistry.getInstance().getSettings(BorisSiteRenderer.class)
                                    != null) {
                            settings = RendererConfigurationRegistry.getInstance().getSettings(BorisSiteRenderer.class);
                        } else {
                            settings = new HashMap<String, Object>();
                            RendererConfigurationRegistry.getInstance().setSettings(rendererClass, settings);
                        }
                        final int selectedIndex = jTabbedPane.getSelectedIndex();
                        if (logger.isDebugEnabled()) {
                            logger.debug("saving selected tab index #" + selectedIndex);
                        }
                        settings.put(SELECTED_TAB, selectedIndex);
                    }
                },
                jTabbedPane));
    }

    /**
     * DOCUMENT ME!
     *
     * @param  renderer                 DOCUMENT ME!
     * @param  jTabbedPane              DOCUMENT ME!
     * @param  parameterSelectionPanel  DOCUMENT ME!
     * @param  exportPanel              DOCUMENT ME!
     * @param  logger                   DOCUMENT ME!
     */
    public void restoreExportSettings(
            final CidsBeanRenderer renderer,
            final JTabbedPane jTabbedPane,
            final ExportParameterSelectionPanel parameterSelectionPanel,
            final JPanel exportPanel,
            final Logger logger) {
        if (RendererConfigurationRegistry.getInstance().hasSettings(renderer)) {
            final Map<String, Object> parameterSettings = RendererConfigurationRegistry.getInstance()
                        .popSettings(renderer);
            this.restoreExportSettings(parameterSettings,
                jTabbedPane,
                parameterSelectionPanel,
                exportPanel,
                logger);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("no saved export settings found");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  aggregationRenderer      DOCUMENT ME!
     * @param  jTabbedPane              DOCUMENT ME!
     * @param  parameterSelectionPanel  DOCUMENT ME!
     * @param  exportPanel              DOCUMENT ME!
     * @param  logger                   DOCUMENT ME!
     */
    public void restoreExportSettings(
            final CidsBeanAggregationRenderer aggregationRenderer,
            final JTabbedPane jTabbedPane,
            final ExportParameterSelectionPanel parameterSelectionPanel,
            final JPanel exportPanel,
            final Logger logger) {
        if (RendererConfigurationRegistry.getInstance().hasSettings(aggregationRenderer)) {
            final Map<String, Object> parameterSettings = RendererConfigurationRegistry.getInstance()
                        .popSettings(aggregationRenderer);
            this.restoreExportSettings(parameterSettings,
                jTabbedPane,
                parameterSelectionPanel,
                exportPanel,
                logger);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("no saved export settings found");
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parameterSettings        DOCUMENT ME!
     * @param  jTabbedPane              DOCUMENT ME!
     * @param  parameterSelectionPanel  DOCUMENT ME!
     * @param  exportPanel              DOCUMENT ME!
     * @param  logger                   DOCUMENT ME!
     */
    protected void restoreExportSettings(
            final Map<String, Object> parameterSettings,
            final JTabbedPane jTabbedPane,
            final ExportParameterSelectionPanel parameterSelectionPanel,
            final JPanel exportPanel,
            final Logger logger) {
        if ((parameterSettings != null) && !parameterSettings.isEmpty()
                    && parameterSettings.containsKey(PARAMETER_SETTINGS)) {
            final Collection<Parameter> selectedParameters = (Collection<Parameter>)parameterSettings.get(
                    PARAMETER_SETTINGS);

            final String exportFormat = parameterSettings.get(
                    EXPORT_FORMAT_SETTINGS).toString();

            if (logger.isDebugEnabled()) {
                logger.debug("restoring saved export '" + exportFormat + "' parameter settings of "
                            + selectedParameters.size() + " selected parameters");
            }

            parameterSelectionPanel.setSelectedParameters(selectedParameters);
            parameterSelectionPanel.setExportFormat(exportFormat);
            final Map<String, Object> settings;
            if (RendererConfigurationRegistry.getInstance().getSettings(BorisSiteRenderer.class)
                        != null) {
                settings = RendererConfigurationRegistry.getInstance().getSettings(BorisSiteRenderer.class);
            } else {
                settings = new HashMap<String, Object>();
                RendererConfigurationRegistry.getInstance().setSettings(BorisSiteRenderer.class, settings);
            }
            final int selectedIndex = jTabbedPane.indexOfComponent(exportPanel);
            if (logger.isDebugEnabled()) {
                logger.debug("saving selected tab index #" + selectedIndex);
            }
            settings.put(SELECTED_TAB, selectedIndex);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("no saved export settings '" + PARAMETER_SETTINGS + "' found");
            }
        }
    }
}
