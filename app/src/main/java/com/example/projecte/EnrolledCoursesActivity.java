package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


import java.util.ArrayList;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.example.projecte.components.CoursePageAdapter;

import java.util.ArrayList;

public class EnrolledCoursesActivity extends AppCompatActivity {

    private ListView listView;
    private CoursePageAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.enrolled_courses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.enrolledCourses), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b == null)
        {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        String username = (String)b.get("name");

        listView = findViewById(R.id.listCourses);

        ArrayList<String> testCourses = new ArrayList<>();
        testCourses.add("CSCI 102");
        testCourses.add("CSCI 103");
        testCourses.add("CSCI 104");
        testCourses.add("CSCI 201");
        testCourses.add("CSCI 310");
        testCourses.add("CSCI 350");


        courseAdapter = new CoursePageAdapter(this, testCourses);
        listView.setAdapter(courseAdapter);

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