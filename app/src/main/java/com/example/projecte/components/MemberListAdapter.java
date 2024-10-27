package com.example.projecte.components;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projecte.MemberListPage;
import com.example.projecte.R;

import java.util.ArrayList;

public class MemberListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> names;
    LayoutInflater inflater;

    public MemberListAdapter(Context context, ArrayList<String> names)
    {
        this.context = context;
        this.names = names;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return names.size();
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
        view = inflater.inflate(R.layout.member_list_item,null);
        TextView tv = (TextView) view.findViewById(R.id.username);
        tv.setText(names.get(i));
        return view;
    }
}
