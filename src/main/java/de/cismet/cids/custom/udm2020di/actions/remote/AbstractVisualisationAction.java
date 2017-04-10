/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.apache.log4j.Logger;

import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.Component;
import java.awt.event.ActionEvent;

import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.widgets.ChartVisualisationComponent;
import de.cismet.cids.custom.udm2020di.widgets.VisualisationParameterSelectionPanel;

import de.cismet.cids.server.actions.ServerActionParameter;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public abstract class AbstractVisualisationAction extends AbstractAction implements VisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final CsvMapper MAPPER = new CsvMapper();
    protected static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    protected static int PK_INDEX = 0;
    protected static Logger LOGGER = Logger.getLogger(AbstractVisualisationAction.class);

    static {
        MAPPER.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        MAPPER.enable(CsvParser.Feature.TRIM_SPACES);
    }

    //~ Instance fields --------------------------------------------------------

    protected final Collection<Parameter> parameters;
    protected final ChartVisualisationComponent chartVisualisationComponent;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractExportAction object.
     *
     * @param  parameters                   DOCUMENT ME!
     * @param  chartVisualisationComponent  DOCUMENT ME!
     */
    public AbstractVisualisationAction(
            final Collection<Parameter> parameters,
            final ChartVisualisationComponent chartVisualisationComponent) {
        super(org.openide.util.NbBundle.getMessage(
                VisualisationParameterSelectionPanel.class,
                "VisualisationParameterSelectionPanel.btnVisualise.text"));
        this.parameters = parameters;
        this.chartVisualisationComponent = chartVisualisationComponent;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected int getPkIndex() {
        return PK_INDEX;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract int getDateIndex();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected SimpleDateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract int getParameterOffset();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Collection<Parameter> getParameters() {
        return parameters;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract String getTaskName();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract int getObjectsSize();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract ServerActionParameter[] getServerActionParameters();

    /**
     * DOCUMENT ME!
     *
     * @param   serverActionParameters  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  ConnectionException  DOCUMENT ME!
     */
    protected String downloadCsvFile(final ServerActionParameter[] serverActionParameters) throws ConnectionException {
        final Object result = SessionManager.getProxy()
                    .executeTask(
                        getTaskName(),
                        SessionManager.getSession().getUser().getDomain(),
                        null,
                        serverActionParameters);
        if ((result != null) && String.class.isAssignableFrom(result.getClass())) {
            final String csvFile = result.toString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("CSV File of size " + csvFile.length() + " downloaded");
            }

            return csvFile;
        } else {
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ((getObjectsSize() > 0)
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming " + getTaskName() + " Export for " + getObjectsSize() + " objects and "
                        + parameters.size() + " parameters");

            final SwingWorker<Map<String, Dataset>, Void> worker = new SwingWorker<Map<String, Dataset>, Void>() {

                    @Override
                    protected Map<String, Dataset> doInBackground() throws Exception {
                        final ServerActionParameter[] serverActionParameters = getServerActionParameters();
                        try {
                            // server action
                            final String csvFile = downloadCsvFile(serverActionParameters);
                            if ((csvFile != null) && !csvFile.isEmpty()) {
                                // process CSV file and generate chart data
                                final CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
                                final MappingIterator<String[]> mappingIterator = MAPPER.readerFor(String[].class)
                                            .with(bootstrapSchema)
                                            .readValues(csvFile);

                                final Map<String, Dataset> datasets = createDataset(
                                        mappingIterator,
                                        getPkIndex(),
                                        getDateIndex(),
                                        getDateFormat(),
                                        getParameterOffset());
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug(datasets.size() + " datasets created from CSV File");
                                }

                                return datasets;
                            } else {
                                final String msg = "Nothing returned by Action '" + getTaskName()
                                            + "'. Check its log to see what went wrong.";
                                LOGGER.error(msg);
                            }
                        } catch (Exception ex) {
                            final String msg = "Error during execution of action '" + getTaskName() + "'";
                            LOGGER.error(
                                msg,
                                ex);
                        }

                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            final Map<String, Dataset> datasets = this.get();
                            if (datasets != null) {
                                // delegate rendering to visualisation component
                                chartVisualisationComponent.renderCharts(datasets);
                            } else {
                                JOptionPane.showMessageDialog((Component)e.getSource(),
                                    "<html><p>Beim Datenzugiff ist ein Fehler aufgetreten.</p></html>",
                                    "Datenvisualisierung",
                                    JOptionPane.WARNING_MESSAGE);
                                chartVisualisationComponent.showParameterPanel();
                            }
                        } catch (final Exception ex) {
                            LOGGER.error(ex.getMessage(), ex);
                            JOptionPane.showMessageDialog((Component)e.getSource(),
                                "<html><p>Beim Datenzugiff ist ein Fehler aufgetreten.</p></html>",
                                "Datenvisualisierung",
                                JOptionPane.ERROR_MESSAGE);
                            chartVisualisationComponent.showParameterPanel();
                        }
                    }
                };
            chartVisualisationComponent.showProgressPanel();
            worker.execute();
        } else {
            LOGGER.error("no server action parameters provided");

            JOptionPane.showMessageDialog((Component)e.getSource(),
                "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                "Datenvisualisierung",
                JOptionPane.WARNING_MESSAGE);
            chartVisualisationComponent.showParameterPanel();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected TimeSeries[] createTimeSeries() {
        final TimeSeries[] timeSeries = new TimeSeries[this.parameters.size()];
        int i = 0;
        for (final Parameter parameter : parameters) {
            timeSeries[i] = new TimeSeries(parameter.getParameterName());
            i++;
        }

        return timeSeries;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   stationPk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract String getStationName(String stationPk);

    /**
     * DOCUMENT ME!
     *
     * @param   mappingIterator  DOCUMENT ME!
     * @param   pkIndex          DOCUMENT ME!
     * @param   dateIndex        DOCUMENT ME!
     * @param   dateFormat       DOCUMENT ME!
     * @param   parameterOffset  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected Map<String, Dataset> createDataset(
            final MappingIterator<String[]> mappingIterator,
            final int pkIndex,
            final int dateIndex,
            final SimpleDateFormat dateFormat,
            final int parameterOffset) {
        final Map<String, Dataset> datasetsMap = new HashMap<String, Dataset>();
        final Map<String, TimeSeries[]> timeseriesMap = new HashMap<String, TimeSeries[]>();

        // final TimeSeries[] timeSeriesArray; = new TimeSeries("L&G European Index Trust", Month.class);

        if (!mappingIterator.hasNext()) {
            LOGGER.warn("no data found in CSV file");
        }

        int numRows = 0;
        while (mappingIterator.hasNext()) {
            try {
                final String[] row = mappingIterator.next();
                final String stationPk = row[pkIndex];
                final TimeSeries[] timeSeriesArray;
                if (!timeseriesMap.containsKey(stationPk)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("creating new timeseries for station " + stationPk);
                    }
                    timeSeriesArray = this.createTimeSeries();
                    timeseriesMap.put(stationPk, timeSeriesArray);
                } else {
                    timeSeriesArray = timeseriesMap.get(stationPk);
                }
                final Date date = dateFormat.parse(row[dateIndex]);
                for (int i = parameterOffset; i < row.length; i++) {
                    if ((row[i] != null) && !row[i].isEmpty()
                                && ((i - parameterOffset) < timeSeriesArray.length)) {
                        // add or update: skip duplicate timestamps!
                        timeSeriesArray[i - parameterOffset].addOrUpdate(
                            new Day(date),
                            Float.parseFloat(row[i]));
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("could not process row " + numRows + " of CSV file: " + ex.getMessage(), ex);
            } finally {
                numRows++;
            }
        }

        for (final String stationPk : timeseriesMap.keySet()) {
            final String stationName = this.getStationName(stationPk);
            final TimeSeries[] timeSeriesArray = timeseriesMap.get(stationPk);
            final TimeSeriesCollection dataset = new TimeSeriesCollection();
            int i = 0;
            for (final TimeSeries timeSeries : timeSeriesArray) {
                if (timeSeries.getItemCount() > 0) {
                    dataset.addSeries(timeSeries);
                } else {
                    LOGGER.warn("time series #" + i + " for station " + stationPk + " does not conatin any values");
                }
                i++;
            }
            datasetsMap.put(stationName, dataset);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(i + " time series created for station " + stationPk);
            }
        }

        return datasetsMap;
    }

    @Override
    public final void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
    }
}
