package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.Color;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.android.flexbox.FlexboxLayout;
import android.widget.TextView;
import android.widget.EditText;



public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner dropdownCourses = findViewById(R.id.dropdownCourses);
        FlexboxLayout selectedCoursesAttribute = findViewById(R.id.selectedCourses); // Ensure this is FlexboxLayout

        //dropdownCourses.setOnItemSelectedListener(this);
        List<String> courses = new ArrayList<>(Arrays.asList("Add Courses", "CSCI 102", "CSCI 103", "CSCI 104", "CSCI 170", "CSCI 201", "CSCI 310", "CSCI 360", "CSCI 356", "CSCI 350"));
        List<String> selectedCourses = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdownCourses.setAdapter(adapter);

        dropdownCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) return;
                String selectedCourse = courses.get(i);
                selectedCourses.add(selectedCourse);
                TextView chip = new TextView(SignupActivity.this);
                chip.setText(selectedCourse);
                chip.setPadding(16, 8, 16, 8);
                chip.setTextColor(Color.WHITE);
                chip.setBackgroundResource(R.drawable.course_selected_background); // Define chip background
                chip.setLayoutParams(new FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                ));

                selectedCoursesAttribute.addView(chip);
                courses.remove(selectedCourse);
                adapter.notifyDataSetChanged();
                dropdownCourses.setSelection(0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            });

            Button signupB = findViewById(R.id.loginButton);
            EditText usernameIn = findViewById(R.id.usernameText);
            EditText passwordIN = findViewById(R.id.passwordText);
            EditText passwordINConf = findViewById(R.id.passwordTextConfirm);
            signupB.setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {
                String password = passwordIN.getText().toString();
                String passwordConf = passwordINConf.getText().toString();
                String username = usernameIn.getText().toString();
                if (username.isEmpty()) {
                    usernameIn.setError("All fields should be filled");
                    usernameIn.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordIN.setError("All fields should be filled");
                    passwordIN.requestFocus();
                    return;
                }
                if (passwordConf.isEmpty()) {
                    passwordINConf.setError("All fields should be filled");
                    passwordINConf.requestFocus();
                    return;
                }
                if(!(password.equals(passwordConf)))
                {
                    passwordIN.setError("Passwords Should Match");
                    passwordIN.requestFocus();
                    return;
                }
                Intent intent = new Intent(SignupActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }



}