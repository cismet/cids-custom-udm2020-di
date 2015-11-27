/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter.wa;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

import de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WaowAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WaowStationSearch;

import static de.cismet.cids.custom.udm2020di.treeicons.WaowStationIconFactory.WAOW_STATION_ICON;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class OwSampleValuesPostFilterGui extends CommonSampleValuesPostFilterGui {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form OwSampleValuesPostFilterGui.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public OwSampleValuesPostFilterGui() throws IOException {
        super(new WaowAggregationValuesSearch(),
            new WaowStationSearch(),
            WAOW_STATION_ICON,
            "WAOW_STATION");
        LOGGER = Logger.getLogger(OwSampleValuesPostFilterGui.class);
        this.active = true;
    }
}
