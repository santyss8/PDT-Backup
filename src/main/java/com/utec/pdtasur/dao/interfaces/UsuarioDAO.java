package com.utec.pdtasur.dao.interfaces;

import com.utec.pdtasur.models.Departamento;
import com.utec.pdtasur.models.Localidad;
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
    Usuario obtenerUsuario(String documento);
    void modificarDatosPropios(Usuario usuario);
    List<Usuario> listarUsuarios();
    boolean seleccionarEmail(String email);
    boolean seleccionarDocumento(String documento);
    boolean seleccionarTelefono(String numero, String documento);
    void insertarTelefono(String documento, String numero);
    void eliminarTelefono(String documento, String numero);
    boolean seleccionarTelefonoEliminar(String documento);

    List<Departamento> listarDepartamentos();
    List<Localidad> listarLocalidades(Departamento departamento);
    Departamento obtenerDepartamento(int id);
    Localidad obtenerLocalidad(int id);
}
