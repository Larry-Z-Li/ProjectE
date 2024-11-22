package com.example.projecte;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class allFieldsInputTest {

    @Test
    public void allInputsThere() {
        ArrayList<String> courses = new ArrayList<>(Arrays.asList("CSCI310", "CSCI201"));
        assertTrue(validSignup("userTest", "userPass", "userPass", courses));
    }

    @Test
    public void noUsername() {
        ArrayList<String> courses = new ArrayList<>(Arrays.asList("CSCI310", "CSCI201"));
        assertFalse(validSignup(null, "userPass", "userPass", courses));
    }

    @Test
    public void noUsernameempty() {
        ArrayList<String> courses = new ArrayList<>(Arrays.asList("CSCI310", "CSCI201"));
        assertFalse(validSignup("", "userPass", "userPass", courses));
    }

    @Test
    public void noPassempty() {
        ArrayList<String> courses = new ArrayList<>(Arrays.asList("CSCI310", "CSCI201"));
        assertFalse(validSignup("userTest", "", "userPass", courses));
    }

    @Test
    public void noMatching() {
        ArrayList<String> courses = new ArrayList<>(Arrays.asList("CSCI310", "CSCI201"));
        assertFalse(validSignup("userTest", "pass", "userPass", courses));
    }

    @Test
    public void emptyArray() {
        ArrayList<String> courses = new ArrayList<>();
        assertFalse(validSignup("userTest", "pass", "userPass", courses));
    }

    public boolean validSignup(String username, String password, String confirmPassword, ArrayList<String> selectedCourses) {
        if(username == null || username.isEmpty())
        {
            return false;
        }

        if(password == null || password.isEmpty())
        {
            return false;
        }

        if(confirmPassword == null || confirmPassword.isEmpty())
        {
            return false;
        }

        if(selectedCourses == null || selectedCourses.isEmpty())
        {
            return false;
        }

        if (!password.equals(confirmPassword))
        {
            return false;
        }

        return true;
    }

}
