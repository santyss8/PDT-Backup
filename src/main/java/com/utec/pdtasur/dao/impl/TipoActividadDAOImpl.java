package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class TipoActividadDAOImpl {
    private Connection connection;

    public TipoActividadDAOImpl() throws SQLException {
        this.connection = getConnection();
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

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

}
