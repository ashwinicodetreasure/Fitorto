package com.ct.fitorto.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.Friends;
import com.ct.fitorto.model.Notifications;
import com.ct.fitorto.utils.DateTimeUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Ashwini on 2/13/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private static final String TAG = "NotificationAdapter";
    Context context;
    private ArrayList<Notifications> mDataSet;
    public OnItemClickListener onItemClickListener;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivUserPics;
        public TextView tvUserName;
        public TextView tvDate;
        public ImageView ivType;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            ivUserPics = (ImageView) v.findViewById(R.id.ivUserPics);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            ivType = (ImageView) v.findViewById(R.id.ivType);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, (Notifications) v.getTag());
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
    public NotificationAdapter(Context context, ArrayList<Notifications> dataSet) {
        this.context = context;
        mDataSet = dataSet;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notification_info, viewGroup, false);
        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        Notifications more = mDataSet.get(position);
        viewHolder.tvUserName.setText(Html.fromHtml("<b>" + more.getSenderName() + "</b> <font color=\"#909090\">" + more.getNotification()));
        try {
            viewHolder.tvDate.setText(DateTimeUtils.formatToYesterdayOrToday(context, more.getDateTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(more.getProfilePic())) {
            Picasso.with(context).load(more.getProfilePic()).placeholder(R.drawable.ic_profile).into(viewHolder.ivUserPics);
        } else {
            Picasso.with(context).load(R.drawable.ic_profile).into(viewHolder.ivUserPics);
        }
        updateNotificationCategory(more.getNotificationCategory(), viewHolder);
        viewHolder.itemView.setTag(more);
    }

    private void updateNotificationCategory(int notificationCategory, ViewHolder viewHolder) {
        switch (notificationCategory) {
            case 0:
                viewHolder.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_noti_follow));
                break;
            case 1:
                viewHolder.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_notification));
                break;
            case 2:
                viewHolder.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_noti_feed));
                break;
            case 3:
                viewHolder.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_noti_like));
                break;
            case 4:
                viewHolder.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_noti_coupon));
                break;
            case 5:
                viewHolder.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_noti_follow));
                break;
        }
    }


    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Notifications position);
    }

}



