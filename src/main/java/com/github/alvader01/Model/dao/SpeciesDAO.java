package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.connection.ConnectionMariaDB;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeciesDAO implements DAO<Species, Integer> {
    private final static String INSERT="INSERT INTO species (id,name,dimension,longevity,subGroup) VALUES (?,?,?,?,?)";
    private final static String UPDATE="UPDATE species SET name = ?, dimension = ?, longevity = ?, subGroup = ? WHERE id = ?";
    private final static String FINDALL="SELECT s.id, s.name, s.dimension, s.longevity, s.subGroup FROM species AS s";
    private final static String FINDBYID="SELECT s.id, s.name, s.dimension, s.longevity, s.subGroup FROM species AS s WHERE s.id = ?";
    private final static String DELETE="DELETE FROM species WHERE id = ?";
    private final static String FINDINFISHTANK="SELECT s.id, s.name, s.dimension, s.longevity, s.subGroup FROM species AS s WHERE s.id = ?";
    @Override
    public Species save(Species species) {
        return null;
    }

    @Override
    public Species update(Species entity) {
        return null;
    }

    @Override
    public Species delete(Species entity) throws SQLException {
        return null;
    }

    @Override
    public Species findById(Integer key) {
        return null;
    }

    @Override
    public List<Species> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    public List<Species> findInFishTank(FishTank fishTank) {
        List<Species> speciesInTank = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINFISHTANK)) {
            ps.setInt(1, fishTank.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Species species = new Species();
                    speciesInTank.add(species);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar especies en el tanque de peces", e);
        }

        return speciesInTank;
    }

    public static SpeciesDAO build(){
        return new SpeciesDAO();
    }



}
