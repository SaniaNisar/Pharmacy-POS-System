package com.pharmacyPOS.data.dao;

import com.pharmacyPOS.data.dao.UserDao;
import com.pharmacyPOS.data.database.DatabaseConnection;
import com.pharmacyPOS.data.entities.User;
import org.junit.jupiter.api.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class testUserDao {

    private static UserDao userDao;

    @BeforeAll
    public static void setUp() {
        DatabaseConnection databaseConnection = new DatabaseConnection(/* your database configuration */);
        userDao = new UserDao(databaseConnection);
    }

    @Test
    public void testGetUserById() {
        int userId = 1; // Assuming this user ID exists in the database
        User user = userDao.getUserById(userId);
        assertNotNull(user);
        assertEquals(userId, user.getUserId());
    }

    @Test
    public void testGetUserByUsername() {
        String username = "testuser"; // Assuming this username exists in the database
        User user = userDao.getUserByUsername(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testGetUserIdByUsername() {
        String username = "testuser"; // Assuming this username exists in the database
        int userId = userDao.getUserIdByUsername(username);
        assertNotEquals(-1, userId);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testCreateUser() {
        User user = new User("newuser", "hashedpassword", "user");
        userDao.createUser(user);
        assertTrue(user.getUserId() > 0);
    }

    @Test
    public void testCreateUserReturnKey() {
        User user = new User("newuser", "hashedpassword", "user");
        int generatedUserId = userDao.createUserReturnKey(user);
        assertTrue(generatedUserId > 0);
        assertEquals(generatedUserId, user.getUserId());
    }

    @Test
    public void testUpdateUser() {
        User user = new User(/* existing user details from the database */);
        user.setUsername("updateduser");
        userDao.updateUser(user);
        User updatedUser = userDao.getUserById(user.getUserId());
        assertEquals("updateduser", updatedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        User user = new User(/* existing user details from the database */);
        int userId = user.getUserId();
        userDao.deleteUser(userId);
        assertNull(userDao.getUserById(userId));
    }

    @Test
    public void testVerifyLogin() {
        String username = "testuser"; // Assuming this username exists in the database
        String password = "hashedpassword"; // Assuming this is the hashed password for the user
        User user = userDao.verifyLogin(username, password);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }
}
