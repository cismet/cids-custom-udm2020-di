/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.postfilter.CommonTagsPostFilterGui;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;
import de.cismet.commons.gui.protocol.ProtocolStepParameter;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class TagsPostFilterProtocolStep extends CommonPostFilterProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PARAMETER_SELECTED_TAGS = CommonTagsPostFilterGui.SELECTED_TAGS;
    private static final Logger LOGGER = Logger.getLogger(TagsPostFilterProtocolStep.class);

    @JsonIgnore
    protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            "TagsPostFilter",
            "TagsPostFilter protocol",
            TagsPostFilterProtocolStep.class.getCanonicalName());

    //~ Instance fields --------------------------------------------------------

    @JsonIgnore
    private final Map<String, String> tmpSelectedTags;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     */
    public TagsPostFilterProtocolStep() {
        super();
        this.tmpSelectedTags = null;
    }

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     *
     * @param  postFilter    DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     * @param  icon          DOCUMENT ME!
     * @param  selectedTags  DOCUMENT ME!
     */
    public TagsPostFilterProtocolStep(
            final String postFilter,
            final String title,
            final ImageIcon icon,
            final Map<String, String> selectedTags) {
        super(postFilter, title, icon);
        this.tmpSelectedTags = selectedTags;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public Map<String, String> getSelectedTags() {
        final Object selectedTags = super.getParameterValue(PARAMETER_SELECTED_TAGS);
        if (selectedTags != null) {
            return (Map<String, String>)selectedTags;
        } else {
            LOGGER.error("SELECTED_TAGS map not found in parameter value set!");
            return null;
        }
    }

    @Override
    public Set<ProtocolStepParameter> createParameters() {
        final Set<ProtocolStepParameter> parameters = super.createParameters();

        if (this.tmpSelectedTags != null) {
            parameters.add(new ProtocolStepParameter(PARAMETER_SELECTED_TAGS, this.tmpSelectedTags));
        } else {
            LOGGER.error("PARAMETER_SELECTED_TAGS is null!");
        }

        return parameters;
    }
}
