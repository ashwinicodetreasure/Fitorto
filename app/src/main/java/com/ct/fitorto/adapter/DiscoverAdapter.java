package com.ct.fitorto.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.FitortoCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.RecyclerViewHolders> {

    private ArrayList<FitortoCategory> itemList;
    private Context context;
    public OnItemClickListener onItemClickListener;

    public DiscoverAdapter(Context context, ArrayList<FitortoCategory> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_discover, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final FitortoCategory category = itemList.get(position);
        holder.title.setText(category.getCategoryName().toUpperCase());

        if (TextUtils.isEmpty(category.getColorCode())) {
        } else {
            //holder.logo.setColorFilter(Color.parseColor(category.getColorCode()), PorterDuff.Mode.DARKEN);
            holder.logo.setBackgroundColor(Color.parseColor(category.getColorCode()));
        }

        holder.content.setText(category.getCount() + " " + category.getCategoryName());
        if (!TextUtils.isEmpty(category.getIcon())) {
            //Toast.makeText(context, "No Image ", Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(category.getIcon()).into(holder.dis);
        } else {

        }
        holder.itemView.setTag(category);
    }

    private double meterDistanceBetweenPoints(float lat_a, float lng_a, float lat_b, float lng_b) {
        float pk = (float) (180.f / Math.PI);

        float a1 = lat_a / pk;
        float a2 = lng_a / pk;
        float b1 = lat_b / pk;
        float b2 = lng_b / pk;

        float t1 = (float) (Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2));
        float t2 = (float) (Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2));
        float t3 = (float) (Math.sin(a1) * Math.sin(b1));
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, FitortoCategory category);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView logo;
        public TextView content;
        public ImageView dis;
        public List<FitortoCategory> mDataset = new ArrayList<>();


        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            logo = (ImageView) itemView.findViewById(R.id.Logo);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            dis = (ImageView) itemView.findViewById(R.id.imagedis);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, (FitortoCategory) view.getTag());
            }
        }

    }
}
