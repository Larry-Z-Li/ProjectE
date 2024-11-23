package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.example.projecte.components.GroupListAdapter;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class GroupListPage extends AppCompatActivity {

    ArrayList<String> groups = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    ArrayList<String> joinedGroups = new ArrayList<>();
    GroupListAdapter groupListAdapter;

    String username;
    ListView listView;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference r = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.group_list_page);
        if(username == null)
        {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            if (b == null) {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
            username = (String) b.get("name");
        }
        getCourses();

    }
    public void getCourses()
    {
        r.child("users").child(username).child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren())
                {
                    courses.add(s.getValue(String.class));
                }

                getGroups();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getGroups() {
        r.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groups = new ArrayList<>();
                for(DataSnapshot i: snapshot.getChildren())
                {

                    if(courses.contains(i.getKey()))
                    {

                        for(DataSnapshot j: i.getChildren())
                        {
                            if(Objects.equals(j.getKey(), "groups"))
                            {
                                for(DataSnapshot k: j.getChildren())
                                {
                                    groups.add(k.getKey());
                                }
                            }
                        }
                    }
                }
                getJoinedGroups();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GroupListPage.this, "uhoh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getJoinedGroups()
    {
        r.child("users").child(username).child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joinedGroups = new ArrayList<>();
                for(DataSnapshot i: snapshot.getChildren())
                {
                    joinedGroups.add(i.getValue(String.class));
                }
                listView = (ListView) findViewById(R.id.group_list_page);
                groupListAdapter = new GroupListAdapter(getApplicationContext(),groups, joinedGroups,username);
                listView.setAdapter(groupListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(view.findViewById(R.id.group_list_join_button).getVisibility() == View.INVISIBLE)
                        {
                            TextView tv = (TextView)view.findViewById(R.id.group_list_name);
                            Intent intent = new Intent(GroupListPage.this,MemberListPage.class);
                            intent.putExtra("name",username);
                            intent.putExtra("group",tv.getText());
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GroupListPage.this, "uhoh", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onNewGroupClick(View view)
    {
        Intent intent = new Intent(GroupListPage.this,NewGroupStartPage.class);
        intent.putExtra("name",username);
        startActivity(intent);
    }

    public void back(View view)
    {
        finish();
    }

}