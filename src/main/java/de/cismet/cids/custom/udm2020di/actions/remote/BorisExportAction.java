/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

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
public class BorisExportAction extends AbstractExportAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(BorisExportAction.class);

    //~ Instance fields --------------------------------------------------------

    protected Collection<String> standorte;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisExportAction object.
     *
     * @param  standorte   DOCUMENT ME!
     * @param  parameters  DOCUMENT ME!
     */
    public BorisExportAction(final Collection<String> standorte,
            final Collection<Parameter> parameters) {
        super("Exportieren");

        this.parameters = parameters;
        this.standorte = standorte;
        this.exportFormat =
            de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_EXPORTFORMAT_CSV;
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final Frame frame;
        if (Component.class.isAssignableFrom(e.getSource().getClass())) {
            frame = (Frame)SwingUtilities.getRoot((Component)e.getSource());
        } else {
            LOGGER.warn("could not dtermine source frame of action");
            frame = JFrame.getFrames()[0];
        }

        if ((standorte != null) && !standorte.isEmpty()
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming BORIS Export for " + standorte.size() + " standorte and "
                        + parameters.size() + " parameters");

            final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                    new ServerActionParameter<Collection<String>>(PARAM_STANDORTE, this.standorte),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, "boris-export")
                };

            if (DownloadManagerDialog.showAskingForUserTitle(frame)) {
                final String filename = "boris-export";
                final String extension = this.getExtention(exportFormat);

                DownloadManager.instance()
                        .add(
                            new ExportActionDownload(
                                DownloadManagerDialog.getJobname(),
                                "",
                                filename,
                                extension,
                                TASK_NAME,
                                serverActionParameters));
            } else {
                LOGGER.warn("Export Action aborted!");
            }
        } else {
            LOGGER.error("no PARAM_STANDORTE and PARAM_PARAMETER server action parameters provided");
            JOptionPane.showMessageDialog(
                frame,
                "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                "Datenexport",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<String> getStandorte() {
        return standorte;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  standorte  DOCUMENT ME!
     */
    public void setStandorte(final Collection<String> standorte) {
        this.standorte = standorte;
    }
}
