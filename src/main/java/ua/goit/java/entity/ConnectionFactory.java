package ua.goit.java.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by bulov on 03.03.2017.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);
    private Connection connection;

    public ConnectionFactory() {
        String url = "jdbc:mysql://localhost:5555/home_task1";
        String user = "root";
        String password = "04062015";

        try {
            LOGGER.info("Try connecting to DB: " + url);
            connection = DriverManager.getConnection(url, user, password);
            LOGGER.info("Connecting is successful");
        } catch (SQLException e) {
            LOGGER.error("Somethings is bad with DB: " + url, e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
