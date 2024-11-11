package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.impl.CategoriaSocioDAOImpl;
import com.utec.pdtasur.dao.impl.SubcomisionDAOImpl;
import com.utec.pdtasur.dao.impl.UsuarioDAOImpl;
import com.utec.pdtasur.dao.interfaces.CategoriaSocioDAO;
import com.utec.pdtasur.dao.interfaces.SubcomisionDAO;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.*;

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
        }while (select) ;

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
        }while (true);
        do {
            System.out.println("Ingrese su apellido");
            String apellido = sc.nextLine();
            if (!validarNombreApellido(apellido)) {
                System.out.println("El apellido ingresado no es válido");
                continue;
            }
            usuario.setApellido(apellido);
            break;
        }while (true);
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
            }catch (InputMismatchException e){
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Ingrese su documento");
            String documento = sc.nextLine();
            if (usuario.getTipoDocumento().equals(TipoDocumento.CEDULA)){
                if (!validarDocumento(documento)) {
                    System.out.println("El documento ingresado no es válido");
                    continue;
                }
            }
            if (!usuario.getTipoDocumento().equals(TipoDocumento.CEDULA));
            usuario.setDocumento(documento);
            break;
        }while (true);
        do {
            System.out.println("Ingrese su domicilio");
            String domicilio = sc.nextLine();
            if (!validarDomicilio(domicilio)) {
                System.out.println("El domicilio ingresado no es válido");
                continue;
            }
            usuario.setDomicilio(domicilio);
            break;
        }while (true);
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
            }catch (InputMismatchException e){
                System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
                sc.nextLine();
            }

        }while (true);
        do {
            List<Integer> telefonos = new ArrayList<>();
            boolean telefonosContinuar = true;
            System.out.println("Ingrese sus telefonos");
            while (telefonosContinuar) {
                String telefono = sc.nextLine();
                if (validarTelefono(telefono)) {
                    telefonos.add(Integer.parseInt(telefono));
                    System.out.println("Telefono agregado");
                }else {
                    System.out.println("El telefono ingresado no es válido");
                    continue;
                }
                System.out.println("Quiere agregar otro telefono? (S/N)");
                String opcion = sc.nextLine();
                if (opcion.equals("S")) {
                    continue;
                }else {
                    telefonosContinuar = false;
                }
            }
            usuario.setTelefonos(telefonos);
            break;
        }while (true);
        do {
            System.out.println("Ingrese su email");
            String email = sc.nextLine();
            if (!validarEmail(email)) {
                System.out.println("El email ingresado no es válido");
                continue;
            }
            usuario.setEmail(email);
            break;
        }while (true);
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
        }while (true);
        do {
            System.out.println("Seleccione su categoria de socio");
            List<CategoriaSocio> categorias = categoriaSocioDAO.listarCategorias();
            for (CategoriaSocio categoriaSocio : categorias){
                System.out.println(categoriaSocio);
            }
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > categorias.size()){
                    System.out.println("Opcion invalida");
                    continue;
                }
                usuario.setCategoriaSocio(categorias.get(opcion-1));
                break;
            }catch (InputMismatchException e){
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Tiene dificultad auditiva S/N");
            String dificultadAuditiva = sc.nextLine();
            if (dificultadAuditiva.equals("S")){
                usuario.setDificultadAuditiva(true);
            }else if (dificultadAuditiva.equals("N")){
                usuario.setDificultadAuditiva(false);
            }else{
                System.out.println("Opcion invalida");
                continue;
            }
            break;
        }while (true);
        do {
            System.out.println("Tiene lenguaje señas S/N");
            String lenguajeSeñas = sc.nextLine();
            if (lenguajeSeñas.equals("S")){
                usuario.setLenguajeSeñas(true);
            }else if (lenguajeSeñas.equals("N")){
                usuario.setLenguajeSeñas(false);
            }else{
                System.out.println("Opcion invalida");
                continue;
            }
            break;
        }while (true);
        do {
            System.out.println("Participa en subcomision S/N");
            String participaSubcomision = sc.nextLine();
            if (participaSubcomision.equals("S")){
                usuario.setParticipaSubcomision(true);
            }else if (participaSubcomision.equals("N")){
                usuario.setParticipaSubcomision(false);
            }else{
                System.out.println("Opcion invalida");
                continue;
            }
            break;
        }while (true);
        if (usuario.isParticipaSubcomision()){
            do {
                System.out.println("Ingrese su subcomision");
                List<Subcomision> subcomisiones = subcomisionDAO.listarSubcomisiones();
                for (Subcomision subcomision : subcomisiones){
                    System.out.println(subcomision);
                }
                try {
                    int opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion < 1 || opcion > subcomisiones.size()){
                        System.out.println("Opcion invalida");
                        continue;
                    }
                    usuario.setSubcomision(subcomisiones.get(opcion-1));
                    break;
                }catch (InputMismatchException e){
                    System.out.println("la opcion tiene que ser un numero");
                    sc.nextLine();
                }
            }while (true);
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
                if (opcion == 1){
                    usuarioDAO.registrarSocio(usuario);
                    break;
                }
                if (opcion == 2){
                    break;
                }

            }catch (InputMismatchException e){
                System.out.println("la opcion tiene que ser un numero");
                sc.nextLine();
            }
        }while (true);




    }

    private void registroNoSocio() {

    }

    private void registroAdmin() {

    }

    public void login() {

    }

    public void listarCategorias(){
        List<CategoriaSocio> categorias = categoriaSocioDAO.listarCategorias();
        for (CategoriaSocio categoriaSocio : categorias){
            System.out.println(categoriaSocio);
        }
    }

    public void listarSubcomisiones(){
        List<Subcomision> subcomisiones = subcomisionDAO.listarSubcomisiones();
        for (Subcomision subcomision : subcomisiones){
            System.out.println(subcomision);
        }
    }







}
