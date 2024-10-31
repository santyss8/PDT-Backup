package com.utec.pdtasur.models;

import java.util.Date;
import java.util.List;

public class Usuario {
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private Date fechaNacimiento;
    private String domicilio;
    private List<String> telefonos;
    private String email;
    private String contraseña;
    private TipoUsuario tipoUsuario;
    private CategoriaSocio categoriaSocio;
    private boolean dificultadAuditiva;
    private boolean manejoLenguajeDeSeñas;
    private Subcomision subcomision;

    public Usuario(){

    }

    public Usuario(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Usuario(String nombres, String apellidos, String tipoDocumento, String numeroDocumento, Date fechaNacimiento, String domicilio, List<String> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean manejoLenguajeDeSeñas, Subcomision subcomision) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.categoriaSocio = categoriaSocio;
        this.dificultadAuditiva = dificultadAuditiva;
        this.manejoLenguajeDeSeñas = manejoLenguajeDeSeñas;
        this.subcomision = subcomision;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public CategoriaSocio getCategoriaSocio() {
        return categoriaSocio;
    }

    public void setCategoriaSocio(CategoriaSocio categoriaSocio) {
        this.categoriaSocio = categoriaSocio;
    }

    public boolean isDificultadAuditiva() {
        return dificultadAuditiva;
    }

    public void setDificultadAuditiva(boolean dificultadAuditiva) {
        this.dificultadAuditiva = dificultadAuditiva;
    }

    public boolean isManejoLenguajeDeSeñas() {
        return manejoLenguajeDeSeñas;
    }

    public void setManejoLenguajeDeSeñas(boolean manejoLenguajeDeSeñas) {
        this.manejoLenguajeDeSeñas = manejoLenguajeDeSeñas;
    }

    public Subcomision getSubcomision() {
        return subcomision;
    }

    public void setSubcomision(Subcomision subcomision) {
        this.subcomision = subcomision;
    }

}
