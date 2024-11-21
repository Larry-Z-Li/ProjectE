package com.example.projecte;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class usernameDuplicateTest {

    @Test
    public void usernameNotDuplicate() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3"));
        assertTrue(isUsernameNotDuplicate("userTest", arrayUsers));
    }

    @Test
    public void usernameNotDuplicateWithNums() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3"));
        assertTrue(isUsernameNotDuplicate("userTest23456788", arrayUsers));
    }

    @Test
    public void usernameIsDuplicate() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3", "userTest"));
        assertFalse(isUsernameNotDuplicate("userTest", arrayUsers));
    }

    @Test
    public void usernameIsDuplicateWithNums() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3", "userTest23456788"));
        assertFalse(isUsernameNotDuplicate("userTest23456788", arrayUsers));
    }

    @Test
    public void usernameNotDuplicateWithSpecialChars() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3"));
        assertTrue(isUsernameNotDuplicate("userTest#$%&/()", arrayUsers));
    }

    @Test
    public void usernameIsDuplicateWithSpecialChars() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3", "userTest#$%&/()"));
        assertFalse(isUsernameNotDuplicate("userTest#$%&/()", arrayUsers));
    }

    @Test
    public void usernameNotDuplicateInEmpty() {
        ArrayList<String> arrayUsers = new ArrayList<>();
        assertTrue(isUsernameNotDuplicate("userTest", arrayUsers));
    }

    @Test
    public void usernameDuplicateInCaseSesitiveCAPS() {
        ArrayList<String> arrayUsers = new ArrayList<>(Arrays.asList("user1", "user2", "user3", "USERTEST"));
        assertFalse(isUsernameNotDuplicate("userTest", arrayUsers));
    }

    public boolean isUsernameNotDuplicate(String username, ArrayList<String> usernamesList) {
        for(int i = 0; i < usernamesList.size(); i++)
        {
            if(usernamesList.get(i).equalsIgnoreCase(username))
            {
                return false;
            }
        }
        return true;
    }

}
