/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import Sirius.server.middleware.types.MetaClass;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Action;

import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_EXPORTFORMAT_CSV;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_INSTALLATIONS;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.eprtr.EprtrExportAction.TASK_NAME;
import static de.cismet.cids.custom.udm2020di.treeicons.EprtrInstallationIconFactory.EPRTR_INSTALLATION_ICON;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class EprtrExportAction extends AbstractExportAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final transient Logger LOGGER = Logger.getLogger(EprtrExportAction.class);
    protected static final String DEFAULT_EXPORTFILE = "eprtr-export";

    //~ Instance fields --------------------------------------------------------

    @JsonProperty(required = true)
    protected Collection<Long> installations;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    public EprtrExportAction(final EprtrExportAction exportAction) {
        super(exportAction);
        this.installations = new ArrayList<Long>(exportAction.getInstallations());
    }

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  parameters     DOCUMENT ME!
     * @param  objectIds      DOCUMENT ME!
     * @param  installations  standorte DOCUMENT ME!
     */
    public EprtrExportAction(
            final Collection<Parameter> parameters,
            final Collection<Long> objectIds,
            final Collection<Long> installations) {
        super(parameters, objectIds);

        this.installations = installations;
        this.exportFormat = PARAM_EXPORTFORMAT_CSV;
        super.putValue(Action.SMALL_ICON, EPRTR_INSTALLATION_ICON);
        super.putValue(
            Action.SHORT_DESCRIPTION,
            NbBundle.getMessage(
                EprtrExportAction.class,
                "EprtrExportAction.description"));
    }

    /**
     * Creates a new EprtrExportAction object.
     *
     * @param  parameters     DOCUMENT ME!
     * @param  objectIds      DOCUMENT ME!
     * @param  installations  DOCUMENT ME!
     * @param  exportFormat   DOCUMENT ME!
     * @param  exportName     DOCUMENT ME!
     */
    @JsonCreator
    public EprtrExportAction(
            final Collection<Parameter> parameters,
            final Collection<Long> objectIds,
            final Collection<Long> installations,
            final String exportFormat,
            final String exportName) {
        this(parameters, objectIds, installations);
        this.exportFormat = exportFormat;
        this.exportName = exportName;
        this.protocolEnabled = false;
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

    @Override
    protected ServerActionParameter[] createServerActionParameters() {
        if ((installations != null) && (installations.size() > 0)
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming EPRTR Export for " + installations.size() + " installations and "
                        + parameters.size() + " parameters");

            return new ServerActionParameter[] {
                    new ServerActionParameter<Collection<Long>>(PARAM_INSTALLATIONS, this.installations),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, "eprtr-export")
                };
        }
        return null;
    }

    @Override
    protected String getTaskname() {
        return TASK_NAME;
    }

    @Override
    protected String getDefaultExportName() {
        return DEFAULT_EXPORTFILE;
    }

    @Override
    public int getClassId() {
        final MetaClass metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", "EPRTR_INSTALLATION");
        if (metaClass != null) {
            return metaClass.getID();
        } else {
            LOGGER.error("could not retrieve EPRTR_INSTALLATION class from UDM2020-DI!");
            return -1;
        }
    }
}
