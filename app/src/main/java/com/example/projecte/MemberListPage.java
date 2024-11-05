package com.example.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projecte.components.GroupListAdapter;
import com.example.projecte.components.MemberListAdapter;
import com.example.projecte.components.NewGroupAdapter;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MemberListPage extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob", "Carol", "Billy", "Jackson", "Emily", "Sabrina", "Alex"
            , "Larry", "Bill", "Dick", "Richard", "Cleo", "Sophie", "Carlos", "Jose"));

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.member_list_page);
        listView = (ListView) findViewById(R.id.list);
        MemberListAdapter memberListAdapter = new MemberListAdapter(getApplicationContext(),names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MemberListPage.this, MemberListItem.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(memberListAdapter);
    }

    public void back(View view)
    {
        finish();
    }

    public void openCalendar(View view){
        Intent intent = new Intent(MemberListPage.this, CalendarPage.class);
        startActivity(intent);
    }

    public void openChat(View view){
        Intent intent = new Intent(MemberListPage.this, ChatPage.class);
        startActivity(intent);
    }

    public void openNewSession(View view){
        Intent intent = new Intent(MemberListPage.this, NewSessionPage.class);
        startActivity(intent);
    }



}

