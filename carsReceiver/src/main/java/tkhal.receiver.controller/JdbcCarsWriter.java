package tkhal.receiver.controller;

import dao.CarDaoJdbcImpl;
import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcCarsWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarStandardizerController.class);
    private Connection connection;

    public void writeToDataBase(Car car) throws SQLException {

        // reading properties for Jdbc connection from config file
        Properties propsJdbc = new Properties();

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("configJdbc.properties");
            propsJdbc.load(input);
            Class.forName(propsJdbc.getProperty("jdbc.driver"));
            connection =
                    DriverManager.getConnection(propsJdbc.getProperty("db.url")
                            + propsJdbc.getProperty("credentials"));
            // starting Jdbc connection
            CarDaoJdbcImpl carDaoJdbc = new CarDaoJdbcImpl(connection);
            // writing car to database
            carDaoJdbc.add(car);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            LOGGER.error("Can't establish connection to our DB", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
