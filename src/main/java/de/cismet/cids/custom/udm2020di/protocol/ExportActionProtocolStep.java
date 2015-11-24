/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

import de.cismet.cids.custom.udm2020di.actions.remote.ExportAction;

import de.cismet.commons.gui.protocol.AbstractProtocolStep;
import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;
import de.cismet.commons.gui.protocol.ProtocolStepParameter;

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

    protected ExportAction tmpExportAction;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ExportActionProtocolStep object.
     */
    public ExportActionProtocolStep() {
        super();
    }

    /**
     * Creates a new ExportActionProtocolStep object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    public ExportActionProtocolStep(final ExportAction exportAction) {
        super();
        this.tmpExportAction = exportAction;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Set<ProtocolStepParameter> createParameters() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("creating serializable ExportAction Parameters");
        }
        final Set<ProtocolStepParameter> parameters = new HashSet<ProtocolStepParameter>();
        parameters.add(new ProtocolStepParameter(PARAMETER_EXPORT_ACTION, this.tmpExportAction));
        return parameters;
    }

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public ExportAction getExportAction() {
        final Object exportAction = super.getParameterValue(PARAMETER_EXPORT_ACTION);
        if (exportAction != null) {
            return (ExportAction)exportAction;
        } else {
            LOGGER.error("Export Action not found in parameter value set!");
            return null;
        }
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("visualizing ExportActionProtocolStepPanel");
        }
        return new ExportActionProtocolStepPanel(this.getExportAction());
    }
}
