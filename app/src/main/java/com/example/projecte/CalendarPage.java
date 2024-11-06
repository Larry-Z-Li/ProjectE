package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecte.components.CalendarPageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CalendarPage extends AppCompatActivity {


    ArrayList<String> titles = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));
    ArrayList<String> locations = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));
    ArrayList<String> starttimes = new ArrayList<>(Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8"
            , "test9", "test10", "test11", "test12", "test13", "test14", "test15"));
    ArrayList<String> endtimes;
    ArrayList<String> dates;
    ArrayList<ArrayList<String>> invitees;
    String username, group, course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calendar_page);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b == null) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        username = (String) b.get("name");
        group = (String) b.get("group");
        course = (String) b.get("course");

        DatabaseReference r = FirebaseDatabase.getInstance().getReference();
        r.child("courses").child(course).child("groups").child(group).child("sessions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                titles = new ArrayList<>();
                locations = new ArrayList<>();
                endtimes = new ArrayList<>();
                starttimes = new ArrayList<>();
                dates = new ArrayList<>();
                invitees = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()) {
                    titles.add(s.child("name").getValue(String.class));
                    locations.add(s.child("location").getValue(String.class));
                    starttimes.add(s.child("starttime").getValue(String.class));
                    endtimes.add(s.child("endtime").getValue(String.class));
                    dates.add(s.child("date").getValue(String.class));

                    ArrayList<String> t = new ArrayList<>();
                    for (DataSnapshot i : s.child("members").getChildren()) {
                        t.add(i.getValue(String.class));
                    }
                    invitees.add(t);
                }
                MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
                ListView lv = (ListView) findViewById(R.id.events);
                OnDateSelectedListener listener = new OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                        // fetch backend for sessions on date and set it to listView
                        ArrayList<String> Ttitles = new ArrayList<>();
                        ArrayList<String> Tlocations = new ArrayList<>();
                        ArrayList<String> Tendtimes = new ArrayList<>();
                        ArrayList<String> Tstarttimes = new ArrayList<>();
                        ArrayList<ArrayList<String>> Tinvitees = new ArrayList<>();
                        String t = date.getYear() + "/" + date.getMonth() + "/" + date.getDay();
                        for (int i = 0; i < dates.size(); i++) {
                            if (t.equals(dates.get(i))) {
                                Ttitles.add(titles.get(i));
                                Tlocations.add(locations.get(i));
                                Tendtimes.add(endtimes.get(i));
                                Tstarttimes.add(starttimes.get(i));
                                Tinvitees.add(invitees.get(i));
                            }
                        }
                        CalendarPageAdapter adapter = new CalendarPageAdapter(getApplicationContext(), Ttitles, Tlocations, Tstarttimes, Tendtimes, Tinvitees);
                        lv.setAdapter(adapter);
                    }
                };
                mcv.setOnDateChangedListener(listener);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        r.child("courses").child(course).child("groups").child(group).child("members")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> members = new ArrayList<>();
                        ArrayList<String> selected_dates = new ArrayList<>();
                        for (DataSnapshot s : snapshot.getChildren()) {
                            members.add(s.getValue(String.class));
                        }
                        r.child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);

                                for (DataSnapshot s : snapshot.getChildren()) {
                                    if (members.contains(s.getKey())) {
                                        for (DataSnapshot j : s.getChildren()) {
                                            if (Objects.equals(j.getKey(), "busy")) {
                                                for (DataSnapshot k : j.getChildren()) {
                                                    selected_dates.add(k.getValue(String.class));
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }

                                DayViewDecorator d = new DayViewDecorator() {
                                    @Override
                                    public boolean shouldDecorate(CalendarDay day) {
                                        //if a member is not available on that day, mark it.
                                        String master = day.getYear()+"/"+day.getMonth()+"/"+day.getDay();
                                        if(selected_dates.contains(master))
                                        {
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

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void resources(View view) {

        Intent intent = new Intent(CalendarPage.this, ResourcePage.class);
        startActivity(intent);
    }
    public void back(View view)
    {
        finish();
    }
}
