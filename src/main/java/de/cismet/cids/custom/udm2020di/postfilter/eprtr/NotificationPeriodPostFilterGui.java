/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.postfilter.eprtr;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.Node;

import org.apache.log4j.Logger;

import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.postfilter.*;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@ServiceProvider(service = PostFilterGUI.class)
public class NotificationPeriodPostFilterGui extends CommonTagsPostFilterGui {

    //~ Instance fields --------------------------------------------------------

    protected final MetaClass eprtrClass;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PollutantTagsPostFilterGui object.
     */
    public NotificationPeriodPostFilterGui() {
        super();
        logger = Logger.getLogger(NotificationPeriodPostFilterGui.class);
        icon = new ImageIcon(getClass().getResource(
                    "/de/cismet/cids/custom/udm2020di/postfilter/eprtr/factory.png"));
        eprtrClass = ClassCacheMultiple.getMetaClass("UDM2020-DI", "EPRTR_INSTALLATION");
        if (eprtrClass == null) {
            logger.warn("could not retrieve EPRTR_INSTALLATION class from UDM2020-DI, "
                        + "filter is disabled!");
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getTitle() {
        return org.openide.util.NbBundle.getMessage(
                NotificationPeriodPostFilterGui.class,
                "NotificationPeriodPostFilterGui.title");
    }

    @Override
    public Integer getDisplayOrderKeyPrio() {
        return super.getDisplayOrderKeyPrio() + 100;
    }

    @Override
    protected String getFilterTagGroup() {
        return "EPRTR.NOTIFICATION_PERIOD";
    }

    @Override
    public boolean canHandle(final Collection<Node> nodes) {
        final boolean canHandle = !this.preFilterNodes(nodes).isEmpty();
        if (logger.isDebugEnabled()) {
            logger.debug("filter can handle " + nodes.size() + " nodes:" + canHandle);
        }
        return canHandle;
    }

    @Override
    protected List<Node> preFilterNodes(final Collection<Node> input) {
        final List<Node> nodes = new ArrayList<Node>();
        if (this.eprtrClass != null) {
            for (final Node node : input) {
                if (node.getClassId() == this.eprtrClass.getId()) {
                    nodes.add(node);
                }
            }
        }

        return nodes;
    }

    @Override
    public boolean isActive() {
        return this.active && (eprtrClass != null);
    }
}
