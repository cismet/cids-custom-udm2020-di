/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets;

import org.jfree.data.general.Dataset;

import java.util.Map;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé <pascal.dihe@cismet.de>
 * @version  $Revision$, $Date$
 */
public interface ChartVisualisationComponent {

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    void showProgressPanel();

    /**
     * DOCUMENT ME!
     */
    void showParameterPanel();

    /**
     * DOCUMENT ME!
     *
     * @param  chartData  DOCUMENT ME!
     */
    void renderCharts(Map<String, Dataset> chartData);
}
