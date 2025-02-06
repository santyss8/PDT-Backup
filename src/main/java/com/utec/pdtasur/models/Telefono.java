package com.utec.pdtasur.models;

public class Telefono {
    private int id;
    private String numero;
    private String tipo;
    private Usuario usuario;

    public Telefono(){

    }

    public Telefono(int id, String numero, String tipo, Usuario usuario){
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return numero;
    }
}
