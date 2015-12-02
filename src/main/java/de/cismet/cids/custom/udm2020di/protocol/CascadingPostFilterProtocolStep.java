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
    protected transient Collection<Node> resultNodes = null;

    @Getter
    @JsonIgnore
    protected transient Collection<Node> filteredNodes = null;

    @Getter
    @Setter
    @JsonProperty(required = true)
    protected String masterPostFilter;

    @Getter
    @JsonProperty(required = true)
    protected final Map<String, CommonPostFilterProtocolStep> protocolSteps;
    
    @JsonIgnore
    CascadingPostFilterProtocolStepPanel protocolStepPanel = null;

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
     * @param  masterPostFilter   DOCUMENT ME!
     * @param  protocolSteps      title DOCUMENT ME!
     * @param  resultCidsNodes    DOCUMENT ME!
     * @param  filteredCidsNodes  DOCUMENT ME!
     */
    @JsonCreator
    protected CascadingPostFilterProtocolStep(@JsonProperty("masterPostFilter") final String masterPostFilter,
            @JsonProperty("protocolSteps") final Map<String, CommonPostFilterProtocolStep> protocolSteps,
            @JsonProperty("resultNodes") final Collection<CidsNode> resultCidsNodes,
            @JsonProperty("filteredNodes") final Collection<CidsNode> filteredCidsNodes) {
        this.masterPostFilter = masterPostFilter;

        this.protocolSteps = Collections.unmodifiableMap(protocolSteps);
        for (final CommonPostFilterProtocolStep protocolStep : this.protocolSteps.values()) {
            protocolStep.setCascadingProtocolStep(this);
        }

        // result Nodes
        this.resultNodes = new ArrayList<Node>((resultCidsNodes != null) ? resultCidsNodes.size() : 0);
        if ((resultCidsNodes != null) && !resultCidsNodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring result nodes from " + resultCidsNodes.size() + " json-deserialized cids nodes");
            }
            for (final CidsNode cidsNode : resultCidsNodes) {
                try {
                    final Node node = CidsNodeFactory.getFactory().legacyCidsNodeFromRestCidsNode(cidsNode);
                    this.resultNodes.add(node);
                } catch (Exception ex) {
                    LOGGER.error("cannot restore result nodes from json-deserialized cids node:" + ex.getMessage(), ex);
                }
            }
        } else {
            LOGGER.warn("cannot restore result nodes from json-deserialized cids nodes: cidsNodes list is empty!");
        }

        this.filteredNodes = new ArrayList<Node>((resultCidsNodes != null) ? filteredCidsNodes.size() : 0);
        if ((filteredCidsNodes != null) && !filteredCidsNodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring filtered nodes from " + resultCidsNodes.size()
                            + " json-deserialized cids nodes");
            }
            for (final CidsNode cidsNode : filteredCidsNodes) {
                try {
                    final Node node = CidsNodeFactory.getFactory().legacyCidsNodeFromRestCidsNode(cidsNode);
                    this.filteredNodes.add(node);
                } catch (Exception ex) {
                    LOGGER.error("cannot filtered restore nodes from json-deserialized cids node:" + ex.getMessage(),
                        ex);
                }
            }
        } else {
            LOGGER.warn("cannot filtered restore nodes from json-deserialized cids nodes: cidsNodes list is empty!");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("restored " + protocolSteps.size()
                        + " protocol steps with master protocol step '" + this.masterPostFilter
                        + "' and " + ((resultCidsNodes != null) ? resultCidsNodes.size() : 0) + " nodes");
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
    @JsonProperty(value = "resultNodes")
    public Collection<CidsNode> getCidsResultNodes() {
        final ArrayList<CidsNode> cidsNodes = new ArrayList<CidsNode>((this.resultNodes != null)
                    ? this.resultNodes.size() : 0);
        if ((this.resultNodes != null) && !this.resultNodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("converting " + this.resultNodes.size() + " result nodes to json-serializable cids nodes");
            }
            for (final Node node : this.resultNodes) {
                try {
                    final CidsNode cidsNode = CidsNodeFactory.getFactory()
                                .restCidsNodeFromLegacyCidsNode(
                                    node,
                                    SessionManager.getProxy().getMetaClass(
                                        node.getClassId(),
                                        node.getDomain()).getName());
                    cidsNodes.add(cidsNode);
                } catch (Exception ex) {
                    LOGGER.error("cannot convert result node to json-serializable cids node:" + ex.getMessage(), ex);
                }
            }
        } else {
            LOGGER.warn("cannot convert result nodes to json-serializable cids nodes: nodes list is empty!");
        }

        return cidsNodes;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonProperty(value = "filteredNodes")
    public Collection<CidsNode> getCidsFilteredNodes() {
        final ArrayList<CidsNode> cidsNodes = new ArrayList<CidsNode>((this.filteredNodes != null)
                    ? this.filteredNodes.size() : 0);
        if ((this.filteredNodes != null) && !this.filteredNodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("converting " + this.filteredNodes.size()
                            + " filtered nodes to json-serializable cids nodes");
            }
            for (final Node node : this.filteredNodes) {
                try {
                    final CidsNode cidsNode = CidsNodeFactory.getFactory()
                                .restCidsNodeFromLegacyCidsNode(
                                    node,
                                    SessionManager.getProxy().getMetaClass(
                                        node.getClassId(),
                                        node.getDomain()).getName());
                    cidsNodes.add(cidsNode);
                } catch (Exception ex) {
                    LOGGER.error("cannot convert filtered node to json-serializable cids node:" + ex.getMessage(), ex);
                }
            }
        } else {
            LOGGER.warn("cannot convert filtered nodes to json-serializable cids nodes: nodes list is empty!");
        }

        return cidsNodes;
    }

    @Override
    public void initParameters() {
        final SearchResultsTree searchResultsTree = ComponentRegistry.getRegistry().getSearchResultsTree();
        if ((searchResultsTree != null) && (searchResultsTree instanceof PostfilterEnabledSearchResultsTree)) {
            this.resultNodes = Collections.unmodifiableCollection(
                    ((PostfilterEnabledSearchResultsTree)searchResultsTree).getOriginalResultNodes());

            this.filteredNodes = Collections.unmodifiableCollection(new ArrayList<Node>(
                        searchResultsTree.getResultNodes()));

            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("saving " + this.resultNodes.size() + " result nodes and "
                            + this.filteredNodes.size() + " filtered nodes in protocol of master postfilter '"
                            + this.masterPostFilter + "'");
            }
        } else {
            LOGGER.error("result nodes cannot be saved, no PostfilterEnabledSearchResultsTree available!");
        }
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        if(this.protocolStepPanel == null) {
        this.protocolStepPanel = new CascadingPostFilterProtocolStepPanel(this);
        }
        return this.protocolStepPanel ;
    }

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public ProtocolStepMetaInfo getMetaInfo() {
        return super.getMetaInfo();
    }
}
