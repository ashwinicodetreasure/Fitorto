package com.ct.fitorto.recycler_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class ProfileHolders extends RecyclerView.ViewHolder  implements View.OnClickListener{
    public ImageView gymimage;
    public TextView gymtitle;

    public ProfileHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        gymimage =(ImageView)itemView.findViewById(R.id.gymimage);
        gymtitle=(TextView)itemView.findViewById((R.id.gymtitle));
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
