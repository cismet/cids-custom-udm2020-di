/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.cismet.cids.custom.udm2020di.protocol.ExportActionProtocolStep;
import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.commons.gui.protocol.ProtocolHandler;

import de.cismet.tools.gui.downloadmanager.DownloadManager;
import de.cismet.tools.gui.downloadmanager.DownloadManagerDialog;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public abstract class AbstractExportAction extends AbstractAction implements ExportAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static transient Logger LOGGER = Logger.getLogger(MossExportAction.class);

    //~ Instance fields --------------------------------------------------------

    protected Collection<Parameter> parameters;

    protected Collection<Long> objectIds;

    protected String exportFormat;

    protected String exportName;

    protected boolean protocolEnabled = true;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractExportAction object.
     */
    protected AbstractExportAction() {
        super(NbBundle.getMessage(
                AbstractExportAction.class,
                "AbstractExportAction.name")); // NOI18N
    }

    /**
     * Creates a new AbstractExportAction object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    protected AbstractExportAction(final AbstractExportAction exportAction) {
        this();
        this.parameters = new ArrayList<Parameter>(exportAction.getParameters().size());
        for (final Parameter parameter : exportAction.getParameters()) {
            this.parameters.add(new Parameter(parameter));
        }
        this.objectIds = new ArrayList<Long>(exportAction.getObjectIds().size());
        this.exportFormat = exportAction.getExportFormat();
        this.exportName = exportAction.getExportName();
        this.protocolEnabled = exportAction.isProtocolEnabled();
        super.setEnabled(!this.parameters.isEmpty());
        super.putValue(Action.SMALL_ICON, exportAction.getValue(Action.SMALL_ICON));
        super.putValue(Action.SHORT_DESCRIPTION, exportAction.getValue(Action.SHORT_DESCRIPTION));
    }

    /**
     * Creates a new AbstractExportAction object.
     *
     * @param  parameters  DOCUMENT ME!
     * @param  objectIds   DOCUMENT ME!
     */
    protected AbstractExportAction(final Collection<Parameter> parameters,
            final Collection<Long> objectIds) {
        this();
        this.parameters = parameters;
        this.objectIds = objectIds;
        super.setEnabled((this.parameters != null) && !this.parameters.isEmpty());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   exportFormat  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected String getExtention(final String exportFormat) {
        if (exportFormat.equalsIgnoreCase(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_XLSX)) {
            return ".xlsx";
        } else if (exportFormat.equalsIgnoreCase(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_XLS)) {
            return ".xls";
        } else if (exportFormat.equalsIgnoreCase(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV)) {
            return ".csv";
        } else if (exportFormat.equalsIgnoreCase(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_SHP)) {
            // return ".shp";
            return ".zip";
        } else {
            LOGGER.warn("unsupported export format '" + exportFormat + "', setting extension to '.bin'");
            return ".bin";
        }
    }

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
     * @param  parameters  DOCUMENT ME!
     */
    @Override
    public void setParameters(final Collection<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Collection<Long> getObjectIds() {
        return objectIds;
    }

    @Override
    public void setObjectIds(final Collection<Long> objectIds) {
        this.objectIds = objectIds;
    }

    /**
     * Get the value of exportFormat.
     *
     * @return  the value of exportFormat
     */
    @Override
    public String getExportFormat() {
        return exportFormat;
    }

    /**
     * Set the value of exportFormat.
     *
     * @param  exportFormat  new value of exportFormat
     */
    @Override
    public void setExportFormat(final String exportFormat) {
        this.exportFormat = exportFormat;
    }

    /**
     * Get the value of exportName.
     *
     * @return  the value of exportName
     */
    @Override
    public String getExportName() {
        return exportName;
    }

    /**
     * Set the value of exportName.
     *
     * @param  exportName  new value of exportName
     */
    @Override
    public void setExportName(final String exportName) {
        this.exportName = exportName;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract ServerActionParameter[] createServerActionParameters();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract String getTaskname();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected abstract String getDefaultExportName();

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Component component;
        if (Component.class.isAssignableFrom(e.getSource().getClass())) {
            component = (Component)e.getSource();
        } else {
            LOGGER.warn("could not determine source frame of action");
            component = JFrame.getFrames()[0];
        }

        final ServerActionParameter[] serverActionParameters = this.createServerActionParameters();
        if ((serverActionParameters != null) && (serverActionParameters.length > 0)) {
            if (DownloadManagerDialog.getInstance().showAskingForUserTitleDialog(component)) {
                final String jobname = DownloadManagerDialog.getInstance().getJobName();
                this.setExportName(((jobname != null) && !jobname.isEmpty()) ? jobname : this.getDefaultExportName());
            }

            if ((this.getExportName() == null) || this.getExportName().isEmpty()) {
                this.setExportName(this.getDefaultExportName());
            }

            final String extension = this.getExtention(this.getExportFormat());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("export filename is " + this.getExportName() + "." + extension);
            }
            DownloadManager.instance()
                    .add(
                        new ExportActionDownload(
                            this.getExportName(),
                            "",
                            this.getExportName(),
                            extension,
                            this.getTaskname(),
                            serverActionParameters));

            if (this.isProtocolEnabled()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("saving export settings to protocol");
                }
                ProtocolHandler.getInstance().recordStep(new ExportActionProtocolStep(AbstractExportAction.this));
            }
        } else {
            LOGGER.error("no server action parameters provided");
            JOptionPane.showMessageDialog(
                component,
                "<html><p>Bitte wählen Sie mindestens einen Parameter aus.</p></html>",
                "Datenexport",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public boolean isProtocolEnabled() {
        return this.protocolEnabled && ProtocolHandler.getInstance().isRecordEnabled();
    }

    @Override
    public void setProtocolEnabled(final boolean protocolEnabled) {
        this.protocolEnabled = protocolEnabled;
    }

    @Override
    public ImageIcon getIcon() {
        final Object icon = this.getValue(Action.SMALL_ICON);
        if ((icon != null) && ImageIcon.class.isAssignableFrom(icon.getClass())) {
            return (ImageIcon)icon;
        }

        return null;
    }

    @Override
    public String getTitle() {
        final Object title = this.getValue(Action.SHORT_DESCRIPTION);
        if (title != null) {
            return title.toString();
        }

        return null;
    }
}
