package com.example.projecte.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projecte.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CalendarPageAdapter extends BaseAdapter {
    ArrayList<String> titles, locations, times, invitees;
    LayoutInflater inflater;
    Context context;
    public CalendarPageAdapter(Context context, ArrayList<String> titles, ArrayList<String> locations, ArrayList<String> times, ArrayList<String> invitees)
    {
        this.context = context;
        this.titles = titles;
        this.locations = locations;
        this.times = times;
        this.invitees = invitees;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.size();
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
        view = inflater.inflate(R.layout.calendar_item,null);
        TextView tv1 = (TextView) view.findViewById(R.id.title);
        TextView tv2 = (TextView) view.findViewById(R.id.location);
        TextView tv3 = (TextView) view.findViewById(R.id.time);
        TextView tv4 = (TextView) view.findViewById(R.id.invitees);
        tv1.setText("Title: " + titles.get(i));
        tv2.setText("Location: " +locations.get(i));
        tv3.setText("Time: " +times.get(i));
        tv4.setText("Invitees: " +invitees.get(i));
        return view;
    }
}
