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

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.types.AggregationValue;

import de.cismet.cidsx.server.api.types.CidsNode;

import de.cismet.commons.gui.protocol.AbstractProtocolStepPanel;
import de.cismet.commons.gui.protocol.ProtocolStepMetaInfo;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public class SampleValuesPostFilterProtocolStep extends CommonPostFilterProtocolStep {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOGGER = Logger.getLogger(SampleValuesPostFilterProtocolStep.class);

    @JsonIgnore
    protected static final ProtocolStepMetaInfo META_INFO = new ProtocolStepMetaInfo(
            SampleValuesPostFilterProtocolStep.class.getSimpleName(),
            "Max Values Post Filter Protocol");

    //~ Instance fields --------------------------------------------------------

    @Getter
    @JsonProperty(required = true)
    final Collection<AggregationValue> aggregationValues;
    @JsonIgnore
    transient SampleValuesPostFilterProtocolStepPanel protocolStepPanel = null;
    @Getter
    @JsonProperty(required = true)
    private final Map<String, Float> selectedValues;
    @Getter
    @JsonProperty(required = true)
    private final Date maxDate;
    @Getter
    @JsonProperty(required = true)
    private final Date minDate;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new TagsPostFilterProtocolStep object.
     *
     * @param  postFilter         DOCUMENT ME!
     * @param  title              DOCUMENT ME!
     * @param  icon               DOCUMENT ME!
     * @param  aggregationValues  DOCUMENT ME!
     * @param  selectedValues     DOCUMENT ME!
     * @param  minDate            DOCUMENT ME!
     * @param  maxDate            DOCUMENT ME!
     */
    public SampleValuesPostFilterProtocolStep(
            final String postFilter,
            final String title,
            final ImageIcon icon,
            final Collection<AggregationValue> aggregationValues,
            final Map<String, Float> selectedValues,
            final Date minDate,
            final Date maxDate) {
        super(postFilter, title, icon);
        this.aggregationValues = aggregationValues;
        this.selectedValues = selectedValues;
        this.minDate = maxDate;
        this.maxDate = minDate;
    }

    /**
     * Creates a new SampleValuesPostFilterProtocolStep object.
     *
     * @param  postFilter         DOCUMENT ME!
     * @param  title              DOCUMENT ME!
     * @param  iconData           DOCUMENT ME!
     * @param  cidsNodes          DOCUMENT ME!
     * @param  aggregationValues  DOCUMENT ME!
     * @param  selectedValues     DOCUMENT ME!
     * @param  minDate            DOCUMENT ME!
     * @param  maxDate            DOCUMENT ME!
     */
    @JsonCreator
    public SampleValuesPostFilterProtocolStep(@JsonProperty("postFilter") final String postFilter,
            @JsonProperty("title") final String title,
            @JsonProperty("iconData") final byte[] iconData,
            @JsonProperty("cidsNodes") final Collection<CidsNode> cidsNodes,
            @JsonProperty("aggregationValues") final Collection<AggregationValue> aggregationValues,
            @JsonProperty("selectedValues") final Map<String, Float> selectedValues,
            @JsonProperty("minDate") final Date minDate,
            @JsonProperty("maxDate") final Date maxDate) {
        super(postFilter, title, iconData, cidsNodes);
        this.aggregationValues = aggregationValues;
        this.selectedValues = selectedValues;
        this.minDate = maxDate;
        this.maxDate = minDate;
    }

    /**
     * Creates a new SampleValuesPostFilterProtocolStep object.
     *
     * @param  protocolStep  DOCUMENT ME!
     */
    protected SampleValuesPostFilterProtocolStep(final SampleValuesPostFilterProtocolStep protocolStep) {
        super(protocolStep);
        this.aggregationValues = new ArrayList<AggregationValue>(protocolStep.aggregationValues);
        this.selectedValues = new HashMap<String, Float>(protocolStep.selectedValues);
        this.minDate = new Date((protocolStep.minDate != null) ? protocolStep.minDate.getTime() : null);
        this.maxDate = new Date((protocolStep.maxDate != null) ? protocolStep.maxDate.getTime() : null);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected ProtocolStepMetaInfo createMetaInfo() {
        return META_INFO;
    }

    @Override
    public AbstractProtocolStepPanel visualize() {
        if (protocolStepPanel == null) {
            protocolStepPanel = new SampleValuesPostFilterProtocolStepPanel(this);
        }

        return protocolStepPanel;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   pollutantKey  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonIgnore
    public AggregationValue getAggregationValue(final String pollutantKey) {
        if (this.getAggregationValues() != null) {
            for (final AggregationValue aggregationValue : this.getAggregationValues()) {
                if (aggregationValue.getPollutantKey().equals(pollutantKey)) {
                    return aggregationValue;
                }
            }
        }

        return null;
    }

    @Override
    public SampleValuesPostFilterProtocolStep clone() throws CloneNotSupportedException {
        return new SampleValuesPostFilterProtocolStep(this);
    }
}
