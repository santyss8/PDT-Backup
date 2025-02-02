package com.utec.pdtasur.models;

import com.utec.pdtasur.models.Departamento;

public class Localidad {
    private int id;
    private Departamento departamento;
    private String localidad;

    public Localidad() {
    }

    public Localidad(int id, Departamento departamento, String localidad) {
        this.id = id;
        this.departamento = departamento;
        this.localidad = localidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Localidad: " + id + " - " + departamento + " - " + localidad;
    }
}
