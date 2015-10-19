/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter.eprtr;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class ReleaseTypePostFilterGui extends NotificationPeriodPostFilterGui {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PollutantTagsPostFilterGui object.
     */
    public ReleaseTypePostFilterGui() {
        super();
        logger = Logger.getLogger(ReleaseTypePostFilterGui.class);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getTitle() {
        return org.openide.util.NbBundle.getMessage(ReleaseTypePostFilterGui.class,
                "ReleaseTypePostFilterGui.title");
    }

    @Override
    public Integer getDisplayOrderKeyPrio() {
        return super.getDisplayOrderKeyPrio() + 100;
    }

    @Override
    protected String getFilterTagGroup() {
        return "EPRTR.RELEASE_TYPE";
    }
}
