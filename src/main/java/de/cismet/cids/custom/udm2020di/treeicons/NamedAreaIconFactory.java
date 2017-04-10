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

import org.apache.log4j.Logger;

import org.openide.util.ImageUtilities;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.ImageUtil;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public final class NamedAreaIconFactory implements CidsTreeObjectIconFactory {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(NamedAreaIconFactory.class);

    //~ Methods ----------------------------------------------------------------

    @Override
    public Icon getClosedPureNodeIcon(final PureTreeNode ptn) {
        // default icon
        return null;
    }

    @Override
    public Icon getOpenPureNodeIcon(final PureTreeNode ptn) {
        // default icon
        return null;
    }

    @Override
    public Icon getLeafPureNodeIcon(final PureTreeNode ptn) {
        // default icon
        return null;
    }

    @Override
    public Icon getOpenObjectNodeIcon(final ObjectTreeNode otn) {
        return getLeafObjectNodeIcon(otn);
    }

    @Override
    public Icon getClosedObjectNodeIcon(final ObjectTreeNode otn) {
        return getLeafObjectNodeIcon(otn);
    }

    @Override
    public Icon getLeafObjectNodeIcon(final ObjectTreeNode otn) {
        final StringBuilder iconNameBuilder = new StringBuilder();
        iconNameBuilder.append("bl-");
        iconNameBuilder.append(otn.getMetaObjectNode().getName().toLowerCase());
        iconNameBuilder.append(".gif");

        try {
            final ImageIcon icon = ImageUtilities.loadImageIcon(
                    ImageUtil.getResourcePath(NamedAreaIconFactory.class, iconNameBuilder.toString()),
                    false);
            return icon;
        } catch (final Exception e) {
            LOG.error("cannot create name area icon " + iconNameBuilder, e);

            return null;
        }
    }

    @Override
    public Icon getClassNodeIcon(final ClassTreeNode dmtn) {
        // default icon
        return null;
    }
}
