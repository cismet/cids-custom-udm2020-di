/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import com.fasterxml.jackson.databind.MappingIterator;

import org.apache.log4j.Logger;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.boris.Standort;
import de.cismet.cids.custom.udm2020di.widgets.ChartVisualisationComponent;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_STANDORTE;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.TASK_NAME;
/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class BorisVisualisationAction extends AbstractVisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(BorisVisualisationAction.class);
    protected static final SimpleDateFormat CHART_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    //~ Instance fields --------------------------------------------------------

    protected final Map<String, Standort> stationMap = new HashMap<String, Standort>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisExportAction object.
     *
     * @param  standorte                    DOCUMENT ME!
     * @param  parameters                   DOCUMENT ME!
     * @param  chartVisualisationComponent  DOCUMENT ME!
     */
    public BorisVisualisationAction(final Collection<Standort> standorte,
            final Collection<Parameter> parameters,
            final ChartVisualisationComponent chartVisualisationComponent) {
        super(parameters, chartVisualisationComponent);

        this.setStations(standorte);
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] getServerActionParameters() {
        final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                new ServerActionParameter<Collection<String>>(
                    PARAM_STANDORTE,
                    new ArrayList<String>(this.stationMap.keySet())),
                new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                new ServerActionParameter<String>(
                    PARAM_EXPORTFORMAT,
                    de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV),
                new ServerActionParameter<String>(PARAM_NAME, "boris-visualisation-export")
            };

        return serverActionParameters;
    }

    @Override
    protected String getTaskName() {
        return TASK_NAME;
    }

    @Override
    protected int getObjectsSize() {
        return (this.stationMap != null) ? this.stationMap.size() : 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<Standort> getStations() {
        return stationMap.values();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  stations  DOCUMENT ME!
     */
    protected final void setStations(final Collection<Standort> stations) {
        if (!this.stationMap.isEmpty()) {
            this.stationMap.clear();
        }

        for (final Standort station : stations) {
            stationMap.put(station.getPk(), station);
        }
    }

    @Override
    protected String getStationName(final String stationPk) {
        return stationPk;
    }

    @Override
    protected int getDateIndex() {
        return 2;
    }

    @Override
    protected int getParameterOffset() {
        return 5;
    }

    @Override
    protected Map<String, Dataset> createDataset(final MappingIterator<String[]> mappingIterator,
            final int pkIndex,
            final int dateIndex,
            final SimpleDateFormat dateFormat,
            final int parameterOffset) {
        final Map<String, Dataset> datasetsMap = new HashMap<String, Dataset>();
        if (!mappingIterator.hasNext()) {
            LOGGER.warn("no data found in CSV file");
        }

        final Parameter[] parameterArray = this.parameters.toArray(new Parameter[this.parameters.size()]);
        int numRows = 0;
        while (mappingIterator.hasNext()) {
            try {
                final String[] row = mappingIterator.next();
                final String stationPk = row[pkIndex];

                final DefaultCategoryDataset dataset;
                final String stationName = this.getStationName(stationPk);
                if (!datasetsMap.containsKey(stationName)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("creating new dataset for station " + stationPk);
                    }
                    dataset = new DefaultCategoryDataset();
                    datasetsMap.put(stationName, dataset);
                } else {
                    dataset = (DefaultCategoryDataset)datasetsMap.get(stationName);
                }

                final Date date = getDateFormat().parse(row[dateIndex]);
                final String dateString = CHART_DATE_FORMAT.format(date);

                for (int i = parameterOffset; i < row.length; i++) {
                    if ((row[i] != null) && !row[i].isEmpty()) {
                        dataset.addValue(Float.parseFloat(row[i]),
                            parameterArray[i - parameterOffset].getParameterName(),
                            dateString);
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("could not process row " + numRows + " of CSV file: " + ex.getMessage(), ex);
            } finally {
                numRows++;
            }
        }

        return datasetsMap;
    }
}
