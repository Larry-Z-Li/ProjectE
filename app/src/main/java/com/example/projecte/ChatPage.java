package com.example.projecte;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecte.components.ChatPageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import android.widget.ListView;


public class ChatPage extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Carol", "Joe",
            "Bob", "Joe", "Joe", "Bob", "Carol", "Bob", "Carol", "Joe", "Carol", "Carol", "Joe", "Joe"));
    ArrayList<String> messages = new ArrayList<>(Arrays.asList("test1", "test2" ,"test3", "test4" ,"test5", "test6" ,"test7", "test8"
            ,"test9","test10","test11","test12","test13","test14","test15","test16666666666987970960896t09696990"));
    String myName = "Joe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_page);

        ListView lv = (ListView) findViewById(R.id.chat);
        ChatPageAdapter chatPageAdapter = new ChatPageAdapter(getApplicationContext(),messages,names,myName);

        lv.setAdapter(chatPageAdapter);
    }
}
