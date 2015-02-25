import config.ConfigExtractor;
import org.junit.Test;

import java.util.Enumeration;

import static org.junit.Assert.assertTrue;

public class ConfigExtractorTest {

    /**
     * Count existing properties.
     */
    @Test
    public void getProperties() {
        int propertyCount = 0;
        Enumeration<String> properties = ConfigExtractor.getPropertyList();

        while (properties.hasMoreElements()) {
            properties.nextElement();
            propertyCount++;
        }

        assertTrue(propertyCount > 0);
    }

    /**
     * Read values for all properties.
     */
    @Test
    public void testValueForAllProperties() {
        Enumeration<String> properties = ConfigExtractor.getPropertyList();
        boolean allPropertiesInitialized = true;

        while (properties.hasMoreElements()) {
            String propertyName = properties.nextElement();
            String propertyValue = ConfigExtractor.getProperty(propertyName);
            if (propertyValue ==  null) {
                allPropertiesInitialized = false;
            }
        }

        assertTrue(allPropertiesInitialized);
    }

    /**
     * Test class for multi instantiation.
     */
    @Test
    public void multipleCallsToGetPropertyList() {
        try {
            for (int i = 0; i < 10; i++) {
                ConfigExtractor.getPropertyList();
            }
            assertTrue(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    /**
     * Try to obtain the value for a nonexistent property.
     */
    @Test
    public void getNonexistentProperty() {
        String nonexistentProperty = "probablyNotAValidProperty";
        String nonExistingPropertyValue = ConfigExtractor.getProperty(nonexistentProperty);

        if (nonExistingPropertyValue == null) {
            assertTrue(true);
        }
        else {
            assertTrue(false);
        }
    }

}