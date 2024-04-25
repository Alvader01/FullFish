package com.github.alvader01.Test;

import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;

import java.sql.SQLException;

import static java.nio.file.Files.delete;

public class testDelete {
    public static void main(String[] args) throws SQLException {
        UserDAO userDAO = new UserDAO();
        User userToDelete = new User("testUser", "Test User", "testPassword", "test@example.com", null);
        userDAO.delete(userToDelete);
    }
}
