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
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class MossIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    public static final ImageIcon MOSS_ICON = ImageUtilities.loadImageIcon(ImageUtil.getResourcePath(
                MossIconFactory.class,
                "grass_16.png"),
            false);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MossIconFactory object.
     */
    public MossIconFactory() {
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        return MOSS_ICON;
    }

    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        return MOSS_ICON;
    }

    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        return MOSS_ICON;
    }

    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return MOSS_ICON;
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return MOSS_ICON;
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        return MOSS_ICON;
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return MOSS_ICON;
    }
}
