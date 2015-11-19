/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import java.util.Collection;

import de.cismet.cids.custom.udm2020di.types.Parameter;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public interface ConfigurableRenderer {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  selectedParameters  DOCUMENT ME!
     */
    void showExportPanel(final Collection<Parameter> selectedParameters);
}
