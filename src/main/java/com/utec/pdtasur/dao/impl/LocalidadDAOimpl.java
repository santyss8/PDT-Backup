package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.LocalidadDAO;
import com.utec.pdtasur.models.Departamento;
import com.utec.pdtasur.models.Localidad;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocalidadDAOimpl implements LocalidadDAO {
    private Connection connection;
    private DepartamentoDAOimpl departamentoDAO = new DepartamentoDAOimpl();

    public LocalidadDAOimpl() throws SQLException {
        this.connection = getConnection();
    }


    @Override
    public List<Localidad> listarLocalidades(Departamento departamento){
        String sql = "SELECT * FROM localidades WHERE departamento = ? ORDER BY id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, departamento.getDepartamento());
            ResultSet rs = ps.executeQuery();
            List<Localidad> localidades = new ArrayList<>();
            while (rs.next()){
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id"));
                localidad.setDepartamento(departamento);
                localidad.setLocalidad(rs.getString("localidad"));
                localidades.add(localidad);
            }
            return localidades;
        }catch (Exception e){
            System.out.println("Error al obtener Localidades");
            e.printStackTrace();
        }
        return null;
    }

    public Localidad obtenerLocalidad(int id){
        String sql = "SELECT * FROM localidades WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Localidad localidad = new Localidad();
                localidad.setId(rs.getInt("id"));
                localidad.setDepartamento(departamentoDAO.obtenerDepartamento(rs.getString("departamento")));
                localidad.setLocalidad(rs.getString("localidad"));
                return localidad;
            }
        }catch (Exception e){
            System.out.println("Error al obtener Localidad");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }
}
