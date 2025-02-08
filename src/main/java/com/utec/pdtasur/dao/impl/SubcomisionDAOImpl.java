package com.utec.pdtasur.dao.impl;

import com.utec.pdtasur.dao.interfaces.SubcomisionDAO;
import com.utec.pdtasur.models.Subcomision;
import com.utec.pdtasur.utils.DatabaseConnection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SubcomisionDAOImpl implements SubcomisionDAO {
    private Connection connection;

    public SubcomisionDAOImpl() throws SQLException {
        this.connection = getConnection();
    }
    @Override
    public void crearSubcomision(Subcomision subcomision) {

        String sql = "INSERT INTO subcomisiones (nombre, descripcion) VALUES (?, ?);";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, subcomision.getNombre());
            ps.setString(2, subcomision.getDescripcion());
            ps.executeUpdate();
            System.out.println("Comision creada con exito");
        }catch (Exception e){
            System.out.println("Error al crear Subcomision");
            e.printStackTrace();
        }

    }

    @Override
    public Subcomision getSubcomision(int id) {

        String sql = "SELECT * FROM subcomisiones where id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Subcomision subcomision = new Subcomision();
                subcomision.setId(rs.getInt("id"));
                subcomision.setNombre(rs.getString("nombre"));
                subcomision.setDescripcion(rs.getString("descripcion"));
                return subcomision;
            }
        }catch (Exception e){
            System.out.println("Hubo un error al obtener los datos");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Subcomision> listarSubcomisiones() {

        List<Subcomision> subcomisiones = new ArrayList<>();

        String sql = "SELECT * FROM subcomisiones ORDER BY id;";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Subcomision subcomision = new Subcomision(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                subcomisiones.add(subcomision);
            }

        }catch (Exception e){
            System.out.println("Ocurrio un error al listar las subcomisiones");
            e.printStackTrace();
        }
        return subcomisiones;

    }

    @Override
    public void eliminarSubcomision(int id) {

        String sql = "DELETE FROM subcomisiones WHERE id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Subcomision eliminada con exito");
        }catch (Exception e){
            System.out.println("Ocurrio un error al eliminar la subcomision");
            e.printStackTrace();
        }

    }



    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }



}
