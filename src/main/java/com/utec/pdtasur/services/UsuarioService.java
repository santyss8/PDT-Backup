package com.utec.pdtasur.services;

import com.utec.pdtasur.dao.interfaces.UsuarioDAO;

public class UsuarioService {
    UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

}
