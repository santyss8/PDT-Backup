package com.utec.pdtasur.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
    private int id;
    private Usuario usuario;
    private Espacio espacio;
    private LocalDateTime fechaActividad;
    private int duracion;
    private int cantidadPersonas;
    private double importeAbonar;
    private LocalDate fechaVencimientoSena;
    private double importePagoSena;
    private LocalDate fechaPagoSena;
    private double importeSenaPagado;
    private double saldoPendiente;
    private String estado;
    private LocalDate fechaConfirmacion;
    private LocalDate fechaCreacion;

    public Reserva(int id) {
        this.id = id;
    }

    public Reserva() {
    }

    public Reserva(Usuario usuario, Espacio espacio, LocalDateTime fechaActividad, int duracion, int cantidadPersonas, double importeAbonar, LocalDate fechaVencimientoSena, double importePagoSena) {
        this.usuario = usuario;
        this.espacio = espacio;
        this.fechaActividad = fechaActividad;
        this.duracion = duracion;
        this.cantidadPersonas = cantidadPersonas;
        this.importeAbonar = importeAbonar;
        this.fechaVencimientoSena = fechaVencimientoSena;
        this.importePagoSena = importePagoSena;
    }

    public Reserva(int id, Usuario usuario, Espacio espacio, LocalDateTime fechaActividad, int duracion, int cantidadPersonas, double importeAbonar, LocalDate fechaVencimientoSena, double importePagoSena, LocalDate fechaPagoSena, double importeSenaPagado, double saldoPendiente, String estado, LocalDate fechaConfirmacion, LocalDate fechaCreacion) {
        this.id = id;
        this.usuario = usuario;
        this.espacio = espacio;
        this.fechaActividad = fechaActividad;
        this.duracion = duracion;
        this.cantidadPersonas = cantidadPersonas;
        this.importeAbonar = importeAbonar;
        this.fechaVencimientoSena = fechaVencimientoSena;
        this.importePagoSena = importePagoSena;
        this.fechaPagoSena = fechaPagoSena;
        this.importeSenaPagado = importeSenaPagado;
        this.saldoPendiente = saldoPendiente;
        this.estado = estado;
        this.fechaConfirmacion = fechaConfirmacion;
        this.fechaCreacion = fechaCreacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public LocalDateTime getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(LocalDateTime fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public double getImporteAbonar() {
        return importeAbonar;
    }

    public void setImporteAbonar(double importeAbonar) {
        this.importeAbonar = importeAbonar;
    }

    public LocalDate getFechaVencimientoSena() {
        return fechaVencimientoSena;
    }

    public void setFechaVencimientoSena(LocalDate fechaVencimientoSena) {
        this.fechaVencimientoSena = fechaVencimientoSena;
    }

    public double getImportePagoSena() {
        return importePagoSena;
    }

    public void setImportePagoSena(double importePagoSena) {
        this.importePagoSena = importePagoSena;
    }

    public LocalDate getFechaPagoSena() {
        return fechaPagoSena;
    }

    public void setFechaPagoSena(LocalDate fechaPagoSena) {
        this.fechaPagoSena = fechaPagoSena;
    }

    public double getImporteSenaPagado() {
        return importeSenaPagado;
    }

    public void setImporteSenaPagado(double importeSenaPagado) {
        this.importeSenaPagado = importeSenaPagado;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(LocalDate fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Reserva: Id - Nombre - Estado" + "\n" +
                id + " - " + espacio.getNombre() + " - " + estado;
    }
}
