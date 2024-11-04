package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

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

        Button logoutIN = findViewById(R.id.loginButton);
        Button signupIN = findViewById(R.id.signupButton);
        EditText usernameIn = findViewById(R.id.usernameText);
        EditText passwordIN = findViewById(R.id.passwordText);
        signupIN.setOnClickListener((new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }

        }

        ));
        logoutIN.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String password = passwordIN.getText().toString();
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
                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}