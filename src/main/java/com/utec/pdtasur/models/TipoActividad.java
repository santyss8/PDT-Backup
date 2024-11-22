package com.utec.pdtasur.models;

import java.time.LocalDate;

public class TipoActividad {
    private int id;
    private String nombre;
    private String descripcion;
    private boolean estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaBaja;
    private String razonBaja;
    private String comentariosBaja;
    private String documentoUsuarioBaja;

    public TipoActividad() {
    }

    public TipoActividad(int id, String nombre, String descripcion, boolean estado, LocalDate fechaCreacion, LocalDate fechaBaja, String razonBaja, String comentariosBaja, String documentoUsuarioBaja) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaBaja = fechaBaja;
        this.razonBaja = razonBaja;
        this.comentariosBaja = comentariosBaja;
        this.documentoUsuarioBaja = documentoUsuarioBaja;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getRazonBaja() {
        return razonBaja;
    }

    public void setRazonBaja(String razonBaja) {
        this.razonBaja = razonBaja;
    }

    public String getComentariosBaja() {
        return comentariosBaja;
    }

    public void setComentariosBaja(String comentariosBaja) {
        this.comentariosBaja = comentariosBaja;
    }

    public String getDocumentoUsuarioBaja() {
        return documentoUsuarioBaja;
    }

    public void setDocumentoUsuarioBaja(String documentoUsuarioBaja) {
        this.documentoUsuarioBaja = documentoUsuarioBaja;
    }

    @Override
    public String toString() {
        return "Listado de actividades: Id - Nombre - Estado - Fecha Creación - Fecha Baja" + "\n" +
                id + " - " + nombre + " - " + estado + " - " + fechaCreacion + " - " + (fechaBaja != null ? fechaBaja : "Activo");

    }
}
