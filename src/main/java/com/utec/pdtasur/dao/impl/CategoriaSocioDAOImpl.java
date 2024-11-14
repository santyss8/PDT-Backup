package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.CategoriaSocioDAO;
import com.utec.pdtasur.models.CategoriaSocio;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CategoriaSocioDAOImpl implements CategoriaSocioDAO {
    private Connection connection;

    public CategoriaSocioDAOImpl() throws SQLException {
        this.connection = getConnection();
    }

    @Override
    public void crearCategoria(CategoriaSocio categoriaSocio) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertCategoria");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, categoriaSocio.getNombre());
            ps.setString(2, categoriaSocio.getDescripcion());
            ps.executeUpdate();
            System.out.println("Categoria creada con exito");
        }catch (Exception e){
            System.out.println("Hubo un error al crear categoria");
            e.printStackTrace();
        }

    }

    @Override
    public CategoriaSocio getCategoria(int id) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.selectCategoriaId");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                CategoriaSocio categoriaSocio = new CategoriaSocio();
                categoriaSocio.setId(rs.getInt("id"));
                categoriaSocio.setNombre(rs.getString("nombre"));
                categoriaSocio.setDescripcion(rs.getString("descripcion"));
                return categoriaSocio;
            }
        }catch (Exception e){
            System.out.println("Error al seleccionar Categoria");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CategoriaSocio> listarCategorias() {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.selectCategoria");

        List<CategoriaSocio> categorias = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CategoriaSocio categoria = new CategoriaSocio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                categorias.add(categoria);
            }
        }catch (Exception e){
            System.out.println("Error al Listar Categorias");
            e.printStackTrace();
        }
        return categorias;
    }

    @Override
    public void eliminarCategoria(int id) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.deleteCategoria");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Categoria eliminada con exito");
        }catch (Exception e){
            System.out.println("Error al eliminar Categoria");
            e.printStackTrace();
        }

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
