package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

public class NewGroupStartPage extends AppCompatActivity {

    List<String> joinedCourses;
    String username, course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_group_start_page);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        username = (String) b.get("name");

        DatabaseReference r = FirebaseDatabase.getInstance().getReference();
        r.child("users").child(username).child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joinedCourses = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    joinedCourses.add(s.getValue(String.class));
                }
                Spinner spinner = (Spinner) findViewById(R.id.dropdownCourses);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, joinedCourses);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        course = joinedCourses.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void submit(View view)
    {
        Intent intent = new Intent(NewGroupStartPage.this, NewGroupPage.class);
        intent.putExtra("name",username);
        intent.putExtra("course",course);
        startActivity(intent);
        finish();
    }
}

