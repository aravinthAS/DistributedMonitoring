package mongo;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MongoDbConfigTest {

    /**
     * Test if Ip is valid.
     */
    @Test
    public void testGetIp() {
        MongoDbConfig mongoConfig = new MongoDbConfig();
        String ip = mongoConfig.getIp();

        final String ipPattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
        assertTrue(ip.matches(ipPattern));
    }

    /**
     * Test of port is valid.
     */
    @Test
    public void testGetPort() {
        MongoDbConfig mongoDbConfig = new MongoDbConfig();
        int port = mongoDbConfig.getPort();
        assertTrue(port >=0 && port <= 65535);
    }
}