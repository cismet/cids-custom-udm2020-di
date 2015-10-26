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

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WaowAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WaowStationSearch;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class OwSampleValuesPostFilterGui extends CommonSampleValuesPostFilterGui {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(OwSampleValuesPostFilterGui.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form OwSampleValuesPostFilterGui.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public OwSampleValuesPostFilterGui() throws IOException {
        super(new WaowAggregationValuesSearch(),
            new WaowStationSearch(),
            new ImageIcon(
                OwSampleValuesPostFilterGui.class.getResource(
                    "/de/cismet/cids/custom/udm2020di/treeicons/waves_16.png")),
            "WAOW_STATION");
        this.active = true;
    }
}
