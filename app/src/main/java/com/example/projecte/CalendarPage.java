package com.example.projecte;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecte.components.CalendarPageAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Arrays;

public class CalendarPage extends AppCompatActivity {


    ArrayList<String> titles = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));
    ArrayList<String> locations = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));
    ArrayList<String> times = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));
    ArrayList<String> invitees = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calendar_page);

        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        ListView lv = (ListView) findViewById(R.id.events);
        OnDateSelectedListener listener = new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // fetch backend for sessions on date and set it to listView
                Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                CalendarPageAdapter adapter = new CalendarPageAdapter(getApplicationContext(), titles, locations, times, invitees);
                lv.setAdapter(adapter);
            }
        };
        mcv.setOnDateChangedListener(listener);
        DayViewDecorator d = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                //if a member is not available on that day, mark it.

                if (day.getDay() % 5 == 0) {
                    return true;
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(15, R.color.cyan));
            }
        };
        mcv.addDecorator(d);

    }

    public void resources(View view) {
    }
}
