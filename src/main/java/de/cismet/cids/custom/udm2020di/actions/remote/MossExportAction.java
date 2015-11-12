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
import java.awt.event.ActionEvent;

import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

import static de.cismet.cids.custom.udm2020di.indeximport.moss.MossImport.DEFAULT_IMPORTFILE;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT_CSV;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT_XLS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_OBJECT_IDS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_SAMPLE_IDS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.TASK_NAME;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class MossExportAction extends AbstractExportAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MossExportAction.class);
    protected static final String DEFAULT_EXPORTFILE = "moss-export";
    protected static final String ORIGINAL_EXPORTFILE = "konvert_join_95_10_final";

    //~ Instance fields --------------------------------------------------------

    protected Collection<Long> objectIds;
    protected Collection<String> sampleIds;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MossExportAction object.
     *
     * @param  objectIds   sites standorte DOCUMENT ME!
     * @param  sampleIds   DOCUMENT ME!
     * @param  parameters  DOCUMENT ME!
     */
    public MossExportAction(final Collection<Long> objectIds,
            final Collection<String> sampleIds,
            final Collection<Parameter> parameters) {
        super();

        this.sampleIds = sampleIds;
        this.objectIds = objectIds;
        this.parameters = parameters;
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
        return objectIds;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  sites  DOCUMENT ME!
     */
    public void setInstallations(final Collection<Long> sites) {
        this.objectIds = sites;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final Component component;
        if (Component.class.isAssignableFrom(e.getSource().getClass())) {
            component = (Component)e.getSource();
        } else {
            LOGGER.warn("could not determine source frame of action '" + TASK_NAME + "'");
            component = JFrame.getFrames()[0];
        }

        if ((objectIds != null) && (objectIds.size() > 0)
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming EPRTR Export for " + objectIds.size() + " sites and "
                        + parameters.size() + " parameters");

            final ServerActionParameter[] serverActionParameters = new ServerActionParameter[] {
                    new ServerActionParameter<Collection<Long>>(PARAM_OBJECT_IDS, this.objectIds),
                    new ServerActionParameter<Collection<String>>(PARAM_SAMPLE_IDS, this.sampleIds),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, DEFAULT_EXPORTFILE)
                };

            if (DownloadManagerDialog.showAskingForUserTitle(component)) {
                final String filename;
                final String extension = this.getExtention(exportFormat);
                ;

                if ((this.exportFormat == null) ? (PARAM_EXPORTFORMAT_XLS == null)
                                                : this.exportFormat.equals(PARAM_EXPORTFORMAT_XLS)) {
                    filename = ORIGINAL_EXPORTFILE;
                } else {
                    filename = DEFAULT_EXPORTFILE;
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Export filename is: " + filename + extension);
                }
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
            LOGGER.error("no PARAM_OBJECT_IDS and PARAM_SAMPLE_IDS server action parameters provided");
            JOptionPane.showMessageDialog(
                component,
                "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                "Datenexport",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}
