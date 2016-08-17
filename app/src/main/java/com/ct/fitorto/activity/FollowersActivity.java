package com.ct.fitorto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.FollowerAdapter;
import com.ct.fitorto.adapter.FollowingAdapter;
import com.ct.fitorto.adapter.NotificationAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.Follower;
import com.ct.fitorto.model.Following;
import com.ct.fitorto.model.JsonResponseFriends;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by codetreasure on 8/17/16.
 */
public class FollowersActivity extends BaseActivity implements FollowingAdapter.OnItemClickListener,FollowerAdapter.OnItemClickListener {

    private PreferenceManager manager;
    private RecyclerView mRecyclerView;
    private FollowerAdapter followerAdapter;
    private FollowingAdapter followingAdapter;
    private TextView tvEmptyView;
    private LinearLayout empty_view;
    private ArrayList<Following> followings = new ArrayList<>();
    private ArrayList<Follower> followers = new ArrayList<>();
    private boolean isFollower;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        isFollower=getIntent().getBooleanExtra(ApplicationData.IS_FOLLOWER,false);
        if (ApplicationUtility.checkConnection(this)) {
            showProgressDialog("Please wait...", false);
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            Call<JsonResponseFriends> response = ApiClientMain.getApiClient().getFriendsList(userId);
            response.enqueue(new Callback<JsonResponseFriends>() {
                @Override
                public void onResponse(Call<JsonResponseFriends> call, Response<JsonResponseFriends> response) {
                    cancelProgressDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getFollowers().size() > 0) {
                                tvEmptyView.setVisibility(View.GONE);
                                followers.addAll(response.body().getFollowers());
                            }else {
                                mRecyclerView.setVisibility(View.GONE);
                                tvEmptyView.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getFollowing().size() > 0) {
                                tvEmptyView.setVisibility(View.GONE);
                                followings.addAll(response.body().getFollowing());
                            }else {
                                mRecyclerView.setVisibility(View.GONE);
                                tvEmptyView.setVisibility(View.VISIBLE);
                            }

                            if(isFollower){
                                updateFollowerData(followers);
                            }else {
                                updateFollowingData(followings);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseFriends> call, Throwable t) {
                    cancelProgressDialog();
                    Toast.makeText(FollowersActivity.this, "Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            empty_view.setVisibility(View.VISIBLE);
            ImageView ivRetry = (ImageView) findViewById(R.id.ivRetry);
            ivRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initDataSet();
                }
            });
         }

    /*    if (ApplicationUtility.checkConnection(this)) {
            empty_view.setVisibility(View.GONE);
            showProgressDialog("Please wait...", false);
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            Call<JsonResponseNotification> call = ApiClientMain.getApiClient().getNotification(userId);
            call.enqueue(new Callback<JsonResponseNotification>() {
                @Override
                public void onResponse(Call<JsonResponseNotification> call, Response<JsonResponseNotification> response) {
                    cancelProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getData().size() > 0) {
                            tvEmptyView.setVisibility(View.GONE);
                            updateData(response.body().getData());
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            tvEmptyView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseNotification> call, Throwable t) {
                    cancelProgressDialog();
                }
            });
        } else {
            empty_view.setVisibility(View.VISIBLE);
            ImageView ivRetry = (ImageView) findViewById(R.id.ivRetry);
            ivRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initDataSet();
                }
            });
        }*/

    }

    private void updateFollowingData(ArrayList<Following> followings) {
        mRecyclerView.setVisibility(View.VISIBLE);
        followingAdapter = new FollowingAdapter(this, followings);
        mRecyclerView.setAdapter(followingAdapter);
    }

    private void updateFollowerData(ArrayList<Follower> followers) {
        mRecyclerView.setVisibility(View.VISIBLE);
        followerAdapter = new FollowerAdapter(this, followers);
        mRecyclerView.setAdapter(followerAdapter);
    }



    private void initView() {
        manager = new PreferenceManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        tvEmptyView = (TextView) findViewById(R.id.tvEmpty);
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                /*Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homeIntent);*/
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    @Override
    public void onItemClick(View view, Following position) {
        Intent i = new Intent(FollowersActivity.this, FriendsProfileActivity.class);
        i.putExtra(ApplicationData.FRIEND_ID, position.getUserID());
        startActivityForResult(i, ApplicationData.RESULT_CODE_FRIEND);
    }



    @Override
    public void onItemClick(View view, Follower position) {
        Intent i=new Intent(FollowersActivity.this,FriendsProfileActivity.class);
        i.putExtra(ApplicationData.FRIEND_ID,position.getUserID());
        startActivityForResult(i,ApplicationData.RESULT_CODE_FRIEND);
    }

}
