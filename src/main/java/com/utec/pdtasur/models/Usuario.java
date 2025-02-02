package com.utec.pdtasur.models;


import java.time.LocalDate;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private TipoUsuario tipoUsuario;
    private String contraseña;
    private TipoDocumento tipoDocumento;
    private String documento;
    private String email;
    private List<Telefono> telefonos;
    private String calle;
    private String numeroPuerta;
    private String apartamento;
    private Departamento departamento;
    private Localidad localidad;
    private int numeroSocio;
    private boolean dificultadAuditiva;
    private boolean lenguajeSeñas;
    private boolean participaSubcomision;
    private Subcomision subcomision;
    private CategoriaSocio categoriaSocio;
    private boolean activo;

    public Usuario(){

    }

    // Constructor con documento para busqueda
    public Usuario(String documento){
        this.documento = documento;
    }

    // Constructor con email y contraseña para login
    public Usuario(String email, String contraseña){
        this.email = email;
        this.contraseña = contraseña;
    }

    // Constructor para No Socios | Administradores (Insertar)
    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String calle, String numeroPuerta, String apartamento, Departamento departamento, Localidad localidad, LocalDate fechaNacimiento, List<Telefono> telefonos, String email, String contraseña, TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.apartamento = apartamento;
        this.departamento = departamento;
        this.localidad = localidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    //Constructor para Socio (Insertar) (particpa en subcomision)
    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String calle, String numeroPuerta, String apartamento, Departamento departamento, Localidad localidad, List<Telefono> telefonos, LocalDate fechaNacimiento, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, Subcomision subcomision) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.apartamento = apartamento;
        this.departamento = departamento;
        this.localidad = localidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.categoriaSocio = categoriaSocio;
        this.dificultadAuditiva = dificultadAuditiva;
        this.lenguajeSeñas = lenguajeSeñas;
        this.participaSubcomision = participaSubcomision;
        this.subcomision = subcomision;
    }

    // no participa en subcomision
    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String calle, String numeroPuerta, String apartamento, Departamento departamento, Localidad localidad, LocalDate fechaNacimiento, List<Telefono> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, int numeroSocio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.apartamento = apartamento;
        this.departamento = departamento;
        this.localidad = localidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.categoriaSocio = categoriaSocio;
        this.dificultadAuditiva = dificultadAuditiva;
        this.lenguajeSeñas = lenguajeSeñas;
        this.participaSubcomision = participaSubcomision;
        this.numeroSocio = numeroSocio;
    }

    // Constructor para recibir No Socio | Admin de Base de Datos
    public Usuario(int id, String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String calle, String numeroPuerta, String apartamento, Departamento departamento, Localidad localidad, LocalDate fechaNacimiento, List<Telefono> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.apartamento = apartamento;
        this.departamento = departamento;
        this.localidad = localidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.activo = activo;
    }

    // Constructor para Recibir Socios de Base de Datos (participa en subcomision)
    public Usuario(int id, String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String calle, String numeroPuerta, String apartamento, Departamento departamento, Localidad localidad, LocalDate fechaNacimiento, List<Telefono> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, Subcomision subcomision, int numeroSocio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.apartamento = apartamento;
        this.departamento = departamento;
        this.localidad = localidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.categoriaSocio = categoriaSocio;
        this.dificultadAuditiva = dificultadAuditiva;
        this.lenguajeSeñas = lenguajeSeñas;
        this.participaSubcomision = participaSubcomision;
        this.subcomision = subcomision;
        this.numeroSocio = numeroSocio;
        this.activo = activo;
    }

    //no participa en subcomision

    public Usuario(int id, String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String calle, String numeroPuerta, String apartamento, Departamento departamento, Localidad localidad, LocalDate fechaNacimiento, List<Telefono> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, int numeroSocio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
        this.apartamento = apartamento;
        this.departamento = departamento;
        this.localidad = localidad;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.categoriaSocio = categoriaSocio;
        this.dificultadAuditiva = dificultadAuditiva;
        this.lenguajeSeñas = lenguajeSeñas;
        this.participaSubcomision = participaSubcomision;
        this.numeroSocio = numeroSocio;
        this.activo = activo;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
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

    public boolean isLenguajeSeñas() {
        return lenguajeSeñas;
    }

    public void setLenguajeSeñas(boolean lenguajeSeñas) {
        this.lenguajeSeñas = lenguajeSeñas;
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

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(String numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", tipoUsuario=" + tipoUsuario +
                ", contraseña='" + contraseña + '\'' +
                ", tipoDocumento=" + tipoDocumento +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", telefonos=" + telefonos +
                ", calle='" + calle + '\'' +
                ", numeroPuerta='" + numeroPuerta + '\'' +
                ", apartamento='" + apartamento + '\'' +
                ", departamento=" + departamento +
                ", localidad=" + localidad +
                ", numeroSocio=" + numeroSocio +
                ", dificultadAuditiva=" + dificultadAuditiva +
                ", lenguajeSeñas=" + lenguajeSeñas +
                ", participaSubcomision=" + participaSubcomision +
                ", subcomision=" + subcomision +
                ", categoriaSocio=" + categoriaSocio +
                ", activo=" + activo +
                '}';
    }

    public String confirmacionSocio(){
        String dificultadAuditivaString = dificultadAuditiva ? "Si" : "No";
        String lenguajeSeñasString = lenguajeSeñas ? "Si" : "No";
        String participaSubcomisionString = participaSubcomision ? "Si" : "No";
        return "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Tipo de documento: " + tipoDocumento.toString().toLowerCase() + "\n" +
                "Documento: " + documento + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Calle: " + calle + "\n" +
                "Numero puerta: " + numeroPuerta + "\n" +
                "Apartamento: " + apartamento + "\n" +
                "Departamento: " + departamento + "\n" +
                "Localidad: " + localidad + "\n" +
                "Telefonos: " + telefonos + "\n" +
                "Email: " + email + "\n" +
                "Tipo de usuario: " + tipoUsuario.toString().toLowerCase() + "\n" +
                "Categoria de socio: " + categoriaSocio + "\n" +
                "Dificultad auditiva: " + dificultadAuditivaString + "\n" +
                "Lenguaje señas: " + lenguajeSeñasString + "\n" +
                "Participa en subcomision: " + participaSubcomisionString + "\n" +
                "Subcomision: " + subcomision;
    }

    public String confirmacionUsuario(){
        return "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Tipo de documento: " + tipoDocumento.toString().toLowerCase() + "\n" +
                "Documento: " + documento + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Calle: " + calle + "\n" +
                "Numero puerta: " + numeroPuerta + "\n" +
                "Apartamento: " + apartamento + "\n" +
                "Departamento: " + departamento + "\n" +
                "Localidad: " + localidad + "\n" +
                "Telefonos: " + telefonos + "\n" +
                "Email: " + email + "\n" +
                "Tipo de usuario: " + tipoUsuario.toString().toLowerCase() + "\n";

    }

    public String mostrarUsuarioGenerico(){
        return "Usuario: " + nombre + " " + apellido + " Documento: " + documento + " Tipo de usuario: " + tipoUsuario.toString();
    }



}
