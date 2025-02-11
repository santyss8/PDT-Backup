package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.*;
import com.utec.pdtasur.models.*;
import com.utec.pdtasur.services.EmailSenderService;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UsuarioDAOImpl implements com.utec.pdtasur.dao.interfaces.UsuarioDAO {
    private Connection connection;
    private DepartamentoDAO departamentoDAO = new DepartamentoDAOimpl();
    private LocalidadDAO localidadDAO = new LocalidadDAOimpl();

    public UsuarioDAOImpl() throws SQLException {
        this.connection = getConnection();
    }
    // Registrar No Socio
    public void registrarNoSocio(Usuario usuario) {

        String sql = "INSERT INTO usuarios(nombre, apellido, tipo_documento, documento, correo, contraseña, tipo_usuario, fecha_nacimiento, calle, nro_puerta, apartamento, id_departamento, id_localidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTipoDocumento().toString());
            ps.setString(4, usuario.getDocumento());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getContraseña());
            ps.setString(7, usuario.getTipoUsuario().toString());
            ps.setDate(8, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(9, usuario.getCalle());
            ps.setString(10, usuario.getNumeroPuerta());
            ps.setString(11, usuario.getApartamento());
            ps.setInt(12, usuario.getDepartamento().getId());
            ps.setInt(13, usuario.getLocalidad().getId());
            ps.executeUpdate();
            System.out.println("Usuario Registrado con Exito");
            System.out.println("Podra ingresar al sistema una vez sea activado por un administrador");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        sql = "INSERT INTO telefonos(documento_usuario, numero, tipo) VALUES (?, ?, ?);";
        for (Telefono telefono : usuario.getTelefonos()){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, telefono.getUsuario().getDocumento());
                ps.setString(2, telefono.getNumero());
                ps.setString(3, telefono.getTipo());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        EmailSenderService emailSenderService = new EmailSenderService();
        emailSenderService.sendEmailRegistroNoSocio(usuario.getEmail(), usuario.getNombre());
    }

    @Override
    public void registrarAdmin(Usuario usuario) {

        String sql = "INSERT INTO usuarios(nombre, apellido, tipo_documento, documento, correo, contraseña, tipo_usuario, fecha_nacimiento, activo, calle, nro_puerta, apartamento, id_departamento, id_localidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTipoDocumento().toString());
            ps.setString(4, usuario.getDocumento());
            ps.setString(5, usuario.getEmail());
            ps.setString(6, usuario.getContraseña());
            ps.setString(7, usuario.getTipoUsuario().toString());
            ps.setDate(8, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setBoolean(9, true);
            ps.setString(10, usuario.getCalle());
            ps.setString(11, usuario.getNumeroPuerta());
            ps.setString(12, usuario.getApartamento());
            ps.setInt(13, usuario.getDepartamento().getId());
            ps.setInt(14, usuario.getLocalidad().getId());
            ps.executeUpdate();
            System.out.println("Usuario Registrado con Exito");
        }catch (Exception e){
            System.out.println("Error al registrar Usuario");
        }

        sql = "INSERT INTO telefonos(documento_usuario, numero, tipo) VALUES (?, ?, ?);";
        for (Telefono telefono : usuario.getTelefonos()){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, telefono.getUsuario().getDocumento());
                ps.setString(2, telefono.getNumero());
                ps.setString(3, telefono.getTipo());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        EmailSenderService emailSenderService = new EmailSenderService();
        emailSenderService.sendEmailRegistroNoSocio(usuario.getEmail(), usuario.getNombre());

    }

    @Override
    public void registrarSocio(Usuario usuario) {

        String sql = "INSERT INTO usuarios (nombre, apellido, tipo_documento, documento, fecha_nacimiento, correo, contraseña, tipo_usuario, categoria_socio, dif_auditiva, leng_señas, participa_subcomision, subcomision, calle, nro_puerta, apartamento, id_departamento, id_localidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING nro_socio;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2,usuario.getApellido());
            ps.setString(3, usuario.getTipoDocumento().toString());
            ps.setString(4, usuario.getDocumento());
            ps.setDate(5, Date.valueOf(usuario.getFechaNacimiento()));
            ps.setString(6, usuario.getEmail());
            ps.setString(7, usuario.getContraseña());
            ps.setString(8, usuario.getTipoUsuario().toString());
            ps.setInt(9, usuario.getCategoriaSocio().getId());
            ps.setBoolean(10, usuario.isDificultadAuditiva());
            ps.setBoolean(11, usuario.isLenguajeSeñas());
            ps.setBoolean(12, usuario.isParticipaSubcomision());
            if (!usuario.isParticipaSubcomision()){
                ps.setNull(13, Types.INTEGER);
            }else {
                ps.setInt(13, usuario.getSubcomision().getId());
            }
            ps.setString(14, usuario.getCalle());
            ps.setString(15, usuario.getNumeroPuerta());
            ps.setString(16, usuario.getApartamento());
            ps.setInt(17, usuario.getDepartamento().getId());
            ps.setInt(18, usuario.getLocalidad().getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                usuario.setNumeroSocio(rs.getInt("nro_socio"));
            }
            System.out.println("Usuario Registrado con Exito");
            System.out.println("Podra ingresar al sistema una vez sea activado por un administrador");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        sql = "INSERT INTO telefonos(documento_usuario, numero, tipo) VALUES (?, ?, ?);";
        for (Telefono telefono : usuario.getTelefonos()){
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, telefono.getUsuario().getDocumento());
                ps.setString(2, telefono.getNumero());
                ps.setString(3, telefono.getTipo());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        EmailSenderService emailSenderService = new EmailSenderService();
        emailSenderService.sendEmailRegistro(usuario.getEmail(), usuario.getNombre(), usuario.getNumeroSocio());


    }


    @Override
    public void eliminar(Usuario usuario) {
        String sql = "UPDATE usuarios SET estado=? WHERE documento =?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setBoolean(1, usuario.isActivo());
            ps.setString(2, usuario.getDocumento());
            ps.executeUpdate();
            EmailSenderService emailSenderService = new EmailSenderService();
            emailSenderService.sendEmailBaja(usuario.getEmail(), usuario.getNombre());
            System.out.println("Usuario Eliminado con Exito");
        }catch (Exception e){
            System.out.println("Error al Eliminar Usuario");
            e.printStackTrace();
        }


    }

    @Override
    public void activar(Usuario usuario) {
        String sql = "UPDATE usuarios SET estado=? WHERE documento =?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setBoolean(1, usuario.isActivo());
            ps.setString(2, usuario.getDocumento());
            ps.executeUpdate();
            EmailSenderService emailSenderService = new EmailSenderService();
            emailSenderService.sendEmailAlta(usuario.getEmail(), usuario.getNombre());
            System.out.println("Usuario Activado con Exito");
        }catch (Exception e){
            System.out.println("Error al Eliminar Usuario");
            e.printStackTrace();
        }


    }

    @Override
    public Usuario login(String email, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE correo=?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Usuario usuario = new Usuario();
                List<Telefono> telefonos = new ArrayList<>();
                String sqlTelefonos = "SELECT * FROM telefonos WHERE documento_usuario = ?;";
                try (PreparedStatement psTelefonos = connection.prepareStatement(sqlTelefonos)){
                    psTelefonos.setString(1, rs.getString("documento"));
                    ResultSet rsTelefonos = psTelefonos.executeQuery();
                    while (rsTelefonos.next()){
                        Telefono telefono = new Telefono();
                        telefono.setNumero(rsTelefonos.getString("numero"));
                        telefono.setTipo(rsTelefonos.getString("tipo"));
                        telefono.setUsuario(usuario);
                        telefonos.add(telefono);
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
                usuario.setCalle(rs.getString("calle"));
                usuario.setNumeroPuerta(rs.getString("nro_puerta"));
                usuario.setApartamento(rs.getString("apartamento"));
                usuario.setDepartamento(departamentoDAO.obtenerDepartamento(rs.getInt("id_departamento")));
                usuario.setLocalidad(localidadDAO.obtenerLocalidad(rs.getInt("id_localidad")));
                usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                usuario.setTelefonos(telefonos);
                usuario.setEmail(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                if (rs.getString("tipo_usuario").equals("SOCIO")){
                    CategoriaSocioDAO categoriaSocioDAO = new CategoriaSocioDAOImpl();
                    CategoriaSocio categoriaSocio = categoriaSocioDAO.getCategoria(rs.getInt("categoria_socio"));
                    usuario.setCategoriaSocio(categoriaSocio);
                    usuario.setDificultadAuditiva(rs.getBoolean("dif_auditiva"));
                    usuario.setLenguajeSeñas(rs.getBoolean("leng_señas"));
                    usuario.setParticipaSubcomision(rs.getBoolean("participa_subcomision"));
                    if (rs.getBoolean("participa_subcomision")){
                        SubcomisionDAO subcomisionDAO = new SubcomisionDAOImpl();
                        usuario.setSubcomision(subcomisionDAO.getSubcomision(rs.getInt("subcomision")));
                    }
                    usuario.setNumeroSocio(rs.getInt("nro_socio"));
                }
                usuario.setActivo(rs.getBoolean("estado"));
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

        if (usuario.getTipoUsuario().equals(TipoUsuario.SOCIO)){
            String sql = "UPDATE usuarios SET tipo_usuario=?, categoria_socio=?, dif_auditiva=?, \"leng_señas\"=?, participa_subcomision=?, subcomision=? WHERE documento = ? RETURNING nro_socio;";
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
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    usuario.setNumeroSocio(rs.getInt("nro_socio"));
                }
                System.out.println("Usuario Modificado con Exito");
                EmailSenderService emailSenderService = new EmailSenderService();
                emailSenderService.sendEmailActivacion(usuario.getEmail(), usuario.getNombre(), usuario.getNumeroSocio());
            } catch (Exception e){
                System.out.println("Error al modificar Usuario");
                e.printStackTrace();
            }
        }
        else {
            String sql = "UPDATE usuarios SET tipo_usuario=?, categoria_socio=?, dif_auditiva=?, \"leng_señas\"=?, participa_subcomision=?, subcomision=? WHERE documento = ?;";
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
                EmailSenderService emailSenderService = new EmailSenderService();
                emailSenderService.sendEmailModificarNosocio(usuario.getEmail(), usuario.getNombre());
            } catch (Exception e){
                System.out.println("Error al modificar Usuario");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void modificarDatosPropios(Usuario usuario) {
        String sql = "UPDATE usuarios SET  nombre=?, apellido=?, contraseña=?, dif_auditiva=?, leng_señas=?, fecha_nacimiento=?, calle=?, nro_puerta=?, apartamento=?, id_departamento=?, id_localidad=? WHERE documento = ?;";

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
            ps.setString(7, usuario.getCalle());
            ps.setString(8, usuario.getNumeroPuerta());
            ps.setString(9, usuario.getApartamento());
            ps.setInt(10, usuario.getDepartamento().getId());
            ps.setInt(11, usuario.getLocalidad().getId());
            ps.setString(12, usuario.getDocumento());
            ps.executeUpdate();
            System.out.println("Usuario Modificado con Exito");
        }catch (Exception e){
            System.out.println("Error al modificar Usuario");
            e.printStackTrace();
        }

    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Usuario usuario = new Usuario();
                List<Telefono> telefonos = new ArrayList<>();
                String sqlTelefonos = "SELECT * FROM telefonos WHERE documento_usuario = ?;";
                try (PreparedStatement psTelefonos = connection.prepareStatement(sqlTelefonos)){
                    psTelefonos.setString(1, rs.getString("documento"));
                    ResultSet rsTelefonos = psTelefonos.executeQuery();
                    while (rsTelefonos.next()){
                        Telefono telefono = new Telefono();
                        telefono.setNumero(rsTelefonos.getString("numero"));
                        telefono.setTipo(rsTelefonos.getString("tipo"));
                        telefono.setUsuario(usuario);
                        telefonos.add(telefono);
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
                usuario.setCalle(rs.getString("calle"));
                usuario.setNumeroPuerta(rs.getString("nro_puerta"));
                usuario.setApartamento(rs.getString("apartamento"));
                usuario.setDepartamento(departamentoDAO.obtenerDepartamento(rs.getInt("id_departamento")));
                usuario.setLocalidad(localidadDAO.obtenerLocalidad(rs.getInt("id_localidad")));
                usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                usuario.setTelefonos(telefonos);
                usuario.setEmail(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                if (rs.getString("tipo_usuario").equals("SOCIO")){
                    CategoriaSocioDAO categoriaSocioDAO = new CategoriaSocioDAOImpl();
                    CategoriaSocio categoriaSocio = categoriaSocioDAO.getCategoria(rs.getInt("categoria_socio"));
                    usuario.setCategoriaSocio(categoriaSocio);
                    usuario.setNumeroSocio(rs.getInt("nro_socio"));
                    usuario.setDificultadAuditiva(rs.getBoolean("dif_auditiva"));
                    usuario.setLenguajeSeñas(rs.getBoolean("leng_señas"));
                    usuario.setParticipaSubcomision(rs.getBoolean("participa_subcomision"));
                    if (rs.getBoolean("participa_subcomision")){
                        SubcomisionDAO subcomisionDAO = new SubcomisionDAOImpl();
                        usuario.setSubcomision(subcomisionDAO.getSubcomision(rs.getInt("subcomision")));
                    }
                }
                usuario.setActivo(rs.getBoolean("estado"));
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
        String sql = "SELECT correo FROM usuarios WHERE correo = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean seleccionarDocumento(String documento) {
        String sql = "SELECT documento FROM usuarios WHERE documento = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean seleccionarTelefono(String numero, String documento) {
        String sql = "SELECT * FROM telefonos WHERE numero = ? AND documento_usuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, numero);
            ps.setString(2, documento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public boolean seleccionarTelefonoEliminar(String documento) {
        String sql = "SELECT * FROM telefonos WHERE documento_usuario = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void insertarTelefono(String documento, String numero, String tipo) {
        String sql = "INSERT INTO telefonos(documento_usuario, numero, tipo) VALUES (?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, documento);
            ps.setString(2, numero);
            ps.setString(3, tipo);
            ps.executeUpdate();
            System.out.println("Telefono agregado");
        }catch (Exception e){
            System.out.println("Error al agregar telefono");
        }

    }

    @Override
    public void eliminarTelefono(String documento, String numero) {
        String sql = "DELETE FROM telefonos WHERE documento_usuario = ? AND numero = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, documento);
            ps.setString(2, numero);
            ps.executeUpdate();
            System.out.println("Telefono eliminado");
        }catch (Exception e){
            System.out.println("Error al eliminar telefono");
        }
    }

    public Usuario obtenerUsuario(String documento){
        String sql = "SELECT * FROM usuarios WHERE documento = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setTipoDocumento(TipoDocumento.valueOf(rs.getString("tipo_documento")));
                usuario.setDocumento(rs.getString("documento"));
                usuario.setCalle(rs.getString("calle"));
                usuario.setNumeroPuerta(rs.getString("nro_puerta"));
                usuario.setApartamento(rs.getString("apartamento"));
                usuario.setDepartamento(departamentoDAO.obtenerDepartamento(rs.getInt("id_departamento")));
                usuario.setLocalidad(localidadDAO.obtenerLocalidad(rs.getInt("id_localidad")));
                usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                usuario.setTelefonos(new ArrayList<>());
                String sqlTelefonos = "SELECT * FROM telefonos WHERE documento_usuario = ?;";
                try (PreparedStatement psTelefonos = connection.prepareStatement(sqlTelefonos)){
                    psTelefonos.setString(1, rs.getString("documento"));
                    ResultSet rsTelefonos = psTelefonos.executeQuery();
                    while (rsTelefonos.next()){
                        usuario.getTelefonos().add(new Telefono(rsTelefonos.getInt("id"), rsTelefonos.getString("numero"), rsTelefonos.getString("tipo"), usuario));
                    }
                }catch (Exception e){
                    System.out.println("Error al Listar Telefonos");
                    e.printStackTrace();
                }
                usuario.setEmail(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipoUsuario(TipoUsuario.valueOf(rs.getString("tipo_usuario")));
                if (rs.getString("tipo_usuario").equals("SOCIO")){
                    CategoriaSocioDAO categoriaSocioDAO = new CategoriaSocioDAOImpl();
                    CategoriaSocio categoriaSocio = categoriaSocioDAO.getCategoria(rs.getInt("categoria_socio"));
                    usuario.setCategoriaSocio(categoriaSocio);
                    usuario.setDificultadAuditiva(rs.getBoolean("dif_auditiva"));
                    usuario.setLenguajeSeñas(rs.getBoolean("leng_señas"));
                    usuario.setParticipaSubcomision(rs.getBoolean("participa_subcomision"));
                    usuario.setNumeroSocio(rs.getInt("nro_socio"));
                    if (rs.getBoolean("participa_subcomision")){
                        SubcomisionDAO subcomisionDAO = new SubcomisionDAOImpl();
                        usuario.setSubcomision(subcomisionDAO.getSubcomision(rs.getInt("subcomision")));
                    }
                }
                usuario.setActivo(rs.getBoolean("estado"));
                return usuario;
            }
        }catch (Exception e){
            System.out.println("Error al obtener Usuario");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }


    
}
