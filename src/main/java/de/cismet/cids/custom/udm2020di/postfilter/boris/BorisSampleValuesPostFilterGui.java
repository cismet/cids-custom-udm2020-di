/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter.boris;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui;
import de.cismet.cids.custom.udm2020di.serversearch.boris.BorisAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.boris.BorisSiteSearch;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class BorisSampleValuesPostFilterGui extends CommonSampleValuesPostFilterGui {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(BorisSampleValuesPostFilterGui.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form BorisSampleValuesPostFilterGui.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public BorisSampleValuesPostFilterGui() throws IOException {
        super(new BorisAggregationValuesSearch(),
            new BorisSiteSearch(),
            new ImageIcon(
                BorisSampleValuesPostFilterGui.class.getResource(
                    "/de/cismet/cids/custom/udm2020di/treeicons/showel_16.png")),
            "BORIS_SITE");
        this.active = true;
    }
}
