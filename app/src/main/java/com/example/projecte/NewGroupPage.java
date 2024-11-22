package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecte.components.GroupListAdapter;
import com.example.projecte.components.NewGroupAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NewGroupPage extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Carol", "Billy", "Jackson", "Emily", "Sabrina", "Alex"
            , "Larry", "Bill", "Dick", "Richard", "Cleo", "Sophie", "Carlos", "Jose"));
    ArrayList<String> selected_names = new ArrayList<>();
    ListView listView;
    String username, course;
    Boolean error = true;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference r = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        username = (String) b.get("name");
        course = (String) b.get("course");

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_group_page);

        r.child("courses").child(course).child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                names = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    names.add(s.getValue(String.class));
                    names.remove(username);
                }
                listView = (ListView) findViewById(R.id.users_list);
                NewGroupAdapter newGroupAdapter = new NewGroupAdapter(getApplicationContext(), names);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView tv = (TextView) view.findViewById(R.id.username);
                        int loc = nameLoc(tv.getText().toString());
                        if (loc == -1) {
                            view.setBackgroundColor(getResources().getColor(R.color.light_grey, null));
                            selected_names.add(tv.getText().toString());
                        } else {
                            view.setBackgroundColor(getResources().getColor(R.color.white, null));
                            selected_names.remove(tv.getText().toString());
                        }
                    }
                });
                listView.setAdapter(newGroupAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public int nameLoc(String str) {
        if (selected_names.isEmpty())
            return -1;
        for (int i = 0; i < selected_names.size(); i++) {
            if (selected_names.get(i).equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public void back(View view) {

        finish();
    }

    public void submit(View view) {
        EditText et = (EditText) findViewById(R.id.group_name);
        TextView error_text = (TextView) findViewById(R.id.submit_error);
        if (et.getText().toString().isEmpty()) {
            error_text.setText("Enter group name");
            return;
        }
        selected_names.add(username);

        r.child("courses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EditText et = (EditText) findViewById(R.id.group_name);
                error = false;
                for (DataSnapshot i : snapshot.getChildren()) {
                    if(error)
                        break;
                    for (DataSnapshot j : i.getChildren()) {
                        if(error)
                            break;
                        if (Objects.equals(j.getKey(), "groups")) {
                            for (DataSnapshot k : j.getChildren()) {
                                if(error)
                                    break;
                                if (Objects.equals(k.getKey(), et.getText().toString())) {
                                    error = true;
                                }
                            }
                        }
                    }
                }
                if (!error) {
                    r.child("courses").child(course).child("groups").child(et.getText().toString())
                            .child("members").setValue(selected_names);
                    r.child("courses").child(course).child("groups").child(et.getText().toString())
                            .child("messages").setValue("");
                    r.child("courses").child(course).child("groups").child(et.getText().toString())
                            .child("resources").setValue("");
                    r.child("courses").child(course).child("groups").child(et.getText().toString())
                            .child("sessions").setValue("");
                    for (String user : selected_names) {
                        DatabaseReference tr = r.child("users").child(user).child("groups").push();
                        tr.setValue(et.getText().toString());
                    }
                    finish();
                } else {
                    TextView error_text = (TextView) findViewById(R.id.submit_error);
                    error_text.setText("Group name already exists");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}

