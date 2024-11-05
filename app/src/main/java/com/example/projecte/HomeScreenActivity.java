package com.example.projecte;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homescreen_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.homescreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        String username = (String) b.get("name");

        TextView tv = (TextView) findViewById(R.id.nameText);
        tv.setText(username);

        Button groups = findViewById(R.id.myGroups);
        groups.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this, GroupListPage.class);
                intent.putExtra("name", username);
                startActivity(intent);
            }
        });


        Button logoutHS = findViewById(R.id.logoutButton);
        logoutHS.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void back(View view)
    {
        finish();
    }
}
