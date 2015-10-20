/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.treeicons;

import Sirius.navigator.types.treenode.ClassTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.types.treenode.PureTreeNode;
import Sirius.navigator.ui.tree.CidsTreeObjectIconFactory;

import org.openide.util.ImageUtilities;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.ImageUtil;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé (pascal.dihe@cismet.de)
 * @version  $Revision$, $Date$
 */
public final class WaowStationIconFactory implements CidsTreeObjectIconFactory {

    //~ Instance fields --------------------------------------------------------

    private final ImageIcon waowStationIcon;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisSiteIconFactory object.
     */
    public WaowStationIconFactory() {
        this.waowStationIcon = ImageUtilities.loadImageIcon(ImageUtil.getResourcePath(
                    WaowStationIconFactory.class,
                    "waves_16.png"),
                false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        return waowStationIcon;
    }

    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        return waowStationIcon;
    }

    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        return waowStationIcon;
    }

    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return waowStationIcon;
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return waowStationIcon;
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        return waowStationIcon;
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return waowStationIcon;
    }
}
