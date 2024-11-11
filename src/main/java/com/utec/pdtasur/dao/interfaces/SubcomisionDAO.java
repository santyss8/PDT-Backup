package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Subcomision;

import java.util.List;

public interface SubcomisionDAO {
    void crearSubcomision(Subcomision subcomision);
    Subcomision getSubcomision(int id);
    List<Subcomision> listarSubcomisiones();
    void eliminarSubcomision(int id);
}
