/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.udm2020di.actions.remote;

import java.util.Collection;

import javax.swing.Action;

import de.cismet.cids.custom.udm2020di.types.Parameter;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public interface ExportAction extends Action {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    Collection<Parameter> getParameters();

    /**
     * DOCUMENT ME!
     *
     * @param  parameters  DOCUMENT ME!
     */
    void setParameters(final Collection<Parameter> parameters);

    /**
     * Get the value of exportFormat.
     *
     * @return  the value of exportFormat
     */
    String getExportFormat();

    /**
     * Set the value of exportFormat.
     *
     * @param  exportFormat  new value of exportFormat
     */
    void setExportFormat(final String exportFormat);

    /**
     * Get the value of exportName.
     *
     * @return  the value of exportName
     */
    String getExportName();

    /**
     * Set the value of exportName.
     *
     * @param  exportName  new value of exportName
     */
    void setExportName(final String exportName);
}
