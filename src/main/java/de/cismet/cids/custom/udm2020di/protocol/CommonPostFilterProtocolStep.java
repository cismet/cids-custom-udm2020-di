/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.navigator.ui.ComponentRegistry;
import Sirius.navigator.ui.tree.PostfilterEnabledSearchResultsTree;
import Sirius.navigator.ui.tree.SearchResultsTree;

import Sirius.server.middleware.types.Node;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.log4j.Logger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import de.cismet.commons.gui.protocol.AbstractProtocolStep;
import de.cismet.commons.gui.protocol.ProtocolStepParameter;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public abstract class CommonPostFilterProtocolStep extends AbstractProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PARAMETER_NODES = "PARAMETER_NODES";
    public static final String PARAMETER_ICON = "PARAMETER_ICON";
    public static final String PARAMETER_TITLE = "PARAMETER_TITLE";
    public static final String PARAMETER_POSTFILTER = "PARAMETER_POSTFILTER";
    private static final Logger LOGGER = Logger.getLogger(CommonPostFilterProtocolStep.class);

    //~ Instance fields --------------------------------------------------------

    @JsonIgnore
    protected final transient String tmpTitle;

    @JsonIgnore
    protected final transient String tmpPostFilter;

    @JsonIgnore
    protected transient ImageIcon icon = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CommonPostFilterProtocolStep object.
     */
    public CommonPostFilterProtocolStep() {
        super();
        this.tmpTitle = null;
        this.tmpPostFilter = null;
    }

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
        this.tmpPostFilter = postFilter;
        this.tmpTitle = title;
        this.icon = icon;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public List<Node> getNodes() {
        final Object nodes = super.getParameterValue(PARAMETER_NODES);
        if (nodes != null) {
            return (List<Node>)nodes;
        } else {
            LOGGER.error("NODES collection not found in parameter value set!");
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public String getTitle() {
        final Object title = super.getParameterValue(PARAMETER_TITLE);
        if (title != null) {
            return title.toString();
        } else {
            LOGGER.error("PARAMETER_TITLE collection not found in parameter value set!");
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public String getPostFilter() {
        final Object postFilter = super.getParameterValue(PARAMETER_POSTFILTER);
        if (postFilter != null) {
            return postFilter.toString();
        } else {
            LOGGER.error("PARAMETER_POSTFILTER collection not found in parameter value set!");
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public ImageIcon getIcon() {
        if (this.icon == null) {
            final Object iconDataObject = super.getParameterValue(PARAMETER_ICON);
            if (iconDataObject != null) {
                final byte[] iconData = (byte[])iconDataObject;
                this.icon = new ImageIcon(iconData);
            } else {
                LOGGER.error("PARAMETER_ICON collection not found in parameter value set!");
                return null;
            }
        }

        return this.icon;
    }

    @Override
    public Set<ProtocolStepParameter> createParameters() {
        final Set<ProtocolStepParameter> parameters = new HashSet<ProtocolStepParameter>();

        final SearchResultsTree searchResultsTree = ComponentRegistry.getRegistry().getSearchResultsTree();
        if ((searchResultsTree != null) && (searchResultsTree instanceof PostfilterEnabledSearchResultsTree)) {
            final List<Node> nodes = ((PostfilterEnabledSearchResultsTree)searchResultsTree).getOriginalResultNodes();
            parameters.add(new ProtocolStepParameter(PARAMETER_NODES, nodes));
        } else {
            LOGGER.error("PARAMETER_NODES cannot be saved, not PostfilterEnabledSearchResultsTree available!");
        }

        if (this.tmpPostFilter != null) {
            parameters.add(new ProtocolStepParameter(PARAMETER_POSTFILTER, this.tmpPostFilter));
        } else {
            LOGGER.error("PARAMETER_POSTFILTER is null!");
        }

        if (this.tmpTitle != null) {
            parameters.add(new ProtocolStepParameter(PARAMETER_TITLE, this.tmpTitle));
        } else {
            LOGGER.error("PARAMETER_TITLE is null!");
        }

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
                parameters.add(new ProtocolStepParameter(PARAMETER_ICON, imageData));
            } catch (IOException ex) {
                LOGGER.error("could not serilaize Image Icon: " + ex.getMessage(), ex);
            }
        } else {
            LOGGER.error("PARAMETER_ICON is null!");
        }

        return parameters;
    }
}
