package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ArrayList<String> allUsers = new ArrayList<>();
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference();


        Button logoutIN = findViewById(R.id.loginButton);
        Button signupIN = findViewById(R.id.signupButton);
        EditText usernameIn = findViewById(R.id.usernameText);
        EditText passwordIN = findViewById(R.id.passwordText);
        signupIN.setOnClickListener((new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }

        }

        ));
        logoutIN.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                password = passwordIN.getText().toString();
                username = usernameIn.getText().toString();
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
                reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(username))
                        {
                            if(!Objects.equals((String) snapshot.child(username).child("password").getValue(), password))
                            {
                                passwordIN.setError("Password incorrect");
                                passwordIN.requestFocus();
                                return;
                            }
                            else
                            {
                                Intent intent = new Intent(LoginActivity.this, EnrolledCoursesActivity.class);
                                intent.putExtra("name",username);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            passwordIN.setError("Password incorrect");
                            passwordIN.requestFocus();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


}