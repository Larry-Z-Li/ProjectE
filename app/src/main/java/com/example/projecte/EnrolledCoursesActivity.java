package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.example.projecte.components.CoursePageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnrolledCoursesActivity extends AppCompatActivity {

    private ListView listView;
    private CoursePageAdapter courseAdapter;
    ArrayList<String> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.enrolled_courses);

        DatabaseReference r = FirebaseDatabase.getInstance().getReference();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b == null)
        {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        String username = (String)b.get("name");

        listView = findViewById(R.id.listCourses);

        r.child("users").child(username).child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses = new ArrayList<>();
                for(DataSnapshot s: snapshot.getChildren())
                {
                    courses.add(s.getValue(String.class));
                    courseAdapter = new CoursePageAdapter(getApplicationContext(), courses);
                    listView.setAdapter(courseAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button homeScreen = findViewById(R.id.HomePageButton);
        homeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(EnrolledCoursesActivity.this, HomeScreenActivity.class);
                intent.putExtra("name",username);
                startActivity(intent);
            }
        });

        Button signoutB = findViewById(R.id.signoutButton);
        signoutB.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(EnrolledCoursesActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}