/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class ImageUtil {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ImageUtil object.
     */
    private ImageUtil() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   c             DOCUMENT ME!
     * @param   resourceName  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static String getResourcePath(final Class<?> c, final String resourceName) {
        return c.getPackage().getName().replaceAll("\\.", "/") + "/" + resourceName; // NOI18N
    }
}
