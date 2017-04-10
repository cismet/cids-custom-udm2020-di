/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import Sirius.navigator.connection.SessionManager;

import Sirius.util.image.ImageAnnotator;

import org.apache.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.tools.gui.downloadmanager.AbstractDownload;

/**
 * Generic Server Action Result Download.
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 * @see      TifferAction
 * @see      ImageAnnotator
 */
public class ExportActionDownload extends AbstractDownload {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(ExportActionDownload.class);

    //~ Instance fields --------------------------------------------------------

    protected final ServerActionParameter[] actionParameters;
    protected final String actionName;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TifferDownload object.
     *
     * @param  title             DOCUMENT ME!
     * @param  directory         DOCUMENT ME!
     * @param  filename          DOCUMENT ME!
     * @param  extension         DOCUMENT ME!
     * @param  actionName        DOCUMENT ME!
     * @param  actionParameters  DOCUMENT ME!
     */
    public ExportActionDownload(
            final String title,
            final String directory,
            final String filename,
            final String extension,
            final String actionName,
            final ServerActionParameter... actionParameters) {
        this.directory = directory;
        this.title = title;
        this.actionName = actionName;
        this.actionParameters = actionParameters;

        status = State.WAITING;
        super.determineDestinationFile(filename, extension);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    @Override
    public void run() {
        if (status != State.WAITING) {
            return;
        }

        if ((actionParameters == null) || (actionParameters.length == 0)) {
            final String msg = "No server action parameters specified, the action '"
                        + this.actionName + "' cannot be executed";
            log.error(msg);
            error(new Exception(msg));
            return;
        }

        status = State.RUNNING;

        stateChanged();

        try {
            final Object result = SessionManager.getProxy()
                        .executeTask(
                            this.actionName,
                            SessionManager.getSession().getUser().getDomain(),
                            null,
                            this.actionParameters);
            if (result != null) {
                LOG.info("Exporting result of action '"
                            + this.actionName + "' to file '" + this.fileToSaveTo.getAbsolutePath() + "'");

                final Path filePath = Paths.get(fileToSaveTo.toURI());

                if (byte[].class.isAssignableFrom(result.getClass())
                            || Byte[].class.isAssignableFrom(result.getClass())) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("writing binary file");
                    }
                    Files.write(filePath, (byte[])result, StandardOpenOption.CREATE);
                } else {
                    Files.write(filePath, result.toString().getBytes("UTF-8"), StandardOpenOption.CREATE);
                }
            } else {
                final String msg = "Nothing returned by Action '" + this.actionName
                            + "'. Check its log to see what went wrong.";
                log.error(msg);
                error(new Exception(msg));
            }
        } catch (Exception ex) {
            final String msg = "Error during execution of action '" + this.actionName + "'";

            log.error(
                msg,
                ex);
            error(ex);
        }

        if (status == State.RUNNING) {
            status = State.COMPLETED;
            stateChanged();
        }
    }
}
