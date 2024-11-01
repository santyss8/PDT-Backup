package com.utec.pdtasur.models;

import java.util.Date;

public class Espacio {
    private String nombre;
    private String ubicacion;
    private int capacidad;
    private double precioSocio;
    private double precioNoSocio;
    private Date fechaPrecio;
    private String observaciones;

    public Espacio(){

    }

    public Espacio(String nombre, String ubicacion, int capacidad, double precioSocio, double precioNoSocio, Date fechaPrecio, String observaciones) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.precioSocio = precioSocio;
        this.precioNoSocio = precioNoSocio;
        this.fechaPrecio = fechaPrecio;
        this.observaciones = observaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioSocio() {
        return precioSocio;
    }

    public void setPrecioSocio(double precioSocio) {
        this.precioSocio = precioSocio;
    }

    public double getPrecioNoSocio() {
        return precioNoSocio;
    }

    public void setPrecioNoSocio(double precioNoSocio) {
        this.precioNoSocio = precioNoSocio;
    }

    public Date getFechaPrecio() {
        return fechaPrecio;
    }

    public void setFechaPrecio(Date fechaPrecio) {
        this.fechaPrecio = fechaPrecio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
