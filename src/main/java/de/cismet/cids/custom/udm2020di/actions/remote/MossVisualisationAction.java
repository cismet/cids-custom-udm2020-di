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

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.moss.Moss;
import de.cismet.cids.custom.udm2020di.widgets.ChartVisualisationComponent;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_OBJECT_IDS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_PARAMETER;
/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MossVisualisationAction extends AbstractVisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MossVisualisationAction.class);

    //~ Instance fields --------------------------------------------------------

    protected final Map<String, Moss> sampleMap = new HashMap<String, Moss>();
    protected final Collection<Long> objectIds = new ArrayList<Long>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  samples                      standorte DOCUMENT ME!
     * @param  parameters                   DOCUMENT ME!
     * @param  chartVisualisationComponent  DOCUMENT ME!
     */
    public MossVisualisationAction(final Collection<Moss> samples,
            final Collection<Parameter> parameters,
            final ChartVisualisationComponent chartVisualisationComponent) {
        super(parameters, chartVisualisationComponent);

        this.setMossSamples(samples);
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] getServerActionParameters() {
        final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                new ServerActionParameter<Collection<Long>>(
                    PARAM_OBJECT_IDS,
                    this.objectIds),
                new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                new ServerActionParameter<String>(
                    PARAM_EXPORTFORMAT,
                    de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV),
                new ServerActionParameter<String>(PARAM_NAME, "moss-visualisation-export")
            };
        return serverActionParameters;
    }

    @Override
    protected String getTaskName() {
        return de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.TASK_NAME;
    }

    @Override
    protected int getObjectsSize() {
        return (this.sampleMap != null) ? this.sampleMap.size() : 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  samples  DOCUMENT ME!
     */
    protected final void setMossSamples(final Collection<Moss> samples) {
        if (!this.sampleMap.isEmpty()) {
            this.sampleMap.clear();
            objectIds.clear();
        }

        for (final Moss sample : samples) {
            sampleMap.put(sample.getSampleId(), sample);
            objectIds.add(sample.getId());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationPk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    protected String getStationName(final String stationPk) {
        if (this.sampleMap.containsKey(stationPk)) {
            final Moss moss = this.sampleMap.get(stationPk);
            final String stationName = moss.getSampleId() + " (" + moss.getType() + ")";
            return stationName;
        } else {
            LOGGER.warn("unknown station: " + stationPk);
            return String.valueOf(stationPk);
        }
    }

    @Override
    protected int getDateIndex() {
        return 2;
    }

    @Override
    protected int getParameterOffset() {
        return 3;
    }
}
