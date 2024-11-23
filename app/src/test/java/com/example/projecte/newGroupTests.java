package com.example.projecte;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class newGroupTests {
    ArrayList<String> avaliableGroups;

    @Test
    public void threeGroups() throws InterruptedException {
        avaliableGroups = new ArrayList<>();
        createGroup("1");
        createGroup("2");
        createGroup("3");

        Assert.assertEquals(3, avaliableGroups.size());
    }

    @Test
    public void makeDuplicateGroups() throws InterruptedException {
        avaliableGroups = new ArrayList<>();
        createGroup("1");
        createGroup("1");
        createGroup("1");

        Assert.assertEquals(1, avaliableGroups.size());
    }

    public void createGroup(String str) {
        if (!avaliableGroups.contains(str)) {
            avaliableGroups.add(str);
        }
    }
}
