package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.DepartamentoDAO;
import com.utec.pdtasur.models.Departamento;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOimpl implements DepartamentoDAO {
    private Connection connection;

    public DepartamentoDAOimpl() throws SQLException {
        this.connection = getConnection();
    }

    @Override
    public Departamento obtenerDepartamento(int id) {
        String sql = "SELECT * FROM departamentos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Departamento departamento = new Departamento();
                departamento.setId(rs.getInt("id"));
                departamento.setDepartamento(rs.getString("departamento"));
                return departamento;
            }
        }catch (Exception e){
            System.out.println("Error al obtener Departamento");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Departamento obtenerDepartamento(String departamento) {
        String sql = "SELECT * FROM departamentos WHERE departamento = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, departamento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Departamento depa = new Departamento();
                depa.setId(rs.getInt("id"));
                depa.setDepartamento(rs.getString("departamento"));
                return depa;
            }
        }catch (Exception e){
            System.out.println("Error al obtener Departamento");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Departamento> listarDepartamentos() {
        String sql = "SELECT * FROM departamentos ORDER BY id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            List<Departamento> departamentos = new ArrayList<>();
            while (rs.next()){
                Departamento departamento = new Departamento();
                departamento.setId(rs.getInt("id"));
                departamento.setDepartamento(rs.getString("departamento"));
                departamentos.add(departamento);
            }
            return departamentos;
        }catch (Exception e){
            System.out.println("Error al obtener Departamentos");
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }
}
