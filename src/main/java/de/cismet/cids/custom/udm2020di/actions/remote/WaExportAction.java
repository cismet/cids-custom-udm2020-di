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

import de.cismet.cids.custom.udm2020di.serveractions.wa.WagwExportAction;
import de.cismet.cids.custom.udm2020di.serveractions.wa.WaowExportAction;
import de.cismet.cids.custom.udm2020di.types.Parameter;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_EXPORTFORMAT_CSV;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_MESSSTELLEN;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.wa.WaExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.treeicons.WagwStationIconFactory.WAGW_STATION_ICON;
import static de.cismet.cids.custom.udm2020di.treeicons.WaowStationIconFactory.WAOW_STATION_ICON;

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

    protected static final Logger LOGGER = Logger.getLogger(WaExportAction.class);

    //~ Instance fields --------------------------------------------------------

    @Getter
    @Setter
    @JsonProperty(required = true)
    protected Collection<String> messstellen;
    @JsonProperty(required = true)
    protected final String waSource;
    protected final String taskName;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WaExportAction object.
     *
     * @param  parameters   DOCUMENT ME!
     * @param  objectIds    DOCUMENT ME!
     * @param  messstellen  DOCUMENT ME!
     * @param  waSource     DOCUMENT ME!
     */

    public WaExportAction(
            final Collection<Parameter> parameters,
            final Collection<Long> objectIds,
            final Collection<String> messstellen,
            final String waSource) {
        super(parameters, objectIds);

        this.messstellen = messstellen;
        this.exportFormat = PARAM_EXPORTFORMAT_CSV;
        this.waSource = (waSource.equalsIgnoreCase(WAGW)) ? WAGW : WAOW;

        if (this.waSource.equalsIgnoreCase(WAGW)) {
            taskName = WagwExportAction.TASK_NAME;
            super.putValue(Action.SMALL_ICON, WAGW_STATION_ICON);
            super.putValue(
                Action.SHORT_DESCRIPTION,
                NbBundle.getMessage(
                    WaExportAction.class,
                    "WaGwExportAction.description"));
        } else {
            taskName = WaowExportAction.TASK_NAME;
            super.putValue(Action.SMALL_ICON, WAOW_STATION_ICON);
            super.putValue(
                Action.SHORT_DESCRIPTION,
                NbBundle.getMessage(
                    WaExportAction.class,
                    "WaOwExportAction.description"));
        }
    }

    /**
     * Creates a new WaExportAction object.
     *
     * @param  parameters    DOCUMENT ME!
     * @param  objectIds     DOCUMENT ME!
     * @param  messstellen   DOCUMENT ME!
     * @param  waSource      DOCUMENT ME!
     * @param  exportFormat  DOCUMENT ME!
     * @param  exportName    DOCUMENT ME!
     */
    @JsonCreator
    public WaExportAction(@JsonProperty("parameters") final Collection<Parameter> parameters,
            @JsonProperty("objectIds") final Collection<Long> objectIds,
            @JsonProperty("messstellen") final Collection<String> messstellen,
            @JsonProperty("waSource") final String waSource,
            @JsonProperty("exportFormat") final String exportFormat,
            @JsonProperty("exportName") final String exportName) {
        this(parameters, objectIds, messstellen, waSource);
        this.exportFormat = exportFormat;
        this.exportName = exportName;
        this.protocolEnabled = false;
        this.protocolAction = true;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(this.waSource + " ExportAction object with " + parameters.size() + " parameters and "
                        + objectIds.size() + " objects restored from JSON");
        }
    }

    /**
     * Creates a new WaExportAction object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    protected WaExportAction(final WaExportAction exportAction) {
        super(exportAction);
        this.messstellen = new ArrayList<String>(exportAction.getMessstellen());
        this.waSource = exportAction.waSource;
        this.taskName = exportAction.taskName;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] createServerActionParameters() {
        if ((messstellen != null) && !messstellen.isEmpty()
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming " + waSource + " Export for " + messstellen.size() + " Messstellen and "
                        + parameters.size() + " parameters");

            return new ServerActionParameter[] {
                    new ServerActionParameter<Collection<String>>(PARAM_MESSSTELLEN, this.messstellen),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, waSource + "-export")
                };
        }
        return null;
    }

    @Override
    protected String getTaskname() {
        return taskName;
    }

    @Override
    protected String getDefaultExportName() {
        return waSource + "-export";
    }

    @Override
    public int getClassId() {
        final MetaClass metaClass;
        if (this.waSource.equalsIgnoreCase(WAGW)) {
            metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", "WAGW_STATION");
        } else {
            metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", "WAOW_STATION");
        }

        if (metaClass != null) {
            return metaClass.getID();
        } else {
            LOGGER.error("could not retrieve WAxW_STATION class from UDM2020-DI!");
            return -1;
        }
    }

    @Override
    public ExportAction clone() throws CloneNotSupportedException {
        return new WaExportAction(this);
    }
}
