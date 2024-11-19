package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.impl.EspacioDAOImpl;
import com.utec.pdtasur.dao.impl.PagoDAOImpl;
import com.utec.pdtasur.dao.impl.UsuarioDAOImpl;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;
import com.utec.pdtasur.models.Espacio;
import com.utec.pdtasur.models.Pago;
import com.utec.pdtasur.models.Usuario;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PagoService {
    PagoDAOImpl pagoDAO = new PagoDAOImpl();
    UsuarioDAO usuarioDAO;
    EspacioDAOImpl espacioDAO;
    Scanner sc = new Scanner(System.in);

    public PagoService() throws Exception {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.espacioDAO = new EspacioDAOImpl();
    }

    public void gestionPagoMenu() throws SQLException {
        do {
            System.out.print("""
                    ----- Gestion de Pagos -----
                    1. Ingresar Pago
                    2. Modificar Pago
                    3. Salir
                    """);
            int opcion = 0;
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion < 1 || opcion > 3){
                    System.out.println("Opcion invalida");
                    continue;
                }
                switch (opcion){
                    case 1:
                        ingresarPago();
                        continue;
                    case 2:
                        modificarPago();
                        continue;
                    case 3:
                        System.out.println("Saliendo...");
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

    private void ingresarPago() throws SQLException {
        Pago pago = new Pago();
        Usuario usuarioPago = null;
        do {
            System.out.println("Ingrese el documento del usuario que pago");
            String documento = sc.nextLine();
            usuarioPago = usuarioDAO.obtenerUsuario(documento);
            if (usuarioPago == null){
                System.out.println("El documento ingresado no es valido");
                continue;
            }
            pago.setUsuario(usuarioPago);
            break;
        }while (true);
        do {
            System.out.println("Desea pagar cuota?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    pago.setEsCuota(true);
                    break;
                }
                if (opcion == 2) {
                    pago.setEsCuota(false);
                    break;
                }
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);
        if (pago.isEsCuota()){
            cobrar(pago);
        }
        if (!pago.isEsCuota()){
            do {
                System.out.println("Seleccione el espacio al que pago ingresando el id");
                int id = 0;
                List<Espacio> espacios = espacioDAO.listarEspacios();
                for (Espacio espacio : espacios){
                    System.out.println(espacio);
                }
                try {
                 id = sc.nextInt();
                 sc.nextLine();
                 for (Espacio espacio : espacios){
                     if (espacio.getId() == id){
                         pago.setEspacio(espacio);
                         break;
                     }
                 }
                 if (pago.getEspacio() == null){
                     System.out.println("El id ingresado no existe");
                     continue;
                 }
                 break;
                }catch (InputMismatchException e){
                    System.out.println("La opcion debe ser un numero");
                    sc.nextLine();
                }
            }while (true);
            cobrar(pago);

        }

    }


    private void cobrar(Pago pago){
        do {
            System.out.println("Ingrese el monto a pagar");
            try {
                double monto = sc.nextDouble();
                sc.nextLine();
                pago.setMonto(monto);
                break;
            }catch (InputMismatchException e){
                System.out.println("El monto ingresado no es valido");
                sc.nextLine();
            }
        }while (true);
        do {
            System.out.println("Ingrese la forma de cobro");
            String formaCobro = sc.nextLine();
            if (!formaCobro.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")){
                System.out.println("La forma de cobro ingresada no es valido");
                continue;
            }
            pago.setFormaCobro(formaCobro);
            break;
        }while (true);
        do {
            System.out.println("Desea realizar el pago?");
            System.out.println("1. Si 2. No");
            try {
                int opcion = sc.nextInt();
                sc.nextLine();
                if (opcion == 1) {
                    pagoDAO.insertarPago(pago);
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("La opcion debe ser un numero");
                sc.nextLine();
            }
        }while (true);

    }

    private void modificarPago() throws SQLException {
        List<Pago> pagos = pagoDAO.listarPagos();
        Pago pago = null;
        System.out.println("Ingrese el documento del usuario:");
        String documento = sc.nextLine();
        Usuario usuario = usuarioDAO.obtenerUsuario(documento);
        if (usuario == null) {
            System.out.println("No existe el usuario con ese documento");
            return;
        }
        for (Pago p : pagos) {
            if (p.getUsuario().getDocumento().equals(documento)) {
                System.out.println(p);
            }
        }
        System.out.println("Seleccione el pago a modificar");
        try {
            int id = sc.nextInt();
            sc.nextLine();
            for (Pago p : pagos) {
                if (p.getId() == id) {
                    pago = p;
                    break;
                }
            }
            pago.setId(id);
            pago.setUsuario(usuario);
        } catch (InputMismatchException e) {
            System.out.println("El id ingresado no es valido");
            sc.nextLine();
        }
            if (pago == null) {
                System.out.println("El pago no existe");
                return;
            }
            System.out.println("Paga cuota?");
            System.out.println("1. Si 2. No");
            int opcion = sc.nextInt();
            sc.nextLine();
            if (opcion == 1) {
                pago.setEsCuota(true);
            } else {
                pago.setEsCuota(false);
            }
            if (pago.isEsCuota()) {
                do {
                    System.out.println("Ingrese el monto a pagar");
                    try {
                        double monto = sc.nextDouble();
                        sc.nextLine();
                        pago.setMonto(monto);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("El monto ingresado no es valido");
                        sc.nextLine();
                    }
                } while (true);
                do {
                    System.out.println("Ingrese la forma de cobro");
                    String formaCobro = sc.nextLine();
                    if (!formaCobro.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")) {
                        System.out.println("La forma de cobro ingresada no es valido");
                        continue;
                    }
                    pago.setFormaCobro(formaCobro);
                    break;
                } while (true);
                do {
                    System.out.println("Desea realizar el pago?");
                    System.out.println("1. Si 2. No");
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion == 1) {
                            pagoDAO.modificarPago(pago);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("La opcion debe ser un numero");
                        sc.nextLine();
                    }
                } while (true);
            }
            if (!pago.isEsCuota()) {
                do {
                    System.out.println("Seleccione el espacio al que pago ingresando el id");
                    int id = 0;
                    List<Espacio> espacios = espacioDAO.listarEspacios();
                    for (Espacio espacio : espacios) {
                        System.out.println(espacio);
                    }
                    try {
                        id = sc.nextInt();
                        sc.nextLine();
                        for (Espacio espacio : espacios) {
                            if (espacio.getId() == id) {
                                pago.setEspacio(espacio);
                                break;
                            }
                        }
                        if (pago.getEspacio() == null) {
                            System.out.println("El id ingresado no existe");
                            continue;
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("La opcion debe ser un numero");
                        sc.nextLine();
                    }
                } while (true);
                do {
                    System.out.println("Ingrese el monto a pagar");
                    try {
                        double monto = sc.nextDouble();
                        sc.nextLine();
                        pago.setMonto(monto);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("El monto ingresado no es valido");
                        sc.nextLine();
                    }
                } while (true);
                do {
                    System.out.println("Ingrese la forma de cobro");
                    String formaCobro = sc.nextLine();
                    if (!formaCobro.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9\\s.,;()\\-]+$")) {
                        System.out.println("La forma de cobro ingresada no es valido");
                        continue;
                    }
                    pago.setFormaCobro(formaCobro);
                    break;
                } while (true);
                do {
                    System.out.println("Desea realizar el pago?");
                    System.out.println("1. Si 2. No");
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion == 1) {
                            pagoDAO.modificarPago(pago);
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("La opcion debe ser un numero");
                        sc.nextLine();
                    }
                } while (true);

            }


        }
    }




