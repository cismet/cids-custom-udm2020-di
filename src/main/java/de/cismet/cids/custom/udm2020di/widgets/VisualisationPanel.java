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

import java.awt.CardLayout;
import java.util.Map;
import org.jfree.data.general.Dataset;

/**
 *
 * @author Pascal Dihé <pascal.dihe@cismet.de>
 */
public class VisualisationPanel extends javax.swing.JPanel implements ChartVisualisationComponent {

    /**
     * Creates new form VisualisationPanel
     */
    public VisualisationPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        visualisationParameterSelectionPanel = new de.cismet.cids.custom.udm2020di.widgets.VisualisationParameterSelectionPanel();
        chartsPanel = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());
        add(visualisationParameterSelectionPanel, "parameterSelection");

        javax.swing.GroupLayout chartsPanelLayout = new javax.swing.GroupLayout(chartsPanel);
        chartsPanel.setLayout(chartsPanelLayout);
        chartsPanelLayout.setHorizontalGroup(
            chartsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        chartsPanelLayout.setVerticalGroup(
            chartsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(chartsPanel, "charts");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartsPanel;
    private de.cismet.cids.custom.udm2020di.widgets.VisualisationParameterSelectionPanel visualisationParameterSelectionPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void renderCharts(Map<String, Dataset> chartData) {
        
        ((CardLayout)this.getLayout()).show(this, "charts");
    }
}
