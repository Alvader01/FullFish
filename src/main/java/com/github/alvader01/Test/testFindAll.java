package com.github.alvader01.Test;

import com.github.alvader01.Model.dao.UserDAO;
import com.github.alvader01.Model.entity.User;

import java.util.List;

public class testFindAll {
    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + ", Name: " + user.getName());
        }
    }
}
