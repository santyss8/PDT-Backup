package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.Espacio;
import com.utec.pdtasur.models.Pago;
import com.utec.pdtasur.models.Usuario;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PagoDAOImpl {

    private Connection connection;

    public PagoDAOImpl() throws SQLException {
        this.connection = getConnection();
    }

    public void insertarPago(Pago pago){
        Properties propiedades = loadProperties();

        String sql = propiedades.getProperty("sql.insertarPago");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, pago.getUsuario().getDocumento());
            ps.setBoolean(2, pago.isEsCuota());
            if (pago.getEspacio() != null){
                ps.setInt(3, pago.getEspacio().getId());
            }else {
                ps.setInt(3, 0);
            }
            ps.setDouble(4, pago.getMonto());
            ps.setString(5, pago.getFormaCobro());
            ps.executeUpdate();
            System.out.println("Pago insertado con Exito");
        }catch (Exception e){
            System.out.println("Error al insertar Pago");
            e.printStackTrace();
        }

    }

    public void modificarPago(Pago pago){
        Properties propiedades = loadProperties();

        String sql = propiedades.getProperty("sql.modificarPago");

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, pago.getUsuario().getDocumento());
            ps.setBoolean(2, pago.isEsCuota());
            ps.setInt(3, pago.getEspacio().getId());
            ps.setDouble(4, pago.getMonto());
            ps.setString(5, pago.getFormaCobro());
            ps.setString(6, pago.getUsuario().getDocumento());
            ps.executeUpdate();
            System.out.println("Pago modificado con Exito");
        }catch (Exception e){
            System.out.println("Error al modificar Pago");
            e.printStackTrace();
        }
    }

    public List<Pago> listarPagos() throws SQLException {
        Properties propiedades = loadProperties();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        EspacioDAOImpl espacioDAO = new EspacioDAOImpl();

        String sql = propiedades.getProperty("sql.selectPagos");

        List<Pago> pagos = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Pago pago = new Pago();
                pago.setId(rs.getInt("id_pago"));
                pago.setUsuario(usuarioDAO.obtenerUsuario(rs.getString("documento_usuario")));
                pago.setEsCuota(rs.getBoolean("es_cuota"));
                if (rs.getInt("id_espacio") != 0){
                    pago.setEspacio(espacioDAO.seleccionarEspacio(rs.getInt("id_espacio")));
                }
                pago.setMonto(rs.getDouble("monto"));
                pago.setFechaCobro(rs.getDate("fecha_cobro").toLocalDate());
                pago.setFormaCobro(rs.getString("forma_cobro"));
                pagos.add(pago);
            }
        }catch (Exception e){
            System.out.println("Error al listar Pagos");
            e.printStackTrace();
        }
        return pagos;
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
