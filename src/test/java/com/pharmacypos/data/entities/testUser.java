package com.pharmacyPOS.data.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testUser {

    @Test
    public void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertEquals(0, user.getUserId());
        assertNull(user.getUsername());
        assertNull(user.getPasswordHash());
        assertNull(user.getRole());
    }

    @Test
    public void testParameterizedConstructor() {
        User user = new User(1, "john_doe", "hashedPassword", "Manager");
        assertNotNull(user);
        assertEquals(1, user.getUserId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("hashedPassword", user.getPasswordHash());
        assertEquals("Manager", user.getRole());
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User();

        user.setUserId(2);
        assertEquals(2, user.getUserId());

        user.setUsername("jane_doe");
        assertEquals("jane_doe", user.getUsername());

        user.setPasswordHash("newHashedPassword");
        assertEquals("newHashedPassword", user.getPasswordHash());

        user.setRole("Sales Assistant");
        assertEquals("Sales Assistant", user.getRole());
    }

    @Test
    public void testSetPasswordHash() {
        User user = new User();

        // Valid password hash
        user.setPasswordHash("validHash");
        assertEquals("validHash", user.getPasswordHash());

        // Null password hash
        user.setPasswordHash(null);
        assertNull(user.getPasswordHash());

        // Edge case: Empty password hash
        user.setPasswordHash("");
        assertEquals("", user.getPasswordHash());
    }

    @Test
    public void testToString() {
        User user = new User(3, "mary_smith", "anotherHash", "Employee");
        String expected = "User{userId=3, username='mary_smith', passwordHash='anotherHash', role='Employee'}";
        assertEquals(expected, user.toString());
    }
}
