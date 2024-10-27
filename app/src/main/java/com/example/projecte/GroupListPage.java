package com.example.projecte;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecte.components.GroupListAdapter;

public class GroupListPage extends AppCompatActivity {

    String[] test = {"test1", "test2" ,"test3", "test4" ,"test5", "test6" ,"test7", "test8"
    ,"test9","test10","test11","test12","test13","test14","test15","test16","test17"};
    Boolean[] joined = {true, false, true, false, false, true, false, true , true, true,
            false,true, false, true , true, true, false};

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.group_list_page);
        listView = (ListView) findViewById(R.id.group_list_page);
        GroupListAdapter groupListAdapter = new GroupListAdapter(getApplicationContext(),test);
        listView.setAdapter(groupListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GroupListPage.this,"clicked group", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onNewGroupClick(View view)
    {
        Toast.makeText(GroupListPage.this,"clicked new group", Toast.LENGTH_SHORT).show();
    }

}