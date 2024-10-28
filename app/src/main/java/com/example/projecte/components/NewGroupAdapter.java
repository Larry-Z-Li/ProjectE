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

import com.example.projecte.R;

import java.util.ArrayList;


public class NewGroupAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> listNames;
    ArrayList<String> selected_names =new ArrayList<>();
    LayoutInflater inflater;


    public NewGroupAdapter(Context context, ArrayList<String> listNames)
    {
        this.context = context;
        this.listNames = listNames;
        inflater = LayoutInflater.from(context);
    }
    public NewGroupAdapter(Context context, ArrayList<String> listNames, ArrayList<String> selected_names)
    {
        this.context = context;
        this.listNames = listNames;
        this.selected_names = selected_names;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listNames.size();
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
        view = inflater.inflate(R.layout.new_group_item,null);
        TextView textView = (TextView) view.findViewById(R.id.username);

        textView.setText(listNames.get(i));
        if(selected_names.contains(listNames.get(i)))
        {
            view.setBackgroundColor(context.getColor(R.color.light_grey));
        }
        return view;
    }

}
