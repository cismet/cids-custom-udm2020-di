/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.actions.remote;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;

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
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "class"
)
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public interface ExportAction extends Action, Cloneable {

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
    @JsonProperty(required = true)
    int getClassId();

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @JsonProperty(required = true)
    Collection<Long> getObjectIds();

    /**
     * DOCUMENT ME!
     *
     * @param  objectIds  DOCUMENT ME!
     */
    @JsonProperty(required = true)
    void setObjectIds(Collection<Long> objectIds);
}
