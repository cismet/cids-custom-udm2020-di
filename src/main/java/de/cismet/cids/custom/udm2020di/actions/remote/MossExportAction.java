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

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Action;

import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT_CSV;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_EXPORTFORMAT_XLS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_OBJECT_IDS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.PARAM_SAMPLE_IDS;
import static de.cismet.cids.custom.udm2020di.serveractions.moss.MossExportAction.TASK_NAME;
import static de.cismet.cids.custom.udm2020di.treeicons.MossIconFactory.MOSS_ICON;

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

    @JsonProperty(required = true)
    @Getter
    @Setter
    protected Collection<String> sampleIds;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MossExportAction object.
     *
     * @param  parameters  DOCUMENT ME!
     * @param  objectIds   sites standorte DOCUMENT ME!
     * @param  sampleIds   DOCUMENT ME!
     */
    public MossExportAction(final Collection<Parameter> parameters,
            final Collection<Long> objectIds,
            final Collection<String> sampleIds) {
        super(parameters, objectIds);

        this.sampleIds = sampleIds;
        this.objectIds = objectIds;
        this.exportFormat = PARAM_EXPORTFORMAT_CSV;
        super.putValue(Action.SMALL_ICON, MOSS_ICON);
        super.putValue(
            Action.SHORT_DESCRIPTION,
            NbBundle.getMessage(
                MossExportAction.class,
                "MossExportAction.description"));
    }

    /**
     * Creates a new MossExportAction object.
     *
     * @param  parameters    DOCUMENT ME!
     * @param  objectIds     DOCUMENT ME!
     * @param  sampleIds     DOCUMENT ME!
     * @param  exportFormat  DOCUMENT ME!
     * @param  exportName    DOCUMENT ME!
     */
    @JsonCreator
    public MossExportAction(@JsonProperty("parameters") final Collection<Parameter> parameters,
            @JsonProperty("objectIds") final Collection<Long> objectIds,
            @JsonProperty("sampleIds") final Collection<String> sampleIds,
            @JsonProperty("exportFormat") final String exportFormat,
            @JsonProperty("exportName") final String exportName) {
        this(parameters, objectIds, sampleIds);
        this.exportFormat = exportFormat;
        this.exportName = exportName;
        this.protocolEnabled = false;
        this.protocolAction = true;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("ExportAction object with " + parameters.size() + " parameters and "
                        + objectIds.size() + " objects restored from JSON");
        }
    }

    /**
     * Creates a new MossExportAction object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    protected MossExportAction(final MossExportAction exportAction) {
        super(exportAction);
        this.sampleIds = new ArrayList<String>(exportAction.getSampleIds());
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getExportName() {
        if (this.exportFormat.equals(PARAM_EXPORTFORMAT_XLS)) {
            return ORIGINAL_EXPORTFILE;
        }

        return super.getExportName();
    }

    @Override
    protected ServerActionParameter[] createServerActionParameters() {
        if ((objectIds != null) && (objectIds.size() > 0)
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming MOSS Export for " + objectIds.size() + " sites and "
                        + parameters.size() + " parameters");

            return new ServerActionParameter[] {
                    new ServerActionParameter<Collection<Long>>(PARAM_OBJECT_IDS, this.objectIds),
                    new ServerActionParameter<Collection<String>>(PARAM_SAMPLE_IDS, this.sampleIds),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, DEFAULT_EXPORTFILE)
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

    /**
     * DOCUMENT ME!
     *
     * @param  sampleIds  DOCUMENT ME!
     */
    public void setSampleIds(final Collection<String> sampleIds) {
        this.sampleIds = sampleIds;
    }

    @Override
    public int getClassId() {
        final MetaClass metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", "MOSS");
        if (metaClass != null) {
            return metaClass.getID();
        } else {
            LOGGER.error("could not retrieve MOSS class from UDM2020-DI!");
            return -1;
        }
    }

    @Override
    public ExportAction clone() throws CloneNotSupportedException {
        return new MossExportAction(this);
    }
}
