package org.opens.tanaguru.sebuilder.tools;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.opens.tanaguru.sebuilder.interpreter.webdriverfactory.FirefoxDriverFactory;

/**
 * Created by ubuntu on 4/3/14.
 */
public class AbstractFirefoxDriverObjectPoolTest extends TestCase {
    protected static final Logger LOGGER = Logger.getLogger(FirefoxDriverFactory.class);

    FirefoxDriverPoolableObjectFactory firefoxDriverPoolableObjectFactory;
    FirefoxDriverObjectPool firefoxDriverObjectPool;

    public void setUp() throws Exception {
        LOGGER.info("Begin Pool Creation");
        super.setUp();
        firefoxDriverPoolableObjectFactory = new FirefoxDriverPoolableObjectFactory();
        firefoxDriverObjectPool = new FirefoxDriverObjectPool(firefoxDriverPoolableObjectFactory);
    }

    protected void logPoolInformation(String message) {
        LOGGER.info(message+" Num Active Objects = " + firefoxDriverObjectPool.getNumActive() +
                " Num Idle Objects  = " + firefoxDriverObjectPool.getNumIdle());
    }

    /**
     * sample Configuration for the pool, this is not a fine tuned configuration
     * done just for the purpose of the test
     */
    protected void setPoolConfiguration(int  maxActive, int maxIdle, long maxWait, long minEvictableIdleTimeMillis,
                                      byte whenexhaustedAction) {
        LOGGER.info("Configuring the Pool");
        firefoxDriverObjectPool.setMaxActive(maxActive);
        firefoxDriverObjectPool.setMaxIdle(maxIdle);
        firefoxDriverObjectPool.setMaxWait(maxWait);
        firefoxDriverObjectPool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        firefoxDriverObjectPool.setWhenExhaustedAction(whenexhaustedAction);
    }


    public void tearDown() throws Exception {
        firefoxDriverObjectPool.close();
    }
}
