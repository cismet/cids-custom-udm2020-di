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

    //~ Instance fields --------------------------------------------------------

    private final ImageIcon mossIcon;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MossIconFactory object.
     */
    public MossIconFactory() {
        this.mossIcon = ImageUtilities.loadImageIcon(ImageUtil.getResourcePath(MossIconFactory.class, "moss_16.png"),
                false);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        return mossIcon;
    }

    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        return mossIcon;
    }

    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        return mossIcon;
    }

    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return mossIcon;
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return mossIcon;
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        return mossIcon;
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return mossIcon;
    }
}
