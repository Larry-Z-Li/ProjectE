package com.example.projecte;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class coursesTests {
    Map<String, String> coursesToGroups;
    ArrayList<String> avaliableGroups;

    @Test
    public void joinOneCourse() {
        coursesToGroups = new HashMap<>();
        avaliableGroups = new ArrayList<>();
        coursesToGroups.put("0", "CSCI301");
        coursesToGroups.put("1", "CSCI301");
        coursesToGroups.put("2", "CSCI301");
        coursesToGroups.put("3", "CSCI301");

        joinCourse("CSCI301");
        Assert.assertEquals(avaliableGroups.size(), 4);
    }

    @Test
    public void joinManyCourses()  {
        coursesToGroups = new HashMap<>();
        avaliableGroups = new ArrayList<>();
        coursesToGroups.put("0", "CSCI301");
        coursesToGroups.put("1", "CSCI301");
        coursesToGroups.put("2", "BISC210");
        coursesToGroups.put("3", "BISC210");
        coursesToGroups.put("4", "HIST101");
        coursesToGroups.put("5", "HIST101");
        coursesToGroups.put("6", "MATH226");
        coursesToGroups.put("7", "MATH226");

        joinCourse("CSCI301");
        joinCourse("BISC210");
        joinCourse("HIST101");
        Assert.assertEquals(avaliableGroups.size(), 6);

    }


    public void joinCourse(String str) {
        coursesToGroups.forEach((key,value) ->{
            if(Objects.equals(value, str))
            {
                avaliableGroups.add(value);
            }
        });
    }
}
