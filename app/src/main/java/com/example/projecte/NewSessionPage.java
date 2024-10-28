package com.example.projecte;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.projecte.components.LockableScrollView;
import com.example.projecte.components.NewGroupAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import nl.joery.timerangepicker.TimeRangePicker;

public class NewSessionPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    int year, month, day;
    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Carol", "Billy", "Jackson", "Emily", "Sabrina", "Alex"
            , "Larry", "Bill", "Dick", "Richard", "Cleo", "Sophie", "Carlos", "Jose"));
    ArrayList<String> selected_names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_session_page);
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH)+1;
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        TextView tv = (TextView) findViewById(R.id.date);
        tv.setText("Date: " + year + "/" + month + "/" + day);
        TimeRangePicker picker = (TimeRangePicker) findViewById(R.id.picker);
        ListView lv = (ListView) findViewById(R.id.users_list);
        NewGroupAdapter newGroupAdapter = new NewGroupAdapter(getApplicationContext(), names,selected_names);
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
            }

            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                TextView end = (TextView) findViewById(R.id.end_time);
                end.setText("End: " + time.toString());
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
        month = i1+1;
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
}
