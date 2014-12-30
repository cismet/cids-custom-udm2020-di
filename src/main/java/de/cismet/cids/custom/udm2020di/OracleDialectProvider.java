/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di;

import Sirius.server.sql.DialectProvider;

import org.openide.util.lookup.ServiceProvider;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
@ServiceProvider(
    service = DialectProvider.class,
    position = 1
)
public final class OracleDialectProvider implements DialectProvider {

    //~ Methods ----------------------------------------------------------------

    @Override
    public String getDialect() {
        return "oracle_11g";
    }
}
