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
import de.cismet.cids.custom.udm2020di.serversearch.wa.WagwAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WagwStationSearch;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class GwSampleValuesPostFilterGui extends CommonSampleValuesPostFilterGui {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(GwSampleValuesPostFilterGui.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GwSampleValuesPostFilterGui.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public GwSampleValuesPostFilterGui() throws IOException {
        super(
            new WagwAggregationValuesSearch(),
            new WagwStationSearch(),
            new ImageIcon(
                GwSampleValuesPostFilterGui.class.getResource(
                    "/de/cismet/cids/custom/udm2020di/treeicons/waves_mirror_16.png")),
            "WAGW_STATION");
        this.active = true;
    }
}
