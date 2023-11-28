package com.pharmacyPOS.service;

import com.pharmacyPOS.data.dao.UserDao;
import com.pharmacyPOS.data.entities.User;

import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public User verifyLogin(String username, String password) {
        return userDao.verifyLogin(username, password);
    }

    public int getUserIdByUsername(String username)
    {
        return (userDao.getUserIdByUsername(username));
    }
}
