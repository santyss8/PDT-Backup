package com.utec.pdtasur.models;

import java.time.LocalDate;

public class Espacio {
    private int id;
    private String nombre;
    private int capacidadMaxima;
    private double precioReservaSocio;
    private double precioReservaNoSocio;
    private LocalDate fechaVigenciaPrecio;
    private String observaciones;
    private boolean activo;
    private LocalDate fechaCreacion;

    public Espacio(){

    }

    public Espacio (int id){
        this.id = id;
    }

    public Espacio(String nombre, int capacidadMaxima, double precioReservaSocio, double precioReservaNoSocio, LocalDate fechaVigenciaPrecio, String observaciones, LocalDate fechaCreacion) {
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.precioReservaSocio = precioReservaSocio;
        this.precioReservaNoSocio = precioReservaNoSocio;
        this.fechaVigenciaPrecio = fechaVigenciaPrecio;
        this.observaciones = observaciones;
        this.fechaCreacion = fechaCreacion;
    }

    public Espacio(int id, String nombre, int capacidadMaxima, double precioReservaSocio, double precioReservaNoSocio, LocalDate fechaVigenciaPrecio, String observaciones, boolean activo, LocalDate fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.precioReservaSocio = precioReservaSocio;
        this.precioReservaNoSocio = precioReservaNoSocio;
        this.fechaVigenciaPrecio = fechaVigenciaPrecio;
        this.observaciones = observaciones;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
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

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getPrecioReservaSocio() {
        return precioReservaSocio;
    }

    public void setPrecioReservaSocio(double precioReservaSocio) {
        this.precioReservaSocio = precioReservaSocio;
    }

    public double getPrecioReservaNoSocio() {
        return precioReservaNoSocio;
    }

    public void setPrecioReservaNoSocio(double precioReservaNoSocio) {
        this.precioReservaNoSocio = precioReservaNoSocio;
    }

    public LocalDate getFechaVigenciaPrecio() {
        return fechaVigenciaPrecio;
    }

    public void setFechaVigenciaPrecio(LocalDate fechaVigenciaPrecio) {
        this.fechaVigenciaPrecio = fechaVigenciaPrecio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Listado de espacios: Id - Nombre - Espacio" + "\n" +
                id + " - " + nombre + " - " + activo;
    }
}
