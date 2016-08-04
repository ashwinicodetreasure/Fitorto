package com.ct.fitorto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.City;

import java.util.List;

/**
 * Created by Ashwini on 5/26/2016.
 */
public class CityAdapter extends ArrayAdapter<City> {


    private Context context;
    private List<City> cityList;

    public CityAdapter(Context context, int resource, List<City> city) {
        super(context, resource, city);
        this.context = context;
        this.cityList = city;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.city_list_item, parent, false);
        City sr = cityList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.city_item);
        tv.setText(sr.getCityName());//+"\n" +sr.getArea()+"\n"+sr.getCity());
        return view;
    }
}