package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecte.components.ChatPageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class ChatPage extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Carol", "Joe",
            "Bob", "Joe", "Joe", "Bob", "Carol", "Bob", "Carol", "Joe", "Carol", "Carol", "Joe", "Joe"));
    public ArrayList<String> messages = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15", "test16666666666987970960896t09696990"));
    String username, name2, group, course, type;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference r = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.chat_page);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        username = (String) b.get("name");
        type = (String) b.get("type");
        if (Objects.equals(type, "group")) {
            course = (String) b.get("course");
            group = (String) b.get("group");
        } else {
            name2 = (String) b.get("name2");
        }

        if (Objects.equals(type, "single")) {
            r.child("users").child(username).child("messages").orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    names = new ArrayList<>();
                    messages = new ArrayList<>();
                    if (snapshot.child(name2).exists()) {
                        for (DataSnapshot s : snapshot.child(name2).getChildren()) {
                            names.add(s.child("name").getValue(String.class));
                            messages.add(s.child("message").getValue(String.class));
                        }
                        ListView lv = (ListView) findViewById(R.id.chat);
                        ChatPageAdapter chatPageAdapter = new ChatPageAdapter(getApplicationContext(), messages, names, username);
                        lv.setAdapter(chatPageAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            r.child("courses").child(course).child("groups").child(group).child("messages").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    names = new ArrayList<>();
                    messages = new ArrayList<>();

                    for (DataSnapshot s : snapshot.getChildren()) {
                        names.add(s.child("name").getValue(String.class));
                        messages.add(s.child("message").getValue(String.class));
                    }
                    ListView lv = (ListView) findViewById(R.id.chat);
                    ChatPageAdapter chatPageAdapter = new ChatPageAdapter(getApplicationContext(), messages, names, username);
                    lv.setAdapter(chatPageAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    public void back(View view) {
        finish();
    }

    public void send(View view) {
        EditText et = (EditText) findViewById(R.id.edit_gchat_message);
        if (Objects.equals(type, "single")) {
            r.child("users").child(username).child("messages").child(name2).child(names.size() + "")
                    .child("name").setValue(username);
            r.child("users").child(username).child("messages").child(name2).child(names.size() + "")
                    .child("message").setValue(et.getText().toString());
            r.child("users").child(name2).child("messages").child(username).child(names.size() + "")
                    .child("name").setValue(username);
            r.child("users").child(name2).child("messages").child(username).child(names.size() + "")
                    .child("message").setValue(et.getText().toString());
            et.setText("");
        } else {
            r.child("courses").child(course).child("groups").child(group).child("messages").child(names.size() + "")
                    .child("name").setValue(username);
            r.child("courses").child(course).child("groups").child(group).child("messages").child(names.size() + "")
                    .child("message").setValue(et.getText().toString());
            et.setText("");
        }
    }
}
