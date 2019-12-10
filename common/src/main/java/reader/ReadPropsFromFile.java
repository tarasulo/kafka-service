package reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class ReadPropsFromFile {
    /**
     * This is a class for reading properties from file
     */
    private final Logger LOGGER = LoggerFactory.getLogger(ReadPropsFromFile.class);

    public Properties read(String fileName) {
        /**
         * This is the method which reading properties from the file
         * and @return properties
         */
        Properties props = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream(fileName);
            props.load(input);
            return props;
        } catch (IOException e) {
            LOGGER.error(String.valueOf(e));
        }
        return null;
    }
}
