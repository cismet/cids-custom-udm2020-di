/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.widgets.boris;

import Sirius.navigator.exception.ConnectionException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.apache.commons.io.IOUtils;
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
import java.awt.Dimension;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JFrame;

import de.cismet.cids.custom.udm2020di.actions.remote.BorisVisualisationAction;
import de.cismet.cids.custom.udm2020di.actions.remote.VisualisationAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.Parameter;
import de.cismet.cids.custom.udm2020di.types.boris.Standort;
import de.cismet.cids.custom.udm2020di.widgets.VisualisationPanel;

import de.cismet.cids.server.actions.ServerActionParameter;

import de.cismet.tools.gui.log4jquickconfig.Log4JQuickConfig;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class BorisVisualisationPanel extends VisualisationPanel {

    //~ Static fields/initializers ---------------------------------------------

    protected static Logger LOGGER = Logger.getLogger(BorisVisualisationPanel.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BorisVisualisationPanel object.
     */
    public BorisVisualisationPanel() {
        super();
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    protected JFreeChart createChart(final String title, final Dataset dataset) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                title,                                             // title
                null,                                              // x-axis label
                org.openide.util.NbBundle.getMessage(
                    BorisVisualisationPanel.class,
                    "BorisVisualisationPanel.chart.y-axis-label"), // y-axis label
                (CategoryDataset)dataset,                          // data
                PlotOrientation.VERTICAL,                          // orientation
                true,                                              // include legend
                true,                                              // tooltips?
                false                                              // URLs?
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

    /**
     * DOCUMENT ME!
     *
     * @param  args  DOCUMENT ME!
     */
    public static void main(final String[] args) {
        try {
            Log4JQuickConfig.configure4LumbermillOnLocalhost();

            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse("2001-01-05 00:00:00.0");

            final Standort borisStandort = OracleImport.JSON_MAPPER.readValue(
                    VisualisationPanel.class.getResourceAsStream(
                        "/de/cismet/cids/custom/udm2020di/testing/BorisStandort.json"),
                    Standort.class);

            final String borisCsv = IOUtils.toString(VisualisationPanel.class.getResourceAsStream(
                        "/de/cismet/cids/custom/udm2020di/testing/boris-export.csv"),
                    "UTF-8");

            final Collection<Parameter> parameters = new ArrayList<Parameter>(borisStandort.getProbenparameter());
            final BorisVisualisationPanel panel = new BorisVisualisationPanel();
            panel.setParameters(parameters);

            final CsvMapper mapper = new CsvMapper();
            final CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            mapper.enable(CsvParser.Feature.TRIM_SPACES);
            final MappingIterator<String[]> readValues = mapper.reader(String[].class)
                        .with(bootstrapSchema)
                        .readValues(borisCsv);
            readValues.readAll();

            final VisualisationAction visualisationAction = new BorisVisualisationAction(
                    Arrays.asList(new Standort[] { borisStandort }),
                    panel.visualisationParameterSelectionPanel.getSelectedParameters(),
                    panel) {

                    @Override
                    protected String downloadCsvFile(final ServerActionParameter[] serverActionParameters)
                            throws ConnectionException {
                        return borisCsv;
                    }
                };
            panel.setVisualisationAction(visualisationAction);

            final JFrame frame = new JFrame("BORIS VisualisationPanel");
            frame.getContentPane().add(panel);
            frame.getContentPane().setPreferredSize(new Dimension(800, 600));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(VisualisationPanel.class).fatal(ex.getMessage(), ex);
            System.exit(1);
        }
    }
}
