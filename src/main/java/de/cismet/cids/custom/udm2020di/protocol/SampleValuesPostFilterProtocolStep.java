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

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;
import de.cismet.commons.gui.protocol.ProtocolStepParameter;

import static de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui.MAX_DATE;
import static de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui.MIN_DATE;
import static de.cismet.cids.custom.udm2020di.postfilter.CommonSampleValuesPostFilterGui.SELECTED_VALUES;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class SampleValuesPostFilterProtocolStep extends CommonPostFilterProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    public static final String PARAMETER_SELECTED_VALUES = SELECTED_VALUES;
    public static final String PARAMETER_MIN_DATE = MIN_DATE;
    public static final String PARAMETER_MAX_DATE = MAX_DATE;

    private static final Logger LOGGER = Logger.getLogger(SampleValuesPostFilterProtocolStep.class);

    @JsonIgnore
    protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            "Max Values Post Filter",
            "\"Max Values Post Filter Protocol",
            SampleValuesPostFilterProtocolStep.class.getCanonicalName());

    //~ Instance fields --------------------------------------------------------

    @JsonIgnore
    private final Map<String, Float> tmpSelectedValues;
    @JsonIgnore
    private final Date tmpMaxDate;
    @JsonIgnore
    private final Date tmpMinDate;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     */
    public SampleValuesPostFilterProtocolStep() {
        super();
        this.tmpSelectedValues = null;
        this.tmpMaxDate = null;
        this.tmpMinDate = null;
    }

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     *
     * @param  postFilter      DOCUMENT ME!
     * @param  title           DOCUMENT ME!
     * @param  icon            DOCUMENT ME!
     * @param  selectedValues  DOCUMENT ME!
     * @param  minDate         DOCUMENT ME!
     * @param  maxDate         DOCUMENT ME!
     */
    public SampleValuesPostFilterProtocolStep(
            final String postFilter,
            final String title,
            final ImageIcon icon,
            final Map<String, Float> selectedValues,
            final Date minDate,
            final Date maxDate) {
        super(postFilter, title, icon);
        this.tmpSelectedValues = null;
        this.tmpMinDate = maxDate;
        this.tmpMaxDate = minDate;
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
    public Map<String, Float> getSelectedValues() {
        final Object selectedTags = super.getParameterValue(PARAMETER_SELECTED_VALUES);
        if (selectedTags != null) {
            return (Map<String, Float>)selectedTags;
        } else {
            LOGGER.error("PARAMETER_SELECTED_VALUES map not found in parameter value set!");
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public Date getMinDate() {
        final Object date = super.getParameterValue(PARAMETER_MIN_DATE);
        if (date != null) {
            return (Date)date;
        } else {
            LOGGER.error("PARAMETER_MIN_DATE map not found in parameter value set!");
            return null;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public Date getMaxDate() {
        final Object date = super.getParameterValue(PARAMETER_MAX_DATE);
        if (date != null) {
            return (Date)date;
        } else {
            LOGGER.error("PARAMETER_MAX_DATE map not found in parameter value set!");
            return null;
        }
    }

    @Override
    public Set<ProtocolStepParameter> createParameters() {
        final Set<ProtocolStepParameter> parameters = super.createParameters();

        if (this.tmpSelectedValues != null) {
            parameters.add(new ProtocolStepParameter(PARAMETER_SELECTED_VALUES, this.tmpSelectedValues));
        } else {
            LOGGER.error("PARAMETER_SELECTED_VALUES is null!");
        }

        if (this.tmpMinDate != null) {
            parameters.add(new ProtocolStepParameter(PARAMETER_MIN_DATE, this.tmpMinDate));
        } else {
            LOGGER.error("PARAMETER_MIN_DATE is null!");
        }

        if (this.tmpMaxDate != null) {
            parameters.add(new ProtocolStepParameter(PARAMETER_MAX_DATE, this.tmpMaxDate));
        } else {
            LOGGER.error("PARAMETER_MAX_DATE is null!");
        }

        return parameters;
    }
}
