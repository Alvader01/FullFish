package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.connection.ConnectionMariaDB;
import com.github.alvader01.Model.entity.FishTank;
import com.github.alvader01.Model.entity.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User, String> {
    private final static String INSERT="INSERT INTO user (username,name,password,email) VALUES (?,?,?,?)";
    private final static String UPDATE="UPDATE user SET name = ?, password = ?, email = ? WHERE username = ?";
    private final static String FINDALL="SELECT u.username, u.name, u.password, u.email FROM user AS u";
    private final static String FINDBYID="SELECT u.username, u.name, u.password, u.email FROM user AS u WHERE u.username = ?";
    private final static String DELETE="DELETE FROM user WHERE username = ?";

    @Override
    public User save(User entity) {
        User result = new User();
        User u = findById(entity.getUsername());
        if (u == null) {
            try (Connection connection = ConnectionMariaDB.getConnection();
                 PreparedStatement ps = connection.prepareStatement(INSERT)) {
                ps.setString(1, entity.getUsername());
                ps.setString(2, entity.getName());
                ps.setString(3, entity.getPassword());
                ps.setString(4, entity.getEmail());
                ps.executeUpdate();

                if (entity.getFishTanks() != null) {
                    for (FishTank fishTank : entity.getFishTanks()) {
                        FishTankDAO.build().save(fishTank);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el usuario", e);
            }
        }
        return result;
    }

    @Override
    public User update(User entity) {
        if (entity == null || entity.getUsername() == null) return entity;

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getUsername());
            ps.executeUpdate();

            if (entity.getFishTanks() != null) {
                List<FishTank> fishTanksBefore = FishTankDAO.build().findByUser(entity.getUsername());
                List<FishTank> fishTanksAfter = entity.getFishTanks();

                List<FishTank> fishTanksToBeDeleted = new ArrayList<>(fishTanksBefore);
                fishTanksToBeDeleted.removeAll(fishTanksAfter);

                for (FishTank fishTank : fishTanksToBeDeleted) {
                    FishTankDAO.build().delete(fishTank);
                }
                for (FishTank fishTank : fishTanksAfter) {
                    FishTankDAO.build().save(fishTank);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario", e);
        }

        return entity;
    }



    @Override
    public User delete(User entity) throws SQLException {
        if (entity != null && entity.getUsername() != null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                pst.setString(1, entity.getUsername());
                pst.executeUpdate();
            }

        }
        return entity;
    }


    @Override
    public User findById(String username) {
        User result = new User();

        if (username == null || username.isEmpty()) {
            return result;
        }

        try (Connection connection = ConnectionMariaDB.getConnection();
             PreparedStatement pst = connection.prepareStatement(FINDBYID)) {
            pst.setString(1, username);
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                result.setUsername(res.getString("username"));
                result.setName(res.getString("name"));
            }

            res.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return result;
    }



    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }


}
