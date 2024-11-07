package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.models.CategoriaSocio;
import com.utec.pdtasur.models.Subcomision;
import com.utec.pdtasur.models.TipoUsuario;
import com.utec.pdtasur.models.Usuario;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class UsuarioDAOImpl implements com.utec.pdtasur.dao.interfaces.UsuarioDAO {
    // Registrar No Socio
    public void registrar(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuario");

        // Conexion a la base de datos
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setInt(3, usuario.getTipoDocumento());
            ps.setString(4, usuario.getNumeroDocumento());
            ps.setDate(5, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(6, usuario.getDomicilio());
            ps.setString(7, usuario.getEmail());
            ps.setString(8, usuario.getContraseña());
            ps.setString(9, usuario.getTipoUsuario().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Ha ocurrido un error");
            e.printStackTrace();
        }
    }

    @Override
    public void registrarAdmin(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuarioAdmin");

        // Conexion a la base de datos
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setInt(3, usuario.getTipoDocumento());
            ps.setString(4, usuario.getNumeroDocumento());
            ps.setDate(5, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(6, usuario.getDomicilio());
            ps.setString(7, usuario.getEmail());
            ps.setString(8, usuario.getContraseña());
            ps.setString(9, usuario.getTipoUsuario().toString());
            ps.setBoolean(10, true);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario");
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("Ha ocurrido un error");
            e.printStackTrace();
        }
    }

    @Override
    public void registrarSocio(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuarioSocio");

        // Conexion a la base de datos
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setInt(3, usuario.getTipoDocumento());
            ps.setString(4, usuario.getNumeroDocumento());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getContraseña());
            ps.setString(7, usuario.getCategoriaSocio().getNombre());
            ps.setBoolean(8, usuario.isDificultadAuditiva());
            ps.setBoolean(9, usuario.isManejoLenguajeDeSeñas());
            ps.setBoolean(10, usuario.isParticipaSubcomision());
            ps.setString(11, usuario.getSubcomision().getNombre());
            ps.setDate(12, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(13, usuario.getDomicilio());
            ps.setString(14, usuario.getTipoUsuario().toString());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error al registrar usuario");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Ha ocurrido un error");
            e.printStackTrace();
        }


    }

    @Override
    public void modificar(Usuario usuario) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.updateUsuario");

    }

    @Override
    public void eliminar(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.deleteUsuario");

        // Conexion a la base de datos
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setString(2, usuario.getNumeroDocumento());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error al eliminar usuario");
            e.printStackTrace();
        }

    }

    @Override
    public Usuario login(String email, String contraseña) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.login");

        return null;

    }

    @Override
    public void modificarDatosPropios(Usuario usuario) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.updateUsuarioSolo");
    }

    @Override
    public List<Usuario> listarUsuarios() {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.selectUsuarios");

        return null;
    }

    // metodo para recuperar propiedades y para generar clase conexion
    private Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fs = new FileInputStream("src/main/resources/app.properties")) {
            properties.load(fs);
        } catch (Exception e){
            System.out.println("Error al cargar configuraciones");
        }
        return properties;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public static void main(String[] args) {
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
        LocalDate fechaNacimiento = LocalDate.of(2004, 9, 16);
        Usuario usuarioAdmin = new Usuario("Santiago", "Molina", 1, "5-447-191-6", fechaNacimiento, "Crottogini 3513", "santimolinarueda@gmail.com", "santi123", TipoUsuario.AUXILIARADMINISTRATIVO);
        usuarioDAO.registrar(usuarioAdmin);
    }


}
