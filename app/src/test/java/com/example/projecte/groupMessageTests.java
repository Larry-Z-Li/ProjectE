package com.example.projecte;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class groupMessageTests {
    String name = "Larry";
    String name2 = "Diego";
    String name3 = "Jime";
    ArrayList<String> names;
    ArrayList<String> messages;

    @Test
    public void text() {
        names = new ArrayList<>();
        messages = new ArrayList<>();
        message(name, "hey");
        message(name2, "wanna study later");
        message(name3, "ok");


        Assert.assertEquals(names.get(0), name);
        Assert.assertEquals(names.get(1), name2);
        Assert.assertEquals(names.get(2), name3);

        Assert.assertEquals(messages.get(0), "hey");
        Assert.assertEquals(messages.get(1), "wanna study later");
        Assert.assertEquals(messages.get(2), "ok");

    }

    @Test
    public void textALot()  {
        names = new ArrayList<>();
        messages = new ArrayList<>();
        for(int i = 0; i < 20;i++)
        {
            message(name,i+"");
        }

        Assert.assertEquals(messages.size(), 20);
        Assert.assertEquals(names.size(), 20);
    }


    public void message(String name, String message) {
        names.add(name);
        messages.add(message);
        return;
    }
}
