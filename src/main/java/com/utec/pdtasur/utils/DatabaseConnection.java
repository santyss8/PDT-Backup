package com.utec.pdtasur.utils;

import java.io.FileInputStream;
import java.io.InputStream;
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
        Properties properties = loadProperties();
        url = properties.getProperty("jdbc.connection.url");
        user = properties.getProperty("jdbc.connection.username");
        password = properties.getProperty("jdbc.connection.password");

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

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                System.out.println("No se pudo encontrar el archivo properties");
                return properties;
            }
            properties.load(input);
        } catch (Exception e) {
            System.out.println("Error al cargar configuraciones");
            e.printStackTrace(); // Para depuración, puedes quitarlo si no lo necesitas
        }
        return properties;
    }



}
