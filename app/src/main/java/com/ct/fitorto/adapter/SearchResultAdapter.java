package com.ct.fitorto.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct.fitorto.R;

import com.ct.fitorto.model.Friends;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by codetreasure on 6/21/16.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private static final String TAG = "SearchResultAdapter";
    private ArrayList<Friends> mDataSet;
    private Activity context;
    public OnItemClickListener onItemClickListener;


    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvUserName;
        public ImageView ivUserPics;
        public TextView tvStatus;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
            ivUserPics = (ImageView) v.findViewById(R.id.ivUserPics);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
        }


        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, (Friends) v.getTag());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public SearchResultAdapter(Activity context, ArrayList<Friends> dataSet) {
        this.context = context;
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_search_friend, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        //final Home myDoctors = mDataSet.get(position);

        Friends follower = mDataSet.get(position);
        viewHolder.tvUserName.setText(follower.getName());
        if (!TextUtils.isEmpty(follower.getProfilePic())) {
            Picasso.with(context).load(follower.getProfilePic()).error(R.drawable.ic_profile).into(viewHolder.ivUserPics);
        } else {
            Picasso.with(context).load(R.drawable.ic_profile).error(R.drawable.ic_profile).into(viewHolder.ivUserPics);
        }

        if(!TextUtils.isEmpty(follower.getStatus())){
            viewHolder.tvStatus.setText(follower.getStatus());
        }
        viewHolder.itemView.setTag(follower);

    }


    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Friends position);
    }
}

