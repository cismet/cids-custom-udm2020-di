/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.navigator.ui.tree.postfilter.PostFilterGUI;

import Sirius.server.middleware.types.Node;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;

import org.openide.util.Exceptions;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.cismet.commons.gui.protocol.ProtocolHandler;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class PostfilterProtocolRegistry {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(PostfilterProtocolRegistry.class);
    private static final PostfilterProtocolRegistry INSTANCE = new PostfilterProtocolRegistry();

    //~ Instance fields --------------------------------------------------------

    private final Map<PostFilterGUI, CommonPostFilterProtocolStep> protocolMap = Collections.synchronizedMap(
            new HashMap<PostFilterGUI, CommonPostFilterProtocolStep>());

    @Getter
    @Setter
    private String masterPostFilter = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PostfilterProtocolRegistry object.
     */
    private PostfilterProtocolRegistry() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static final PostfilterProtocolRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterGUI  postFilterClassName DOCUMENT ME!
     * @param   protocolStep   settings DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CommonPostFilterProtocolStep putProtocolStep(
            final PostFilterGUI postFilterGUI,
            final CommonPostFilterProtocolStep protocolStep) {
        final CommonPostFilterProtocolStep oldProtocolStep = protocolMap.put(postFilterGUI, protocolStep);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("persistent configuration settings '" + postFilterGUI.getClass().getSimpleName()
                        + "' " + ((oldProtocolStep != null) ? "overwitten" : "stored"));
        }

        return oldProtocolStep;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilter    DOCUMENT ME!
     * @param   protocolStep  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CommonPostFilterProtocolStep putProtocolStep(
            final String postFilter,
            final CommonPostFilterProtocolStep protocolStep) {
        for (final PostFilterGUI postFilterGUI : this.protocolMap.keySet()) {
            if (postFilterGUI.getClass().getSimpleName().equals(postFilter)) {
                return this.putProtocolStep(postFilterGUI, protocolStep);
            }
        }

        LOGGER.warn("no protocol step for postFilter '" + postFilter
                    + "' available for master post filter '" + this.getMasterPostFilter() + "'");
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterGUI  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CommonPostFilterProtocolStep getProtocolStep(final PostFilterGUI postFilterGUI) {
        return protocolMap.get(postFilterGUI);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterGUI  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CommonPostFilterProtocolStep clearProtocolStep(final PostFilterGUI postFilterGUI) {
        final CommonPostFilterProtocolStep oldProtocolStep = this.protocolMap.get(postFilterGUI);
        this.protocolMap.put(postFilterGUI, null);
        return oldProtocolStep;
    }

    /**
     * DOCUMENT ME!
     */
    public void clearAll() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("clearing " + this.protocolMap.size()
                        + " post filter settings for master post filter '" + this.getMasterPostFilter() + "'");
        }
        for (final PostFilterGUI postFilterGUI : this.protocolMap.keySet()) {
            this.protocolMap.put(postFilterGUI, null);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilter  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CommonPostFilterProtocolStep getProtocolStep(final String postFilter) {
        for (final PostFilterGUI postFilterGUI : this.protocolMap.keySet()) {
            if (postFilterGUI.getClass().getSimpleName().equals(postFilter)) {
                return this.getProtocolStep(postFilterGUI);
            }
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public Map<String, CommonPostFilterProtocolStep> getActiveProtocolSteps() {
        final HashMap<String, CommonPostFilterProtocolStep> activeProtocolSteps =
            new HashMap<String, CommonPostFilterProtocolStep>();
        for (final Map.Entry<PostFilterGUI, CommonPostFilterProtocolStep> entry
                    : this.protocolMap.entrySet()) {
            if (entry.getKey().isActive() && (entry.getValue() != null)) {
                final String key = entry.getKey().getClass().getSimpleName();
                try {
                    activeProtocolSteps.put(key,
                        entry.getValue().clone());
                } catch (Exception ex) {
                    LOGGER.error("could not clone protocol for filter '" + key + "'", ex);
                }
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("returning " + activeProtocolSteps.size()
                        + " active protocol steps for active post filters of "
                        + this.protocolMap.size() + " total post filters for master post filter '"
                        + this.getMasterPostFilter() + "'");
        }

        return activeProtocolSteps;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterGUI  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasProtocolStep(final PostFilterGUI postFilterGUI) {
        return this.protocolMap.containsKey(postFilterGUI)
                    && (this.protocolMap.get(postFilterGUI) != null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilter  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean hasProtocolStep(final String postFilter) {
        for (final PostFilterGUI postFilterGUI : this.protocolMap.keySet()) {
            if (postFilterGUI.getClass().getSimpleName().equals(postFilter)) {
                return this.protocolMap.get(postFilterGUI) != null;
            }
        }

        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  postFilterGUI  DOCUMENT ME!
     * @param  protocolStep   DOCUMENT ME!
     */
    public void recordCascadingProtocolStep(final PostFilterGUI postFilterGUI,
            final CommonPostFilterProtocolStep protocolStep) {
        final String postFilter = postFilterGUI.getClass().getSimpleName();
        // reset restore flag!
        this.setMasterPostFilter(postFilter);

        // save protocol for executed filter
        this.putProtocolStep(postFilterGUI, protocolStep);

        final Map<String, CommonPostFilterProtocolStep> activeProtocolSteps = this.getActiveProtocolSteps();
        LOGGER.info("recording " + activeProtocolSteps.size() + " protocol steps for cascading master post filter '"
                    + postFilter + "'");

        // create cascading filter protocol setp with refence to all active filters
        // inlcuding the currently executed filter
        final CascadingPostFilterProtocolStep cascadingProtocolStep = new CascadingPostFilterProtocolStep(
                postFilter,
                activeProtocolSteps);

        // record the step
        ProtocolHandler.getInstance().recordStep(cascadingProtocolStep);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cascadingProtocolStep  DOCUMENT ME!
     */
    public void restoreCascadingProtocolStep(final CascadingPostFilterProtocolStep cascadingProtocolStep) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("restoring post filter GUI settings of CascadingPostFilterProtocolStep with "
                        + cascadingProtocolStep.getProtocolSteps().size()
                        + " protocol steps and " + cascadingProtocolStep.getNodes().size()
                        + " nodes for master post filter '"
                        + cascadingProtocolStep.getMasterPostFilter() + "' and clearing "
                        + this.protocolMap.size() + " saved protocol settings");
        }

        this.clearAll();
        this.setMasterPostFilter(cascadingProtocolStep.getMasterPostFilter());
        for (final Map.Entry<String, CommonPostFilterProtocolStep> entry
                    : cascadingProtocolStep.getProtocolSteps().entrySet()) {
            this.putProtocolStep(entry.getKey(), entry.getValue());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param   postFilterGUI  DOCUMENT ME!
     * @param   nodes          DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean isShouldRestoreSettings(final PostFilterGUI postFilterGUI, final Collection<Node> nodes) {
        if (this.hasProtocolStep(postFilterGUI)) {
            if (this.getProtocolStep(postFilterGUI).getNodes() != null) {
                final Collection<Node> nodesSaved = this.getProtocolStep(postFilterGUI).getNodes();
                final int nodesHash = nodes.hashCode();
                final int savedNodesHash = nodesSaved.hashCode();
                // if (savedNodesHash == nodesHash) {
                if ((nodes.size() == nodesSaved.size()) && nodes.containsAll(nodesSaved)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("post filter settings should be restored for " + nodesSaved.size()
                                    + " saved nodes for master post filter '" + this.getMasterPostFilter() + "'");
                    }
                    return true;
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("post filter settings should NOT be restored for " + nodesSaved.size()
                                    + " saved nodes ("
                                    + savedNodesHash + "), " + nodes.size() + " new nodes (" + nodesHash
                                    + ") available!");
                    }
                    this.clearAll();
                }
            }
        }

        return false;
    }
}
