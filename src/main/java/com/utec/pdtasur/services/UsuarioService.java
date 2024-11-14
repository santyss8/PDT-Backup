package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.impl.CategoriaSocioDAOImpl;
import com.utec.pdtasur.dao.impl.SubcomisionDAOImpl;
import com.utec.pdtasur.dao.impl.UsuarioDAOImpl;
import com.utec.pdtasur.dao.interfaces.CategoriaSocioDAO;
import com.utec.pdtasur.dao.interfaces.SubcomisionDAO;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.*;
import com.utec.pdtasur.utils.HashContraseña;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.utec.pdtasur.services.ValidarService.*;
import static com.utec.pdtasur.utils.HashContraseña.*;



public class UsuarioService {
    UsuarioDAO usuarioDAO;
    CategoriaSocioDAO categoriaSocioDAO;
    SubcomisionDAO subcomisionDAO;
    Scanner sc;
    ValidarService validarService;

    public UsuarioService() throws SQLException {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.sc = new Scanner(System.in);
        this.categoriaSocioDAO = new CategoriaSocioDAOImpl();
        this.subcomisionDAO = new SubcomisionDAOImpl();
        this.validarService = new ValidarService();
    }

    public void registrar() throws SQLException {
        boolean select = true;
        do {
            System.out.print("""
                    Que tipo de usuario desea registrar
                    1. Socio
                    2. No socio
                    3. Administrativo
                    4. Salir
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        registroSocio();
                        break;
                    case 2:
                        registroNoSocio();
                        break;
                    case 3:
                        registroAdmin();
                        break;
                    case 4:
                        select = false;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }

            System.out.println();
        } while (select);

    }

    private void registroSocio() {
        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.SOCIO);
        System.out.println("----- Registro de usuario Socio -----");
        do {
            System.out.println("Ingrese su nombre");
            String nombre = sc.nextLine();
            if (!validarNombreApellido(nombre)) {
                System.out.println("El nombre ingresado no es válido");
                continue;
            }
            usuario.setNombre(nombre);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su apellido");
            String apellido = sc.nextLine();
            if (!validarNombreApellido(apellido)) {
                System.out.println("El apellido ingresado no es válido");
                continue;
            }
            usuario.setApellido(apellido);
            break;
        } while (true);
        do {
            System.out.print("""
                    Ingrese su tipo de documento
                    1. Cedula
                    2. DNI
                    3. Pasaporte
                    4. Otro
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        usuario.setTipoDocumento(TipoDocumento.CEDULA);
                        break;
                    case 2:
                        usuario.setTipoDocumento(TipoDocumento.DNI);
                        break;
                    case 3:
                        usuario.setTipoDocumento(TipoDocumento.PASAPORTE);
                        break;
                    case 4:
                        usuario.setTipoDocumento(TipoDocumento.OTRO);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);
        do {
            System.out.println("Ingrese su documento");
            String documento = sc.nextLine();
            if (usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)) {
                if (!validarDocumento(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            if (!usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)) {
                if (!validarDocumentoGenerico(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            usuario.setDocumento(documento);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su domicilio");
            String domicilio = sc.nextLine();
            if (!validarDomicilio(domicilio)) {
                System.out.println("El domicilio ingresado no es válido");
                continue;
            }
            usuario.setDomicilio(domicilio);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su fecha de nacimiento (aaaa-mm-dd)");
            try {
                String fechaNacimiento = sc.nextLine();
                if (validarFechaNacimiento(fechaNacimiento) == null) {
                    System.out.println("La fecha de nacimiento no es válida");
                    continue;
                }
                usuario.setFechaNacimiento(validarFechaNacimiento(fechaNacimiento));
                break;
            } catch (InputMismatchException e) {
                System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
                sc.nextLine();
            }

        } while (true);
        do {
            List<String> telefonos = new ArrayList<>();
            boolean telefonosContinuar = true;
            System.out.println("Ingrese sus telefonos");
            while (telefonosContinuar) {
                String telefono = sc.nextLine();
                if (validarTelefono(telefono)) {
                    telefonos.add(telefono);
                    System.out.println("Telefono agregado");
                } else {
                    System.out.println("El telefono ingresado no es válido");
                    continue;
                }
                System.out.println("Quiere agregar otro telefono? (S/N)");
                String opcion = sc.nextLine();
                if (opcion.equals("S")) {
                    continue;
                } else {
                    telefonosContinuar = false;
                }
            }
            usuario.setTelefonos(telefonos);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su email");
            String email = sc.nextLine();
            if (!validarEmail(email)) {
                System.out.println("El email ingresado no es válido");
                continue;
            }
            usuario.setEmail(email);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su contraseña");
            String contraseña = sc.nextLine();
            if (!validarContraseña(contraseña)) {
                System.out.println("La contraseña ingresada no es válida");
                continue;
            }
            contraseña = hashPassword(contraseña);
            usuario.setContraseña(contraseña);
            break;
        } while (true);
        do {
            System.out.println("Seleccione su categoria de socio");
            List<CategoriaSocio> categorias = categoriaSocioDAO.listarCategorias();
            for (CategoriaSocio categoriaSocio : categorias) {
                System.out.println(categoriaSocio);
            }
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > categorias.size()) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                usuario.setCategoriaSocio(categorias.get(opcion - 1));
                break;
            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);
        do {
            System.out.println("Tiene dificultad auditiva S/N");
            String dificultadAuditiva = sc.nextLine();
            if (dificultadAuditiva.equals("S")) {
                usuario.setDificultadAuditiva(true);
            } else if (dificultadAuditiva.equals("N")) {
                usuario.setDificultadAuditiva(false);
            } else {
                System.out.println("Opcion invalida");
                continue;
            }
            break;
        } while (true);
        do {
            System.out.println("Tiene lenguaje señas S/N");
            String lenguajeSeñas = sc.nextLine();
            if (lenguajeSeñas.equals("S")) {
                usuario.setLenguajeSeñas(true);
            } else if (lenguajeSeñas.equals("N")) {
                usuario.setLenguajeSeñas(false);
            } else {
                System.out.println("Opcion invalida");
                continue;
            }
            break;
        } while (true);
        do {
            System.out.println("Participa en subcomision S/N");
            String participaSubcomision = sc.nextLine();
            if (participaSubcomision.equals("S")) {
                usuario.setParticipaSubcomision(true);
            } else if (participaSubcomision.equals("N")) {
                usuario.setParticipaSubcomision(false);
            } else {
                System.out.println("Opcion invalida");
                continue;
            }
            break;
        } while (true);
        if (usuario.isParticipaSubcomision()) {
            do {
                System.out.println("Ingrese su subcomision");
                List<Subcomision> subcomisiones = subcomisionDAO.listarSubcomisiones();
                for (Subcomision subcomision : subcomisiones) {
                    System.out.println(subcomision);
                }
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion < 1 || opcion > subcomisiones.size()) {
                        System.out.println("Opcion invalida");
                        continue;
                    }
                    usuario.setSubcomision(subcomisiones.get(opcion - 1));
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("la opcion tiene que ser un numero");
                    sc.nextLine();
                }
            } while (true);
        }
        do {
            System.out.println("Desea registrar al Usuario con los datos introducidos");
            System.out.println(usuario.confirmacionSocio());
            System.out.print("""
                    1. Si
                    2. No
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 2) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                if (opcion == 1) {
                    usuarioDAO.registrarSocio(usuario);
                    break;
                }
                if (opcion == 2) {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);


    }

    private void registroNoSocio() throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.NOSOCIO);
        System.out.println("----- Registro de usuario NoSocio -----");
        do {
            System.out.println("Ingrese su nombre");
            String nombre = sc.nextLine();
            if (!validarNombreApellido(nombre)) {
                System.out.println("El nombre ingresado no es válido");
                continue;
            }
            usuario.setNombre(nombre);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su apellido");
            String apellido = sc.nextLine();
            if (!validarNombreApellido(apellido)) {
                System.out.println("El apellido ingresado no es válido");
                continue;
            }
            usuario.setApellido(apellido);
            break;
        } while (true);
        do {
            System.out.print("""
                    Ingrese su tipo de documento
                    1. Cedula
                    2. DNI
                    3. Pasaporte
                    4. Otro
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        usuario.setTipoDocumento(TipoDocumento.CEDULA);
                        break;
                    case 2:
                        usuario.setTipoDocumento(TipoDocumento.DNI);
                        break;
                    case 3:
                        usuario.setTipoDocumento(TipoDocumento.PASAPORTE);
                        break;
                    case 4:
                        usuario.setTipoDocumento(TipoDocumento.OTRO);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);
        do {
            System.out.println("Ingrese su documento");
            String documento = sc.nextLine();
            if (usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)) {
                if (!validarDocumento(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            if (!usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)) {
                if (!validarDocumentoGenerico(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            usuario.setDocumento(documento);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su domicilio");
            String domicilio = sc.nextLine();
            if (!validarDomicilio(domicilio)) {
                System.out.println("El domicilio ingresado no es válido");
                continue;
            }
            usuario.setDomicilio(domicilio);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su fecha de nacimiento (aaaa-mm-dd)");
            try {
                String fechaNacimiento = sc.nextLine();
                if (validarFechaNacimiento(fechaNacimiento) == null) {
                    System.out.println("La fecha de nacimiento no es válida");
                    continue;
                }
                usuario.setFechaNacimiento(validarFechaNacimiento(fechaNacimiento));
                break;
            } catch (InputMismatchException e) {
                System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
                sc.nextLine();
            }

        } while (true);
        do {
            List<String> telefonos = new ArrayList<>();
            boolean telefonosContinuar = true;
            System.out.println("Ingrese sus telefonos");
            while (telefonosContinuar) {
                String telefono = sc.nextLine();
                if (validarTelefono(telefono)) {
                    telefonos.add(telefono);
                    System.out.println("Telefono agregado");
                } else {
                    System.out.println("El telefono ingresado no es válido");
                    continue;
                }
                System.out.println("Quiere agregar otro telefono? (S/N)");
                String opcion = sc.nextLine();
                if (opcion.equals("S")) {
                    continue;
                } else {
                    telefonosContinuar = false;
                }
            }
            usuario.setTelefonos(telefonos);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su email");
            String email = sc.nextLine();
            if (!validarEmail(email)) {
                System.out.println("El email ingresado no es válido");
                continue;
            }
            usuario.setEmail(email);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su contraseña");
            String contraseña = sc.nextLine();
            if (!validarContraseña(contraseña)) {
                System.out.println("La contraseña ingresada no es válida");
                continue;
            }
            contraseña = hashPassword(contraseña);
            usuario.setContraseña(contraseña);
            break;
        } while (true);
        do {
            System.out.println("Desea registrar al Usuario con los datos introducidos");
            System.out.println(usuario.confirmacionUsuario());
            System.out.print("""
                    1. Si
                    2. No
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 2) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                if (opcion == 1) {
                    usuarioDAO.registrarNoSocio(usuario);
                    break;
                }
                if (opcion == 2) {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);

    }

    private void registroAdmin() {
        Usuario usuario = new Usuario();
        usuario.setTipoUsuario(TipoUsuario.AUXILIARADMINISTRATIVO);
        System.out.println("Para registrar un usuario como administrador, debe ingresar su email y contraseña");
        Usuario usuarioAdmin = login();
        if (usuarioAdmin == null) {
            System.out.println("El usuario no existe");
            return;
        }
        if (!usuarioAdmin.getTipoUsuario().equals(TipoUsuario.AUXILIARADMINISTRATIVO)) {
            System.out.println("El usuario no es administrador");
            return;
        }
        System.out.println("----- Bienvenido al Registro de usuario AuxiliarAdministrador -----");

        do {
            System.out.println("Ingrese su nombre");
            String nombre = sc.nextLine();
            if (!validarNombreApellido(nombre)) {
                System.out.println("El nombre ingresado no es válido");
                continue;
            }
            usuario.setNombre(nombre);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su apellido");
            String apellido = sc.nextLine();
            if (!validarNombreApellido(apellido)) {
                System.out.println("El apellido ingresado no es válido");
                continue;
            }
            usuario.setApellido(apellido);
            break;
        } while (true);
        do {
            System.out.print("""
                    Ingrese su tipo de documento
                    1. Cedula
                    2. DNI
                    3. Pasaporte
                    4. Otro
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        usuario.setTipoDocumento(TipoDocumento.CEDULA);
                        break;
                    case 2:
                        usuario.setTipoDocumento(TipoDocumento.DNI);
                        break;
                    case 3:
                        usuario.setTipoDocumento(TipoDocumento.PASAPORTE);
                        break;
                    case 4:
                        usuario.setTipoDocumento(TipoDocumento.OTRO);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);
        do {
            System.out.println("Ingrese su documento");
            String documento = sc.nextLine();
            if (usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)) {
                if (!validarDocumento(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            if (!usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)) {
                if (!validarDocumentoGenerico(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            usuario.setDocumento(documento);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su domicilio");
            String domicilio = sc.nextLine();
            if (!validarDomicilio(domicilio)) {
                System.out.println("El domicilio ingresado no es válido");
                continue;
            }
            usuario.setDomicilio(domicilio);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su fecha de nacimiento (aaaa-mm-dd)");
            try {
                String fechaNacimiento = sc.nextLine();
                if (validarFechaNacimiento(fechaNacimiento) == null) {
                    System.out.println("La fecha de nacimiento no es válida");
                    continue;
                }
                usuario.setFechaNacimiento(validarFechaNacimiento(fechaNacimiento));
                break;
            } catch (InputMismatchException e) {
                System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
                sc.nextLine();
            }

        } while (true);
        do {
            List<String> telefonos = new ArrayList<>();
            boolean telefonosContinuar = true;
            System.out.println("Ingrese sus telefonos");
            while (telefonosContinuar) {
                String telefono = sc.nextLine();
                if (validarTelefono(telefono)) {
                    telefonos.add(telefono);
                    System.out.println("Telefono agregado");
                } else {
                    System.out.println("El telefono ingresado no es válido");
                    continue;
                }
                System.out.println("Quiere agregar otro telefono? (S/N)");
                String opcion = sc.nextLine();
                if (opcion.equals("S")) {
                    continue;
                } else {
                    telefonosContinuar = false;
                }
            }
            usuario.setTelefonos(telefonos);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su email");
            String email = sc.nextLine();
            if (!validarEmail(email)) {
                System.out.println("El email ingresado no es válido");
                continue;
            }
            usuario.setEmail(email);
            break;
        } while (true);
        do {
            System.out.println("Ingrese su contraseña");
            String contraseña = sc.nextLine();
            if (!validarContraseña(contraseña)) {
                System.out.println("La contraseña ingresada no es válida");
                continue;
            }
            contraseña = hashPassword(contraseña);
            usuario.setContraseña(contraseña);
            break;
        } while (true);
        do {
            System.out.println("Desea registrar al Usuario con los datos introducidos");
            System.out.println(usuario.confirmacionUsuario());
            System.out.print("""
                    1. Si
                    2. No
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 2) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                if (opcion == 1) {
                    usuarioDAO.registrarAdmin(usuario);
                    break;
                }
                if (opcion == 2) {
                    break;
                }

            } catch (InputMismatchException e) {
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        } while (true);

    }

    public Usuario login() {
        String email;
        String contraseña;
        System.out.println("----- Login -----");
        do {
            System.out.println("Ingrese su email");
            email = sc.nextLine();
            if (!validarEmailLogin(email)) {
                continue;
            }
            break;
        } while (true);
        do {
            System.out.println("Ingrese su contraseña");
            contraseña = sc.nextLine();
            if (!validarContraseña(contraseña)) {
                continue;
            }
            break;

        } while (true);
        Usuario usuario = usuarioDAO.login(email, contraseña);
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return null;
        }
        if (!usuario.isActivo()) {
            System.out.println("Usuario no activo");
            return null;
        }
        if (comparePassword(contraseña, usuario.getContraseña())) {
            return usuario;
        } else {
            System.out.println("Contraseña incorrecta");
            return null;
        }


    }




    public void gestionUsuarioMenu() {
        boolean bandera = true;
        int opcion;
        while (bandera) {
            opcion = 0;
            System.out.print("""
                    ----- Gestion de Usuario -----
                    1. Listar Usuarios
                    2. Modificar Datos de Usuario
                    3. Activar Usuario
                    4. Baja de Usuario
                    5. Salir
                    """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Ingrese la opcion correcta");
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    modificarDatos();
                    break;
                case 3:
                    activarUsuario();
                    break;
                case 4:
                    bajaUsuario();
                    break;
                case 5:
                    bandera = false;
                    break;
                default:
                    System.out.println("Opcion incorrecta");
                    break;
            }

        }

    }

    private void listarUsuarios() {
        boolean bandera = true;
        int opcion = 0;
        while (bandera) {
            System.out.println("----- Lista de Usuarios -----");
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            System.out.print("""
                    Como quiere filtrar los usuarios?
                    1. Nombre
                    2. Apellido
                    3. Documento
                    4. Tipo de Usuario
                    5. Estado
                    6. Por Defecto (Estado: Activo)
                    7. Salir
                    """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 7) {
                    System.out.println("Opcion invalida");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre");
                    String nombre = sc.nextLine();
                    if (nombre.matches("^[a-zA-Z]+$")) {
                        usuarios = filtrarPorNombre(usuarios, nombre);
                    } else {
                        System.out.println("El nombre ingresado no es válido");
                        continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por nombre: " + nombre + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    String opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        bandera = false;
                    }
                    continue;
                case 2:
                    System.out.println("Ingrese el apellido");
                    String apellido = sc.nextLine();
                    if (apellido.matches("^[a-zA-Z]+$")) {
                        usuarios = filtrarPorApellido(usuarios, apellido);
                    } else {
                        System.out.println("El apellido ingresado no es válido");
                        continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por apellido: " + apellido + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        bandera = false;
                    }
                    continue;
                case 3:
                    System.out.println("Ingrese su documento");
                    String documento = sc.nextLine();
                    System.out.println("----- Lista de Usuarios filtrados por documento: " + documento + " -----");
                    mostrarUsuariosListados(filtrarPorDocumento(usuarios, documento));
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        bandera = false;
                    }

                    continue;
                case 4:
                    System.out.println("Seleccione un tipo de usuario");
                    TipoUsuario tipoUsuarioTemp = null;
                    int i = 1;
                    for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
                        ;
                        System.out.println(i + ". " + tipoUsuario);
                        i = i + 1;
                    }
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion < 1 || opcion > 3) {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                        if (opcion == 1) {
                            tipoUsuarioTemp = TipoUsuario.SOCIO;
                        }
                        if (opcion == 2) {
                            tipoUsuarioTemp = TipoUsuario.NOSOCIO;
                        }
                        if (opcion == 3) {
                            tipoUsuarioTemp = TipoUsuario.AUXILIARADMINISTRATIVO;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Tienes que ingresar un numero");
                        sc.nextLine();
                        continue;
                    }
                    switch (opcion) {
                        case 1:
                            System.out.println("Filtrado por Socio");
                            usuarios = filtrarPorTipoUsuario(usuarios, TipoUsuario.SOCIO);
                            break;
                        case 2:
                            System.out.println("Filtrado por No Socio");
                            usuarios = filtrarPorTipoUsuario(usuarios, TipoUsuario.NOSOCIO);
                            break;
                        case 3:
                            System.out.println("Filtrado por AuxiliarAdministrador");
                            usuarios = filtrarPorTipoUsuario(usuarios, TipoUsuario.AUXILIARADMINISTRATIVO);
                            break;
                        default:
                            System.out.println("Opcion invalida");
                            continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por tipo de usuario: " + tipoUsuarioTemp.toString().toLowerCase() + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        bandera = false;
                    }
                    continue;
                case 5:
                    System.out.println("Seleccione un estado");
                    System.out.println("1. Activo");
                    System.out.println("2. Inactivo");
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion < 1 || opcion > 2) {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                        if (opcion == 1) {
                            System.out.println("Filtrado por estado activo");
                            usuarios = filtrarPorEstado(usuarios, true);
                        }
                        if (opcion == 2) {
                            System.out.println("Filtrado por estado inactivo");
                            usuarios = filtrarPorEstado(usuarios, false);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Tienes que ingresar un numero");
                        sc.nextLine();
                        continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por estado: " + opcion + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        bandera = false;
                    }
                    continue;
                case 6:
                    usuarios = filtrarPorEstado(usuarios, true);
                    System.out.println("----- Lista de Usuarios filtrados por estado activo -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        bandera = false;
                    }
                    continue;
                case 7:
                    System.out.println("saliendo...");
                    bandera = false;
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

    }

    private void mostrarUsuariosListados(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getTipoUsuario().equals(TipoUsuario.SOCIO)) {
                System.out.println(usuario.confirmacionSocio());
                System.out.println("----------------------------");
            }
            if (usuario.getTipoUsuario().equals(TipoUsuario.NOSOCIO) || usuario.getTipoUsuario().equals(TipoUsuario.AUXILIARADMINISTRATIVO)) {
                System.out.println(usuario.confirmacionUsuario());
                System.out.println("----------------------------");
            }
        }
    }


    public static List<Usuario> filtrarPorNombre(List<Usuario> usuarios, String nombre) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (nombre == null || nombre.isEmpty() || usuario.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public static List<Usuario> filtrarPorApellido(List<Usuario> usuarios, String apellido) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (apellido == null || apellido.isEmpty() || usuario.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public static List<Usuario> filtrarPorDocumento(List<Usuario> usuarios, String documento) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (documento == null || documento.isEmpty() || usuario.getDocumento().toLowerCase().contains(documento.toLowerCase())) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public static List<Usuario> filtrarPorTipoUsuario(List<Usuario> usuarios, TipoUsuario tipoUsuario) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (tipoUsuario == null || usuario.getTipoUsuario().equals(tipoUsuario)) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }

    public static List<Usuario> filtrarPorEstado(List<Usuario> usuarios, boolean estadoActivo) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.isActivo() == estadoActivo) {
                resultado.add(usuario);
            }
        }
        return resultado;
    }


    private void modificarDatos() {
        boolean bandera = true;
        int opcion;
        while (bandera){
            System.out.print("""
                ----- Modificar Datos de Usuario -----
                1. Listar Usuarios
                2. Salir
                """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 2){
                    System.out.println("Opcion invalida");
                    continue;
                }
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }
            switch (opcion){
                case 1:
                    listarUsuariosModificar();
                    continue;
                case 2:
                    System.out.println("saliendo");
                    bandera = false;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida");
                    break;
            }


        }

    }

    private void listarUsuariosModificar() {
        int opcion = 0;
        boolean bandera = true;
        while (bandera){
            System.out.println("----- Listado de Usuarios ------");
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            System.out.print("""
                    Como quiere filtrar los usuarios?
                    1. Nombre
                    2. Apellido
                    3. Documento
                    4. Tipo de Usuario
                    5. Estado
                    6. Por Defecto (Estado: Activo)
                    7. Salir
                    """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 7) {
                    System.out.println("Opcion invalida");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nombre");
                    String nombre = sc.nextLine();
                    if (nombre.matches("^[a-zA-Z]+$")) {
                        usuarios = filtrarPorNombre(usuarios, nombre);
                    } else {
                        System.out.println("El nombre ingresado no es válido");
                        continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por nombre: " + nombre + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    String opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        menuModificarDatos(usuarios);
                    }
                    continue;
                case 2:
                    System.out.println("Ingrese el apellido");
                    String apellido = sc.nextLine();
                    if (apellido.matches("^[a-zA-Z]+$")) {
                        usuarios = filtrarPorApellido(usuarios, apellido);
                    } else {
                        System.out.println("El apellido ingresado no es válido");
                        continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por apellido: " + apellido + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        menuModificarDatos(usuarios);
                    }
                    continue;
                case 3:
                    System.out.println("Ingrese su documento");
                    String documento = sc.nextLine();
                    System.out.println("----- Lista de Usuarios filtrados por documento: " + documento + " -----");
                    mostrarUsuariosListados(filtrarPorDocumento(usuarios, documento));
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        menuModificarDatos(usuarios);
                    }

                    continue;
                case 4:
                    System.out.println("Seleccione un tipo de usuario");
                    TipoUsuario tipoUsuarioTemp = null;
                    int i = 1;
                    for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
                        ;
                        System.out.println(i + ". " + tipoUsuario);
                        i = i + 1;
                    }
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion < 1 || opcion > 3) {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                        if (opcion == 1) {
                            tipoUsuarioTemp = TipoUsuario.SOCIO;
                        }
                        if (opcion == 2) {
                            tipoUsuarioTemp = TipoUsuario.NOSOCIO;
                        }
                        if (opcion == 3) {
                            tipoUsuarioTemp = TipoUsuario.AUXILIARADMINISTRATIVO;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Tienes que ingresar un numero");
                        sc.nextLine();
                        continue;
                    }
                    switch (opcion) {
                        case 1:
                            System.out.println("Filtrado por Socio");
                            usuarios = filtrarPorTipoUsuario(usuarios, TipoUsuario.SOCIO);
                            break;
                        case 2:
                            System.out.println("Filtrado por No Socio");
                            usuarios = filtrarPorTipoUsuario(usuarios, TipoUsuario.NOSOCIO);
                            break;
                        case 3:
                            System.out.println("Filtrado por AuxiliarAdministrador");
                            usuarios = filtrarPorTipoUsuario(usuarios, TipoUsuario.AUXILIARADMINISTRATIVO);
                            break;
                        default:
                            System.out.println("Opcion invalida");
                            continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por tipo de usuario: " + tipoUsuarioTemp.toString().toLowerCase() + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        menuModificarDatos(usuarios);
                    }
                    continue;
                case 5:
                    System.out.println("Seleccione un estado");
                    System.out.println("1. Activo");
                    System.out.println("2. Inactivo");
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion < 1 || opcion > 2) {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                        if (opcion == 1) {
                            System.out.println("Filtrado por estado activo");
                            usuarios = filtrarPorEstado(usuarios, true);
                        }
                        if (opcion == 2) {
                            System.out.println("Filtrado por estado inactivo");
                            usuarios = filtrarPorEstado(usuarios, false);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Tienes que ingresar un numero");
                        sc.nextLine();
                        continue;
                    }
                    System.out.println("----- Lista de Usuarios filtrados por estado: " + opcion + " -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        menuModificarDatos(usuarios);
                    }
                    continue;
                case 6:
                    usuarios = filtrarPorEstado(usuarios, true);
                    System.out.println("----- Lista de Usuarios filtrados por estado activo -----");
                    mostrarUsuariosListados(usuarios);
                    System.out.println("Quiere filtrar otra vez? (S/N)");
                    opcionFiltro = sc.nextLine();
                    if (opcionFiltro.equals("S")) {
                        continue;
                    }
                    if (opcionFiltro.equals("N")) {
                        menuModificarDatos(usuarios);
                    }
                    continue;
                case 7:
                    System.out.println("saliendo...");
                    bandera = false;
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

    }

    private void menuModificarDatos(List<Usuario> usuarios){
        Usuario usuario = null;
        System.out.println("Ingrese un documento para seleccionar Usuario");
        String documento = sc.nextLine();
        for (Usuario usu : usuarios){
            if (usu.getDocumento().equals(documento)){
                usuario = usu;
            }
        }
        if (usuario == null){
            System.out.println("El documento que ingresaste no corresponde a ningun usuario");
        }
        if (usuario != null){
            System.out.println("Ha seleccionado al Usuario: ");
            System.out.println(usuario.mostrarUsuarioGenerico());
            if (usuario.getTipoUsuario() == TipoUsuario.SOCIO){
                System.out.println("Seguro que quiere cambiar este usuario a No Socio?");
                System.out.println("1. Si 2. No");
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion == 1){
                        usuario.setTipoUsuario(TipoUsuario.NOSOCIO);
                        usuarioDAO.modificar(usuario);
                    }
                }catch (InputMismatchException e){
                    System.out.println("La opcion debe ser un numero");
                }
            }
            else if (usuario.getTipoUsuario() == TipoUsuario.NOSOCIO){
                System.out.println("Segurio que quiere cambiar este usuario a Socio?");
                System.out.println("1. Si 2. No");
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion == 1){
                        usuario.setTipoUsuario(TipoUsuario.SOCIO);
                        do {
                            System.out.println("Seleccione su categoria de socio");
                            List<CategoriaSocio> categorias = categoriaSocioDAO.listarCategorias();
                            for (CategoriaSocio categoriaSocio : categorias) {
                                System.out.println(categoriaSocio);
                            }
                            try {
                                opcion = sc.nextInt();
                                sc.nextLine();
                                if (opcion < 1 || opcion > categorias.size()) {
                                    System.out.println("Opcion invalida");
                                    continue;
                                }
                                usuario.setCategoriaSocio(categorias.get(opcion - 1));
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("la opcion tiene que ser un numero");
                                sc.nextLine();
                            }
                        } while (true);
                        do {
                            System.out.println("Tiene dificultad auditiva S/N");
                            String dificultadAuditiva = sc.nextLine();
                            if (dificultadAuditiva.equals("S")) {
                                usuario.setDificultadAuditiva(true);
                            } else if (dificultadAuditiva.equals("N")) {
                                usuario.setDificultadAuditiva(false);
                            } else {
                                System.out.println("Opcion invalida");
                                continue;
                            }
                            break;
                        } while (true);
                        do {
                            System.out.println("Tiene lenguaje señas S/N");
                            String lenguajeSeñas = sc.nextLine();
                            if (lenguajeSeñas.equals("S")) {
                                usuario.setLenguajeSeñas(true);
                            } else if (lenguajeSeñas.equals("N")) {
                                usuario.setLenguajeSeñas(false);
                            } else {
                                System.out.println("Opcion invalida");
                                continue;
                            }
                            break;
                        } while (true);
                        do {
                            System.out.println("Participa en subcomision S/N");
                            String participaSubcomision = sc.nextLine();
                            if (participaSubcomision.equals("S")) {
                                usuario.setParticipaSubcomision(true);
                            } else if (participaSubcomision.equals("N")) {
                                usuario.setParticipaSubcomision(false);
                            } else {
                                System.out.println("Opcion invalida");
                                continue;
                            }
                            break;
                        } while (true);
                        if (usuario.isParticipaSubcomision()) {
                            do {
                                System.out.println("Ingrese su subcomision");
                                List<Subcomision> subcomisiones = subcomisionDAO.listarSubcomisiones();
                                for (Subcomision subcomision : subcomisiones) {
                                    System.out.println(subcomision);
                                }
                                try {
                                    opcion = sc.nextInt();
                                    sc.nextLine();
                                    if (opcion < 1 || opcion > subcomisiones.size()) {
                                        System.out.println("Opcion invalida");
                                        continue;
                                    }
                                    usuario.setSubcomision(subcomisiones.get(opcion - 1));
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("la opcion tiene que ser un numero");
                                    sc.nextLine();
                                }
                            }while (true);
                            }

                        usuarioDAO.modificar(usuario);
                    }
                }catch (InputMismatchException e){
                    System.out.println("La opcion debe ser un numero");
                }
            }

        }
    }


    private void activarUsuario(){
        boolean bandera = true;
        int opcion;
        while (bandera){
            System.out.print("""
                ----- Activar Usuario en el sistema -----
                1. Listar Usuarios
                2. Salir
                """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 2){
                    System.out.println("Opcion invalida");
                    continue;
                }
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }
            switch (opcion){
                case 1:
                    listarUsuariosActivar();
                    continue;
                case 2:
                    System.out.println("saliendo");
                    bandera = false;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida");
                    break;
            }


        }

    }

    public void listarUsuariosActivar(){
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        usuarios = filtrarPorEstado(usuarios, false);
        System.out.println("Usuarios pendientes para activar:");
        mostrarUsuariosListados(usuarios);
        do {
            Usuario usuario = null;
            System.out.println("Seleccione un Usuario ingresando su documento");
            String documento = sc.nextLine();
            for (Usuario usu : usuarios){
                if (usu.getDocumento().equals(documento)){
                    usuario = usu;
                }
            }
            if (usuario == null){
                System.out.println("El documento que ingreso es invalido");
                continue;
            }
            System.out.println("El usuario seleccionado es: " + usuario.getNombre() + " " + usuario.getApellido() + " - " + usuario.getTipoUsuario().toString().toLowerCase());
            System.out.println("¿Quiere activar este Usuario? S/N");
            String opcion = sc.nextLine();
            if (opcion.toLowerCase().equals("s")){
                usuario.setActivo(true);
                usuarioDAO.activar(usuario);
            }
            break;


        }while (true);
    }

    private void bajaUsuario(){
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        usuarios = filtrarPorEstado(usuarios, true);
        System.out.println("Usuarios Activos:");
        mostrarUsuariosListados(usuarios);
        do {
            Usuario usuario = null;
            System.out.println("Seleccione un Usuario ingresando su documento");
            String documento = sc.nextLine();
            for (Usuario usu : usuarios){
                if (usu.getDocumento().equals(documento)){
                    usuario = usu;
                }
            }
            if (usuario == null){
                System.out.println("El documento que ingreso es invalido");
                continue;
            }
            System.out.println("El usuario seleccionado es: " + usuario.getNombre() + " " + usuario.getApellido() + " - " + usuario.getTipoUsuario().toString().toLowerCase());
            System.out.println("¿Quiere dar de baja a este Usuario? S/N");
            String opcion = sc.nextLine();
            if (opcion.toLowerCase().equals("s")){
                usuario.setActivo(false);
                usuarioDAO.eliminar(usuario);
            }
            break;


        }while (true);

    }


    public void gestionUsuarioNoSocioMenu(Usuario usuarioActual) {
        boolean bandera = true;
        int opcion;
        while (bandera){
            System.out.print("""
                    ----- Gestion de Usuario -----
                    1. Modificar Datos de Usuario
                    2. Modificar Telefonos
                    3. Salir
                    """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 3){
                    System.out.println("Opcion invalida");
                    continue;
                }
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }
            switch (opcion){
                case 1:
                    modificarDatosPropios(usuarioActual);
                    continue;
                case 2:
                    modificarTelefonos(usuarioActual);
                    continue;
                case 3:
                    System.out.println("saliendo");
                    bandera = false;
                    break;
                default:
                    System.out.println("Seleccione una opcion valida");
                    break;

            }
        }
    }

    private void modificarDatosPropios(Usuario usuarioActual){
        do {
            System.out.println("Desea cambiar su nombre?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese su nuevo nombre");
                    String nombre = sc.nextLine();
                    if (!validarNombreApellido(nombre)){
                        System.out.println("El nombre ingresado no es válido");
                        continue;
                    }
                    usuarioActual.setNombre(nombre);
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
            }
            break;
        }while (true);
        do {
            System.out.println("Desea cambiar su apellido?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese su nuevo apellido");
                    String apellido = sc.nextLine();
                    if (!validarNombreApellido(apellido)){
                        System.out.println("El apellido ingresado no es válido");
                        continue;
                    }
                    usuarioActual.setApellido(apellido);
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
            }
            break;
        }while (true);
        do {
            System.out.println("Desea cambiar su contraseña?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese su nueva contraseña");
                    String contraseña = sc.nextLine();
                    if (!validarContraseña(contraseña)){
                        System.out.println("La contraseña ingresada no es válida");
                        continue;
                    }
                    contraseña = hashPassword(contraseña);
                    usuarioActual.setContraseña(contraseña);
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
            }
            break;
        }while (true);
        do {
            System.out.println("Desea cambiar su fecha de nacimiento?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese su nueva fecha de nacimiento");
                    String fechaNacimiento = sc.nextLine();
                    if (validarFechaNacimiento(fechaNacimiento) == null){
                        System.out.println("La fecha de nacimiento no es válida");
                        continue;
                    }
                    usuarioActual.setFechaNacimiento(validarFechaNacimiento(fechaNacimiento));
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
            }
            break;
        }while (true);
        if (usuarioActual.getTipoUsuario().equals(TipoUsuario.SOCIO)){
            do {
                System.out.println("Desea cambiar su dificultad auditiva?");
                System.out.println("1. Si 2. No");
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion == 1){
                        System.out.println("Ingrese su nueva dificultad auditiva");
                        String dificultadAuditiva = sc.nextLine();
                        if (dificultadAuditiva.equalsIgnoreCase("S")){
                            usuarioActual.setDificultadAuditiva(true);
                        }else if (dificultadAuditiva.equalsIgnoreCase("N")){
                            usuarioActual.setDificultadAuditiva(false);
                        }else {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                    }
                }catch (InputMismatchException e){
                    System.out.println("La opcion debe ser un numero");
                }
                break;
            }while (true);
            do {
                System.out.println("Desea cambiar su lenguaje señas?");
                System.out.println("1. Si 2. No");
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion == 1){
                        System.out.println("Ingrese su nueva lenguaje señas");
                        String lenguajeSeñas = sc.nextLine();
                        if (lenguajeSeñas.equalsIgnoreCase("S")){
                            usuarioActual.setLenguajeSeñas(true);
                        }else if (lenguajeSeñas.equalsIgnoreCase("N")){
                            usuarioActual.setLenguajeSeñas(false);
                        }else {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                    }
                }catch (InputMismatchException e){
                    System.out.println("La opcion debe ser un numero");
                }
                break;
            }while (true);
        }
        do {
            System.out.println("Desea cambiar su domicilio?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese su nuevo domicilio");
                    String domicilio = sc.nextLine();
                    if (!validarDomicilio(domicilio)){
                        System.out.println("El domicilio ingresado no es válido");
                        continue;
                    }
                    usuarioActual.setDomicilio(domicilio);
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
            }
            break;
        }while (true);
        do {
            System.out.println("Confirma que desea cambiar sus datos");
            System.out.println("Los datos nuevos son: ");
            if (usuarioActual.getTipoUsuario().equals(TipoUsuario.SOCIO)){
                System.out.println(usuarioActual.confirmacionSocio());
            }
            if (usuarioActual.getTipoUsuario().equals(TipoUsuario.NOSOCIO)){
                System.out.println(usuarioActual.confirmacionUsuario());
            }
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    usuarioDAO.modificarDatosPropios(usuarioActual);
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
            }
            break;
        }while (true);

    }

    private void modificarTelefonos(Usuario usuarioActual){
        int opcion;
        do {
            System.out.print("""
                    ----- Modificar Telefonos -----
                    1. Agregar Telefono
                    2. Eliminar Telefono
                    3. Salir
                    """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 3){
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion){
                    case 1:
                        System.out.println("Ingrese el telofono que desea agregar");
                        String numero = sc.nextLine();
                        if (!validarTelefono(numero)){
                            System.out.println("El numero ingresado no es válido");
                            continue;
                        }
                        usuarioDAO.insertarTelefono(usuarioActual.getDocumento(), numero);
                        usuarioActual.agregarTelefono(numero);
                        break;
                    case 2:
                        System.out.println("Seleccione el numero que desea eliminar");
                        for (String numeroTelefono : usuarioActual.getTelefonos()){
                            System.out.println(numeroTelefono);
                        }
                        try {
                            numero = sc.nextLine();
                            if (!validarTelefonoEliminar(numero, usuarioActual.getDocumento())){
                                System.out.println("El numero ingresado no es válido");
                                continue;
                            }
                            if (!usuarioActual.getTelefonos().contains(numero)){
                                System.out.println("El numero no existe");
                                continue;
                            }
                            usuarioDAO.eliminarTelefono(usuarioActual.getDocumento(), numero);
                            usuarioActual.getTelefonos().remove(numero);
                        }catch (InputMismatchException e){
                            System.out.println("El numero ingresado no es válido");
                            continue;
                        }
                        break;
                    case 3:
                        System.out.println("saliendo");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        continue;
                }
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }
            break;
        }while (true);

    }


}

