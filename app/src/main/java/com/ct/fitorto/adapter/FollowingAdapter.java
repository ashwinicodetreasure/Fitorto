package com.ct.fitorto.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.Following;
import com.ct.fitorto.model.JsonResponseFriends;
import com.ct.fitorto.model.JsonResponseSearchFriends;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 3/28/2016.
 */
public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private static final String TAG = "FollowingAdapter";
    private ProgressDialog mProgressDialog;
    private ArrayList<Following> mDataSet;
    private Activity context;
    public OnItemClickListener onItemClickListener;
    private PreferenceManager manager;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvUserName;
        public ImageView ivUserPics;
        public Button btnFollow;
        public String flag;
        public TextView tvStatus;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
            ivUserPics = (ImageView) v.findViewById(R.id.ivUserPics);
            btnFollow = (Button) v.findViewById(R.id.btnFollow);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
        }


        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, (Following) v.getTag());
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
    public FollowingAdapter(Activity context, ArrayList<Following> dataSet) {
        this.context = context;
        mDataSet = dataSet;
        manager = new PreferenceManager(context);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(String.valueOf("Please wait.."));
        mProgressDialog.setCancelable(false);
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_friends_detail, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        //final Home myDoctors = mDataSet.get(position);

        final Following follower = mDataSet.get(position);
        viewHolder.tvUserName.setText(follower.getName());
        if (!TextUtils.isEmpty(follower.getProfilePic())) {
            Picasso.with(context).load(follower.getProfilePic()).error(R.drawable.ic_profile).into(viewHolder.ivUserPics);
        } else {
            Picasso.with(context).load(R.drawable.ic_profile).error(R.drawable.ic_profile).into(viewHolder.ivUserPics);
        }

        if (!TextUtils.isEmpty(follower.getStatus())) {
            viewHolder.tvStatus.setText(follower.getStatus());
        }

        viewHolder.btnFollow.setVisibility(View.VISIBLE);

        if (follower.getIsFollowing().equals("0")) {
            viewHolder.btnFollow.setText("Follow");
        } else {
            viewHolder.btnFollow.setText("Unfollow");
        }

        viewHolder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follower.getIsFollowing().equals("1")) {
                    viewHolder.flag = "0";
                } else {
                    viewHolder.flag = "1";
                }
                unfollow(follower, viewHolder.flag, viewHolder, position);
            }
        });
        viewHolder.itemView.setTag(follower);

    }

    public void unfollow(final Following follower, final String flag, final ViewHolder viewHolder, final int position) {
        if(ApplicationUtility.checkConnection(context)){
            mProgressDialog.show();
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            Call<JsonResponseSearchFriends> response = ApiClientMain.getApiClient().follow(userId, follower.getUserID(), flag);
            response.enqueue(new Callback<JsonResponseSearchFriends>() {
                @Override
                public void onResponse(Call<JsonResponseSearchFriends> call, Response<JsonResponseSearchFriends> response) {
                    try {
                        if ((mProgressDialog != null) && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                    } catch (final IllegalArgumentException e) {
                        // Handle or log or ignore
                    } catch (final Exception e) {
                        // Handle or log or ignore
                    } finally {
                        mProgressDialog = null;
                    }
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            if (flag.equals("0")) {
                                follower.setIsFollowing("0");
                                viewHolder.btnFollow.setText("Follow");
                                mDataSet.get(position).setIsFollowing("0");
                            } else {
                                follower.setIsFollowing("1");
                                viewHolder.btnFollow.setText("Unfollow");
                                mDataSet.get(position).setIsFollowing("1");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseSearchFriends> call, Throwable t) {

                }
            });

        }else {
            Toast.makeText(context, "No internet found.Check your connection or try again", Toast.LENGTH_SHORT).show();
        }

    }

    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Following position);
    }
}

