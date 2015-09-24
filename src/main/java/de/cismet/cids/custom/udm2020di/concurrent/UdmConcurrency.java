/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.udm2020di.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import de.cismet.commons.concurrency.CismetExecutors;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
public final class UdmConcurrency {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient ThreadGroup UDM2020DI_THREAD_GROUP;

    private static final transient ExecutorService MISC_POOL;

    static {
        final SecurityManager s = System.getSecurityManager();
        final ThreadGroup parent = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

        UDM2020DI_THREAD_GROUP = new ThreadGroup(parent, "UDM2020DI"); // NOI18N

        MISC_POOL = CismetExecutors.newFixedThreadPool(8, createThreadFactory("general-purpose")); // NOI18N
    }

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UdmConcurrency object.
     */
    private UdmConcurrency() {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   prefix  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ThreadFactory createThreadFactory(final String prefix) {
        return createThreadFactory(prefix, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   prefix      DOCUMENT ME!
     * @param   excHandler  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ThreadFactory createThreadFactory(final String prefix,
            final Thread.UncaughtExceptionHandler excHandler) {
        return new UdmThreadFactory(prefix, excHandler);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static ExecutorService getUdmGeneralPurposePool() {
        return MISC_POOL;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * Very similar to the {@link Executors#defaultThreadFactory()} implementation.
     *
     * @version  $Revision$, $Date$
     */
    private static final class UdmThreadFactory implements ThreadFactory {

        //~ Static fields/initializers -----------------------------------------

        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        //~ Instance fields ----------------------------------------------------

        private final transient String prefix;
        private final transient AtomicInteger createCount;
        private final transient Thread.UncaughtExceptionHandler excHandler;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new UdmThreadFactory object.
         *
         * @param  prefix      DOCUMENT ME!
         * @param  excHandler  DOCUMENT ME!
         */
        UdmThreadFactory(final String prefix, final Thread.UncaughtExceptionHandler excHandler) {
            this.prefix = prefix + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-"; // NOI18N
            this.createCount = new AtomicInteger(1);
            this.excHandler = excHandler;
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(UDM2020DI_THREAD_GROUP, r, prefix + createCount.getAndIncrement(), 0);

            t.setDaemon(false);
            t.setPriority(Thread.NORM_PRIORITY);
            t.setUncaughtExceptionHandler(excHandler);

            return t;
        }
    }
}
