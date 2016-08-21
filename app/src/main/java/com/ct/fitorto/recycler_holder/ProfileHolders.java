package com.ct.fitorto.recycler_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ct.fitorto.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class ProfileHolders extends RecyclerView.ViewHolder  implements View.OnClickListener{
    public CircleImageView gymimage;
    public TextView gymtitle;
    public TextView gymstatus;
    public TextView checkedin;

    public ProfileHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        gymimage =(CircleImageView)itemView.findViewById(R.id.gym_image);
        gymtitle=(TextView)itemView.findViewById((R.id.gym_name));
        gymstatus=(TextView)itemView.findViewById(R.id.gym_status);
        checkedin= (TextView) itemView.findViewById(R.id.checkedin);
    }

    @Override
    public void onClick(View v) {
       // Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
