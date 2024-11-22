package com.example.projecte;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class addingMembersTest {

    @Test
    public void memberaddValid() {
        ArrayList<String> members = new ArrayList<>(Arrays.asList("jimena", "diego", "larry"));
        assertTrue(addMemberValid("userTest", members));
        assertFalse(addMemberValid("jimena", members));
    }

    @Test
    public void memberaddnotValid() {
        ArrayList<String> members = new ArrayList<>(Arrays.asList("jimena", "diego", "larry"));
        assertFalse(addMemberValid("jimena", members));
    }

    @Test
    public void memberaddnotValidOnly() {
        ArrayList<String> members = new ArrayList<>(Arrays.asList("jimena"));
        assertFalse(addMemberValid("jimena", members));
    }

    @Test
    public void memberaddnotValidEmpty() {
        ArrayList<String> members = new ArrayList<>();
        assertTrue(addMemberValid("jimena", members));
        assertFalse(addMemberValid("jimena", members));

    }

    public boolean addMemberValid(String member, ArrayList<String> members) {
        if (members.contains(member)) return false;
        members.add(member);
        return true;
    }
}
