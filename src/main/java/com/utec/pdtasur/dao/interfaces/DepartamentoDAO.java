package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Departamento;

import java.util.List;

public interface DepartamentoDAO {
    public Departamento obtenerDepartamento(int id);
    public Departamento obtenerDepartamento(String departamento);
    public List<Departamento> listarDepartamentos();
}
