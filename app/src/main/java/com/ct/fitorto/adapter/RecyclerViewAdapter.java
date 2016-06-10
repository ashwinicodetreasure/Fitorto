package com.ct.fitorto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.FitortoCategory;
import com.ct.fitorto.recycler_holder.RecyclerViewHolders;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<FitortoCategory> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<FitortoCategory> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_fragment, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        FitortoCategory category=itemList.get(position);

        holder.title.setText(category.getCategoryName());

        if(TextUtils.isEmpty(category.getColorCode())) {

            Toast.makeText(context,"No Image ",Toast.LENGTH_SHORT).show();

        }else
        {
            //holder.logo.setColorFilter(Color.parseColor(category.getColorCode()), PorterDuff.Mode.DARKEN);
            holder.logo.setBackgroundColor(Color.parseColor(category.getColorCode()));



        }

        // for(int i=0; i<=category.getCount().length();i++) {
        if (category.getCategoryName().equals("Gym")) {
            holder.content.setText(category.getCount() + " Gym");
        } else if (category.getCategoryName().equals("Aerobic")) {
            holder.content.setText(category.getCount() + " Aerobic");
        } else if (category.getCategoryName().equals("Dance")) {
            holder.content.setText(category.getCount() + " Dance");
        } else if (category.getCategoryName().equals("Yoga")) {
            holder.content.setText(category.getCount() + " Yoga");
        } else if (category.getCategoryName().equals("Zumba")) {
            holder.content.setText(category.getCount() + " Zumba");
        } else if (category.getCategoryName().equals("Marital Arts")) {
            holder.content.setText(category.getCount() + " Marital Arts");
        } else if (category.getCategoryName().equals("Crossfit")) {
            holder.content.setText(category.getCount() + " Crossfit");
        }
        //}






        if(TextUtils.isEmpty(category.getIcon())) {

            Toast.makeText(context,"No Image ",Toast.LENGTH_SHORT).show();

        }else
        {
            Picasso.with(context)
                    .load(category.getIcon())
                    .into(holder.dis);
        }

       /* if(!TextUtils.isEmpty(category.getIcon())) {
            Picasso.with(context)
                    .load(category.getColorCode())
                    .into(holder.dis);
        }*/
        //holder.dis.setImageResource(category.getIcon());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
