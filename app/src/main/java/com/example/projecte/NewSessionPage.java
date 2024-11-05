package com.example.projecte;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.projecte.components.LockableScrollView;
import com.example.projecte.components.NewGroupAdapter;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import nl.joery.timerangepicker.TimeRangePicker;

public class NewSessionPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    int year, month, day;
    String starttime, endttime;
    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Carol", "Billy", "Jackson", "Emily", "Sabrina", "Alex"
            , "Larry", "Bill", "Dick", "Richard", "Cleo", "Sophie", "Carlos", "Jose"));
    ArrayList<String> selected_names = new ArrayList<>();
    String username, course, group;
    DatabaseReference r = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_session_page);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        username = (String) b.get("name");
        course = (String) b.get("course");
        group = (String) b.get("group");

        r.child("courses").child(course).child("groups").child(group).child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                names = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    names.add(s.getValue(String.class));
                }
                names.remove(username);
                ListView lv = (ListView) findViewById(R.id.users_list);
                NewGroupAdapter newGroupAdapter = new NewGroupAdapter(getApplicationContext(), names, selected_names);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                lv.setAdapter(newGroupAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        TextView tv = (TextView) findViewById(R.id.date);
        tv.setText("Date: " + year + "/" + month + "/" + day);
        TimeRangePicker picker = (TimeRangePicker) findViewById(R.id.picker);

        LockableScrollView sv = (LockableScrollView) findViewById(R.id.new_session_page);
        picker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    sv.setScrollingEnabled(false);
                else if (event.getAction() == MotionEvent.ACTION_UP)
                    sv.setScrollingEnabled(true);
                return false;
            }
        });


        picker.setOnTimeChangeListener(new TimeRangePicker.OnTimeChangeListener() {
            @Override
            public void onStartTimeChange(@NonNull TimeRangePicker.Time time) {
                TextView start = (TextView) findViewById(R.id.start_time);
                start.setText("Start: " + time.toString());
                starttime = time.toString();
            }

            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                TextView end = (TextView) findViewById(R.id.end_time);
                end.setText("End: " + time.toString());
                endttime = time.toString();
            }

            @Override
            public void onDurationChange(@NonNull TimeRangePicker.TimeDuration timeDuration) {

            }
        });
    }

    public void datePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1 + 1;
        day = i2;
        TextView tv = (TextView) findViewById(R.id.date);
        tv.setText("Date: " + year + "/" + month + "/" + day);
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
        EditText et = (EditText) findViewById(R.id.title);
        if(et.getText().toString().isEmpty())
        {
            TextView error = (TextView) findViewById(R.id.submit_error);
            error.setText("Please fill out name");
            return;
        }
        EditText et2 = (EditText) findViewById(R.id.location);
        if(et2.getText().toString().isEmpty())
        {
            TextView error = (TextView) findViewById(R.id.submit_error);
            error.setText("Please fill out location");
            return;
        }


        DatabaseReference newr = r.child("courses").child(course).child("groups").child(group).child("sessions")
                .push();
        selected_names.add(username);

        newr.child("date").setValue(year+"/"+month+"/"+day);
        newr.child("endtime").setValue(endttime);
        newr.child("starttime").setValue(starttime);
        newr.child("name").setValue(et.getText().toString());
        newr.child("location").setValue(et2.getText().toString());
        newr.child("members").setValue(selected_names);


        for(String s : selected_names)
        {
            r.child("users").child(s).child("busy").push().setValue(year+"/"+month+"/"+day);
        }
        finish();
    }
}
