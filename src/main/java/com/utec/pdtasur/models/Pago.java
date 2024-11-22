package com.utec.pdtasur.models;

import java.time.LocalDate;
import java.util.Date;

public class Pago {
    private int id;
    private Usuario usuario;
    private boolean esCuota;
    private Espacio espacio;
    private double monto;
    private LocalDate fechaCobro;
    private String formaCobro;

    public Pago() {

    }

    public Pago(int id, Usuario usuario, boolean esCuota, Espacio espacio, double monto, LocalDate fechaCobro, String formaCobro) {
        this.id = id;
        this.usuario = usuario;
        this.esCuota = esCuota;
        this.espacio = espacio;
        this.monto = monto;
        this.fechaCobro = fechaCobro;
        this.formaCobro = formaCobro;
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

    public boolean isEsCuota() {
        return esCuota;
    }

    public void setEsCuota(boolean esCuota) {
        this.esCuota = esCuota;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDate fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getFormaCobro() {
        return formaCobro;
    }

    public void setFormaCobro(String formaCobro) {
        this.formaCobro = formaCobro;
    }

    @Override
    public String toString() {
        return "Pago: " + "\n" +
                "Id: " + id +
                " - Usuario: " + usuario.getNombre() +
                " - Es Cuota: " + esCuota +
                " - Espacio: " + espacio +
                " - Monto: " + monto +
                " - Fecha de cobro: " + fechaCobro +
                " - Forma de cobro: " + formaCobro + '\n';
    }
}
