/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter.moss;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui;
import de.cismet.cids.custom.udm2020di.postfilter.wa.*;
import de.cismet.cids.custom.udm2020di.serversearch.moss.MossAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.moss.MossSearch;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WagwAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.wa.WagwStationSearch;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class MossSampleValuesPostFilterGui extends CommonSampleValuesPostFilterGui {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(MossSampleValuesPostFilterGui.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GwSampleValuesPostFilterGui.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public MossSampleValuesPostFilterGui() throws IOException {
        super(new MossAggregationValuesSearch(),
            new MossSearch(),
            new ImageIcon(
                MossSampleValuesPostFilterGui.class.getResource(
                    "/de/cismet/cids/custom/udm2020di/treeicons/moss_16.png")),
            "MOSS");
        this.active = true;
    }
}
