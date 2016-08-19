package com.ct.fitorto.recycler_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ct.fitorto.R;

import org.w3c.dom.Text;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class CardHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    public TextView currentw;
    public TextView currentd;
    public TextView oldd;
    public TextView oldw;
    public TextView tvwh;
    public TextView add;


    public CardHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        currentw=(TextView)itemView.findViewById((R.id.current_w));
        currentd=(TextView)itemView.findViewById(R.id.card_date);
        oldd=(TextView)itemView.findViewById((R.id.pre_date));
        oldw=(TextView)itemView.findViewById((R.id.old_w));
        tvwh=(TextView)itemView.findViewById((R.id.tv_wh));
        add= (TextView) itemView.findViewById(R.id.add);

    }

    @Override
    public void onClick(View v) {
        // Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


    }
}
