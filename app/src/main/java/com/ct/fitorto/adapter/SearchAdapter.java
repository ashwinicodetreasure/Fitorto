package com.ct.fitorto.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.Search;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Search item);
    }

    private ArrayList<Search> items;
    private OnItemClickListener listener;

    public SearchAdapter(OnItemClickListener listener, ArrayList<Search> items) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gymaname;
        private TextView gymloaction;
        private TextView gymrating;
        private ImageView gymImage1;
        private ImageView gymImage2;
        private ImageView gymImage3;
        private ImageView gymImage4;
        private ImageView imageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            gymaname = (TextView) itemView.findViewById(R.id.title);
            gymloaction = (TextView) itemView.findViewById(R.id.location);
            gymrating = (TextView) itemView.findViewById(R.id.textView10);
            gymImage1 = (ImageView) itemView.findViewById(R.id.image1);
            gymImage2 = (ImageView) itemView.findViewById(R.id.image2);
            gymImage3 = (ImageView) itemView.findViewById(R.id.image3);
            gymImage4 = (ImageView) itemView.findViewById(R.id.image4);
            imageButton = (ImageView) itemView.findViewById(R.id.imageButton);
        }

        public void bind(final Search item, final OnItemClickListener listener) {


            if (TextUtils.isEmpty(item.getGymName())) {
            } else {
                gymaname.setText(item.getGymName());
            }


            if (TextUtils.isEmpty(item.getArea())) {
            } else {
                gymloaction.setText(item.getArea());
            }

            if(item.getRating().equals("0"))
                 {
                     imageButton.setImageResource(R.drawable.blank);
                 }
            else{
                imageButton.setImageResource(R.drawable.star);

            }



            if (TextUtils.isEmpty(item.getRating())) {
            } else {
                gymrating.setText(item.getRating());
            }


            if (TextUtils.isEmpty(item.getLogo())) {

                Toast.makeText(itemView.getContext(), "No Image ", Toast.LENGTH_SHORT).show();
                Log.d("no image", item.getLogo());

            } else {
                Picasso.with(itemView.getContext())
                        .load(item.getLogo())
                        .into(gymImage1);
                Picasso.with(itemView.getContext())
                        .load(item.getLogo())
                        .into(gymImage2);
                Picasso.with(itemView.getContext())
                        .load(item.getLogo())
                        .into(gymImage3);
                Picasso.with(itemView.getContext())
                        .load(item.getLogo())
                        .into(gymImage4);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}