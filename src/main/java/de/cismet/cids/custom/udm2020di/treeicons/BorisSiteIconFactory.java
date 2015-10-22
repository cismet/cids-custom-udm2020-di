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
public final class BorisSiteIconFactory implements CidsTreeObjectIconFactory {

    //~ Instance fields --------------------------------------------------------

    private final ImageIcon borisSiteIcon;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisSiteIconFactory object.
     */
    public BorisSiteIconFactory() {
        this.borisSiteIcon = ImageUtilities.loadImageIcon(
                ImageUtil.getResourcePath(BorisSiteIconFactory.class, "showel_16.png"),
                false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   ptn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        return borisSiteIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   ptn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        return borisSiteIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   ptn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        return borisSiteIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   otn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return borisSiteIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   otn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return borisSiteIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   otn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        return borisSiteIcon;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   dmtn  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        return borisSiteIcon;
    }
}
