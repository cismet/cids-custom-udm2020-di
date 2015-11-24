/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.apache.log4j.Logger;

import java.util.Collection;

import de.cismet.cids.custom.udm2020di.actions.remote.WaExportAction;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class WaowStationAggregationRenderer extends WagwStationAggregationRenderer {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(WaowStationAggregationRenderer.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WaowStationAggregationRenderer object.
     */
    public WaowStationAggregationRenderer() {
        super();
        this.stationType = WaExportAction.WAOW;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getTitle() {
        String desc = "";
        final Collection<CidsBean> beans = this.getCidsBeans();
        if ((beans != null) && (beans.size() > 0)) {
            desc += beans.size() + " Oberflächengewässer Messstellen ausgewählt";
        }
        return desc;
    }

    @Override
    protected WaowStationAggregationRenderer getOuter() {
        return WaowStationAggregationRenderer.this;
    }
}
