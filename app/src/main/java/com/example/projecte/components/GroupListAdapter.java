package com.example.projecte.components;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projecte.GroupListPage;
import com.example.projecte.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class GroupListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> groups;
    ArrayList<String> joinedGroups;
    LayoutInflater inflater;
    String username;


    public GroupListAdapter(Context context, ArrayList<String> groups, ArrayList<String> joinedGroups, String username)
    {
        this.context = context;
        this.groups = groups;
        this.joinedGroups = joinedGroups;
        inflater = LayoutInflater.from(context);
        this.username = username;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.group_list_item,null);
        TextView textView = (TextView) view.findViewById(R.id.group_list_name);
        ImageView imageView = (ImageView) view.findViewById(R.id.group_list_join_button);
        textView.setText(groups.get(i));
        if(!joinedGroups.contains(groups.get(i)))
        {
            imageView.setImageResource(R.drawable.add_button);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseReference r = FirebaseDatabase.getInstance().getReference();
                    r.child("courses").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Boolean found = false;
                            for(DataSnapshot j : snapshot.getChildren())
                            {
                                if(found)
                                    break;
                                for(DataSnapshot k : j.getChildren())
                                {
                                    if(found)
                                        break;
                                    if(Objects.equals(k.getKey(), "groups"))
                                    {
                                        for(DataSnapshot l: k.getChildren())
                                        {
                                            if(found)
                                                break;
                                            if(Objects.equals(l.getKey(), groups.get(i)))
                                            {
                                                for(DataSnapshot m : l.getChildren())
                                                {
                                                    if(found)
                                                        break;
                                                    if(Objects.equals(m.getKey(), "members"))
                                                    {
                                                        r.child("courses").child(j.getKey()).child(k.getKey()).child(l.getKey())
                                                                .child(m.getKey()).push().setValue(username);
                                                        imageView.setVisibility(View.INVISIBLE);
                                                        found = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            r.child("users").child(username).child("groups").push().setValue(groups.get(i));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }
        else
        {
            imageView.setVisibility(View.INVISIBLE);
        }

        return view;
    }

}
