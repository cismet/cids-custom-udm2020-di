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

import de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui;
import de.cismet.cids.custom.udm2020di.serversearch.boris.BorisAggregationValuesSearch;
import de.cismet.cids.custom.udm2020di.serversearch.boris.BorisSiteSearch;

import static de.cismet.cids.custom.udm2020di.treeicons.BorisSiteIconFactory.BORIS_SITE_ICON;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class BorisSampleValuesPostFilterGui extends CommonSampleValuesPostFilterGui {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form BorisSampleValuesPostFilterGui.
     *
     * @throws  IOException  DOCUMENT ME!
     */
    public BorisSampleValuesPostFilterGui() throws IOException {
        super(new BorisAggregationValuesSearch(),
            new BorisSiteSearch(),
            BORIS_SITE_ICON,
            "BORIS_SITE");
        LOGGER = Logger.getLogger(BorisSampleValuesPostFilterGui.class);
        this.active = true;
    }
}
