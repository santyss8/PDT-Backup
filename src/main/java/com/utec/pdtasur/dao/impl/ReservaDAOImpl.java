package com.utec.pdtasur.dao.impl;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import com.utec.pdtasur.dao.interfaces.ReservaDAO;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.Reserva;
import com.utec.pdtasur.models.Usuario;
import com.utec.pdtasur.utils.DatabaseConnection;

import javax.xml.transform.Result;


public class ReservaDAOImpl implements ReservaDAO {

    private Connection connection;
    private UsuarioDAO usuarioDAO;
    private EspacioDAOImpl espacioDAO;

    public ReservaDAOImpl() throws SQLException {
        this.connection = getConnection();
        this.usuarioDAO = new UsuarioDAOImpl();
        this.espacioDAO = new EspacioDAOImpl();

    }

    public void reservarEspacio(Reserva reserva){
        String sql = "INSERT INTO reservas (documento_usuario, espacio_id, fecha_actividad, duracion, cantidad_personas, importe_abonar, fecha_vto_sena, importe_sena) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reserva.getUsuario().getDocumento());
            ps.setInt(2, reserva.getEspacio().getId());
            ps.setTimestamp(3, Timestamp.valueOf(reserva.getFechaActividad()));
            ps.setInt(4, reserva.getDuracion());
            ps.setInt(5, reserva.getCantidadPersonas());
            ps.setDouble(6, reserva.getImporteAbonar());
            ps.setDate(7, Date.valueOf(reserva.getFechaVencimientoSena()));
            ps.setDouble(8, reserva.getImportePagoSena());
            ps.executeUpdate();
            System.out.println("Reserva creada exitosamente");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void cancelarReserva(Reserva reserva){
        String sql = "UPDATE reservas SET estado = 'Cancelada' WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, reserva.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void aprobarReserva(Reserva reserva){
        String sql = "UPDATE reservas SET estado = 'Confirmada', fecha_confirmacion = ? WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, reserva.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void pagoSena(Reserva reserva){
        String sql = "UPDATE reservas SET fecha_pago_sena = ?, importe_sena_pagado = ? WHERE id = ?;";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setDouble(2, reserva.getImporteSenaPagado());
            ps.setInt(3, reserva.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Reserva> listarReservasConfirmadas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE estado = 'Confirmada' ORDER BY id;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setUsuario(usuarioDAO.obtenerUsuario(rs.getString("documento_usuario")));
                reserva.setEspacio(espacioDAO.seleccionarEspacio(rs.getInt("espacio_id")));
                reserva.setFechaActividad(rs.getTimestamp("fecha_actividad").toLocalDateTime());
                reserva.setDuracion(rs.getInt("duracion"));
                reserva.setCantidadPersonas(rs.getInt("cantidad_personas"));
                reserva.setImporteAbonar(rs.getDouble("importe_abonar"));
                reserva.setFechaVencimientoSena(rs.getDate("fecha_vto_sena").toLocalDate());
                reserva.setImportePagoSena(rs.getDouble("importe_sena"));
                if (rs.getDate("fecha_pago_sena") != null){
                    reserva.setFechaPagoSena(rs.getDate("fecha_pago_sena").toLocalDate());
                }
                reserva.setImporteSenaPagado(rs.getDouble("importe_sena_pagado"));
                reserva.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                reserva.setEstado(rs.getString("estado"));
                if (rs.getDate("fecha_confirmacion") != null){
                    reserva.setFechaConfirmacion(rs.getDate("fecha_confirmacion").toLocalDate());
                }
                reserva.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                reservas.add(reserva);
            }
        }catch (SQLException e){
            System.out.println("Error al listar reservas");
        }
        return reservas;
    }



    public List<Reserva> reportePorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<Reserva> reservas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM reservas where 1=1");
        if (fechaDesde != null) {
            sql.append(" AND fecha_actividad >= ?");
        }
        if (fechaHasta != null) {
            sql.append(" AND fecha_actividad <= ?");
        }
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            // Establecer parámetros
            if (fechaDesde != null) {
                stmt.setDate(paramIndex++, Date.valueOf(fechaDesde));
            }
            if (fechaHasta != null) {
                stmt.setDate(paramIndex++, Date.valueOf(fechaHasta));
            }
            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();
            // Procesar los resultados
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setUsuario(usuarioDAO.obtenerUsuario(rs.getString("documento_usuario")));
                reserva.setEspacio(espacioDAO.seleccionarEspacio(rs.getInt("espacio_id")));
                reserva.setFechaActividad(rs.getTimestamp("fecha_actividad").toLocalDateTime());
                reserva.setDuracion(rs.getInt("duracion"));
                reserva.setCantidadPersonas(rs.getInt("cantidad_personas"));
                reserva.setImporteAbonar(rs.getDouble("importe_abonar"));
                reserva.setFechaVencimientoSena(rs.getDate("fecha_vto_sena").toLocalDate());
                reserva.setImportePagoSena(rs.getDouble("importe_sena"));
                if (rs.getDate("fecha_pago_sena") != null){
                    reserva.setFechaPagoSena(rs.getDate("fecha_pago_sena").toLocalDate());
                }
                reserva.setImporteSenaPagado(rs.getDouble("importe_sena_pagado"));
                reserva.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                reserva.setEstado(rs.getString("estado"));
                if (rs.getDate("fecha_confirmacion") != null){
                    reserva.setFechaConfirmacion(rs.getDate("fecha_confirmacion").toLocalDate());
                }
                reserva.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());

                reservas.add(reserva);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return reservas;
    }

    public List<Reserva> listarReservasPendientes(){
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE estado = 'Pendiente' ORDER BY id;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setUsuario(usuarioDAO.obtenerUsuario(rs.getString("documento_usuario")));
                reserva.setEspacio(espacioDAO.seleccionarEspacio(rs.getInt("espacio_id")));
                reserva.setFechaActividad(rs.getTimestamp("fecha_actividad").toLocalDateTime());
                reserva.setDuracion(rs.getInt("duracion"));
                reserva.setCantidadPersonas(rs.getInt("cantidad_personas"));
                reserva.setImporteAbonar(rs.getDouble("importe_abonar"));
                reserva.setFechaVencimientoSena(rs.getDate("fecha_vto_sena").toLocalDate());
                reserva.setImportePagoSena(rs.getDouble("importe_sena"));
                if (rs.getDate("fecha_pago_sena") != null){
                    reserva.setFechaPagoSena(rs.getDate("fecha_pago_sena").toLocalDate());
                }
                reserva.setImporteSenaPagado(rs.getDouble("importe_sena_pagado"));
                reserva.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                reserva.setEstado(rs.getString("estado"));
                if (rs.getDate("fecha_confirmacion") != null){
                    reserva.setFechaConfirmacion(rs.getDate("fecha_confirmacion").toLocalDate());
                }
                reserva.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                reservas.add(reserva);
            }
        }catch (SQLException e){
            System.out.println("Error al listar reservas");
        }
        return reservas;
    }

    public List<Reserva> listarReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas ORDER BY id;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setUsuario(usuarioDAO.obtenerUsuario(rs.getString("documento_usuario")));
                reserva.setEspacio(espacioDAO.seleccionarEspacio(rs.getInt("espacio_id")));
                reserva.setFechaActividad(rs.getTimestamp("fecha_actividad").toLocalDateTime());
                reserva.setDuracion(rs.getInt("duracion"));
                reserva.setCantidadPersonas(rs.getInt("cantidad_personas"));
                reserva.setImporteAbonar(rs.getDouble("importe_abonar"));
                reserva.setFechaVencimientoSena(rs.getDate("fecha_vto_sena").toLocalDate());
                reserva.setImportePagoSena(rs.getDouble("importe_sena"));
                if (rs.getDate("fecha_pago_sena") != null){
                    reserva.setFechaPagoSena(rs.getDate("fecha_pago_sena").toLocalDate());
                }
                reserva.setImporteSenaPagado(rs.getDouble("importe_sena_pagado"));
                reserva.setSaldoPendiente(rs.getDouble("saldo_pendiente"));
                reserva.setEstado(rs.getString("estado"));
                if (rs.getDate("fecha_confirmacion") != null){
                    reserva.setFechaConfirmacion(rs.getDate("fecha_confirmacion").toLocalDate());
                }
                reserva.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                reservas.add(reserva);
            }
        }catch (SQLException e){
            System.out.println("Error al listar reservas");
        }
        return reservas;
    }


    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }



}