package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.TipoActividad;

import java.util.List;

public interface TipoActividadDAO {
    void insertarTipoActividad(TipoActividad tipoActividad);
    void actualizarTipoActividad(TipoActividad tipoActividad);
    void activarTipoActividad(TipoActividad tipoActividad);
    void eliminarTipoActividad(TipoActividad tipoActividad);
    List<TipoActividad> listarTiposActividad();
    TipoActividad obtenerTipoActividad(int id);
}
