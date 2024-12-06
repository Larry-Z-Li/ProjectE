package com.example.projecte.components;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.projecte.ChatPage;
import com.example.projecte.PdfViewerActivity;
import com.example.projecte.R;

import android.content.Intent;

import java.util.ArrayList;

public class ChatPageAdapter extends BaseAdapter {

    ArrayList<String> names;
    ArrayList<String> messages;
    Context context;
    LayoutInflater inflater;
    String myName;

    public ChatPageAdapter(Context context, ArrayList<String> messages, ArrayList<String> names, String myName) {
        this.context = context;
        this.names = names;
        this.messages = messages;
        inflater = LayoutInflater.from(context);
        this.myName = myName;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        return false;
//    }

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
        if (myName.equals(names.get(i))) {
            view = inflater.inflate(R.layout.my_text, null);
            TextView tv = (TextView) view.findViewById(R.id.message);
            if (messages.get(i) == null) {
                return view;
            }
            if (messages.get(i).startsWith("resource:")) {
                tv.setText("Click to view resource");
                tv.setTypeface(null, Typeface.BOLD_ITALIC);
            } else {
                tv.setText(messages.get(i));
            }

        } else {
            view = inflater.inflate(R.layout.their_text, null);
            TextView tv = (TextView) view.findViewById(R.id.name);
            tv.setText(names.get(i));
            TextView tv2 = (TextView) view.findViewById(R.id.message);
            if (messages.get(i) == null) {
                return view;
            }
            if (messages.get(i).startsWith("resource:")) {
                tv2.setText("Click to view resource");
                tv2.setTypeface(null, Typeface.BOLD_ITALIC);
            } else {
                tv2.setText(messages.get(i));
            }
        }

        return view;
    }
}
