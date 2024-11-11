package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.impl.UsuarioDAOImpl;
import com.utec.pdtasur.dao.interfaces.UsuarioDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class ValidarService {
    private static UsuarioDAO usuarioDAO;

    public ValidarService() throws SQLException {
        usuarioDAO = new UsuarioDAOImpl();
    }
    public static boolean validarNombreApellido(String nombreApellido) {
        if (nombreApellido == null || nombreApellido.trim().isEmpty()) {
            return false;
        }
        if (nombreApellido.length() < 2 || nombreApellido.length() > 40) {
            return false;
        }
        if (!nombreApellido.matches("^[A-ZÁÉÍÓÚÜÑa-záéíóúüñ]{2,40}(\\s[A-ZÁÉÍÓÚÜÑa-záéíóúüñ]{2,40})*$")) {
            return false;
        }
        return true;
    }

    public static boolean validarDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }
        if (usuarioDAO.seleccionarDocumento(documento)){
            System.out.println("El documento ya esta registrado");
            return false;
        }
        return documento.matches("\\d{1}\\.\\d{3}\\.\\d{3}-\\d");
    }

    public static boolean validarDocumentoGenerico(String documento) {
        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }
        if (usuarioDAO.seleccionarDocumento(documento)){
            System.out.println("El documento ya esta registrado");
            return false;
        }
        String regex = "^[A-Za-z0-9]{5,15}$";
        return documento.matches(regex);
    }

    public static boolean validarDomicilio(String domicilio) {
        if (domicilio == null || domicilio.trim().isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9\\s.,#/-]{5,100}$";
        return domicilio.matches(regex);
    }

    public static LocalDate validarFechaNacimiento(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.trim().isEmpty()) {
            System.out.println("La fecha de nacimiento no puede estar vacía.");
            return null;
        }

        try {
            LocalDate fecha = LocalDate.parse(fechaNacimiento);

            if (fecha.isAfter(LocalDate.now())) {
                System.out.println("La fecha de nacimiento no puede ser en el futuro.");
                return null;
            }

            if (Period.between(fecha, LocalDate.now()).getYears() < 18) {
                System.out.println("Debe ser mayor de 18 años.");
                return null;
            }

            return fecha;
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use yyyy-MM-dd.");
            return null;
        }
    }

    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        if (usuarioDAO.seleccionarTelefono(telefono)){
            System.out.println("El telefono ya esta registrado");
            return false;
        }
        return telefono.matches("\\d{7,15}");
    }

    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (usuarioDAO.seleccionarEmail(email)){
            System.out.println("El email ya esta registrado");
            return false;
        }
        return email.matches("^[A-Za-z0-9]([A-Za-z0-9._%+-]{0,63}[A-Za-z0-9])?@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean validarContraseña(String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            return false;
        }
        return contrasena.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");
    }


}
