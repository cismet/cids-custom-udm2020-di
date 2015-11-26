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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

import org.apache.log4j.Logger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import de.cismet.cidsx.server.api.types.CidsNode;
import de.cismet.cidsx.server.api.types.legacy.CidsNodeFactory;

import de.cismet.commons.gui.protocol.AbstractProtocolStep;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public abstract class CommonPostFilterProtocolStep extends AbstractProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(CommonPostFilterProtocolStep.class);

    //~ Instance fields --------------------------------------------------------

    @Getter
    @JsonProperty(required = true)
    protected final String title;

    @Getter
    @JsonProperty(required = true)
    protected final String postFilter;

    @Getter
    @JsonIgnore
    protected transient ImageIcon icon = null;

    @Getter
    @JsonIgnore
    protected transient List<Node> nodes = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CommonPostFilterProtocolStep object.
     *
     * @param  postFilter  DOCUMENT ME!
     * @param  title       DOCUMENT ME!
     * @param  icon        DOCUMENT ME!
     */
    public CommonPostFilterProtocolStep(
            final String postFilter,
            final String title,
            final ImageIcon icon) {
        super();
        this.postFilter = postFilter;
        this.title = title;
        this.icon = icon;
    }

    /**
     * Constructor for Jackson deserialization.
     *
     * @param  postFilter  DOCUMENT ME!
     * @param  title       DOCUMENT ME!
     * @param  iconData    DOCUMENT ME!
     * @param  cidsNodes   DOCUMENT ME!
     */
    protected CommonPostFilterProtocolStep(
            final String postFilter,
            final String title,
            final byte[] iconData,
            final Collection<CidsNode> cidsNodes) {
        this.postFilter = postFilter;
        this.title = title;
        this.setIconData(iconData);
        this.setCidsNodes(cidsNodes);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonProperty
    public byte[] getIconData() {
        if (this.icon != null) {
            try {
                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                final BufferedImage bimage = new BufferedImage(this.icon.getIconWidth(),
                        this.icon.getIconHeight(),
                        BufferedImage.TYPE_INT_ARGB);
                final Graphics2D bGr = bimage.createGraphics();
                bGr.drawImage(this.icon.getImage(), 0, 0, null);
                bGr.dispose();
                ImageIO.write(bimage, "png", bos);
                bos.flush();
                bos.close();
                final byte[] imageData = bos.toByteArray();
                return imageData;
            } catch (IOException ex) {
                LOGGER.error("could not serilaize Image Icon: " + ex.getMessage(), ex);
            }
        } else {
            LOGGER.error("PARAMETER_ICON is null!");
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  iconData  DOCUMENT ME!
     */
    @JsonProperty
    public final void setIconData(final byte[] iconData) {
        if ((iconData != null) && (iconData.length > 0)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring icon from json-deserialized binary png image");
            }
            this.icon = new ImageIcon(iconData);
        } else {
            LOGGER.warn("cannot restore icon from json-deserialized binary png image: byte array is empty!");
        }
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
                    final CidsNode cidsNode = CidsNodeFactory.getFactory().restCidsNodeFromLegacyCidsNode(node, 
                            SessionManager.getProxy().getMetaClass(
                                    node.getClassId(), node.getDomain()).getName());
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

    /**
     * DOCUMENT ME!
     *
     * @param  cidsNodes  DOCUMENT ME!
     */
    @JsonProperty
    public final void setCidsNodes(final Collection<CidsNode> cidsNodes) {
        if ((cidsNodes != null) && !cidsNodes.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring nodes from " + cidsNodes.size() + " json-deserialized cids nodes");
            }
            this.nodes = new ArrayList<Node>(cidsNodes.size());
            for (final CidsNode cidsNode : cidsNodes) {
                try {
                    final Node node = CidsNodeFactory.getFactory().legacyCidsNodeFromRestCidsNode(cidsNode);
                    nodes.add(node);
                } catch (Exception ex) {
                    LOGGER.error("cannot restore nodes from json-deserialized cids node:" + ex.getMessage(), ex);
                }
            }
        } else {
            LOGGER.warn("cannot restore nodes from json-deserialized cids nodes: cidsNodes list is empty!");
        }
    }

    @Override
    public void initParameters() {
        final SearchResultsTree searchResultsTree = ComponentRegistry.getRegistry().getSearchResultsTree();
        if ((searchResultsTree != null) && (searchResultsTree instanceof PostfilterEnabledSearchResultsTree)) {
            this.nodes = ((PostfilterEnabledSearchResultsTree)searchResultsTree).getOriginalResultNodes();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.info("saving " + this.nodes.size() + " nodes in protocol of postfilter '"
                            + this.postFilter + "'");
            }
        } else {
            LOGGER.error("PARAMETER_NODES cannot be saved, not PostfilterEnabledSearchResultsTree available!");
        }
    }
}
