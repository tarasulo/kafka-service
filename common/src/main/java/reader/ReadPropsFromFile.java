package reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ReadPropsFromFile is a class for reading properties from file
 */
public class ReadPropsFromFile {

    private final Logger LOGGER = LoggerFactory.getLogger(ReadPropsFromFile.class);

    /**
     * This is the method which reading properties from the file
     * and @return properties
     */
    public Properties read(String fileName) {
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
