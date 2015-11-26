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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.Action;

import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.Tag;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.server.actions.ServerActionParameter;

import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_EXPORTFORMAT;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_NAME;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_PARAMETER;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_STANDORTE;
import static de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.TASK_NAME;
import static de.cismet.cids.custom.udm2020di.treeicons.BorisSiteIconFactory.BORIS_SITE_ICON;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class BorisExportAction extends AbstractExportAction {

    //~ Static fields/initializers ---------------------------------------------

    protected static final String DEFAULT_EXPORTFILE = "boris-export";
    protected static final transient Logger LOGGER = Logger.getLogger(BorisExportAction.class);

    //~ Instance fields --------------------------------------------------------

    @Getter
    @Setter
    @JsonProperty(required = true)
    protected Collection<String> standorte;

    //~ Constructors -----------------------------------------------------------

// @Getter
// @Setter
// @JsonProperty(required = true)
// @JsonDeserialize(contentAs=Tag.class)
// protected Collection<Tag> tags;

    /**
     * Creates a new BorisExportAction object.
     *
     * @param  parameters  DOCUMENT ME!
     * @param  objectIds   DOCUMENT ME!
     * @param  standorte   DOCUMENT ME!
     */
    public BorisExportAction(final Collection<Parameter> parameters,
            final Collection<Long> objectIds,
            final Collection<String> standorte) {
        super(parameters, objectIds);

        this.standorte = standorte;
        this.exportFormat =
            de.cismet.cids.custom.udm2020di.serveractions.boris.BorisExportAction.PARAM_EXPORTFORMAT_CSV;
        super.putValue(Action.SMALL_ICON, BORIS_SITE_ICON);
        super.putValue(
            Action.SHORT_DESCRIPTION,
            NbBundle.getMessage(
                BorisExportAction.class,
                "BorisExportAction.description"));
    }

    /**
     * Creates a new BorisExportAction object.
     *
     * @param  parameters    DOCUMENT ME!
     * @param  objectIds     DOCUMENT ME!
     * @param  standorte     DOCUMENT ME!
     * @param  exportFormat  DOCUMENT ME!
     * @param  exportName    DOCUMENT ME!
     */
    @JsonCreator
    public BorisExportAction(@JsonProperty("parameters") final Collection<Parameter> parameters,
            @JsonProperty("objectIds") final Collection<Long> objectIds,
            @JsonProperty("standorte") final Collection<String> standorte,
            @JsonProperty("exportFormat") final String exportFormat,
            @JsonProperty("exportName") final String exportName) {
        this(parameters, objectIds, standorte);
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
     * Creates a new BorisExportAction object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    protected BorisExportAction(final BorisExportAction exportAction) {
        super(exportAction);
        this.standorte = new ArrayList<String>(exportAction.getStandorte());
    }

    /**
     * Creates a new BorisExportAction object.
     */
    private BorisExportAction() {
        super();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ServerActionParameter[] createServerActionParameters() {
        if ((standorte != null) && !standorte.isEmpty()
                    && (parameters != null) && !parameters.isEmpty()) {
            LOGGER.info("perfoming BORIS Export for " + standorte.size() + " standorte and "
                        + parameters.size() + " parameters");

            return new ServerActionParameter[] {
                    new ServerActionParameter<Collection<String>>(PARAM_STANDORTE, this.standorte),
                    new ServerActionParameter<Collection<Parameter>>(PARAM_PARAMETER, this.parameters),
                    new ServerActionParameter<String>(PARAM_EXPORTFORMAT, this.exportFormat),
                    new ServerActionParameter<String>(PARAM_NAME, "boris-export")
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
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            final com.fasterxml.jackson.databind.ObjectMapper mapper =
                new com.fasterxml.jackson.databind.ObjectMapper();
            mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
            final BorisExportAction borisExportAction = new BorisExportAction();
            borisExportAction.setStandorte(Arrays.asList(new String[] { "Standort 1", "Standort 2", "Standort 3" }));
            // borisExportAction.setTags(Arrays.asList(new Tag[]{new Tag(), new Tag()}));
            final String jsonString = mapper.writeValueAsString(borisExportAction);
            System.out.println(mapper.writeValueAsString(borisExportAction));

            final BorisExportAction exportAction = (BorisExportAction)mapper.readValue(jsonString, ExportAction.class);
            System.out.println(exportAction.getClass().getCanonicalName());
            System.out.println(exportAction.getStandorte().iterator().next().getClass());
            // System.out.println(exportAction.getTags().iterator().next().getClass());
            // System.out.println(exportAction.getTags().iterator().next().isSelected());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getClassId() {
        final MetaClass metaClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", "BORIS_SITE");
        if (metaClass != null) {
            return metaClass.getID();
        } else {
            LOGGER.error("could not retrieve BORIS_SITE class from UDM2020-DI!");
            return -1;
        }
    }

    @Override
    public ExportAction clone() throws CloneNotSupportedException {
        return new BorisExportAction(this);
    }
}
