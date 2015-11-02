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

import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_EXPORTFORMAT_CSV;
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
public class EprtrExportAction extends AbstractExportAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(EprtrExportAction.class);

    //~ Instance fields --------------------------------------------------------

    protected Collection<Long> installations;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  installations  standorte DOCUMENT ME!
     * @param  parameters     DOCUMENT ME!
     */
    public EprtrExportAction(final Collection<Long> installations,
            final Collection<Parameter> parameters) {
        super();

        this.parameters = parameters;
        this.installations = installations;
        this.exportFormat = PARAM_EXPORTFORMAT_CSV;

        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<Long> getInstallations() {
        return installations;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  installations  DOCUMENT ME!
     */
    public void setInstallations(final Collection<Long> installations) {
        this.installations = installations;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final Frame frame;
        if (Component.class.isAssignableFrom(e.getSource().getClass())) {
            // FIXME: support for jdialog
            frame = (Frame)SwingUtilities.getRoot((Component)e.getSource());
        } else {
            LOGGER.warn("could not determine source frame of action");
            frame = JFrame.getFrames()[0];
        }

        if ((installations != null) && (installations.size() > 0)
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming EPRTR Export for " + installations.size() + " installations and "
                        + parameters.size() + " parameters");

            final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                    new ServerActionParameter<Collection<Long>>(PARAM_INSTALLATIONS, this.installations),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, "eprtr-export")
                };

            if (DownloadManagerDialog.showAskingForUserTitle(frame)) {
                final String filename = "eprtr-export";
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
            LOGGER.error("no PARAM_INSTALLATIONS and PARAM_PARAMETER server action parameters provided");
            JOptionPane.showMessageDialog(
                frame,
                "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                "Datenexport",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
