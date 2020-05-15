package by.epam;

import java.sql.Connection;
import java.sql.SQLException;

public class Connections {
    private static ConnectionPool connectionPool;

    public static Connection get() throws SQLException {
        if (connectionPool == null){
            connectionPool = new ConnectionPool(5);
        }
        return connectionPool.retrieve();
    }
    public static void putBack(Connection connection){
        connectionPool.putBack(connection);
    }
}
