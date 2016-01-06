/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.types.Tag;

import de.cismet.cidsx.server.api.types.CidsNode;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class TagsPostFilterProtocolStep extends CommonPostFilterProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    @JsonIgnore protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            TagsPostFilterProtocolStep.class.getSimpleName(),
            "TagsPostFilter protocol");

    //~ Instance fields --------------------------------------------------------

    @JsonProperty(required = true)
    @Getter @Setter private Collection<Tag> filterTags;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     *
     * @param  postFilter  DOCUMENT ME!
     * @param  title       DOCUMENT ME!
     * @param  icon        DOCUMENT ME!
     * @param  filterTags  selectedTags DOCUMENT ME!
     */
    public TagsPostFilterProtocolStep(
            final String postFilter,
            final String title,
            final ImageIcon icon,
            final Collection<Tag> filterTags) {
        super(postFilter, title, icon);
        this.filterTags = filterTags;
    }

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     *
     * @param  postFilter  DOCUMENT ME!
     * @param  title       DOCUMENT ME!
     * @param  iconData    DOCUMENT ME!
     * @param  cidsNodes   DOCUMENT ME!
     * @param  filterTags  DOCUMENT ME!
     */
    @JsonCreator
    public TagsPostFilterProtocolStep(@JsonProperty("postFilter") final String postFilter,
            @JsonProperty("title") final String title,
            @JsonProperty("iconData") final byte[] iconData,
            @JsonProperty("cidsNodes") final Collection<CidsNode> cidsNodes,
            @JsonProperty("filterTags") final Collection<Tag> filterTags) {
        super(postFilter, title, iconData, cidsNodes);
        this.filterTags = filterTags;
    }

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     *
     * @param   protocolStep  DOCUMENT ME!
     *
     * @throws  CloneNotSupportedException  DOCUMENT ME!
     */
    protected TagsPostFilterProtocolStep(final TagsPostFilterProtocolStep protocolStep)
            throws CloneNotSupportedException {
        super(protocolStep);
        this.filterTags = new ArrayList<Tag>(protocolStep.filterTags.size());
        for (final Tag tag : protocolStep.filterTags) {
            this.filterTags.add(tag.clone());
        }
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        return new TagsPostFilterProtocolStepPanel(this);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<Tag> getSelectedTags() {
        final ArrayList<Tag> selectedTags = new ArrayList<Tag>();
        if ((this.filterTags != null) && !this.filterTags.isEmpty()) {
            for (final Tag tag : this.filterTags) {
                if (tag.isSelected()) {
                    selectedTags.add(tag);
                }
            }
        }

        return selectedTags;
    }

    @Override
    public TagsPostFilterProtocolStep clone() throws CloneNotSupportedException {
        return new TagsPostFilterProtocolStep(this);
    }

    @Override
    public int appliedFilters() {
        return (this.getSelectedTags() != null) ? this.getSelectedTags().size() : 0;
    }
}
