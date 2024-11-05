package com.example.projecte;


import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {

    List<String> courses = new ArrayList<>(Arrays.asList("Add Courses", "test"));
    ArrayList<String> allUsers = new ArrayList<>();
    List<String> selectedCourses;

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

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference();

        reference.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses = new ArrayList<>();
                courses.add("Add Courses");

                for (DataSnapshot s : snapshot.getChildren()) {
                    courses.add(s.getKey());

                }
                Spinner dropdownCourses = findViewById(R.id.dropdownCourses);
                FlexboxLayout selectedCoursesAttribute = findViewById(R.id.selectedCourses); // Ensure this is FlexboxLayout

                //dropdownCourses.setOnItemSelectedListener(this);
                selectedCourses = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, courses);
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

//                Toast.makeText(getApplicationContext(),tempcourses.size(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        reference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    allUsers.add(s.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                if (!(password.equals(passwordConf))) {
                    passwordIN.setError("Passwords Should Match");
                    passwordIN.requestFocus();
                    return;
                }
                if (allUsers.contains(username)) {
                    usernameIn.setError("That username already exists");
                    usernameIn.requestFocus();
                    return;
                }
                if (selectedCourses.isEmpty()) {
                    passwordINConf.setError("Select at least one class");
                    return;
                }
                reference.child("users").child(username).child("password").setValue(password);
                reference.child("users").child(username).child("messages").setValue("");
                reference.child("users").child(username).child("groups").setValue("");
                reference.child("users").child(username).child("busy").setValue("");
                for (int i = 0; i < selectedCourses.size(); i++) {
                    reference.child("users").child(username).child("courses").child(i+"")
                            .setValue(selectedCourses.get(i));
                    reference.child("courses").child(selectedCourses.get(i)).child("members").push().setValue(username);
                }

                Intent intent = new Intent(SignupActivity.this, EnrolledCoursesActivity.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });
    }

    public void back(View view) {
        finish();
    }

}