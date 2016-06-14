package com.ct.fitorto.recycler_holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.FitortoCategory;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView title;
    public ImageView logo;
    public TextView content;
    public ImageView dis;
    public List<FitortoCategory> mDataset=new ArrayList<>();


    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        title = (TextView)itemView.findViewById(R.id.tv_title);
        logo = (ImageView)itemView.findViewById(R.id.Logo);
        content =(TextView)itemView.findViewById(R.id.tv_content);
        dis = (ImageView)itemView.findViewById(R.id.imagedis);
    }

    @Override
    public void onClick(View view) {
        int i =getPosition();
       // FitortoCategory fc=mDataset.get(getPosition());
        Toast.makeText(view.getContext(), "Clicked Country Position = " +getPosition(), Toast.LENGTH_SHORT).show();

       /* if(getPosition()==0){
            Intent i=new Intent(itemView.getContext(), SearchResultActivity.class);
            i.get
            itemView.getContext().startActivity(i);
        }*/
    }

}