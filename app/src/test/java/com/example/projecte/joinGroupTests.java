package com.example.projecte;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class joinGroupTests {
    ArrayList<String> joinedGroups;
    ArrayList<String> avaliableGroups;

    @Test
    public void threeGroups() throws InterruptedException {
        avaliableGroups = new ArrayList<>();
        joinedGroups = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            avaliableGroups.add(i + "");
        }
        joiningGroup("1");
        joiningGroup("2");
        joiningGroup("3");
        Assert.assertEquals(3, joinedGroups.size());
    }

    @Test
    public void joinBadGroups() throws InterruptedException {
        avaliableGroups = new ArrayList<>();
        joinedGroups = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            avaliableGroups.add(i + "");
        }
        joiningGroup(21+"");
        joiningGroup(-1+"");
        joiningGroup(50+"");

        Assert.assertEquals(0, joinedGroups.size());
    }

    @Test
    public void joinDuplicateGroups() throws InterruptedException {
        avaliableGroups = new ArrayList<>();
        joinedGroups = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            avaliableGroups.add(i + "");
        }
        joiningGroup("1");
        joiningGroup("1");
        joiningGroup("1");
        Assert.assertEquals(1, joinedGroups.size());
    }

    public void joiningGroup(String str) {
        if (avaliableGroups.contains(str)) {
            if (!joinedGroups.contains(str)) {
                joinedGroups.add(str);

            }
        }
    }
}
