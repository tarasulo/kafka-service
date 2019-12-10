package tkhal.receiver.controller;

import dao.CarDaoJdbcImpl;
import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.ReadPropsFromFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * @author Taras Khalak
 */
public class JdbcCarsWriter {
    /**
     * This is class for connection to database
     * and writing cars
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CarStandardizerController.class);
    private Connection connection;
    private ReadPropsFromFile readPropsFromFile;

    public JdbcCarsWriter() {
        readPropsFromFile = new ReadPropsFromFile();
    }

    public void writeToDataBase(Car car) throws SQLException {
        /**
         * This is the method which reading properties from file,
         * making connection to database
         * and writing cars
         */
        new JdbcCarsWriter();

        // reading properties for Jdbc connection from config file
        Properties propsJdbc = readPropsFromFile.read("configJdbc.properties");

        try {
            Class.forName(propsJdbc.getProperty("jdbc.driver"));
            connection =
                    DriverManager.getConnection(propsJdbc.getProperty("db.url")
                            + propsJdbc.getProperty("credentials"));
            // starting Jdbc connection
            CarDaoJdbcImpl carDaoJdbc = new CarDaoJdbcImpl(connection);
            // writing car to database
            carDaoJdbc.add(car);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Can't establish connection to our DB", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
