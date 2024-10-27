package com.example.projecte.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecte.GroupListPage;
import com.example.projecte.R;

public class GroupListAdapter extends BaseAdapter {

    Context context;
    String listNames[];
    LayoutInflater inflater;


    public GroupListAdapter(Context context, String[] listNames)
    {
        this.context = context;
        this.listNames = listNames;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listNames.length;
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
        textView.setText(listNames[i]);
        imageView.setImageResource(R.drawable.add_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked join group", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
