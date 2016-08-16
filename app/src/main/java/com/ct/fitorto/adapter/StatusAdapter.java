package com.ct.fitorto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.City;
import com.ct.fitorto.model.FitortoStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvind on 8/15/16.
 */
public class StatusAdapter extends ArrayAdapter<FitortoStatus> {


    private Context context;
    private ArrayList<FitortoStatus> cityList;

    public StatusAdapter(Context context, int resource, ArrayList<FitortoStatus> city) {
        super(context, resource, city);
        this.context = context;
        this.cityList = city;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.city_list_item, parent, false);
        FitortoStatus sr = cityList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.city_item);
        tv.setText(sr.getStatus());
        return view;
    }
}