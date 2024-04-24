package com.github.alvader01.Model.dao;

import com.github.alvader01.Model.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements DAO<User, String> {
    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User delete(User entity) throws SQLException {
        return null;
    }

    @Override
    public User findById(String key) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }


}
