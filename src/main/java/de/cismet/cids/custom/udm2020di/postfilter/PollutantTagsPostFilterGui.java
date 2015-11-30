/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import org.apache.log4j.Logger;

import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import javax.swing.ImageIcon;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class PollutantTagsPostFilterGui extends CommonTagsPostFilterGui {

    //~ Static fields/initializers ---------------------------------------------

    public static final String TAG_GROUP = "POLLUTANT";

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PollutantTagsPostFilterGui object.
     */
    public PollutantTagsPostFilterGui() {
        super();
        LOGGER = Logger.getLogger(PollutantTagsPostFilterGui.class);
        icon = new ImageIcon(getClass().getResource(
                    NbBundle.getMessage(
                        CommonTagsPostFilterGui.class,
                        "PollutantTagsPostFilterGui.icon")));
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getTitle() {
        return NbBundle.getMessage(
                CommonTagsPostFilterGui.class,
                "PollutantTagsPostFilterGui.title");
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Integer getDisplayOrderKeyPrio() {
        return super.getDisplayOrderKeyPrio() - 10;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    protected String getFilterTagGroup() {
        return TAG_GROUP;
    }
}
