package com.utec.pdtasur.models;

public class CategoriaSocio {
    private int id;
    private String nombre;
    private String descripcion;

    public CategoriaSocio(){

    }

    public CategoriaSocio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public CategoriaSocio(int id) {
        this.id = id;
    }

    public CategoriaSocio(int id, String nombre, String descripcion){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public String toString() {
        return id + "- " + nombre;
    }
}
