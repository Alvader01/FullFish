package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.connection.ConnectionMariaDB;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FishTankDAO implements DAO<FishTank, Integer> {
    private final static String INSERT="INSERT INTO fishtank (id,name,capacity,lengthy,width,height) VALUES (?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE fishtank SET name = ?, capacity = ?, lengthy = ?, width = ?, height = ? WHERE id = ?";    private final static String FINDALL="SELECT f.id, f.name, f.capacity, f.lengthy, f.width, f.height, FROM fishtank AS f";
    private final static String FINDBYUSERNAME="SELECT f.id, f.name, f.capacity, f.lengthy, f.width, f.height FROM fishtank AS f WHERE f.id = ?";
    private final static String DELETE="DELETE FROM fishtank WHERE id = ?";


    @Override
    public FishTank save(FishTank fishTank) {
        FishTank result = new FishTank();
        FishTank f = findById(fishTank.getId());
        if (f == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setInt(1, fishTank.getId());
                ps.setString(2, fishTank.getName());
                ps.setInt(3, fishTank.getCapacity());
                ps.setFloat(4, fishTank.getLengthy());
                ps.setFloat(5, fishTank.getWidth());
                ps.setFloat(6, fishTank.getHeight());
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
    public FishTank delete(FishTank entity) throws SQLException {
        return null;
    }

    @Override
    public FishTank findById(Integer key) {
        return null;
    }

    @Override
    public List<FishTank> findAll() {
        return null;
    }

    public List<FishTank> findByUser(String username) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    public static FishTankDAO build(){
        return new FishTankDAO();
    }


    public class FishTankEager extends FishTank {
        private User user;
        private UserDAO userDAO;
        public FishTankEager(UserDAO userDAO) {
            this.userDAO = userDAO;
        }
        public void loadFishTanksForUser(String username) {
            User user = userDAO.findByUsername(username);
            if (user != null) {
                List<FishTank> fishTanks = user.getFishTanks();
                for (FishTank fishTank : fishTanks) {
                    List<Species> speciesInTank = fishTank.getSpeciess();
                    fishTank.setSpeciess(speciesInTank);
                }
                user.setFishTanks(fishTanks);
                this.user = user;
            } else {
                System.out.println("Usuario no encontrado");
            }
        }
    }
}


