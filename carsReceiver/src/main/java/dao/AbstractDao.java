package dao;

import java.sql.Connection;

/**
 * AbstractDao is a universal class for making connection
 */
public class AbstractDao<T> {

    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }
}
