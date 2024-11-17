package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.models.Espacio;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EspacioDAOImpl {
    private Connection connection;
    // Metodo para conectar a la base de datos
    public EspacioDAOImpl() throws SQLException {
        this.connection = getConnection();
    }

    // Metodo para crear un espacio
    public void insertarEspacio(Espacio espacio) throws SQLException {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertarEspacio");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, espacio.getNombre());
            ps.setInt(2, espacio.getCapacidadMaxima());
            ps.setDouble(3, espacio.getPrecioReservaSocio());
            ps.setDouble(4, espacio.getPrecioReservaNoSocio());
            ps.setDate(5, Date.valueOf(espacio.getFechaVigenciaPrecio()));
            ps.setString(6, espacio.getObservaciones());
            ps.executeUpdate();
            System.out.println("Espacio creado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo para listar todos los espacios
    public List<Espacio> listarEspacios() throws SQLException {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.selectEspacio");
        List<Espacio> espacios = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Espacio espacio = new Espacio(rs.getInt("id"), rs.getString("nombre"), rs.getInt("capacidad_maxima"), rs.getDouble("precio_reserva_socio"), rs.getDouble("precio_reserva_no_socio"), rs.getDate("fecha_vigencia_precios").toLocalDate(), rs.getString("observaciones"), rs.getBoolean("estado"), rs.getDate("fecha_creacion").toLocalDate());
                espacios.add(espacio);
            }
            return espacios;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void modificarEspacio(Espacio espacio){
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.modificarEspacio");
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, espacio.getNombre());
            ps.setInt(2, espacio.getCapacidadMaxima());
            ps.setDouble(3, espacio.getPrecioReservaSocio());
            ps.setDouble(4, espacio.getPrecioReservaNoSocio());
            ps.setDate(5, Date.valueOf(espacio.getFechaVigenciaPrecio()));
            ps.setString(6, espacio.getObservaciones());
            ps.setInt(7, espacio.getId());
            ps.executeUpdate();
            System.out.println("Espacio Modificado con exito");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void activarEspacio(Espacio espacio){
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.activarEspacio");
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, espacio.getId());
            ps.executeUpdate();
            System.out.println("Espacio activado Correctamente");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<Espacio> espaciosDisponibles(int capacidadMaxima, LocalDate fecha){
        Properties properties = loadProperties();
        List<Espacio> espaciosDisponibles = new ArrayList<>();
        String sql = properties.getProperty("sql.seleccionarEspaciosDisponibles");

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, capacidadMaxima);
            ps.setDate(2, Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Espacio espacio = new Espacio();
                espacio.setId(rs.getInt("id"));
                espacio.setNombre(rs.getString("nombre"));
                espacio.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                espacio.setPrecioReservaSocio(rs.getDouble("precio_reserva_socio"));
                espacio.setPrecioReservaNoSocio(rs.getDouble("precio_reserva_no_socio"));
                espacio.setFechaVigenciaPrecio(rs.getDate("fecha_vigencia_precios").toLocalDate());
                espacio.setObservaciones(rs.getString("observaciones"));
                espacio.setActivo(rs.getBoolean("estado"));
                espacio.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                espaciosDisponibles.add(espacio);
            }
        }catch (SQLException e){
            System.out.println("Error al listar espacios disponibles");
            e.printStackTrace();
        }

        return espaciosDisponibles;

    }


    public Espacio seleccionarEspacio(int id){
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.selectEspacioId");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Espacio espacio = new Espacio();
                espacio.setId(rs.getInt("id"));
                espacio.setNombre(rs.getString("nombre"));
                espacio.setCapacidadMaxima(rs.getInt("capacidad_maxima"));
                espacio.setPrecioReservaSocio(rs.getDouble("precio_reserva_socio"));
                espacio.setPrecioReservaNoSocio(rs.getDouble("precio_reserva_no_socio"));
                espacio.setFechaVigenciaPrecio(rs.getDate("fecha_vigencia_precios").toLocalDate());
                espacio.setObservaciones(rs.getString("observaciones"));
                espacio.setActivo(rs.getBoolean("estado"));
                espacio.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                return espacio;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void eliminarEspacio(Espacio espacio){
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.eliminarEspacio");
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, espacio.getId());
            ps.executeUpdate();
            System.out.println("Espacio eliminado con exito");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }



    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
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
