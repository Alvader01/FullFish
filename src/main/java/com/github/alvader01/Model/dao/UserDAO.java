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
    private final static String FINDBYUSERNAME="SELECT u.username, u.password FROM user AS u WHERE u.username = ?";
    private final static String DELETE="DELETE FROM user WHERE username = ?";

    @Override
    public User save(User user) {
        User result = new User();
        User u = findByUsername(user.getUsername());
        if (u == null) {
            try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(INSERT)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.executeUpdate();

                if (user.getFishTanks() != null) {
                    for (FishTank fishTank : user.getFishTanks()) {
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
    public User update(User user) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.executeUpdate();

            if (user.getFishTanks() != null) {
                List<FishTank> fishTanksBefore = FishTankDAO.build().findByUser(user.getUsername());
                List<FishTank> fishTanksAfter = user.getFishTanks();

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

        return user;
    }



    @Override
    public User delete(User user) throws SQLException {
        if (user != null && user.getUsername() != null) {
            try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
                pst.setString(1, user.getUsername());
                pst.executeUpdate();
            }

        }
        return user;
    }

    @Override
    public User findById(String key) {
        return null;
    }

    public User findByUsername(String username){
        User result = null;
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                result = new User();
                result.setUsername(res.getString("username"));
                result.setPassword(res.getString("password"));
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;


    }




    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();

        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {

            ResultSet res = pst.executeQuery();
            while(res.next()){
                User u = new User();
                u.setUsername(res.getString("username"));
                u.setName(res.getString("name"));
                result.add(u);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(String username) {
        try (PreparedStatement ps = ConnectionMariaDB.getConnection().prepareStatement(FINDBYUSERNAME)) {
            ps.setString(1, username);
            ResultSet res = ps.executeQuery();

            return res.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void close() throws IOException {

    }


}
