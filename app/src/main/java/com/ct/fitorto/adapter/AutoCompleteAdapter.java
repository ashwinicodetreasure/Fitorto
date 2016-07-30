package com.ct.fitorto.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.ct.fitorto.model.categoryName;

import java.util.ArrayList;

public class AutoCompleteAdapter extends ArrayAdapter<categoryName> implements
        Filterable {

    private ArrayList<categoryName> fullList;
    private ArrayList<categoryName> mOriginalValues;
    private ArrayFilter mFilter;

    public AutoCompleteAdapter(Context context, int resource,
                               int textViewResourceId, ArrayList<categoryName> fullList) {

        super(context, resource, textViewResourceId, fullList);
        this.fullList = fullList;
        mOriginalValues = new ArrayList<categoryName>(fullList);

    }


    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public categoryName getItem(int position) {
        return fullList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (lock) {
                    mOriginalValues = new ArrayList<categoryName>(fullList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<categoryName> list = new ArrayList<categoryName>(
                            mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                ArrayList<categoryName> values = mOriginalValues;
                int count = values.size();

                ArrayList<categoryName> newValues = new ArrayList<categoryName>(count);

                for (int i = 0; i < count; i++) {
                    categoryName item = values.get(i);
                    if (item.getCategoryName().toLowerCase().contains(prefixString)) {
                        newValues.add(item);
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            if (results.values != null) {
                fullList = (ArrayList<categoryName>) results.values;
            } else {
                fullList = new ArrayList<categoryName>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}