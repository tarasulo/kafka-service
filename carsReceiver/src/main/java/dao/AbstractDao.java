package dao;

import java.sql.Connection;

/**
 * @author Taras Khalak
 */
public class AbstractDao<T> {
    /**
     * This is universal class for making connection
     */
    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }
}
