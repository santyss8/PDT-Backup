package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.CategoriaSocioDAO;
import com.utec.pdtasur.dao.interfaces.SubcomisionDAO;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.*;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UsuarioDAOImpl implements com.utec.pdtasur.dao.interfaces.UsuarioDAO {
    private Connection connection;

    public UsuarioDAOImpl() throws SQLException {
        this.connection = getConnection();
    }
    // Registrar No Socio
    public void registrarNoSocio(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuario");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTipoDocumento().toString());
            ps.setString(4, usuario.getDocumento());
            ps.setString(5, usuario.getDomicilio());
            ps.setString(6, usuario.getEmail());
            ps.setString(7, usuario.getContraseña());
            ps.setString(8, usuario.getTipoUsuario().toString());
            ps.setDate(9, Date.valueOf(usuario.getFechaNacimiento()));
            ps.executeUpdate();
            System.out.println("Usuario Registrado con Exito");
        }catch (Exception e){
            System.out.println("Error al registrar Usuario");
        }

        sql = properties.getProperty("sql.insertTelefonos");
        for (int numero : usuario.getTelefonos()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, usuario.getDocumento());
                ps.setInt(2, numero);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println("Error al registrar Usuario");
            }
        }
    }

    @Override
    public void registrarAdmin(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuarioAdmin");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTipoDocumento().toString());
            ps.setString(4, usuario.getDocumento());
            ps.setString(5, usuario.getDomicilio());
            ps.setString(6, usuario.getEmail());
            ps.setString(7, usuario.getContraseña());
            ps.setString(8, usuario.getTipoUsuario().toString());
            ps.setDate(9, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setBoolean(10, true);
            ps.executeUpdate();
            System.out.println("Usuario Registrado con Exito");
        }catch (Exception e){
            System.out.println("Error al registrar Usuario");
        }

        sql = properties.getProperty("sql.insertTelefonos");
        for (int numero : usuario.getTelefonos()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, usuario.getDocumento());
                ps.setInt(2, numero);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println("Error al registrar Usuario");
            }
        }

    }

    @Override
    public void registrarSocio(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.insertUsuarioSocio");

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2,usuario.getApellido());
            ps.setString(3, usuario.getTipoDocumento().toString());
            ps.setString(4, usuario.getDocumento());
            ps.setDate(5, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(6, usuario.getDomicilio());
            ps.setString(7, usuario.getEmail());
            ps.setString(8, usuario.getContraseña());
            ps.setString(9, usuario.getTipoUsuario().toString());
            ps.setInt(10, usuario.getCategoriaSocio().getId());
            ps.setBoolean(11, usuario.isDificultadAuditiva());
            ps.setBoolean(12, usuario.isLenguajeSeñas());
            ps.setBoolean(13, usuario.isParticipaSubcomision());
            if (!usuario.isParticipaSubcomision()){
                ps.setNull(14, Types.INTEGER);
            }else {
                ps.setInt(14, usuario.getSubcomision().getId());
            }
            ps.executeUpdate();
            System.out.println("Usuario Registrado con Exito");
        }catch (Exception e){
            System.out.println("Error al registrar Usuario");
            e.printStackTrace();
        }

        sql = properties.getProperty("sql.insertTelefonos");
        for (int numero : usuario.getTelefonos()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, usuario.getDocumento());
                ps.setInt(2, numero);
                ps.executeUpdate();
            }catch (Exception e){
                System.out.println("Error al registrar Usuario");
                e.printStackTrace();
            }
        }


    }


    @Override
    public void eliminar(Usuario usuario) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.deleteUsuario");
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setBoolean(1, usuario.isActivo());
            ps.setString(2, usuario.getDocumento());
            ps.executeUpdate();
            System.out.println("Usuario Eliminado con Exito");
        }catch (Exception e){
            System.out.println("Error al Eliminar Usuario");
            e.printStackTrace();
        }


    }

    @Override
    public Usuario login(String email, String contraseña) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.login");
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, email);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Usuario usuario = new Usuario();
                List<Integer> telefonos = new ArrayList<>();
                String sqlTelefonos = properties.getProperty("sql.selectTelefonos");
                try (PreparedStatement psTelefonos = connection.prepareStatement(sqlTelefonos)){
                    psTelefonos.setString(1, rs.getString("documento"));
                    ResultSet rsTelefonos = psTelefonos.executeQuery();
                    while (rsTelefonos.next()){
                        telefonos.add(rsTelefonos.getInt("numero"));
                    }
                }catch (Exception e){
                    System.out.println("Error al Listar Telefonos");
                    e.printStackTrace();
                }
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setTipoDocumento(TipoDocumento.valueOf(rs.getString("tipo_documento")));
                usuario.setDocumento(rs.getString("documento"));
                usuario.setDomicilio(rs.getString("domicilio"));
                usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                usuario.setTelefonos(telefonos);
                usuario.setEmail(rs.getString("email"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                if (rs.getString("tipo_usuario").equals("SOCIO")){
                    CategoriaSocioDAO categoriaSocioDAO = new CategoriaSocioDAOImpl();
                    CategoriaSocio categoriaSocio = categoriaSocioDAO.getCategoria(rs.getInt("id_categoria_socio"));
                    usuario.setCategoriaSocio(categoriaSocio);
                    usuario.setDificultadAuditiva(rs.getBoolean("dificultad_auditiva"));
                    usuario.setLenguajeSeñas(rs.getBoolean("lenguaje_señas"));
                    usuario.setParticipaSubcomision(rs.getBoolean("participa_subcomision"));
                    if (rs.getBoolean("participa_subcomision")){
                        SubcomisionDAO subcomisionDAO = new SubcomisionDAOImpl();
                        usuario.setSubcomision(subcomisionDAO.getSubcomision(rs.getInt("id_subcomision")));
                    }
                }
                usuario.setActivo(rs.getBoolean("activo"));
                return usuario;
            }
        }catch (Exception e){
            System.out.println("Error al Login");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modificar(Usuario usuario) {
        Properties properties = loadProperties();

        if (usuario.getTipoUsuario().equals(TipoUsuario.SOCIO)){
            String sql = properties.getProperty("sql.updateUsuarioSocio");
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, usuario.getTipoUsuario().toString());
                ps.setInt(2, usuario.getCategoriaSocio().getId());
                ps.setBoolean(3, usuario.isDificultadAuditiva());
                ps.setBoolean(4, usuario.isLenguajeSeñas());
                ps.setBoolean(5, usuario.isParticipaSubcomision());
                if (usuario.isParticipaSubcomision()){
                    ps.setInt(6, usuario.getSubcomision().getId());
                }else{
                    ps.setNull(6, Types.INTEGER);
                }
                ps.setString(7, usuario.getDocumento());
                ps.executeUpdate();
                System.out.println("Usuario Modificado con Exito");
            } catch (Exception e){
                System.out.println("Error al modificar Usuario");
                e.printStackTrace();
            }
        }
        else {
            String sql = properties.getProperty("sql.updateUsuario");
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, usuario.getTipoUsuario().toString());
                ps.setNull(2, Types.INTEGER);
                ps.setNull(3, Types.BOOLEAN);
                ps.setNull(4, Types.BOOLEAN);
                ps.setNull(5, Types.BOOLEAN);
                ps.setNull(6, Types.INTEGER);
                ps.setString(7, usuario.getDocumento());
                ps.executeUpdate();
                System.out.println("Usuario Modificado con Exito");
            } catch (Exception e){
                System.out.println("Error al modificar Usuario");
                e.printStackTrace();
            }
        }


    }

    @Override
    public void modificarDatosPropios(Usuario usuario) {
        Properties properties = loadProperties();

        String sql = properties.getProperty("sql.updateUsuarioSolo");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getContraseña());
            if (usuario.getTipoUsuario().equals(TipoUsuario.SOCIO)){
                ps.setBoolean(4, usuario.isDificultadAuditiva());
                ps.setBoolean(5, usuario.isLenguajeSeñas());
            }else {
                ps.setNull(4, Types.BOOLEAN);
                ps.setNull(5, Types.BOOLEAN);
            }
            ps.setDate(6, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(7, usuario.getDomicilio());
            ps.setString(8, usuario.getDocumento());
            ps.executeUpdate();
            System.out.println("Usuario Modificado con Exito");
        }catch (Exception e){
            System.out.println("Error al modificar Usuario");
            e.printStackTrace();
        }

    }

    @Override
    public List<Usuario> listarUsuarios() {
        Properties properties = loadProperties();
        List<Usuario> usuarios = new ArrayList<>();
        String sql = properties.getProperty("sql.selectUsuarios");
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuario usuario = new Usuario();
                List<Integer> telefonos = new ArrayList<>();
                String sqlTelefonos = properties.getProperty("sql.selectTelefonos");
                try (PreparedStatement psTelefonos = connection.prepareStatement(sqlTelefonos)){
                    psTelefonos.setString(1, rs.getString("documento"));
                    ResultSet rsTelefonos = psTelefonos.executeQuery();
                    while (rsTelefonos.next()){
                        telefonos.add(rsTelefonos.getInt("numero"));
                    }
                }catch (Exception e){
                    System.out.println("Error al Listar Telefonos");
                    e.printStackTrace();
                }
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setTipoDocumento(TipoDocumento.valueOf(rs.getString("tipo_documento")));
                usuario.setDocumento(rs.getString("documento"));
                usuario.setDomicilio(rs.getString("domicilio"));
                usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                usuario.setTelefonos(telefonos);
                usuario.setEmail(rs.getString("email"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                if (rs.getString("tipo_usuario").equals("SOCIO")){
                    CategoriaSocioDAO categoriaSocioDAO = new CategoriaSocioDAOImpl();
                    CategoriaSocio categoriaSocio = categoriaSocioDAO.getCategoria(rs.getInt("id_categoria_socio"));
                    usuario.setCategoriaSocio(categoriaSocio);
                    usuario.setDificultadAuditiva(rs.getBoolean("dificultad_auditiva"));
                    usuario.setLenguajeSeñas(rs.getBoolean("lenguaje_señas"));
                    usuario.setParticipaSubcomision(rs.getBoolean("participa_subcomision"));
                    if (rs.getBoolean("participa_subcomision")){
                        SubcomisionDAO subcomisionDAO = new SubcomisionDAOImpl();
                        usuario.setSubcomision(subcomisionDAO.getSubcomision(rs.getInt("id_subcomision")));
                    }
                }
                usuario.setActivo(rs.getBoolean("activo"));
                usuarios.add(usuario);

            }

        }catch (Exception e){
            System.out.println("Error al Listar Usuarios");
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public boolean seleccionarEmail(String email) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.selectEmail");
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("Email encontrado");
                return true;
            }
        }catch (Exception e){
            System.out.println("Error al Seleccionar Email");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean seleccionarDocumento(String documento) {
        Properties properties = loadProperties();
        String sql = properties.getProperty("sql.selectDocumento");
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                System.out.println("Documento encontrado");
                return true;
            }
        }catch (Exception e){
            System.out.println("Error al Seleccionar Documento");
            e.printStackTrace();
        }
        return false;
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
