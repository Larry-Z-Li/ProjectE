package com.example.projecte.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.projecte.R;

import java.util.ArrayList;
import java.util.List;

public class ResourcePageAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<String> originalData;
    private List<String> filteredData;
    private ItemFilter mFilter = new ItemFilter();

    public ResourcePageAdapter(Context context, String[] data) {
        this.context = context;
        this.originalData = new ArrayList<>();
        for (String item : data) {
            originalData.add(item);
        }
        this.filteredData = originalData;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.resource_item, parent, false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.resource_type_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(filteredData.get(position));
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // Custom filter implementation
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase().trim();
            FilterResults results = new FilterResults();

            final List<String> list = originalData;
            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<>(count);

            for (String item : list) {
                if (item.toLowerCase().contains(filterString)) {
                    nlist.add(item);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
