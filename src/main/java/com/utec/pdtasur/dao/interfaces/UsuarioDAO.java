package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Usuario;

import java.util.List;

public interface UsuarioDAO {
    void registrar(Usuario usuario);
    void modificar(Usuario usuario, String documento);
    void eliminar(Usuario usuario);
    void login(String email, String contraseña);
    void modificarDatosPropios(Usuario usuario);

    List<Usuario> listarUsuarios();
}
