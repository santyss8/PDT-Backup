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
        int opcion;
        while (running) {
            if (usuarioActual == null) {
                opcion = 0;
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
                        usuarioService.login();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            }
            System.out.println();
        }


    }



}




