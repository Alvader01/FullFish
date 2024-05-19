package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.connection.ConnectionMariaDB;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FishTankDAO implements DAO<FishTank, Integer> {
    private final static String INSERT="INSERT INTO Fishtank (name, capacity, lengthy, width, height, user_username) VALUES (?, ?, ?, ?, ?, ?);";
    private final static String UPDATE = "UPDATE Fishtank SET name = ?, capacity = ?, lengthy = ?, width = ?, height = ? WHERE id = ?";
    private final static String FINDALL = "SELECT f.id, f.name, f.capacity, f.lengthy, f.width, f.height FROM Fishtank AS f";
    private final static String FINDBYID = "SELECT f.id, f.name, f.capacity, f.lengthy, f.width, f.height FROM Fishtank AS f WHERE f.id = ?";
    private final static String DELETE = "DELETE FROM Fishtank WHERE id = ?";
    private final static String FINDBYUSER = "SELECT * FROM fish_tank WHERE username = ?";



    public FishTank saveFishTank(FishTank fishTank, User currentUser) {
        FishTank result = new FishTank();
        FishTank f = findById(fishTank.getId());
        if (f == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, fishTank.getName());
                ps.setInt(2, fishTank.getCapacity());
                ps.setFloat(3, fishTank.getLengthy());
                ps.setFloat(4, fishTank.getWidth());
                ps.setFloat(5, fishTank.getHeight());
                ps.setString(6, currentUser.getUsername());
                ps.executeUpdate();

                if (fishTank.getSpeciess() != null) {
                    for (Species species : fishTank.getSpeciess()) {
                        SpeciesDAO.build().save(species);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el usuario", e);
            }
        }
        return result;
    }


    @Override
    public FishTank save(FishTank entity) {
        return null;
    }

    @Override
    public FishTank update(FishTank fishTank) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            ps.setString(1, fishTank.getName());
            ps.setInt(2, fishTank.getCapacity());
            ps.setFloat(3, fishTank.getLengthy());
            ps.setFloat(4, fishTank.getWidth());
            ps.setFloat(5, fishTank.getHeight());
            ps.setInt(6, fishTank.getId());

            ps.executeUpdate();

            if(fishTank.getSpeciess() != null) {
                List<Species> speciesBefore = SpeciesDAO.build().findInFishTank(fishTank);
                List<Species> speciesAfter = fishTank.getSpeciess();

                List<Species> speciesToBeRemoved = new ArrayList<>(speciesBefore);
                speciesToBeRemoved.removeAll(speciesAfter);

                for(Species s : speciesToBeRemoved) {
                    SpeciesDAO.build().delete(s);
                }
                for(Species s : speciesAfter) {
                    SpeciesDAO.build().save(s);
                }
            }



        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }

        return fishTank;
    }


    @Override
    public FishTank delete(FishTank fishTank) throws SQLException {
        if ( fishTank!= null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                ps.setInt(1, fishTank.getId());
                ps.executeUpdate();
            }

        }
        return fishTank;
    }

    @Override
    public FishTank findById(Integer id) {
        FishTank result = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYID)) {
            ps.setString(1, id.toString());
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                result = new FishTank();
                result.setId(res.getInt("id"));
                result.setName(res.getString("name"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }


    @Override
    public List<FishTank> findAll() {
        List<FishTank> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                FishTank f = new FishTank();
                f.setId(res.getInt("id"));
                f.setName(res.getString("name"));
                f.setCapacity(res.getInt("capacity"));
                f.setLengthy(res.getFloat("lengthy"));
                f.setWidth(res.getFloat("width"));
                f.setHeight(res.getFloat("height"));
                result.add(f);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public List<FishTank> findByUser(String username) {
        List<FishTank> fishTanks = new ArrayList<>();

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(FINDBYUSER)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FishTank fishTank = new FishTank();
                fishTank.setId(rs.getInt("id"));
                fishTank.setName(rs.getString("name"));

                fishTanks.add(fishTank);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar los tanques de peces del usuario", e);
        }

        return fishTanks;
    }

    @Override
    public void close() throws IOException {

    }

    public static FishTankDAO build(){
        return new FishTankDAO();
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

}


