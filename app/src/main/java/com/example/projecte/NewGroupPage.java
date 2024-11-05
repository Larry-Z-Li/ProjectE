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
import com.example.projecte.components.NewGroupAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NewGroupPage extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>(Arrays.asList("Joe", "Bob" ,"Carol", "Billy" ,"Jackson", "Emily" ,"Sabrina", "Alex"
            ,"Larry","Bill","Dick","Richard","Cleo","Sophie","Carlos","Jose"));
    ArrayList<String> selected_names = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_group_page);
        listView = (ListView) findViewById(R.id.users_list);
        NewGroupAdapter newGroupAdapter = new NewGroupAdapter(getApplicationContext(),names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)view.findViewById(R.id.username);
                int loc = nameLoc(tv.getText().toString());
                if(loc == -1)
                {
                    view.setBackgroundColor(getResources().getColor(R.color.light_grey,null));
                    selected_names.add(tv.getText().toString());
                }
                else
                {
                    view.setBackgroundColor(getResources().getColor(R.color.white,null));
                    selected_names.remove(tv.getText().toString());
                }
            }
        });
        listView.setAdapter(newGroupAdapter);
    }

    public int nameLoc(String str)
    {
        if(selected_names.isEmpty())
            return -1;
        for(int i = 0; i < selected_names.size();i++)
        {
            if(selected_names.get(i).equals(str))
            {
                return i;
            }
        }
        return -1;
    }

    public void back(View view)
    {
        finish();
    }

    public void submit(View view)
    {

    }

}

