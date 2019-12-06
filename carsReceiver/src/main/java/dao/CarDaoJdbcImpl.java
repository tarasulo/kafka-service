package dao;

import model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDaoJdbcImpl extends AbstractDao<Car> {
    private static String queryAddCar = "INSERT INTO factory.car "
            + "(brand, model, year, engine) VALUES (?, ?, ?, ?);";
    private static String queryGetById = "SELECT * FROM factory.car WHERE id = ?;";
    private static String queryGetLast = "SELECT * FROM factory.car WHERE id = (SELECT max(id) FROM factory.car);";

    private final static Logger LOGGER = LoggerFactory.getLogger(CarDaoJdbcImpl.class);

    public CarDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    public Car add(Car finalCar) {
        try (PreparedStatement statement = connection.prepareStatement(
                queryAddCar, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, finalCar.getBrand());
            statement.setString(2, finalCar.getModel());
            statement.setInt(3, finalCar.getYear());
            statement.setDouble(4, finalCar.getEngine());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                finalCar.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Can't create car", e);
        }
        return finalCar;
    }

    public Car getById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(queryGetById)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                Double engine = resultSet.getDouble("engine");
                return new Car(brand, model, year, engine);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get item by id " + id, e);
        }
        return null;
    }

    public Car getBylastId() {
        try (PreparedStatement statement = connection.prepareStatement(queryGetLast)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                Double engine = resultSet.getDouble("engine");
                return new Car(brand, model, year, engine);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get last item  ", e);
        }
        return null;
    }
}
