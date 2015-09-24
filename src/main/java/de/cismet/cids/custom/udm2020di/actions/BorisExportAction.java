/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.widgets.export.ExportActionDownload;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_STANDORTE;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.TASK_NAME;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public class BorisExportAction extends AbstractAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger log = Logger.getLogger(BorisExportAction.class);

    //~ Instance fields --------------------------------------------------------

    protected Collection<String> standorte;

    protected Collection<Parameter> parameters;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisExportAction object.
     *
     * @param  standorte   DOCUMENT ME!
     * @param  parameters  DOCUMENT ME!
     */
    public BorisExportAction(final Collection<String> standorte, final Collection<Parameter> parameters) {
        super("Exportieren");

        this.standorte = standorte;
        this.parameters = parameters;
        this.setEnabled(!parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        log.info("perfoming BORIS Export for  for " + standorte.size() + " standorte and "
                    + parameters.size() + " parameters");

        final Frame frame;
        if (Component.class.isAssignableFrom(e.getSource().getClass())) {
            frame = (Frame)SwingUtilities.getRoot((Component)e.getSource());
        } else {
            log.warn("could not dtermine source frame of action");
            frame = JFrame.getFrames()[0];
        }

        if ((standorte != null) && !standorte.isEmpty()
                    && (parameters != null) && !parameters.isEmpty()) {
            final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                    new ServerActionParameter<Collection<String>>(PARAM_STANDORTE, standorte),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, parameters)
                };

            if (DownloadManagerDialog.showAskingForUserTitle(frame)) {
                final String filename = "boris-export";
                final String extension = "csv";

                DownloadManager.instance()
                        .add(
                            new ExportActionDownload(
                                DownloadManagerDialog.getJobname(),
                                "",
                                filename,
                                "."
                                + extension,
                                TASK_NAME,
                                serverActionParameters));
            } else {
                log.warn("Export Action aborted!");
            }
        } else {
            log.error("no PARAM_STANDORTE and PARAM_PARAMETER server action parameters provided");
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

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Collection<Parameter> getParameters() {
        return parameters;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  parameters  DOCUMENT ME!
     */
    public void setParameters(final Collection<Parameter> parameters) {
        this.parameters = parameters;
    }
}
