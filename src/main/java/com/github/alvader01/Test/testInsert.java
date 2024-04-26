package com.github.alvader01.Test;

import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;

public class testInsert {
    public static void main(String[] args) {
        User u  = new User("testUser", "Test User", "testPassword", "test@example.com", null);
        User u1 = new User("user1", "User One", "password1", "user1@example.com", null);
        User u2 = new User("user2", "User Two", "password2", "user2@example.com", null);
        UserDAO userDAO = new UserDAO();
        userDAO.save(u1);
        userDAO.save(u2);
        userDAO.save(u);


    }
}
