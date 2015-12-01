/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import Sirius.server.middleware.types.Node;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import de.cismet.cidsx.server.api.types.CidsNode;

import de.cismet.commons.gui.protocol.AbstractProtocolStep;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public abstract class CommonPostFilterProtocolStep extends AbstractProtocolStep implements Cloneable {

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
    protected final transient ImageIcon icon;

    /** Back-reference to parent step. ignored in serialized json! */
    @Getter
    @Setter
    @JsonIgnore
    protected transient CascadingPostFilterProtocolStep cascadingProtocolStep;

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
     * Creates a new CommonPostFilterProtocolStep object.
     *
     * @param  protocolStep  DOCUMENT ME!
     */
    protected CommonPostFilterProtocolStep(final CommonPostFilterProtocolStep protocolStep) {
        this.title = protocolStep.title;
        this.icon = protocolStep.icon;
        this.postFilter = protocolStep.postFilter;
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

        if ((iconData != null) && (iconData.length > 0)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("restoring icon from json-deserialized binary png image");
            }
            this.icon = new ImageIcon(iconData);
        } else {
            LOGGER.warn("cannot restore icon from json-deserialized binary png image: byte array is empty!");
            this.icon = null;
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public Collection<Node> getNodes() {
        if (this.getCascadingProtocolStep() != null) {
            return this.getCascadingProtocolStep().getNodes();
        } else {
            LOGGER.warn("could not get nodes, CascadingProtocolStep is null (" + this.getPostFilter() + ")");
            return Arrays.asList(new Node[0]);
        }
    }

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

    @Override
    public abstract CommonPostFilterProtocolStep clone() throws CloneNotSupportedException;
}
