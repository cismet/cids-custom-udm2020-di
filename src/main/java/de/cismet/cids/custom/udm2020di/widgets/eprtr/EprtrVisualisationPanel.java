/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets.eprtr;

import org.apache.log4j.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;

import java.awt.Color;

import de.cismet.cids.custom.udm2020di.widgets.VisualisationPanel;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class EprtrVisualisationPanel extends VisualisationPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static Logger LOGGER = Logger.getLogger(EprtrVisualisationPanel.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisVisualisationPanel object.
     */
    public EprtrVisualisationPanel() {
        super(true);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected JFreeChart createChart(final String title, final Dataset dataset) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                title,                    // title
                null,                     // x-axis label
                "Messwert",               // y-axis label
                (CategoryDataset)dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
                );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        final BarRenderer renderer = (BarRenderer)plot.getRenderer();
        renderer.setDrawBarOutline(true);

//        final CategoryAxis domainAxis = plot.getDomainAxis();
//        domainAxis.setCategoryLabelPositions(
//            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }
}
