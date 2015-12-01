/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.PostfilterEnabledSearchResultsTree;
import Sirius.navigator.ui.tree.SearchResultsTree;

import Sirius.server.middleware.types.Node;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import de.cismet.cidsx.server.api.types.CidsNode;
import de.cismet.cidsx.server.api.types.legacy.CidsNodeFactory;

import de.cismet.commons.gui.protocol.AbstractProtocolStep;
import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class CascadingPostFilterProtocolStep extends AbstractProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(CascadingPostFilterProtocolStep.class);

    @JsonIgnore
    protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            CascadingPostFilterProtocolStep.class.getSimpleName(),
            "Cascading Post Filter (not directly visible in GUI!)");

    //~ Instance fields --------------------------------------------------------

    @Getter
    @JsonIgnore
    protected transient Collection<Node> nodes = null;

    @Getter
    @Setter
    @JsonProperty(required = true)
    protected String masterPostFilter;

    @Getter
    @JsonProperty(required = true)
    protected final Map<String, CommonPostFilterProtocolStep> protocolSteps;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CommonPostFilterProtocolStep object.
     *
     * @param  masterPostFilter  postFilter DOCUMENT ME!
     * @param  protocolSteps     DOCUMENT ME!
     */
    public CascadingPostFilterProtocolStep(
            final String masterPostFilter,
            final Map<String, CommonPostFilterProtocolStep> protocolSteps) {
        this.masterPostFilter = masterPostFilter;
        this.protocolSteps = Collections.unmodifiableMap(protocolSteps);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("created CascadingPostFilterProtocolStep with " + protocolSteps.size()
                        + " protocol steps for master post filter '" + masterPostFilter + "'");
        }

        for (final CommonPostFilterProtocolStep protocolStep : this.protocolSteps.values()) {
            protocolStep.setCascadingProtocolStep(this);
        }
    }

    /**
     * Constructor for Jackson deserialization.
     *
     * @param  masterPostFilter  DOCUMENT ME!
     * @param  protocolSteps     title DOCUMENT ME!
     * @param  cidsNodes         DOCUMENT ME!
     */
    @JsonCreator
    protected CascadingPostFilterProtocolStep(@JsonProperty("masterPostFilter") final String masterPostFilter,
            @JsonProperty("protocolSteps") final Map<String, CommonPostFilterProtocolStep> protocolSteps,
            @JsonProperty("cidsNodes") final Collection<CidsNode> cidsNodes) {
        this.masterPostFilter = masterPostFilter;

        this.protocolSteps = Collections.unmodifiableMap(protocolSteps);
        for (final CommonPostFilterProtocolStep protocolStep : this.protocolSteps.values()) {
            protocolStep.setCascadingProtocolStep(this);
        }

        this.nodes = new ArrayList<Node>((cidsNodes != null) ? cidsNodes.size() : 0);
        if ((cidsNodes != null) && !cidsNodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring nodes from " + cidsNodes.size() + " json-deserialized cids nodes");
            }
            for (final CidsNode cidsNode : cidsNodes) {
                try {
                    final Node node = CidsNodeFactory.getFactory().legacyCidsNodeFromRestCidsNode(cidsNode);
                    this.nodes.add(node);
                } catch (Exception ex) {
                    LOGGER.error("cannot restore nodes from json-deserialized cids node:" + ex.getMessage(), ex);
                }
            }
        } else {
            LOGGER.warn("cannot restore nodes from json-deserialized cids nodes: cidsNodes list is empty!");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("restored " + protocolSteps.size()
                        + " protocol steps with master protocol step '" + this.masterPostFilter
                        + "' and " + ((cidsNodes != null) ? cidsNodes.size() : 0) + " nodes");
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    protected CommonPostFilterProtocolStep getMasterProtocolStepInternal() {
        if ((this.masterPostFilter != null) && this.protocolSteps.containsKey(this.masterPostFilter)) {
            return this.protocolSteps.get(this.masterPostFilter);
        } else {
            LOGGER.warn("master protocol step '" + masterPostFilter
                        + "' not found in map of " + this.protocolSteps.size()
                        + "' protocol steps");
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonProperty
    public Collection<CidsNode> getCidsNodes() {
        final ArrayList<CidsNode> cidsNodes = new ArrayList<CidsNode>();
        if ((this.nodes != null) && !this.nodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("converting " + this.nodes.size() + " nodes to json-serializable cids nodes");
            }
            for (final Node node : this.nodes) {
                try {
                    final CidsNode cidsNode = CidsNodeFactory.getFactory()
                                .restCidsNodeFromLegacyCidsNode(
                                    node,
                                    SessionManager.getProxy().getMetaClass(
                                        node.getClassId(),
                                        node.getDomain()).getName());
                    cidsNodes.add(cidsNode);
                } catch (Exception ex) {
                    LOGGER.error("cannot convert node to json-serializable cids node:" + ex.getMessage(), ex);
                }
            }
        } else {
            LOGGER.warn("cannot convert nodes to json-serializable cids nodes: nodes list is empty!");
        }

        return cidsNodes;
    }

    @Override
    public void initParameters() {
        final SearchResultsTree searchResultsTree = ComponentRegistry.getRegistry().getSearchResultsTree();
        if ((searchResultsTree != null) && (searchResultsTree instanceof PostfilterEnabledSearchResultsTree)) {
            this.nodes = Collections.unmodifiableCollection(
                    ((PostfilterEnabledSearchResultsTree)searchResultsTree).getOriginalResultNodes());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("saving " + this.nodes.size() + " nodes in protocol of master postfilter '"
                            + this.masterPostFilter + "'");
            }
        } else {
            LOGGER.error("PARAMETER_NODES cannot be saved, not PostfilterEnabledSearchResultsTree available!");
        }
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        final CommonPostFilterProtocolStep protocolStep = this.getMasterProtocolStepInternal();
        return (protocolStep != null) ? protocolStep.visualize() : null;
    }

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public ProtocolStepMetaInfo getMetaInfo() {
        final CommonPostFilterProtocolStep protocolStep = this.getMasterProtocolStepInternal();
        return (protocolStep != null) ? protocolStep.getMetaInfo() : super.getMetaInfo();
    }
}
