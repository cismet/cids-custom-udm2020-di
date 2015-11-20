package de.cismet.cids.custom.udm2020di.tostringconverter;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 *
 * @author Pascal Dihé <pascal.dihe@cismet.de>
 */
public class WagwToStringConverter  extends CustomToStringConverter {
    @Override
    public String createString() {
        return cidsBean.getProperty("src_messstelle_pk").toString();
    }
}
