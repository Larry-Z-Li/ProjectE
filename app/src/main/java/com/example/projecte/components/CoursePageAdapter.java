package com.example.projecte.components;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.projecte.R;

public class CoursePageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> courses;
    private LayoutInflater inflater;

    public CoursePageAdapter(Context context, ArrayList<String> courses) {
        this.context = context;
        this.courses = courses;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.course_list, parent, false);
        }

        TextView tv = convertView.findViewById(R.id.courseName);
        tv.setText(courses.get(position));

        return convertView;
    }

}