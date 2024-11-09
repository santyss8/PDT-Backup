package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.CategoriaSocio;

import java.util.List;

public interface CategoriaSocioDAO {
    void crearCategoria(CategoriaSocio categoriaSocio);
    CategoriaSocio getCategoria(int id);
    List<CategoriaSocio> listarCategorias();
    void eliminarCategoria(int id);
}
