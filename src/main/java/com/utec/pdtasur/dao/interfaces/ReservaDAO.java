package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Reserva;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ReservaDAO {
    void reservarEspacio(com.utec.pdtasur.models.Reserva reserva);
    void cancelarReserva(com.utec.pdtasur.models.Reserva reserva);
    void aprobarReserva(com.utec.pdtasur.models.Reserva reserva);
    void pagoSena(com.utec.pdtasur.models.Reserva reserva);
    List<Reserva> listarReservasConfirmadas() throws SQLException;
    List<Reserva> reportePorFecha(LocalDate fechaDesde, LocalDate fechaHasta);
    List<Reserva> listarReservasPendientes();
    List<Reserva> listarReservas() throws SQLException;

}
