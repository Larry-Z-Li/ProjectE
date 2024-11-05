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

//    String[] test = {"test1", "test2" ,"test3", "test4" ,"test5", "test6" ,"test7", "test8"
//    ,"test9","test10","test11","test12","test13","test14","test15","test16","test17"};
//    Boolean[] joined = {true, false, true, false, false, true, false, true , true, true,
//            false,true, false, true , true, true, false};

    ArrayList<String> groups = new ArrayList<>();
    ArrayList<String> courses = new ArrayList<>();
    ArrayList<String> joinedGroups = new ArrayList<>();
    GroupListAdapter groupListAdapter;

    String username;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.group_list_page);

//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//        if (b == null) {
//            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
//        }
//        username = (String) b.get("name");
        username = "test";


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference r = db.getReference();
        r.child("users").child(username).child("courses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren())
                {
                    courses.add(s.getValue(String.class));
                }

                r.child("courses").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        groups = new ArrayList<>();
                        for(DataSnapshot i: snapshot.getChildren())
                        {
                            Toast.makeText(getApplicationContext(), ""+courses.size(), Toast.LENGTH_SHORT).show();

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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(GroupListPage.this, "uhoh", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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