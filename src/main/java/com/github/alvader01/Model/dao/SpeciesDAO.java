package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.connection.ConnectionMariaDB;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeciesDAO implements DAO<Species, Integer> {
    private final static String INSERT="INSERT INTO species (id,name,dimension,longevity) VALUES (?,?,?,?)";
    private final static String UPDATE="UPDATE species SET name = ?, dimension = ?, longevity = ? WHERE id = ?";
    private final static String FINDALL="SELECT s.id, s.name, s.dimension, s.longevity FROM species AS s";
    private final static String FINDBYID="SELECT s.id, s.name, s.dimension, s.longevity FROM species AS s WHERE s.id = ?";
    private final static String DELETE="DELETE FROM species WHERE id = ?";
    private final static String ADDSPECIESINTANK = "INSERT INTO Holds (fishtankId, speciesId) VALUES (?, ?)";
    private final static String DELETESPECIESFROMTANK = "DELETE FROM Holds WHERE fishtankId = ? AND speciesId = ?";
    private final static String UPDATESPECIESINTANK = "UPDATE Holds SET speciesId = ? WHERE fishtankId = ?";
    private final static String FINDALLSPECIESINTANK = "SELECT s.id, s.name, s.dimension, s.longevity FROM Species AS s JOIN Holds AS h ON s.id = h.speciesId WHERE h.fishtankId = ?";
    private final static String FINDINFISHTANK="SELECT s.id, s.name, s.dimension, s.longevity FROM species AS s WHERE s.id = ?";

    @Override
    public Species save(Species species) {
        return null;
    }

    public Species saveSpecies(Species species, User currentUser) {
        Species result = new Species();
        Species s = findById(species.getId());
        if (s == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setInt(1, species.getId());
                ps.setString(2, species.getName());
                ps.setInt(3, species.getDimension());
                ps.setInt(4, species.getLongevity());
                ps.setString(5, currentUser.getUsername());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar la especie", e);
            }
        }
        return result;

    }




        @Override
    public Species update(Species species) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                ps.setString(1, species.getName());
                ps.setInt(2, species.getDimension());
                ps.setInt(3, species.getLongevity());
                ps.setInt(4, species.getId());

                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al actualizar la especie", e);
            }

            return species;
        }


    @Override
    public Species delete(Species species) throws SQLException {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            ps.setInt(1, species.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la especie", e);
        }

        return species;
    }

    @Override
    public Species findById(Integer id) {
        Species species = null;

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    species = new Species();
                    species.setId(rs.getInt("id"));
                    species.setName(rs.getString("name"));
                    species.setDimension(rs.getInt("dimension"));
                    species.setLongevity(rs.getInt("longevity"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar la especie por ID", e);
        }

        return species;
    }

    @Override
    public List<Species> findAll() {
        List<Species> result = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Species species = new Species();
                species.setId(rs.getInt("id"));
                species.setName(rs.getString("name"));
                species.setDimension(rs.getInt("dimension"));
                species.setLongevity(rs.getInt("longevity"));

                result.add(species);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar todas las especies", e);
        }

        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public List<Species> findAllSpeciesInTank(FishTank fishTank) {
        List<Species> speciesList = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALLSPECIESINTANK)) {
            ps.setInt(1, fishTank.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Species species = new Species();
                    species.setId(rs.getInt("id"));
                    species.setName(rs.getString("name"));
                    species.setDimension(rs.getInt("dimension"));
                    species.setLongevity(rs.getInt("longevity"));

                    speciesList.add(species);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar todas las especies en el acuario", e);
        }

        return speciesList;
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

    public void addSpeciesToTank(Species species, FishTank fishTank) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(ADDSPECIESINTANK)) {
            ps.setInt(1, species.getId());
            ps.setInt(2, fishTank.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al añadir la especie al acuario", e);
        }
    }

    public void removeSpeciesFromTank(Species species, FishTank fishTank) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETESPECIESFROMTANK)) {
            ps.setInt(1, species.getId());
            ps.setInt(2, fishTank.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la especie del acuario", e);
        }

    }

    public void updateSpeciesInTank(Species species, FishTank fishTank) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATESPECIESINTANK)) {
            ps.setString(1, species.getName());
            ps.setInt(2, species.getDimension());
            ps.setInt(3, species.getLongevity());
            ps.setInt(5, fishTank.getId());
            ps.setInt(6, species.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la especie en el acuario", e);
        }
    }

    public boolean exists(Integer id) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setString(1, id.toString());
            ResultSet res = ps.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static SpeciesDAO build(){
        return new SpeciesDAO();
    }



}
