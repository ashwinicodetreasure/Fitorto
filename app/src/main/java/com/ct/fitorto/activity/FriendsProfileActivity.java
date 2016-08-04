package com.ct.fitorto.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.FeedAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.Feed;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseFeed;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.model.JsonResponseFriendsProfile;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 6/28/2016.
 */
public class FriendsProfileActivity extends BaseActivity {


    private PreferenceManager preferenceManager;
    private TextView followers;
    private TextView following;
    private TextView username;
    private CircleImageView profile;
    private TextView userstatus;
    private TextView edit;
    private LinearLayoutManager llayout;
    private FeedAdapter adapter;
    private RecyclerView rview;
    private ProgressDialog pDialog;
    private ArrayList<Feed> feed = new ArrayList<>();
    private String str = "";
    private String friendID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.friends_profile);
        initToolbar(true);
        preferenceManager = new PreferenceManager(FriendsProfileActivity.this);
        friendID = getIntent().getStringExtra(ApplicationData.FRIEND_ID);
        initView();
        displayProfileData();
        FeedData();
    }

    /*Feed*/
    private void FeedData() {

        Call<JsonResponseFeed> response = ApiClientMain.getApiClient().getResponseFedd(friendID);
        response.enqueue(new Callback<JsonResponseFeed>() {
            @Override
            public void onResponse(Call<JsonResponseFeed> call, Response<JsonResponseFeed> response) {
                if (response.isSuccessful()) {
                    JsonResponseFeed jsonResponse = response.body();
                    feed = new ArrayList<>(jsonResponse.getData());
                    adapter = new FeedAdapter(FriendsProfileActivity.this, feed);
                    rview.setAdapter(adapter);
                    pDialog.dismiss();
                } else {
                    Toast.makeText(FriendsProfileActivity.this, "Error darim else onresponse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonResponseFeed> call, Throwable t) {

            }
        });
    }

    /*User data to be display*/
    private void displayProfileData() {

        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);            //stored userid replace with static data
        Call<JsonResponseFriendsProfile> response = ApiClientMain.getApiClient().getResponseFriendProfile(userid, friendID);

        response.enqueue(new Callback<JsonResponseFriendsProfile>() {

            @Override
            public void onResponse(Call<JsonResponseFriendsProfile> call, Response<JsonResponseFriendsProfile> response) {
                JsonResponseFriendsProfile resp = response.body();
                if (resp != null) {
                    updateFriendProfile(resp);
                    //checking if response is not null

                }
            }

            @Override
            public void onFailure(Call<JsonResponseFriendsProfile> call, Throwable t) {
                Log.d("Error", "failed");
                Toast.makeText(FriendsProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateFriendProfile(JsonResponseFriendsProfile resp) {
        if (resp.getData().size() > 0) {
            FitortoUser user = resp.getData().get(0);           //retrieving user data
            if (user != null) {
                if (!TextUtils.isEmpty(user.getProfilePic())) {
                    Picasso.with(getApplicationContext())
                            .load(user.getProfilePic())
                            .error(R.drawable.ic_profile)
                            .placeholder(R.drawable.ic_profile)
                            .into(profile);
                } else {
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.ic_profile)
                            .placeholder(R.drawable.ic_profile)
                            .into(profile);

                }

                if (!TextUtils.isEmpty(user.getName())) {
                    username.setText(user.getName());
                }

                if (!TextUtils.isEmpty(user.getStatus())) {
                    userstatus.setText(user.getStatus());
                } else {
                    userstatus.setText("No Status");
                }

                if (!TextUtils.isEmpty(String.valueOf(user.getFollowersCount()))) {
                    String str = String.valueOf(user.getFollowersCount());
                    if (str.equals("null")) {
                        followers.setText("0");
                    } else {
                        followers.setText(String.valueOf(user.getFollowersCount()));
                    }
                } else {
                    followers.setText("0");
                }

                if (!TextUtils.isEmpty(String.valueOf(user.getFollowingCount()))) {
                    String str = String.valueOf(user.getFollowingCount());
                    if (str.equals("null")) {
                        following.setText("0");
                    } else {
                        following.setText(String.valueOf(user.getFollowingCount()));
                    }
                } else {
                    following.setText("0");
                }
                if (!TextUtils.isEmpty(user.getIsFollowing())) {
                    if (user.getIsFollowing().equals("1")) {
                        edit.setText("unfollow");
                        // displayProfilData();
                    } else if (user.getIsFollowing().equals("0")) {
                        edit.setText("follow");
                        //  displayProfilData();
                    }
                }
            }
        }

    }

    /*All Register componets*/
    private void initView() {
        followers = (TextView) findViewById(R.id.followers);
        following = (TextView) findViewById(R.id.following);
        profile = (CircleImageView) findViewById(R.id.profile_image);
        username = (TextView) findViewById(R.id.user_name);
        userstatus = (TextView) findViewById(R.id.user_status);
        edit = (TextView) findViewById(R.id.editbtn);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow();
            }
        });
        llayout = new LinearLayoutManager(FriendsProfileActivity.this);
        rview = (RecyclerView) findViewById(R.id.discover_recycler);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(llayout);
        pDialog = new ProgressDialog(FriendsProfileActivity.this);

        pDialog.setMessage("loading ...");
        pDialog.show();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow();

            }
        });
    }


    /*Follow Button*/
    private void follow() {
        showProgressDialog("Please wait..", false);
        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        String str1 = edit.getText().toString();
        if (str1.equals("follow")) {
            str = "0";
        } else {
            str = "1";
        }
        Call<JsonResponseFollow> response = ApiClientMain.getApiClient().getResponseFollow(userid, friendID, str);
        response.enqueue(new Callback<JsonResponseFollow>() {
            @Override
            public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {
                cancelProgressDialog();
                JsonResponseFollow resp = response.body();
                if (resp != null) {
                    if (!resp.getStatus().equals("1")) {
                        edit.setText("Follow");
                    } else if (!resp.getStatus().equals("0")) {
                        edit.setText("unFollow");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseFollow> call, Throwable t) {
                cancelProgressDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishThis();
    }

    private void finishThis() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
