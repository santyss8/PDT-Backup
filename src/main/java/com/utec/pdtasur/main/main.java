package com.utec.pdtasur.main;

import com.utec.pdtasur.models.*;
import com.utec.pdtasur.services.UsuarioService;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
    private static Scanner sc = new Scanner(System.in);
    private static Usuario usuarioActual;


    private static UsuarioService usuarioService;

    static {
        try {
            usuarioService = new UsuarioService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        boolean running = true;
        int opcion = 0;
        while (running) {
            // Usuario no ha iniciado sesion
            if (usuarioActual == null) {
                System.out.print("""
                        ----- Bienvenido al sistema ------
                        1. Registro de usuario
                        2. Iniciar sesion
                        3. Salir
                        Elija una opcion:
                        """);
                try {
                    opcion = sc.nextInt();
                    sc.nextLine();
                    if (opcion < 1 || opcion > 3) {
                        System.out.println("Opcion incorrecta");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("la opcion tiene que ser un numero");
                    sc.nextLine();
                }

                switch (opcion) {
                    case 1:
                        usuarioService.registrar();
                        break;
                    case 2:
                        usuarioActual = usuarioService.login();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
                System.out.println();
                // Sesion Iniciada
            } if (usuarioActual != null){
                // Menu de AuxiliarAdministrador
                if (usuarioActual.getTipoUsuario().equals(TipoUsuario.AUXILIARADMINISTRATIVO)){
                    System.out.print("""
                            ----- Bienvenido al sistema -----
                            1. Gestion de Usuario
                            2. Gestion de Espacio
                            3. Gestion de Tipo de Actividad
                            4. Gestion de Pagos
                            5. Salir
                            """);
                    try {
                        opcion = sc.nextInt();
                        sc.nextLine();
                        if (opcion < 1 || opcion > 5) {
                            System.out.println("Opcion invalida");
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("la opcion tiene que ser un numero");
                        sc.nextLine();
                    }
                    switch (opcion){
                        case 1:
                            usuarioService.gestionUsuarioMenu(usuarioActual);
                            continue;
                        case 2:
                            System.out.println("Menu de espacio"); // TODO
                            continue;
                        case 3:
                            System.out.println("Menu de tipo de actividad"); // TODO
                            continue;
                        case 4:
                            System.out.println("Menu de Pagos"); // TODO
                            continue;
                        case 5:
                            System.out.println("Saliendo...");
                            running = false;
                            break;
                        default:
                            System.out.println("Opcion invalida");
                            continue;

                    }
                    break;
                }
                // Menu de Socio
                if (usuarioActual.getTipoUsuario().equals(TipoUsuario.SOCIO)){
                    System.out.print("""
                            ----- Bienvenido al sistema -----
                            1. Gestion de Usuario
                            2. Gestion de Espacio
                            3. Salir
                            """);
                    System.out.print("Elija una opcion: ");

                    break;
                }
                // Menu de NoSocio
                if (usuarioActual.getTipoUsuario().equals(TipoUsuario.NOSOCIO)){
                    System.out.print("""
                            ----- Bienvenido al sistema -----
                            1. Gestion de Usuario
                            2. Gestion de Espacio
                            3. Salir
                            """);
                    System.out.print("Elija una opcion: ");

                    break;
                }
            }
        }


    }



}




