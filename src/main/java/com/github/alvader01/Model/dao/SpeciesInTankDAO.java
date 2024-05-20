package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.connection.ConnectionMariaDB;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.SpeciesInTank;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeciesInTankDAO implements DAO<SpeciesInTank, Integer> {
    private final static String ADDSPECIESINTANK = "INSERT INTO Holds (fishtankId, speciesId) VALUES (?, ?)";
    private final static String DELETESPECIESFROMTANK = "DELETE FROM Holds WHERE fishtankId = ? AND speciesId = ?";
    private final static String FINDINFISHTANK = "SELECT COUNT(*) FROM Holds WHERE fishTankId = ? AND speciesId = ?\n";
    private final static String FINDSPECIESBYID = "SELECT id, name, dimension, longevity FROM Species WHERE id = ?";
    private final static String FINDALLSPECIESINTANK = "SELECT s.id, s.name, s.dimension, s.longevity FROM Species AS s JOIN Holds AS h ON s.id = h.speciesId WHERE h.fishtankId = ?";

    /**
     * Adds a Species to a FishTank in the database.
     *
     * @param fishTankId   The ID of the FishTank to which the Species will be added.
     * @param speciesId    The ID of the Species to be added to the FishTank.
     */
    public void addSpeciesToTank(int fishTankId, int speciesId) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(ADDSPECIESINTANK)) {
            ps.setInt(1, fishTankId);
            ps.setInt(2, speciesId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar la especie al acuario", e);}
    }

    /**
     * Removes a Species from a FishTank in the database.
     *
     * @param fishTankId   The ID of the FishTank from which the Species will be removed.
     * @param speciesId    The ID of the Species to be removed from the FishTank.
     * @return         True if the Species was successfully removed from the FishTank, false otherwise.
     */
    public boolean removeSpeciesFromTank(int fishTankId, int speciesId) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETESPECIESFROMTANK)) {
            ps.setInt(1, fishTankId);
            ps.setInt(2, speciesId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la especie del acuario", e);
        }
    }

    public List<Species> findAllSpeciesInTank(int fishTankId) {
        List<Species> speciesList = new ArrayList<>();

        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDALLSPECIESINTANK)) {
            ps.setInt(1, fishTankId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int dimension = rs.getInt("dimension");
                int longevity = rs.getInt("longevity");

                Species species = new Species(id, name, dimension, longevity);
                speciesList.add(species);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar las especies en el acuario", e);
        }

        return speciesList;
    }


    @Override
    public SpeciesInTank save(SpeciesInTank entity) {
        return null;
    }

    @Override
    public SpeciesInTank update(SpeciesInTank entity) {
        return null;
    }

    @Override
    public SpeciesInTank delete(SpeciesInTank entity) throws SQLException {
        return null;
    }

    @Override
    public SpeciesInTank findById(Integer key) {
        return null;
    }

    @Override
    public List<SpeciesInTank> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    public static SpeciesInTankDAO build() {
        return new SpeciesInTankDAO();
    }
    /**
     * A description of the entire Java function.
     *
     * @param  speciesId     description of parameter
     * @return               description of return value
     */
    public Species findSpeciesById(int speciesId) {
        Species species = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDSPECIESBYID)) {
            ps.setInt(1, speciesId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int dimension = rs.getInt("dimension");
                int longevity = rs.getInt("longevity");

                species = new Species(id, name, dimension, longevity);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar la especie por ID", e);
        }

        return species;
    }

    /**
     * A method to check if a species is present in a fish tank.
     *
     * @param  fishTankId    the ID of the fish tank to check
     * @param  speciesId     the ID of the species to search for
     * @return              true if the species is in the tank, false otherwise
     */
    public boolean isSpeciesInTank(int fishTankId, int speciesId) {
        Species species = findSpeciesById(speciesId);

        if (species != null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDINFISHTANK)) {
                ps.setInt(1, fishTankId);
                ps.setInt(2, speciesId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al verificar la asociación de la especie con el acuario", e);
            }
        }

        return false;
    }
}
