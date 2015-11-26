/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import org.apache.log4j.Logger;

import de.cismet.cids.custom.udm2020di.actions.remote.ExportAction;

import de.cismet.commons.gui.protocol.AbstractProtocolStep;
import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class ExportActionProtocolStep extends AbstractProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(ExportActionProtocolStep.class);
    private static final String PARAMETER_EXPORT_ACTION = "ExportAction";

    @JsonIgnore
    protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            "ExportAction",
            "comment step protocol",
            ExportActionProtocolStep.class.getCanonicalName());

    //~ Instance fields --------------------------------------------------------

    @Getter
    @JsonProperty(required = true)
    protected final ExportAction exportAction;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ExportActionProtocolStep object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    @JsonCreator
    public ExportActionProtocolStep(final ExportAction exportAction) {
        super();
        this.exportAction = exportAction;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("visualizing ExportActionProtocolStepPanel");
        }
        return new ExportActionProtocolStepPanel(this.getExportAction());
    }
}
