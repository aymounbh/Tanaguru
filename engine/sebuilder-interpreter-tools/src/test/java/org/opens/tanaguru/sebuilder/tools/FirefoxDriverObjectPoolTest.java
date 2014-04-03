package org.opens.tanaguru.sebuilder.tools;

import org.openqa.selenium.firefox.FirefoxDriver;

import static org.apache.commons.pool.impl.GenericObjectPool.WHEN_EXHAUSTED_GROW;

/**
 * Created by ubuntu on 3/31/14.
 */
public class FirefoxDriverObjectPoolTest extends AbstractFirefoxDriverObjectPoolTest {

    public static final int MAX_ACTIVE = 5;
    public static final int MAX_IDLE = 5;
    public static final long MAX_WAIT = 60000;
    public static final long MIN_EVICTABLE_IDLE_TIME_MILLIS = 60000;



    @Override
    public void setUp() throws Exception {
        super.setUp();
        setPoolConfiguration(MAX_ACTIVE,MAX_IDLE,MAX_WAIT,MIN_EVICTABLE_IDLE_TIME_MILLIS,
                WHEN_EXHAUSTED_GROW);
    }

    public void test_pool_configuration() {
        assertEquals("testing max active pool property", 5, firefoxDriverObjectPool.getMaxActive());
        assertEquals("testing max idle pool property", 5, firefoxDriverObjectPool.getMaxIdle());
        assertEquals("testing max wait pool property", 60000, firefoxDriverObjectPool.getMaxWait());
        assertEquals("testing mine evictable idle time millis pool property", 60000, firefoxDriverObjectPool.getMinEvictableIdleTimeMillis());
        assertEquals("testing when exhausted action pool property", 2, firefoxDriverObjectPool.getWhenExhaustedAction());
    }

    public void test_borrow_object() throws Exception {
        doWithInstance("http://www.google.com");
        doWithInstance("http://www.oceaneconsulting.com");
    }

    public void test_borrow_multiple_objects() throws Exception {
        for (int i = 0; i < 10; i++) {
            logPoolInformation("Before borrowing object");
            doWithInstance("http://www.google.com");
            doWithInstance("http://www.oceaneconsulting.com");
            logPoolInformation("After borrowing object");
        }
    }

    private void doWithInstance(String url) throws Exception {
        getInstanceFromThePool(url);
    }

    /**
     * borrows an object from the pool, do some work then return it back to the pool
     * @param url
     * @throws Exception
     */
    private void getInstanceFromThePool(String url) throws Exception {
        final FirefoxDriver firefoxDriver = firefoxDriverObjectPool.borrowObject();
        firefoxDriver.get(url);
        returnInstanceBackIntoPool(firefoxDriver);
    }

    private void returnInstanceBackIntoPool(FirefoxDriver driver) throws Exception {
        driver.close();
//        firefoxDriverObjectPool.returnObject(driver);
        logPoolInformation("After borrowing object");
    }

}
