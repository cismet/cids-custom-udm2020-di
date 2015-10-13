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
package de.cismet.cids.custom.udm2020di.actions.local;

import java.util.Collection;

import javax.swing.AbstractAction;

import de.cismet.cids.custom.udm2020di.actions.remote.ExportAction;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   pd
 * @version  $Revision$, $Date$
 */
public abstract class AbstractCidsBeanExportAction extends AbstractAction implements CidsBeanExportAction {

    //~ Instance fields --------------------------------------------------------

    private Collection<CidsBean> cidsBeans;

    private String exportFormat;

    private String exportName;

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the value of exportName.
     *
     * @return  the value of exportName
     */
    @Override
    public String getExportName() {
        return exportName;
    }

    /**
     * Set the value of exportName.
     *
     * @param  exportName  new value of exportName
     */
    @Override
    public void setExportName(final String exportName) {
        this.exportName = exportName;
    }

    /**
     * Get the value of exportFormat.
     *
     * @return  the value of exportFormat
     */
    @Override
    public String getExportFormat() {
        return exportFormat;
    }

    /**
     * Set the value of exportFormat.
     *
     * @param  exportFormat  new value of exportFormat
     */
    @Override
    public void setExportFormat(final String exportFormat) {
        this.exportFormat = exportFormat;
    }

    /**
     * Get the value of cidsBeans.
     *
     * @return  the value of cidsBeans
     */
    @Override
    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    /**
     * Set the value of cidsBeans.
     *
     * @param  cidsBeans  new value of cidsBeans
     */
    @Override
    public void setCidsBeans(final Collection<CidsBean> cidsBeans) {
        this.cidsBeans = cidsBeans;
    }
}
