package processors;

import com.fasterxml.jackson.databind.JsonNode;
import dmon.core.commons.datamodel.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Use invocation to load the list of classes representing processors
 * and the class representing the adapter.
 */
public class ProcessorsExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ProcessorsExecutor.class);

    /**
     * Load and execute the processors classes and the adapter class.
     *
     * @param result the raw results generated by the monitoring tool
     * @param measurement contains the processors list and the adapter
     * @return processed results
     */
    public String executeProcesses(String result, Measurement measurement) {
        String[] processors = measurement.getProcessors();
        if (processors != null) {
            for (String processor : processors) {
                try {
                    processor = "processors." + processor;
                    Class<?> cls = Class.forName(processor);
                    Method method = cls.getDeclaredMethod("process", new Class[]{String.class, JsonNode.class});
                    Object obj = cls.newInstance();
                    result = (String) method.invoke(obj, new Object[]{result, null});
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (measurement.getAdapter() != null) {
            try {
                Class<?> cls = Class.forName(measurement.getAdapter());
                Method method = cls.getDeclaredMethod("adaptMessage", new Class[] {String.class, Measurement.class});
                Object obj = cls.newInstance();
                result = (String)method.invoke(obj, new Object[] {result, measurement});
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return result;
    }
}
