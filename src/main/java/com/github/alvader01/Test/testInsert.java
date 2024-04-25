package com.github.alvader01.Test;

import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;

public class testInsert {
    public static void main(String[] args) {
        User u  = new User("testUser", "Test User", "testPassword", "test@example.com", null);
        UserDAO userDAO = new UserDAO();
        userDAO.save(u);

    }
}
