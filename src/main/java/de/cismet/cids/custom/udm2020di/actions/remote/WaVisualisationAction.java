/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.serveractions.wa.WagwExportAction;
import de.cismet.cids.custom.udm2020di.serveractions.wa.WaowExportAction;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.wa.Messstelle;
import de.cismet.cids.custom.udm2020di.widgets.ChartVisualisationComponent;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.actions.remote.WaExportAction.WAGW;
import static de.cismet.cids.custom.udm2020di.actions.remote.WaExportAction.WAOW;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_MESSSTELLEN;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_PARAMETER;
/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class WaVisualisationAction extends AbstractVisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(WaVisualisationAction.class);

    //~ Instance fields --------------------------------------------------------

    protected final String waSource;
    protected final String taskName;

    protected final Map<String, Messstelle> stationMap = new HashMap<String, Messstelle>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  waSource                     DOCUMENT ME!
     * @param  stations                     standorte DOCUMENT ME!
     * @param  parameters                   DOCUMENT ME!
     * @param  chartVisualisationComponent  DOCUMENT ME!
     */
    public WaVisualisationAction(final String waSource,
            final Collection<Messstelle> stations,
            final Collection<Parameter> parameters,
            final ChartVisualisationComponent chartVisualisationComponent) {
        super(parameters, chartVisualisationComponent);

        if (waSource.equalsIgnoreCase(WAGW)) {
            this.waSource = waSource;
            taskName = WagwExportAction.TASK_NAME;
        } else if (waSource.equalsIgnoreCase(WAOW)) {
            this.waSource = waSource;
            taskName = WaowExportAction.TASK_NAME;
        } else {
            this.waSource = WAGW;
            taskName = WagwExportAction.TASK_NAME;
            LOGGER.error("unsupported WA Station Type: " + this.waSource);
        }

        this.setMessstellen(stations);
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] getServerActionParameters() {
        final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                new ServerActionParameter<Collection<String>>(
                    PARAM_MESSSTELLEN,
                    new ArrayList<String>(this.stationMap.keySet())),
                new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                new ServerActionParameter<String>(
                    PARAM_EXPORTFORMAT,
                    de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV),
                new ServerActionParameter<String>(PARAM_NAME, this.waSource + "-visualisation-export")
            };
        return serverActionParameters;
    }

    @Override
    protected String getTaskName() {
        return taskName;
    }

    @Override
    protected int getObjectsSize() {
        return (this.stationMap != null) ? this.stationMap.size() : 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stations  DOCUMENT ME!
     */
    protected final void setMessstellen(final Collection<Messstelle> stations) {
        if (!this.stationMap.isEmpty()) {
            this.stationMap.clear();
        }

        for (final Messstelle station : stations) {
            stationMap.put(station.getPk(), station);
        }
    }

    @Override
    protected String getStationName(final String stationPk) {
        if (this.stationMap.containsKey(stationPk)) {
            final Messstelle station = this.stationMap.get(stationPk);
            final String stationName = ((station.getName() != null)
                            && !station.getName().isEmpty()) ? station.getName() : stationPk;
            return stationName;
        } else {
            LOGGER.warn("unknown station: " + stationPk);
            return stationPk;
        }
    }

    @Override
    protected int getDateIndex() {
        return 2;
    }

    @Override
    protected int getParameterOffset() {
        return 5;
    }
}
