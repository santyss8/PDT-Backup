package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDAO {
    void registrar(Usuario usuario) throws SQLException;
    void registrarAdmin(Usuario usuario);
    void registrarSocio(Usuario usuario);
    void modificar(Usuario usuario);
    void eliminar(Usuario usuario);
    Usuario login(String email, String contraseña);
    void modificarDatosPropios(Usuario usuario);
    List<Usuario> listarUsuarios();
}
