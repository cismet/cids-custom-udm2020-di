/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ImageIcon;

import de.cismet.cids.custom.udm2020di.types.Parameter;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@type"
)

/**
 * SubTypes Defintiion only required if used with NAME property!
 * see http://stackoverflow.com/questions/31665620/is-jacksons-jsonsubtypes-still-necessary-for-polymorphic-deserialization
 */
@JsonSubTypes(
    {
        @Type(
            value = BorisExportAction.class,
            name = "BORIS"
        ),
        @Type(
            value = EprtrExportAction.class,
            name = "EPRTR"
        ),
        @Type(
            value = MossExportAction.class,
            name = "MOSS"
        ),
        @Type(
            value = WaExportAction.class,
            name = "WATER"
        )
    }
)
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ExportAction extends Action, Cloneable {

    //~ Instance fields --------------------------------------------------------

    String PARAMETER_SETTINGS = "PARAMETER_SETTINGS";
    String EXPORT_FORMAT_SETTINGS = "EXPORT_FORMAT_SETTINGS";

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonProperty(required = true)
    Collection<Parameter> getParameters();

    /**
     * DOCUMENT ME!
     *
     * @param  parameters  DOCUMENT ME!
     */
    @JsonProperty(required = true)
    void setParameters(final Collection<Parameter> parameters);

    /**
     * Get the value of exportFormat.
     *
     * @return  the value of exportFormat
     */
    @JsonProperty(required = true)
    String getExportFormat();

    /**
     * Set the value of exportFormat.
     *
     * @param  exportFormat  new value of exportFormat
     */
    @JsonProperty(required = true)
    void setExportFormat(final String exportFormat);

    /**
     * Get the value of exportName.
     *
     * @return  the value of exportName
     */
    @JsonProperty(required = true)
    String getExportName();

    /**
     * Set the value of exportName.
     *
     * @param  exportName  new value of exportName
     */
    @JsonProperty(required = true)
    void setExportName(final String exportName);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean isProtocolEnabled();

    /**
     * DOCUMENT ME!
     *
     * @param  protocolEnabled  DOCUMENT ME!
     */
    void setProtocolEnabled(boolean protocolEnabled);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    String getTitle();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    ImageIcon getIcon();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    int getClassId();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonProperty(required = true)
    Map<Long, String> getObjects();

    /**
     * DOCUMENT ME!
     *
     * @param  objects  DOCUMENT ME!
     */
    @JsonProperty(required = true)
    void setObjects(Map<Long, String> objects);

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    boolean isProtocolAction();
}
