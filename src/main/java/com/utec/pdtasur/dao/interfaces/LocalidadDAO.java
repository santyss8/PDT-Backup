package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Departamento;
import com.utec.pdtasur.models.Localidad;

import java.util.List;

public interface LocalidadDAO {
    public Localidad obtenerLocalidad(int id);
    public List<Localidad> listarLocalidades(Departamento departamento);
}
