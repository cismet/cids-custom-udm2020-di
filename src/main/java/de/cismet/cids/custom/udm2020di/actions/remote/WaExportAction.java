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

import de.cismet.cids.custom.udm2020di.serveractions.wa.WagwExportAction;
import de.cismet.cids.custom.udm2020di.serveractions.wa.WaowExportAction;
import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_EXPORTFORMAT_CSV;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_MESSSTELLEN;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_PARAMETER;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class WaExportAction extends AbstractExportAction {

    //~ Static fields/initializers ---------------------------------------------

    public static final String WAGW = de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.WAGW;
    public static final String WAOW = de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.WAOW;

    protected static final Logger LOG = Logger.getLogger(WaExportAction.class);

    //~ Instance fields --------------------------------------------------------

    protected Collection<String> messstellen;
    protected final String waSource;
    protected final String taskName;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WaExportAction object.
     *
     * @param  waSource     DOCUMENT ME!
     * @param  messstellen  DOCUMENT ME!
     * @param  parameters   DOCUMENT ME!
     */
    public WaExportAction(final String waSource,
            final Collection<String> messstellen,
            final Collection<Parameter> parameters) {
        super("Exportieren");

        if (waSource.equalsIgnoreCase(WAGW)) {
            this.waSource = waSource;
            taskName = WagwExportAction.TASK_NAME;
        } else if (waSource.equalsIgnoreCase(WAOW)) {
            this.waSource = waSource;
            taskName = WaowExportAction.TASK_NAME;
        } else {
            this.waSource = WAGW;
            taskName = WagwExportAction.TASK_NAME;
            LOG.error("unsupported WA Station Type: " + this.waSource);
        }

        this.parameters = parameters;
        this.messstellen = messstellen;
        this.exportFormat = PARAM_EXPORTFORMAT_CSV;
        this.setEnabled(!this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Frame frame;
        if (Component.class.isAssignableFrom(e.getSource().getClass())) {
            frame = (Frame)SwingUtilities.getRoot((Component)e.getSource());
        } else {
            LOG.warn("could not determine source frame of action");
            frame = JFrame.getFrames()[0];
        }

        if ((messstellen != null) && !messstellen.isEmpty()
                    && (parameters != null) && !parameters.isEmpty()) {
            LOG.info("perfoming " + waSource + " Export for " + messstellen.size() + " Messstellen and "
                        + parameters.size() + " parameters");

            final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                    new ServerActionParameter<Collection<String>>(PARAM_MESSSTELLEN, this.messstellen),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, waSource + "-export")
                };

            if (DownloadManagerDialog.showAskingForUserTitle(frame)) {
                final String filename = waSource + "-export";
                final String extension = this.getExtention(exportFormat);

                DownloadManager.instance()
                        .add(
                            new ExportActionDownload(
                                DownloadManagerDialog.getJobname(),
                                "",
                                filename,
                                extension,
                                taskName,
                                serverActionParameters));
            } else {
                LOG.warn(waSource + " Export Action aborted!");
            }
        } else {
            LOG.error("no PARAM_STANDORTE and PARAM_MESSWERTE server action parameters provided");
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
    public Collection<String> getMessstellen() {
        return messstellen;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  messstellen  DOCUMENT ME!
     */
    public void setMessstellen(final Collection<String> messstellen) {
        this.messstellen = messstellen;
    }
}
