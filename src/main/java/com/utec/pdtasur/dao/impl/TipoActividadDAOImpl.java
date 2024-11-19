package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.models.TipoActividad;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TipoActividadDAOImpl {
    private Connection connection;

    public TipoActividadDAOImpl() throws SQLException {
        this.connection = getConnection();
    }

    public void insertarTipoActividad(TipoActividad tipoActividad){
        Properties propiedades = loadProperties();

        String sql = propiedades.getProperty("sql.insertarTipoActividad");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, tipoActividad.getNombre());
            ps.setString(2, tipoActividad.getDescripcion());
            ps.executeUpdate();
            System.out.println("Tipo de Actividad insertado con Exito");
        }catch (Exception e){
            System.out.println("Error al insertar Tipo de Actividad");
            e.printStackTrace();
        }

    }

    public void actualizarTipoActividad(TipoActividad tipoActividad){
        Properties propiedades = loadProperties();

        String sql = propiedades.getProperty("sql.actualizarTipoActividad");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, tipoActividad.getDescripcion());
            ps.setInt(2, tipoActividad.getId());
            ps.executeUpdate();
            System.out.println("Tipo de Actividad actualizado con Exito");
        }catch (Exception e){
            System.out.println("Error al actualizar Tipo de Actividad");
            e.printStackTrace();
        }
    }

    public void activarTipoActividad(TipoActividad tipoActividad){
        Properties propiedades = loadProperties();

        String sql = propiedades.getProperty("sql.activarTipoActividad");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, tipoActividad.getId());
            ps.executeUpdate();
            System.out.println("Tipo de Actividad activado con Exito");
        }catch (Exception e){
            System.out.println("Error al activar Tipo de Actividad");
            e.printStackTrace();
        }
    }

    public void eliminarTipoActividad(TipoActividad tipoActividad){
        Properties propiedades = loadProperties();

        String sql = propiedades.getProperty("sql.eliminarTipoActividad");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, tipoActividad.getRazonBaja());
            ps.setString(2, tipoActividad.getComentariosBaja());
            ps.setString(3, tipoActividad.getDocumentoUsuarioBaja());
            ps.setInt(4, tipoActividad.getId());
            ps.executeUpdate();
            System.out.println("Tipo de Actividad eliminado con Exito");
        }catch (Exception e){
            System.out.println("Error al eliminar Tipo de Actividad");
            e.printStackTrace();
        }
    }

    public List<TipoActividad> listarTiposActividad(){
        Properties propiedades = loadProperties();
        String sql = propiedades.getProperty("sql.seleccionarTipoActividad");
        List<TipoActividad> tiposActividad = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                TipoActividad tipoActividad = new TipoActividad();
                tipoActividad.setId(rs.getInt("id_tipo_actividad"));
                tipoActividad.setNombre(rs.getString("nombre"));
                tipoActividad.setDescripcion(rs.getString("descripcion"));
                tipoActividad.setEstado(rs.getBoolean("estado"));
                if (rs.getDate("fecha_creacion") != null){
                    tipoActividad.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                }
                if (rs.getDate("fecha_baja") != null){
                    tipoActividad.setFechaBaja(rs.getDate("fecha_baja").toLocalDate());
                }
                if (rs.getString("razon_baja") != null){
                    tipoActividad.setRazonBaja(rs.getString("razon_baja"));
                }
                if (rs.getString("comentarios_baja") != null){
                    tipoActividad.setComentariosBaja(rs.getString("comentarios_baja"));
                }
                if (rs.getString("documento_usuario_baja") != null){
                    tipoActividad.setDocumentoUsuarioBaja(rs.getString("documento_usuario_baja"));
                }
                tiposActividad.add(tipoActividad);
            }
        }catch (Exception e){
            System.out.println("Error al listar Tipos de Actividad");
            e.printStackTrace();
        }
        return tiposActividad;
    }


    private TipoActividad obtenerTipoActividad(int id){
        Properties propiedades = loadProperties();
        String sql = propiedades.getProperty("sql.seleccionarTipoActividadId");
        TipoActividad tipoActividad = new TipoActividad();
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                tipoActividad.setId(rs.getInt("id_tipo_actividad"));
                tipoActividad.setNombre(rs.getString("nombre"));
                tipoActividad.setDescripcion(rs.getString("descripcion"));
                tipoActividad.setEstado(rs.getBoolean("estado"));
                if (rs.getDate("fecha_creacion") != null){
                    tipoActividad.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                }
                if (rs.getDate("fecha_baja") != null){
                    tipoActividad.setFechaBaja(rs.getDate("fecha_baja").toLocalDate());
                }
                if (rs.getString("razon_baja") != null){
                    tipoActividad.setRazonBaja(rs.getString("razon_baja"));
                }
                if (rs.getString("comentarios_baja") != null){
                    tipoActividad.setComentariosBaja(rs.getString("comentarios_baja"));
                }
                if (rs.getString("documento_usuario_baja") != null){
                    tipoActividad.setDocumentoUsuarioBaja(rs.getString("documento_usuario_baja"));
                }
                return tipoActividad;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
