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
import java.util.HashMap;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.eprtr.Installation;
import de.cismet.cids.custom.udm2020di.widgets.ChartVisualisationComponent;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_INSTALLATIONS;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.TASK_NAME;
/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class EprtrVisualisationAction extends AbstractVisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(EprtrVisualisationAction.class);

    //~ Instance fields --------------------------------------------------------

    protected final Map<Long, Installation> installationMap = new HashMap<Long, Installation>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  installations                standorte DOCUMENT ME!
     * @param  parameters                   DOCUMENT ME!
     * @param  chartVisualisationComponent  DOCUMENT ME!
     */
    public EprtrVisualisationAction(final Collection<Installation> installations,
            final Collection<Parameter> parameters,
            final ChartVisualisationComponent chartVisualisationComponent) {
        super(parameters, chartVisualisationComponent);

        this.setInstallations(installations);
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] getServerActionParameters() {
        final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                new ServerActionParameter<Collection<Long>>(
                    PARAM_INSTALLATIONS,
                    new ArrayList<Long>(this.installationMap.keySet())),
                new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                new ServerActionParameter<String>(
                    PARAM_EXPORTFORMAT,
                    de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV),
                new ServerActionParameter<String>(PARAM_NAME, "eprtr-visualisation-export")
            };
        return serverActionParameters;
    }

    @Override
    protected String getTaskName() {
        return TASK_NAME;
    }

    @Override
    protected int getObjectsSize() {
        return (this.installationMap != null) ? this.installationMap.size() : 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  installations  DOCUMENT ME!
     */
    protected final void setInstallations(final Collection<Installation> installations) {
        if (!this.installationMap.isEmpty()) {
            this.installationMap.clear();
        }

        for (final Installation installation : installations) {
            installationMap.put(installation.getErasId(), installation);
        }
    }

    @Override
    protected String getStationName(final String installationPk) {
        return this.getStationName(Long.parseLong(installationPk));
    }

    /**
     * DOCUMENT ME!
     *
     * @param   installationPk  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected String getStationName(final Long installationPk) {
        if (this.installationMap.containsKey(installationPk)) {
            final Installation installation = this.installationMap.get(installationPk);
            final String installationName = ((installation.getName() != null)
                            && !installation.getName().isEmpty()) ? installation.getName()
                                                                  : String.valueOf(installationPk);
            return installationName;
        } else {
            LOGGER.warn("unknown installation: " + installationPk);
            return String.valueOf(installationPk);
        }
    }

    @Override
    protected int getDateIndex() {
        return 4;
    }

    @Override
    protected int getParameterOffset() {
        return 8;
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
                final String installationPk = row[pkIndex];

                final DefaultCategoryDataset dataset;
                final String installationName = this.getStationName(installationPk);
                if (!datasetsMap.containsKey(installationName)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("creating new dataset for installation " + installationPk);
                    }
                    dataset = new DefaultCategoryDataset();
                    datasetsMap.put(installationName, dataset);
                } else {
                    dataset = (DefaultCategoryDataset)datasetsMap.get(installationName);
                }

                final String dateString = row[dateIndex];
                for (int i = parameterOffset; i < row.length; i++) {
                    if ((row[i] != null) && !row[i].isEmpty()) {
                        final String parameterName = parameterArray[i - parameterOffset].getParameterName()
                                    + " [kg/Jahr " + row[7] + ']';
                        dataset.addValue(Float.parseFloat(row[i]),
                            parameterName,
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
