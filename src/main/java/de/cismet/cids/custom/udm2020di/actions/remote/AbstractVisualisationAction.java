/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import Sirius.navigator.connection.SessionManager;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.apache.log4j.Logger;

import org.jfree.data.general.Dataset;

import java.awt.Component;
import java.awt.event.ActionEvent;

import java.util.Collection;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.server.actions.ServerActionParameter;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public abstract class AbstractVisualisationAction extends AbstractAction implements VisualisationAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final CsvMapper MAPPER = new CsvMapper();

    protected static Logger LOGGER = Logger.getLogger(AbstractVisualisationAction.class);

    //~ Instance fields --------------------------------------------------------

    protected final Collection<Parameter> parameters;
    protected final CsvSchema csvSchema;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractExportAction object.
     *
     * @param  name        DOCUMENT ME!
     * @param  parameters  DOCUMENT ME!
     */
    public AbstractVisualisationAction(final String name, final Collection<Parameter> parameters) {
        super(name);
        this.parameters = parameters;
        this.csvSchema = CsvSchema.emptySchema().withHeader();
    }

    //~ Methods ----------------------------------------------------------------

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
     * @param   mappingIterator  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract Map<String, Dataset> createDataset(MappingIterator<String[]> mappingIterator);

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

                                final MappingIterator<String[]> mappingIterator = MAPPER.readerFor(Map.class)
                                            .with(csvSchema)
                                            .readValues(result.toString());

                                final Map<String, Dataset> datasets = createDataset(mappingIterator);
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER.debug(datasets.size() + " datasets created from CSV File");
                                }
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
                            final Map<String, Dataset> chartMap = this.get();
                            if (chartMap == null) {
                            } else {
                                JOptionPane.showMessageDialog((Component)e.getSource(),
                                    "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                                    "Datenexport",
                                    JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (final Exception ex) {
                            LOGGER.error(ex.getMessage(), ex);
                        }
                    }
                };
        } else {
            LOGGER.error("no server action parameters provided");
            JOptionPane.showMessageDialog((Component)e.getSource(),
                "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                "Datenexport",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
