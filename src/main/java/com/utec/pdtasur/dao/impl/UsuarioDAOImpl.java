package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
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

// TODO Arreglar detalles de subcomisiones para que se registren de la mejor forma

public class UsuarioDAOImpl implements com.utec.pdtasur.dao.interfaces.UsuarioDAO {
    private Connection connection;

    public UsuarioDAOImpl() throws SQLException {
        this.connection = getConnection();
    }
    // Registrar No Socio
    public void registrar(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuario");

        // Conexion a la base de datos
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getCategoriaSocio().getNombre());
            ps.setBoolean(2, usuario.isDificultadAuditiva());
            ps.setBoolean(3, usuario.isManejoLenguajeDeSeñas());
            ps.setString(4, usuario.getSubcomision().getDescripcion());
            ps.setString(5, usuario.getTipoUsuario().toString());
            ps.setString(6, usuario.getSubcomision().getNombre());
            ps.setString(7, usuario.getNumeroDocumento());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error al modificar usuario");
            e.printStackTrace();
        }

    }

    @Override
    public void eliminar(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.deleteUsuario");

        // Conexion a la base de datos
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("tipo_usuario").equals("NOSOCIO") || rs.getString("tipo_usuario").equals("AUXILIARADMINISTRATIVO")) {
                    return new Usuario(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("tipo_documento"),
                            rs.getString("documento"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("domicilio"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            TipoUsuario.valueOf(rs.getString("tipo_usuario"))
                    );
                }
                if (rs.getString("tipo_usuario").equals("SOCIO")){
                    return new Usuario(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("tipo_documento"),
                            rs.getString("documento"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("domicilio"),
                            rs.getString("email"),
                            rs.getString("contraseña"),
                            TipoUsuario.valueOf(rs.getString("tipo_usuario")),
                            new CategoriaSocio(rs.getString("categoria_socio")),
                            rs.getBoolean("dificultad_auditiva"),
                            rs.getBoolean("lenguaje_señas"),
                            rs.getBoolean("participa_subcomision"),
                            new Subcomision(rs.getString("subcomision"), rs.getString("detalle_subcomision"))
                    );
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void modificarDatosPropios(Usuario usuario) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.updateUsuarioSolo");

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getContraseña());
            ps.setBoolean(4, usuario.isDificultadAuditiva());
            ps.setBoolean(5, usuario.isManejoLenguajeDeSeñas());
            ps.setDate(6, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(7, usuario.getDomicilio());
            ps.setString(8, usuario.getNumeroDocumento());
            ps.executeUpdate();
        }catch (SQLException e) {
            System.out.println("Error al modificar usuario");
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.selectUsuarios");
        List<Usuario> usuarios = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String tipoUsuario = resultSet.getString("tipo_usuario");
                if (tipoUsuario.equals("NOSOCIO") || tipoUsuario.equals("AUXILIARADMINISTRATIVO")) {
                    Usuario usuario = new Usuario();
                    usuario.setNombres(resultSet.getString("nombre"));
                    usuario.setApellidos(resultSet.getString("apellido"));
                    usuario.setTipoDocumento(resultSet.getInt("tipo_documento"));
                    usuario.setNumeroDocumento(resultSet.getString("documento"));
                    usuario.setFechaNacimiento(resultSet.getDate("fecha_nacimiento").toLocalDate());
                    usuario.setDomicilio(resultSet.getString("domicilio"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setContraseña(resultSet.getString("contraseña"));
                    usuario.setTipoUsuario(TipoUsuario.valueOf(resultSet.getString("tipo_usuario")));
                    usuarios.add(usuario);
                }
                if (tipoUsuario.equals("SOCIO")){
                    Usuario usuario = new Usuario(
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getInt("tipo_documento"),
                            resultSet.getString("documento"),
                            resultSet.getDate("fecha_nacimiento").toLocalDate(),
                            resultSet.getString("domicilio"),
                            resultSet.getString("email"),
                            resultSet.getString("contraseña"),
                            TipoUsuario.valueOf(resultSet.getString("tipo_usuario")),
                            new CategoriaSocio(resultSet.getString("categoria_socio")),
                            resultSet.getBoolean("dificultad_auditiva"),
                            resultSet.getBoolean("lenguaje_señas"),
                            resultSet.getBoolean("subcomision"),
                            new Subcomision(resultSet.getString("detalle_subcomision"), resultSet.getString("detalle_subcomision"))
                    );
                    usuarios.add(usuario);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error al recuperar usuarios");
            e.printStackTrace();
        }

        return usuarios;
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

    
}
