package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecte.components.GroupListAdapter;
import com.example.projecte.components.MemberListAdapter;
import com.example.projecte.components.NewGroupAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MemberListPage extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();

    String username, group, course;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.member_list_page);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        username = (String) b.get("name");
        group = (String) b.get("group");

        DatabaseReference r = FirebaseDatabase.getInstance().getReference();
        r.child("courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot i : snapshot.getChildren()) {
                    for (DataSnapshot j : i.getChildren()) {
                        if (Objects.equals(j.getKey(), "groups")) ;
                        {
                            for (DataSnapshot k : j.getChildren()) {
                                if (Objects.equals(k.getKey(), group)) {
                                    course = i.getKey();
                                    for (DataSnapshot l : k.getChildren()) {
                                        if (Objects.equals(l.getKey(), "members")) {
                                            for (DataSnapshot m : l.getChildren()) {
                                                names.add(m.getValue(String.class));
                                            }
                                            names.remove(username);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                listView = (ListView) findViewById(R.id.list);
                MemberListAdapter memberListAdapter = new MemberListAdapter(getApplicationContext(), names);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MemberListPage.this, ChatPage.class);
                        intent.putExtra("name", username);
                        intent.putExtra("name2", names.get(i));
                        intent.putExtra("type", "single");
                        startActivity(intent);
                    }
                });
                listView.setAdapter(memberListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void back(View view) {
        finish();
    }

    public void chat(View view) {
        Intent intent = new Intent(MemberListPage.this, ChatPage.class);
        intent.putExtra("name", username);
        intent.putExtra("type", "group");
        intent.putExtra("course", course);
        intent.putExtra("group", group);
        startActivity(intent);

    }

    public void new_session(View view) {
        Intent intent = new Intent(MemberListPage.this, NewSessionPage.class);
        intent.putExtra("name", username);
        intent.putExtra("course", course);
        intent.putExtra("group", group);
        startActivity(intent);
    }

    public void calendar(View view) {
        Intent intent = new Intent(MemberListPage.this, CalendarPage.class);
        intent.putExtra("name", username);
        intent.putExtra("course", course);
        intent.putExtra("group", group);
        startActivity(intent);
    }

}

