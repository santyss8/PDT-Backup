package com.utec.pdtasur.models;


import java.time.LocalDate;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private String documento;
    private LocalDate fechaNacimiento;
    private String domicilio;
    private List<Integer> telefonos;
    private String email;
    private String contraseña;
    private TipoUsuario tipoUsuario;
    private CategoriaSocio categoriaSocio;
    private boolean dificultadAuditiva;
    private boolean lenguajeSeñas;
    private boolean participaSubcomision;
    private Subcomision subcomision;
    private int numeroSocio;
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
    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String domicilio, LocalDate fechaNacimiento, List<Integer> telefonos, String email, String contraseña, TipoUsuario tipoUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
    }

    //Constructor para Socio (Insertar) (particpa en subcomision)
    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String domicilio, List<Integer> telefonos, LocalDate fechaNacimiento, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, Subcomision subcomision) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
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
    public Usuario(String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String domicilio, LocalDate fechaNacimiento, List<Integer> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, int numeroSocio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
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
    public Usuario(int id, String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String domicilio, LocalDate fechaNacimiento,List<Integer> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonos = telefonos;
        this.email = email;
        this.contraseña = contraseña;
        this.tipoUsuario = tipoUsuario;
        this.activo = activo;
    }

    // Constructor para Recibir Socios de Base de Datos (participa en subcomision)
    public Usuario(int id, String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String domicilio, LocalDate fechaNacimiento, List<Integer> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, Subcomision subcomision, int numeroSocio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
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

    public Usuario(int id, String nombre, String apellido, TipoDocumento tipoDocumento, String documento, String domicilio, LocalDate fechaNacimiento, List<Integer> telefonos, String email, String contraseña, TipoUsuario tipoUsuario, CategoriaSocio categoriaSocio, boolean dificultadAuditiva, boolean lenguajeSeñas, boolean participaSubcomision, int numeroSocio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.domicilio = domicilio;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Integer> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Integer> telefonos) {
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

    @Override
    public String toString() {
        return "Usuario { \n" +
                "  id = " + id + ", \n" +
                "  nombre = '" + nombre + "', \n" +
                "  apellido = '" + apellido + "', \n" +
                "  tipoDocumento = " + tipoDocumento + ", \n" +
                "  documento = '" + documento + "', \n" +
                "  fechaNacimiento = " + fechaNacimiento + ", \n" +
                "  domicilio = '" + domicilio + "', \n" +
                "  telefonos = " + telefonos + ", \n" +
                "  email = '" + email + "', \n" +
                "  contraseña = '" + contraseña + "', \n" +
                "  tipoUsuario = " + tipoUsuario + ", \n" +
                "  categoriaSocio = " + categoriaSocio + ", \n" +
                "  dificultadAuditiva = " + dificultadAuditiva + ", \n" +
                "  lenguajeSeñas = " + lenguajeSeñas + ", \n" +
                "  participaSubcomision = " + participaSubcomision + ", \n" +
                "  subcomision = " + subcomision + ", \n" +
                "  numeroSocio = " + numeroSocio + ", \n" +
                "  activo = " + activo + "\n" +
                "}";
    }

    public String confirmacionSocio(){
        String dificultadAuditivaString = dificultadAuditiva ? "Si" : "No";
        String lenguajeSeñasString = lenguajeSeñas ? "Si" : "No";
        String participaSubcomisionString = participaSubcomision ? "Si" : "No";
        return "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido +
                "Tipo de documento: " + tipoDocumento.toString().toLowerCase() + "\n" +
                "Documento: " + documento + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Domicilio: " + domicilio + "\n" +
                "Telefonos: " + telefonos + "\n" +
                "Email: " + email + "\n" +
                "Contraseña: " + contraseña + "\n" +
                "Tipo de usuario: " + tipoUsuario.toString().toLowerCase() + "\n" +
                "Categoria de socio: " + categoriaSocio + "\n" +
                "Dificultad auditiva: " + dificultadAuditivaString + "\n" +
                "Lenguaje señas: " + lenguajeSeñasString + "\n" +
                "Participa en subcomision: " + participaSubcomisionString + "\n" +
                "Subcomision: " + subcomision;
    }



}
