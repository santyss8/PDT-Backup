package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    void registrarNoSocio(Usuario usuario) throws SQLException;
    void registrarAdmin(Usuario usuario);
    void registrarSocio(Usuario usuario);
    void modificar(Usuario usuario);
    void eliminar(Usuario usuario);
    void activar(Usuario usuario);
    Usuario login(String email, String contraseña);
    void modificarDatosPropios(Usuario usuario);
    List<Usuario> listarUsuarios();
    boolean seleccionarEmail(String email);
    boolean seleccionarDocumento(String documento);
    boolean seleccionarTelefono(String numero);
    void insertarTelefono(String documento, String numero);

}
