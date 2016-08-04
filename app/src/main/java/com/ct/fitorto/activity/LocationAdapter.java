package com.ct.fitorto.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.City;
import com.ct.fitorto.model.Location;

import java.util.List;

/**
 * Created by codetreasure on 7/1/16.
 */
public class LocationAdapter  extends ArrayAdapter<Location> {


    private Context context;
    private List<Location> cityList;

    public LocationAdapter(Context context, int resource, List<Location> city) {
        super(context, resource, city);
        this.context = context;
        this.cityList = city;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.city_list_item, parent, false);
        Location sr = cityList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.city_item);
        tv.setText(sr.getArea()+", "+sr.getCity());//+"\n" +sr.getArea()+"\n"+sr.getCity());
        return view;
    }
}