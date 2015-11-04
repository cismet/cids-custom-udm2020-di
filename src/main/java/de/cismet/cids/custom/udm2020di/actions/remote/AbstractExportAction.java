/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import java.util.Collection;

import javax.swing.AbstractAction;

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.widgets.ExportParameterSelectionPanel;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public abstract class AbstractExportAction extends AbstractAction implements ExportAction {

    //~ Instance fields --------------------------------------------------------

    protected Collection<Parameter> parameters;

    protected String exportFormat;

    protected String exportName;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AbstractExportAction object.
     */
    public AbstractExportAction() {
        super(org.openide.util.NbBundle.getMessage(
                ExportParameterSelectionPanel.class,
                "ExportParameterSelectionPanel.btnExport.text")); // NOI18N
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
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_CSV)) {
            return ".csv";
        } else if (exportFormat.equalsIgnoreCase(
                        de.cismet.cids.custom.udm2020di.serveractions.AbstractExportAction.PARAM_EXPORTFORMAT_SHP)) {
            // return ".shp";
            return ".zip";
        } else {
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
}
