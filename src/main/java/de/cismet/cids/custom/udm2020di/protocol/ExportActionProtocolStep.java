/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class ExportActionProtocolStep extends AbstractProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            "ExportAction",
            "comment step protocol",
            ExportActionProtocolStep.class.getCanonicalName());

    //~ Instance fields --------------------------------------------------------

    protected ExportAction exportAction;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ExportActionProtocolStep object.
     */
    public ExportActionProtocolStep() {
    }

    /**
     * Creates a new ExportActionProtocolStep object.
     *
     * @param  exportAction  DOCUMENT ME!
     */
    public ExportActionProtocolStep(final ExportAction exportAction) {
        this.exportAction = exportAction;
        this.exportAction.setProtocolEnabled(false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    protected void copyParams(final AbstractProtocolStep other) {
        super.copyParams(other);
        this.setExportAction(((ExportActionProtocolStep)other).getExportAction());
    }
}
