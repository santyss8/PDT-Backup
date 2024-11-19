package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.impl.TipoActividadDAOImpl;
import com.utec.pdtasur.models.TipoActividad;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



public class TipoActividadService {
    Scanner sc;
    TipoActividadDAOImpl tipoActividadDAO;
    ValidarService validarService;

    public TipoActividadService() throws Exception {
        this.sc = new Scanner(System.in);
        this.tipoActividadDAO = new TipoActividadDAOImpl();
        this.validarService = new ValidarService();
    }

    public void gestionTipoActividadMenu(){
        do {
            System.out.print("""
                ----- Gestion de Tipo de Actividad -----
                1. Ingresar Tipo de Actividad
                2. Modificar Tipo de Actividad
                3. Dar de baja Tipo de Actividad
                4. Activar Tipo de Actividad
                5. Salir
                """);
            int opcion = 0;
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 5){
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion){
                    case 1:
                        ingresarTipoActividad();
                        continue;
                    case 2:
                        modificarTipoActividad();
                        continue;
                    case 3:
                        darDeBajaTipoActividad();
                        continue;
                    case 4:
                        activarTipoActividad();
                        continue;
                    case 5:
                        System.out.println("saliendo");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        continue;
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
            }
        }while (true);

    }

    public static void main(String[] args) throws Exception {
        TipoActividadService tipoActividadService = new TipoActividadService();
        tipoActividadService.gestionTipoActividadMenu();
    }

    private void activarTipoActividad() {
        List<TipoActividad> tiposActividad = listarTiposActividadInActivos();
        TipoActividad tipoActividadActivar = null;
        if (tiposActividad.isEmpty()){
            System.out.println("No hay Tipos de Actividad Activos");
            return;
        }
        do {
            System.out.println("----- Lista de Tipos de Actividad Activos -----");
            for (TipoActividad tipoActividad : tiposActividad){
                if (!tipoActividad.isEstado()){
                    System.out.println(tipoActividad);
                }
            }
            System.out.println("Seleccione el Tipo de Actividad que desea activar ingresando su id");
            try {
                int id = sc.nextInt();
                sc.nextLine();
                for (TipoActividad tipoActividad : tiposActividad){
                    if (tipoActividad.getId() == id){
                        tipoActividadActivar = tipoActividad;
                        break;
                    }
                }
                if (tipoActividadActivar == null){
                    System.out.println("El id ingresado no existe");
                    continue;
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Desea activar el Tipo de Actividad " + tipoActividadActivar.getNombre() + "?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    tipoActividadDAO.activarTipoActividad(tipoActividadActivar);
                    break;
                }
                if (opcion == 2) {
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);

    }

    private List<TipoActividad> listarTiposActividadActivos(){
        List<TipoActividad> tiposActividad = tipoActividadDAO.listarTiposActividad();
        List<TipoActividad> tiposActividadActivos = new ArrayList<>();
        for (TipoActividad tipoActividad : tiposActividad){
            if (tipoActividad.isEstado()){
                tiposActividadActivos.add(tipoActividad);
            }
        }
        return tiposActividadActivos;
    }

    private List<TipoActividad> listarTiposActividadInActivos(){
        List<TipoActividad> tiposActividad = tipoActividadDAO.listarTiposActividad();
        List<TipoActividad> tiposActividadInActivos = new ArrayList<>();
        for (TipoActividad tipoActividad : tiposActividad){
            if (!tipoActividad.isEstado()){
                tiposActividadInActivos.add(tipoActividad);
            }
        }
        return tiposActividadInActivos;
    }

    private void darDeBajaTipoActividad() {
        List<TipoActividad> tiposActividad = listarTiposActividadActivos();
        TipoActividad tipoActividadBaja = null;
        if (tiposActividad.isEmpty()){
            System.out.println("No hay Tipos de Actividad Activos");
            return;
        }
        do {
            System.out.println("----- Lista de Tipos de Actividad Inactivos -----");
            for (TipoActividad tipoActividad : tiposActividad){
                if (tipoActividad.isEstado()){
                    System.out.println(tipoActividad);
                }
            }
            System.out.println("Seleccione el Tipo de Actividad que desea dar de baja ingresando su id");
            try {
                int id = sc.nextInt();
                sc.nextLine();
                for (TipoActividad tipoActividad : tiposActividad){
                    if (tipoActividad.getId() == id){
                        tipoActividadBaja = tipoActividad;
                        break;
                    }
                }
                if (tipoActividadBaja == null){
                    System.out.println("El id ingresado no existe");
                    continue;
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Ingrese la razon de baja del Tipo de Actividad");
            String razonBaja = sc.nextLine();
            if (!razonBaja.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")){
                System.out.println("La razon de baja ingresada no es válida");
                continue;
            }
            tipoActividadBaja.setRazonBaja(razonBaja);
            break;
        }while (true);
        do {
            System.out.println("Ingrese los comentarios de baja del Tipo de Actividad");
            String comentariosBaja = sc.nextLine();
            if (!comentariosBaja.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")){
                System.out.println("Los comentarios de baja ingresados no es válidos");
                continue;
            }
            tipoActividadBaja.setComentariosBaja(comentariosBaja);
            break;
        }while (true);
        do {
            System.out.println("Ingrese el documento del usuario que baja el Tipo de Actividad");
            String documentoUsuarioBaja = sc.nextLine();
            tipoActividadBaja.setDocumentoUsuarioBaja(documentoUsuarioBaja);
            break;
        }while (true);
        do {
            System.out.println("Desea dar de baja el Tipo de Actividad " + tipoActividadBaja.getNombre() + " con el documento de usuario " + tipoActividadBaja.getDocumentoUsuarioBaja() + "?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    tipoActividadDAO.eliminarTipoActividad(tipoActividadBaja);
                    break;
                }
                if (opcion == 2) {
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);

    }

    private void modificarTipoActividad() {
        List<TipoActividad> tiposActividad = tipoActividadDAO.listarTiposActividad();
        TipoActividad tipoActividadModificar = null;
        do {
            System.out.println("Seleccione el Tipo de Actividad que desea modificar ingreando su id");
            int id = 0;
            for (TipoActividad tipoActividad : tiposActividad){
                System.out.println(tipoActividad);
            }
            try {
                id = sc.nextInt();
                sc.nextLine();
                for (TipoActividad tipoActividad : tiposActividad){
                    if (tipoActividad.getId() == id){
                        tipoActividadModificar = tipoActividad;
                        break;
                    }
                }
                if (tipoActividadModificar == null){
                    System.out.println("El id ingresado no existe");
                    continue;
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Ingrese la nueva descripcion del Tipo de Actividad");
            String descripcion = sc.nextLine();
            if (!descripcion.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")){
                System.out.println("La descripcion ingresada no es válida");
                continue;
            }
            tipoActividadModificar.setDescripcion(descripcion);
            break;
        }while (true);
        do {
            System.out.println("Desea Modificar el Tipo de Actividad " + tipoActividadModificar.getNombre() + "?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    tipoActividadDAO.actualizarTipoActividad(tipoActividadModificar);
                    break;
                }
                if (opcion == 2) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);


    }

    private void ingresarTipoActividad() {
        TipoActividad tipoActividad = new TipoActividad();
        do {
            System.out.println("Ingrese el nombre del Tipo de Actividad");
            String nombre = sc.nextLine();
            if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$")){
                System.out.println("El nombre ingresado no es válido");
                continue;
            }
            tipoActividad.setNombre(nombre);
            break;
        }while (true);
        do {
            System.out.println("Ingrese la descripcion del Tipo de Actividad");
            String descripcion = sc.nextLine();
            if (!descripcion.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")){
                System.out.println("La descripcion ingresada no es válida");
                continue;
            }
            tipoActividad.setDescripcion(descripcion);
            break;
        }while (true);
        do {
            System.out.println("Desea registrar el tipo de actividad " + tipoActividad.getNombre() + "?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    tipoActividadDAO.insertarTipoActividad(tipoActividad);
                    break;
                }
                if (opcion == 2){
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);

    }
}
