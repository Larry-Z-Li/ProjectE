package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EnrolledCoursesActivity extends AppCompatActivity {

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

        Button homeScreen = findViewById(R.id.HomePageButton);
        homeScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(EnrolledCoursesActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Button signoutB = findViewById(R.id.signoutButton);
        signoutB.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(EnrolledCoursesActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}