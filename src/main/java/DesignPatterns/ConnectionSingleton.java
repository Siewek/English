package DesignPatterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionSingleton {
    private static String jdbcUrl = "jdbc:sqlite:wordsdb.db";
    private static Connection connection = null;

    private ConnectionSingleton() {};

    static Connection getInstance() {
        if(connection == null) {
            try {
                Properties connectionProperties = new Properties();
                connectionProperties.put("charSet", "UTF8");
                connectionProperties.put("encoding", "UTF8");
                connection = DriverManager.getConnection(jdbcUrl, connectionProperties);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
