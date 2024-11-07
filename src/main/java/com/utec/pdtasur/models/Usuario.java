package com.utec.pdtasur.models;

import java.time.LocalDate;
import java.util.List;

public class Usuario {
    private String nombres;
    private String apellidos;
    private int tipoDocumento;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String domicilio;
    private List<String> telefonos;
    private String email;
    private String contraseña;
    private TipoUsuario tipoUsuario;
    private CategoriaSocio categoriaSocio;
    private boolean dificultadAuditiva;
    private boolean manejoLenguajeDeSeñas;
    private boolean participaSubcomision;
    private Subcomision subcomision;

    // Constructor Vacio
    public Usuario(){

    }

    // Constructor con numero de documento (identificador)
    public Usuario(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    // Constructor con todos los datos (socio)
    public Usuario(String nombres, String apellidos, int tipoDocumento, String numeroDocumento, LocalDate fechaNacimiento, String domicilio, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean manejoLenguajeDeSeñas, boolean participaSubcomision, Subcomision subcomision) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.categoriaSocio = categoriaSocio;
        this.dificultadAuditiva = dificultadAuditiva;
        this.manejoLenguajeDeSeñas = manejoLenguajeDeSeñas;
        this.participaSubcomision = participaSubcomision;
        this.subcomision = subcomision;
    }

    // constructor no socio y administrador
    public Usuario(String nombres, String apellidos, int tipoDocumento, String numeroDocumento, LocalDate fechaNacimiento, String domicilio, String email, String contraseña, TipoUsuario tipoUsuario) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
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

    public int getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
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

    public boolean isParticipaSubcomision() {
        return participaSubcomision;
    }

    public void setParticipaSubcomision(boolean participaSubcomision) {
        this.participaSubcomision = participaSubcomision;
    }

    public Subcomision getSubcomision() {
        return subcomision;
    }

    public void setSubcomision(Subcomision subcomision) {
        this.subcomision = subcomision;
    }
}
