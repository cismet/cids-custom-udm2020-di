/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.objectrenderer;

import org.apache.log4j.Logger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import java.io.IOException;

import javax.swing.JLabel;

import de.cismet.cids.custom.udm2020di.actions.remote.WaExportAction;
import de.cismet.cids.custom.udm2020di.indeximport.OracleImport;
import de.cismet.cids.custom.udm2020di.types.wa.Messstelle;
import de.cismet.cids.custom.udm2020di.types.wa.OwMessstelle;

/**
 * DOCUMENT ME!
 *
 * @author   Pascal Dihé
 * @version  $Revision$, $Date$
 */
public class WaowStationRenderer extends WagwStationRenderer {

    //~ Static fields/initializers ---------------------------------------------

    protected static final Logger LOGGER = Logger.getLogger(WagwStationRenderer.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new WaowStationRenderer object.
     */
    public WaowStationRenderer() {
        super();
        this.stationType = WaExportAction.WAOW;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     *
     * @throws  IOException  DOCUMENT ME!
     */
    @Override
    protected Messstelle deserializeStation() throws IOException {
        return OracleImport.JSON_MAPPER.readValue(WaowStationRenderer.this.getCidsBean().getProperty("src_content")
                        .toString(),
                OwMessstelle.class);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  gridBagConstraints  DOCUMENT ME!
     * @param  messstelle          DOCUMENT ME!
     */
    @Override
    protected void addCustomStationLabels(
            final GridBagConstraints gridBagConstraints,
            final Messstelle messstelle) {
        final OwMessstelle owMessstelle = (OwMessstelle)messstelle;
        JLabel label;

        if ((owMessstelle.getOperativ() != null)
                    && !owMessstelle.getOperativ().isEmpty()) {
            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.0;
            label = new JLabel("Operativ:");
            label.setMaximumSize(new Dimension(150, 50));
            standortdatenPanel.add(label, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 1.0;
            label = new JLabel("<html>" + owMessstelle.getOperativ() + "</html>");
            label.setMaximumSize(new Dimension(200, 50));
            standortdatenPanel.add(label, gridBagConstraints);
        }

        if ((owMessstelle.getGewaessername() != null)
                    && !owMessstelle.getGewaessername().isEmpty()) {
            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.0;
            label = new JLabel("Gewässername:");
            label.setMaximumSize(new Dimension(150, 50));
            standortdatenPanel.add(label, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 1.0;
            label = new JLabel("<html>" + owMessstelle.getGewaessername() + "</html>");
            label.setMaximumSize(new Dimension(200, 50));
            standortdatenPanel.add(label, gridBagConstraints);
        }

        if ((owMessstelle.getGewaesserEzk() != null)
                    && !owMessstelle.getGewaesserEzk().isEmpty()) {
            gridBagConstraints.gridy++;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.0;
            label = new JLabel("Einzugsgebietsgrößenklasse:");
            label.setMaximumSize(new Dimension(150, 50));
            standortdatenPanel.add(label, gridBagConstraints);
            gridBagConstraints.gridx = 1;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            label = new JLabel("<html>" + owMessstelle.getGewaesserEzk() + "</html>");
            label.setMaximumSize(new Dimension(200, 50));
            standortdatenPanel.add(label, gridBagConstraints);
        }
    }

    @Override
    protected WaowStationRenderer getOuter() {
        return WaowStationRenderer.this;
    }

    @Override
    public String getTitle() {
        String desc = " Oberflächengewässermessstelle";
        if (this.getCidsBean() != null) {
            desc += ": ";
            desc += this.getCidsBean().getProperty("name").toString();
        }
        return desc;
    }
}
