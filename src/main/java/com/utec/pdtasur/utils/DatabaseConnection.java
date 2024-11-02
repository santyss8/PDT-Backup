package com.utec.pdtasur.utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;
    private String url;
    private String user;
    private String password;

    private DatabaseConnection() throws SQLException {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/app.properties")) {
            properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("Error loading properties file");
            e.printStackTrace();
        }
        url = properties.getProperty("jdbc.url");
        user = properties.getProperty("jdbc.username");
        password = properties.getProperty("jdbc.password");
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new SQLException("Error al conectar a la base de datos");
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }



}
