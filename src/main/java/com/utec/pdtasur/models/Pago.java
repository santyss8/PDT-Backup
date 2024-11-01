package com.utec.pdtasur.models;

import java.util.Date;

public class Pago {
    private Usuario usuario;
    private double cuotaSocio;
    private double inscripcionActividad;
    private double reservaEspacio;
    private Date fechaPago;
    private String formaPago;

    public Pago(){

    }

    public Pago(Usuario usuario, double cuotaSocio, double inscripcionActividad, double reservaEspacio, Date fechaPago, String formaPago) {
        this.usuario = usuario;
        this.cuotaSocio = cuotaSocio;
        this.inscripcionActividad = inscripcionActividad;
        this.reservaEspacio = reservaEspacio;
        this.fechaPago = fechaPago;
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getCuotaSocio() {
        return cuotaSocio;
    }

    public void setCuotaSocio(double cuotaSocio) {
        this.cuotaSocio = cuotaSocio;
    }

    public double getInscripcionActividad() {
        return inscripcionActividad;
    }

    public void setInscripcionActividad(double inscripcionActividad) {
        this.inscripcionActividad = inscripcionActividad;
    }

    public double getReservaEspacio() {
        return reservaEspacio;
    }

    public void setReservaEspacio(double reservaEspacio) {
        this.reservaEspacio = reservaEspacio;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
}
