package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.impl.EspacioDAOImpl;
import com.utec.pdtasur.dao.impl.ReservaDAOImpl;
import com.utec.pdtasur.dao.impl.UsuarioDAOImpl;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.Espacio;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EspacioService {
    private UsuarioDAO usuarioDAO;
    private EspacioDAOImpl espacioDAO;
    private ReservaDAOImpl reservaDAO;
    Scanner sc;
    ValidarService validarService;

    public EspacioService() throws SQLException {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.espacioDAO = new EspacioDAOImpl();
        this.reservaDAO = new ReservaDAOImpl();
        this.sc = new Scanner(System.in);
        this.validarService = new ValidarService();
    }

    public void gestionEspacioAdminMenu() throws SQLException {
        do {
            System.out.print("""
                    ----- Gestion de Espacio -----
                    1. Crear Espacio
                    2. Listar Espacios
                    3. Modificar Datos de Espacio
                    4. Activar Espacio
                    5. Eliminar Espacio
                    6. Reservar Espacio
                    7. Reporte de Reservas
                    8. Salir
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 8) {
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        crearEspacio();
                        continue;
                    case 2:
                        listarEspacios();
                        continue;
                    case 3:
                        modificarEspacio();
                        continue;
                    case 4:
                        activarEspacio();
                        continue;
                    case 5:
                        eliminarEspacio();
                        continue;
                    case 6:
                        reservarEspacioMenu(); // TODO
                        continue;
                    case 7:
                        reporteReservasMenu(); // TODO
                        continue;
                    case 8:
                        System.out.println("saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
                continue;
            }
            break;
        }while (true);

    }

    private void crearEspacio() throws SQLException {
        boolean bandera = true;
        int opcion;
        Espacio espacio = new Espacio();
        do {
            System.out.println("Ingrese el nombre del espacio:");
            String nombre = sc.nextLine();
            if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,50}$")) {
                System.out.println("El nombre ingresado no es válido");
                continue;
            }
            espacio.setNombre(nombre);
            break;
        }while (true);
        do {
            System.out.println("Ingrese la capacidad máxima:");
            int capacidadMaxima;
            try {
                capacidadMaxima = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){
                System.out.println("La capacidad máxima debe ser un numero");
                sc.nextLine();
                continue;
            }
            if (capacidadMaxima < 0){
                System.out.println("La capacidad máxima no puede ser negativa");
                continue;
            }
            espacio.setCapacidadMaxima(capacidadMaxima);
            break;
        }while (true);
        do {
            System.out.println("Ingrese el precio para socios:");
            double precioSocio;
            try {
                precioSocio = sc.nextDouble();
                sc.nextLine();
                if (precioSocio < 0){
                    System.out.println("El precio para socios no puede ser negativo");
                    sc.nextLine();
                    continue;
                }
                espacio.setPrecioReservaSocio(precioSocio);
                break;
            }catch (InputMismatchException e){
                System.out.println("El precio para socios debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Ingrese el precio para no socios:");
            double precioNoSocio;
            try {
                precioNoSocio = sc.nextDouble();
                sc.nextLine();
                if (precioNoSocio < 0){
                    System.out.println("El precio para no socios no puede ser negativo");
                    sc.nextLine();
                    continue;
                }
                espacio.setPrecioReservaNoSocio(precioNoSocio);
                break;
            }catch (InputMismatchException e){
                System.out.println("El precio para no socios debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Ingrese la fecha de vigencia de precios (AAAA-MM-DD):");
            String fechaVigencia = sc.nextLine();
            try {
                LocalDate fechaVigenciaLocal = LocalDate.parse(fechaVigencia);
                espacio.setFechaVigenciaPrecio(fechaVigenciaLocal);
                break;
            }catch (DateTimeParseException e){
                System.out.println("La fecha ingresada no es válida");
            }
        }while (true);
        do {
            System.out.println("Ingrese la observaciones (opcional):");
            String observaciones = sc.nextLine();
            if (observaciones.length() > 50){
                System.out.println("La longitud de las observaciones no puede ser mayor a 50");
                continue;
            }
            if (observaciones.isEmpty()){
                espacio.setObservaciones(null);
                break;
            }
            espacio.setObservaciones(observaciones);
            break;
        }while (true);
        do {
            System.out.println("Desea crear el espacio?");
            System.out.println("1. Si 2. No");
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    espacioDAO.insertarEspacio(espacio);
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);


    }


    private void listarEspacios() throws SQLException {
        boolean bandera = true;
        int opcion = 0;
        while (bandera){
            System.out.println("----- Lista de Espacios -----");
            List<Espacio> espacios = espacioDAO.listarEspacios();
            System.out.print("""
                    Como quiere filtrar los espacios?
                    1. Nombre
                    2. Estado
                    3. Por Defecto (Estado: Activo)
                    4. Salir
                    """);
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 4){
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion){
                    case 1:
                        System.out.println("Ingrese el nombre del espacio:");
                        String nombre = sc.nextLine();
                        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,50}$")) {
                            System.out.println("El nombre ingresado no es válido");
                            continue;
                        }
                        espacios = filtrarPorNombre(espacios, nombre);
                        System.out.println("----- Lista de Espacios filtrados por nombre: " + nombre + " -----");
                        mostrarEspaciosListados(espacios);
                        System.out.println("Quiere filtrar otra vez? (S/N)");
                        String opcionFiltro = sc.nextLine();
                        if (opcionFiltro.equalsIgnoreCase("S")){
                            continue;
                        }
                        if (opcionFiltro.equalsIgnoreCase("N")){
                            bandera = false;
                        }
                        break;
                    case 2:
                        System.out.println("Seleccione un estado");
                        System.out.println("1. Activo");
                        System.out.println("2. Inactivo");
                        try {
                            opcion = sc.nextInt();
                            sc.nextLine();
                            if (opcion < 1 || opcion > 2){
                                System.out.println("Opcion invalida");
                                continue;
                            }
                            if (opcion == 1){
                                System.out.println("Filtrado por estado activo");
                                espacios = filtrarPorEstado(espacios, true);
                            }
                            if (opcion == 2){
                                System.out.println("Filtrado por estado inactivo");
                                espacios = filtrarPorEstado(espacios, false);
                            }
                            System.out.println("----- Lista de Espacios filtrados por estado: " + opcion + " -----");
                            mostrarEspaciosListados(espacios);
                            System.out.println("Quiere filtrar otra vez? (S/N)");
                            opcionFiltro = sc.nextLine();
                            if (opcionFiltro.equalsIgnoreCase("S")){
                                continue;
                            }
                            if (opcionFiltro.equalsIgnoreCase("N")){
                                bandera = false;
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Tienes que ingresar un numero");
                            sc.nextLine();
                        }
                    case 3:
                        espacios = filtrarPorEstado(espacios, true);
                        System.out.println("----- Lista de Espacios filtrados por estado activo -----");
                        mostrarEspaciosListados(espacios);
                        System.out.println("Quiere filtrar otra vez? (S/N)");
                        opcionFiltro = sc.nextLine();
                        if (opcionFiltro.equalsIgnoreCase("S")){
                            continue;
                        }
                        if (opcionFiltro.equalsIgnoreCase("N")){
                            bandera = false;
                        }
                        break;
                    case 4:
                        System.out.println("saliendo...");
                        bandera = false;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
            }
        }

    }

    private List<Espacio> filtrarPorNombre(List<Espacio> espacios, String nombre){
        List<Espacio> resultado = new ArrayList<>();
        for (Espacio espacio : espacios){
            if (nombre == null || nombre.isEmpty() || espacio.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                resultado.add(espacio);
            }
        }
        return resultado;
    }

    private List<Espacio> filtrarPorEstado(List<Espacio> espacios, boolean estadoActivo){
        List<Espacio> resultado = new ArrayList<>();
        for (Espacio espacio : espacios){
            if (espacio.isActivo() == estadoActivo){
                resultado.add(espacio);
            }
        }
        return resultado;
    }

    private void mostrarEspaciosListados(List<Espacio> espacios){
        for (Espacio espacio : espacios){
            System.out.println(espacio);
        }
    }


    private void modificarEspacio() throws SQLException {
        List<Espacio> espacios = espacioDAO.listarEspacios();
        Espacio espacioModificar = null;
        do {
            System.out.print("""
                Como quiere filtrar los espacios?
                1. Por Nombre
                2. Por Estado
                3. Salir
                """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    System.out.println("Ingrese el nombre del espacio a filtrar");
                    String nombre = sc.nextLine();
                    espacios = filtrarPorNombre(espacios, nombre);
                } else if (opcion == 2) {
                    System.out.print("""
                        Ingrese el estado del espacio a filtrar
                        1. Activo
                        2. Inactivo
                        3. Salir
                        """);
                    try {
                        int opcionEstado = sc.nextInt();
                        sc.nextLine();
                        if (opcionEstado == 1) {
                            espacios = filtrarPorEstado(espacios, true);
                        } else if (opcionEstado == 2) {
                            espacios = filtrarPorEstado(espacios, false);
                        } else {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Error al filtrar por estado");
                        sc.nextLine();
                        continue;
                    }
                }
                else {
                    System.out.println("Opcion invalida");
                    continue;
                }
            }catch (Exception e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
                continue;
            }
            mostrarEspaciosListados(espacios);
            break;
        }while (true);
        do {
            System.out.println("Seleccione un Espacio para modificar ingresando su id");
            try {
                int id = sc.nextInt();
                sc.nextLine();
                for (Espacio espacio : espacios) {
                    if (espacio.getId() == id) {
                        espacioModificar = espacio;
                        break;
                    }
                }
                if (espacioModificar == null) {
                    System.out.println("Espacio no encontrado");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Error al seleccionar espacio");
                sc.nextLine();
                continue;
            }
            break;
        }while (true);
        do {
            System.out.println("Modificando espacio: " + espacioModificar.getNombre());
            System.out.print("""
                        Desea cambiar el nombre del espacio?
                        1. Si
                        2. No
                        """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    System.out.println("Ingrese el nuevo nombre del espacio");
                    String nombre = sc.nextLine();
                    espacioModificar.setNombre(nombre);
                    break;
                }else {
                    System.out.println("No se ha cambiado el nombre");
                    break;
                }
            }catch (Exception e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.print("""
                    Desea cambiar la capacidad Maxima?
                    1. Si
                    2. No
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    System.out.println("Ingrese la nueva capacidad maxima");
                    int capacidad;
                    try {
                        capacidad = sc.nextInt();
                        sc.nextLine();
                    }catch (InputMismatchException e){
                        System.out.println("La capacidad tiene que ser un numero");
                        sc.nextLine();
                        continue;
                    }
                    espacioModificar.setCapacidadMaxima(capacidad);
                    break;
                }else {
                    System.out.println("No se ha cambiado la capacidad maxima");
                    break;
                }
            }catch (Exception e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.print("""
                    Desea cambiar el precio para los Socios?
                    1. Si
                    2. No
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese el nuevo monto");
                    try {
                        double monto = sc.nextDouble();
                        sc.nextLine();
                        espacioModificar.setPrecioReservaSocio(monto);
                    }catch (InputMismatchException e){
                        System.out.println("La opcion debe ser un numero");
                        sc.nextLine();
                        continue;
                    }
                    break;
                } else {
                    System.out.println("No se cambia precio para los socios");
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("la opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.print("""
                    Desea cambiar el precio para los no Socios?
                    1. Si
                    2. No
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1){
                    System.out.println("Ingrese el nuevo monto");
                    try {
                        double monto = sc.nextDouble();
                        sc.nextLine();
                        espacioModificar.setPrecioReservaNoSocio(monto);
                    }catch (InputMismatchException e){
                        System.out.println("La opcion debe ser un numero");
                        sc.nextLine();
                        continue;
                    }
                    break;
                } else {
                    System.out.println("No se cambia precio para los no socios");
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("la opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.print("""
                Desea cambiar las observaciones del espacio?
                1. Si
                2. No
                """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    System.out.println("Ingrese la nueva observacion");
                    String observacion = sc.nextLine();
                    espacioModificar.setObservaciones(observacion);
                    break;
                }else {
                    System.out.println("No se ha cambiado la observacion");
                    break;
                }
            }catch (Exception e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.print("""
                    Desea aplicar los cambios?
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
                    espacioDAO.modificarEspacio(espacioModificar);
                    break;
                }else{
                    System.out.println("Espacio no modificado");
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
            }

        }while (true);



    }


    private void activarEspacio() throws SQLException {
        List<Espacio> espacios = espacioDAO.listarEspacios();
        espacios = filtrarPorEstado(espacios, false);
        System.out.println("Espacios Inactivos:");
        mostrarEspaciosListados(espacios);
        do {
            System.out.println("Seleccione un Espacio para activar ingresando su id");
            try {
                int id = sc.nextInt();
                sc.nextLine();
                if (espacios.stream().anyMatch(espacio -> espacio.getId() == id)) {
                    espacioDAO.activarEspacio(espacios.stream().filter(espacio -> espacio.getId() == id).findFirst().get());
                    break;
                }
                System.out.println("Espacio no encontrado");
                continue;
            } catch (Exception e) {
                System.out.println("Error al activar espacio");
                sc.nextLine();
            }
            break;
        } while (true);
    }


    private void eliminarEspacio() throws SQLException {
        List<Espacio> espacios = espacioDAO.listarEspacios();
        espacios = filtrarPorEstado(espacios, true);
        System.out.println("Espacios Activos:");
        mostrarEspaciosListados(espacios);
        do {
            System.out.println("Seleccione un Espacio para eliminar ingresando su id");
            try {
                int id = sc.nextInt();
                sc.nextLine();
                if (espacios.stream().anyMatch(espacio -> espacio.getId() == id)) {
                    Espacio espacioEliminar = espacios.stream().filter(espacio -> espacio.getId() == id).findFirst().get();
                    System.out.println("Confirme que desea eliminar el espacio " + espacioEliminar.getNombre());
                    System.out.println("1. Si");
                    System.out.println("2. No");
                    try {
                        int opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion == 1) {
                            espacioDAO.eliminarEspacio(espacioEliminar);
                            break;
                        }
                        break;
                    }
                    catch (Exception e) {
                        System.out.println("Error al eliminar espacio");
                        sc.nextLine();
                    }
                }
                System.out.println("El Espacio no existe");
                break;
            }catch (InputMismatchException e){
                System.out.println("Tienes que ingresar un numero");
                sc.nextLine();
            }
            break;
        }while (true);

    }


    private void reservarEspacioMenu(){
        do {
            System.out.print("""
                    1. Reservar Espacio
                    2. Cancelar Reserva
                    3. Confirmar Reserva
                    4. Salir
                    """);
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                switch (opcion){
                    case 1:
                        reservarEspacio();
                        continue;
                    case 2:
                        cancelarReserva();
                        continue;
                    case 3:
                        confirmarReserva();
                        continue;
                    case 4:
                        System.out.println("saliendo...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion tiene que ser un numero");
                sc.nextLine();
            }
        }while (true);
        

    }

    private void cancelarReserva() {
    }

    private void confirmarReserva() {
    }


    private void reservarEspacio() {
    }


    private void reporteReservasMenu(){

    }

    public void gestionEspacioMenu(){

    }
}
