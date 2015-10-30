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

import org.jfree.data.general.Dataset;

import java.util.Collection;
import java.util.Map;

import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_STANDORTE;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.TASK_NAME;
import de.cismet.cids.custom.udm2020di.widgets.ChartVisualisationComponent;
import java.util.HashMap;
import org.jfree.data.time.TimeSeries;
/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class BorisVisualisationAction extends AbstractVisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(BorisVisualisationAction.class);

    //~ Instance fields --------------------------------------------------------

    protected final Collection<String> standorte;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisExportAction object.
     *
     * @param  standorte   DOCUMENT ME!
     * @param  parameters  DOCUMENT ME!
     */
    public BorisVisualisationAction(final Collection<String> standorte,
            final Collection<Parameter> parameters, 
            final ChartVisualisationComponent chartVisualisationComponent) {
        super("Exportieren", parameters, chartVisualisationComponent);

        this.standorte = standorte;
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] getServerActionParameters() {
        final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                new ServerActionParameter<Collection<String>>(PARAM_STANDORTE, this.standorte),
                new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                new ServerActionParameter<String>(
                    PARAM_EXPORTFORMAT,
                    de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV),
                new ServerActionParameter<String>(PARAM_NAME, "boris-export")
            };

        return serverActionParameters;
    }

    @Override
    protected String getTaskName() {
        return TASK_NAME;
    }

    @Override
    protected int getObjectsSize() {
        return (this.standorte != null) ? this.standorte.size() : 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<String> getStandorte() {
        return standorte;
    }

    @Override
    protected Map<String, Dataset> createDataset(final MappingIterator<String[]> mappingIterator) {
        LOGGER.debug("generating chart datasets for " + this.standorte.size() + " standorte");
        final Map<String, Dataset> datasets = new HashMap(this.standorte.size());
        final TimeSeries[] timeSeries = this.createTimeSeries();
        
        
      //  final TimeSeries[] timeSeries; = new TimeSeries("L&G European Index Trust", Month.class);
        while(mappingIterator.hasNext()) {
            final String[] row = mappingIterator.next();
            
        }
        
        
        return datasets;
        
    }

    @Override
    protected String getStationCaption(String stationPk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
