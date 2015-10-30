/*
 * ***************************************************
 * 
 * cismet GmbH, Saarbruecken, Germany
 * 
 *               ... and it just works.
 * 
 * ***************************************************
 */
package de.cismet.cids.custom.udm2020di.widgets;

import java.util.Map;
import org.jfree.data.general.Dataset;

/**
 *
 * @author Pascal Dihé <pascal.dihe@cismet.de>
 */
public interface ChartVisualisationComponent {
    
    public void renderCharts(Map<String, Dataset> chartData);
    
}
