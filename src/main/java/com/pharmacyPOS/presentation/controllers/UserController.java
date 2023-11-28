package com.pharmacyPOS.presentation.controllers;

import com.pharmacyPOS.data.entities.User;
import com.pharmacyPOS.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void createUser(User user) {
        userService.createUser(user);
    }

    public void updateUser(int id, User user) {
        user.setUserId(id);
        userService.updateUser(user);
    }

    public void deleteUser( int id) {
        userService.deleteUser(id);
    }

    public int createUserReturnKey(User user)
    {
        return (userService.createUserReturnKey(user));
    }

    public User verifyLogin( String username, String password) {
        return userService.verifyLogin(username, password);
    }

    public int getUserIdByUsername(String username)
    {
        return (userService.getUserIdByUsername(username));
    }
}
