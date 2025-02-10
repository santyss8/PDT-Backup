package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Espacio;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface EspacioDAO {
    void insertarEspacio(Espacio espacio) throws SQLException;
    List<Espacio> listarEspacios() throws SQLException;
    void modificarEspacio(Espacio espacio) throws SQLException;
    void activarEspacio(Espacio espacio) throws SQLException;
    List<Espacio> espaciosDisponibles(int capacidadMaxima, LocalDate fecha) throws SQLException;
    Espacio seleccionarEspacio(int id) throws SQLException;
    void eliminarEspacio(Espacio espacio) throws SQLException;
}
