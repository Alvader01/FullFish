package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.Species;
import com.github.alvader01.Model.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FishTankDAO implements DAO<FishTank, Integer> {


    @Override
    public FishTank save(FishTank entity) {
        return null;
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

    @Override
    public void close() throws IOException {

    }

    public class FishTankEager extends FishTank {
        private User user;
        private UserDAO userDAO;

        public FishTankEager(UserDAO userDAO) {
            this.userDAO = userDAO;
        }

        public void loadFishTanksForUser(String username) {
            User user = userDAO.findById(username);

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


