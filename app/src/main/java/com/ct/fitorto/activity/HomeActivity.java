package com.ct.fitorto.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.fragments.DiscoverFragment;
import com.ct.fitorto.fragments.FeedFragment;
import com.ct.fitorto.fragments.FriendsFragment;
import com.ct.fitorto.fragments.ProfileFragment;
import com.ct.fitorto.model.JsonResponseNotification;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ashwini on 5/13/2016.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout feed;
    private LinearLayout discover;
    private LinearLayout profile;
    private LinearLayout friends;
    private ImageView feedimg;
    private ImageView discoverimg;
    private ImageView profileimg;
    private ImageView friendsimg;
    private TextView tvfeed;
    private TextView tvdis;
    private TextView tvpro;
    private TextView tvfrd;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment fr = null;
    private TextView tvTitle;
    private TextView tvNotificationBadge;
    private int badgeNumber=0;
    private PreferenceManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        tvTitle= (TextView) toolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("Discover");
        fr = new DiscoverFragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.show_fragment, fr);
        ft.commit();

        manager=new PreferenceManager(this);
        feed = (LinearLayout) findViewById(R.id.feed);
        feed.setOnClickListener(this);

        discover = (LinearLayout) findViewById(R.id.discover);
        /*discover.setTextColor(Color.parseColor("#388E3C"));
        discover.getBackground().setColorFilter(Color.parseColor("#6c6c6d"), PorterDuff.Mode.SRC_IN);*/
        discover.setOnClickListener(this);

        profile = (LinearLayout) findViewById(R.id.profile);
        profile.setOnClickListener(this);

        friends = (LinearLayout) findViewById(R.id.friends);
        friends.setOnClickListener(this);

        feedimg = (ImageView) findViewById(R.id.feedimg);
        discoverimg = (ImageView) findViewById(R.id.discoverimg);
        profileimg = (ImageView) findViewById(R.id.profileimg);
        friendsimg = (ImageView) findViewById(R.id.friendsimg);

        tvfeed = (TextView) findViewById(R.id.tvfeed);
        tvdis = (TextView) findViewById(R.id.tvdis);
        tvpro = (TextView) findViewById(R.id.tvpro);
        tvfrd = (TextView) findViewById(R.id.tvfrd);

        discoverimg.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
        tvdis.setTextColor(getResources().getColor(R.color.selectedTab));

        feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
        discoverimg.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
        profileimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
        friendsimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
        tvfeed.setTextColor(getResources().getColor(R.color.unSelectedTab));
        tvdis.setTextColor(getResources().getColor(R.color.selectedTab));
        tvpro.setTextColor(getResources().getColor(R.color.unSelectedTab));
        tvfrd.setTextColor(getResources().getColor(R.color.unSelectedTab));
        getNotificationCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.discover_menu, menu);
        MenuItem notification = menu.findItem(R.id.notification);
        View view =   MenuItemCompat.getActionView(notification);
        tvNotificationBadge = (TextView) view.findViewById(R.id.hotlist_hot);
        new MyMenuItemStuffListener(view, "Show hot message") {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivityForResult(i, ApplicationData.REQUEST_CODE_NOTIFICATION);
            }
        };
        return true;
    }
    // call the updating code on the main thread,
// so we can call this asynchronously
    public void updateHotCount(final int new_badgeNumber) {
        badgeNumber = new_badgeNumber;
        if (tvNotificationBadge == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_badgeNumber == 0)
                    tvNotificationBadge.setVisibility(View.INVISIBLE);
                else {
                    tvNotificationBadge.setVisibility(View.VISIBLE);
                    tvNotificationBadge.setText(Integer.toString(new_badgeNumber));
                }
            }
        });
    }


    private void getNotificationCount() {
        if (ApplicationUtility.checkConnection(this)) {
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            Call<JsonResponseNotification> call = ApiClientMain.getApiClient().getNotification(userId);
            call.enqueue(new Callback<JsonResponseNotification>() {
                @Override
                public void onResponse(Call<JsonResponseNotification> call, Response<JsonResponseNotification> response) {
                    if (response.body() != null) {
                         updateNotificationCount(response.body().getCount());
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseNotification> call, Throwable t) {

                }
            });
        }
    }

    private void updateNotificationCount(int count) {
        int previousCount=manager.getPreferenceIntValues(ApplicationData.NOTIFICATION_BADGE_COUNT);
        int notificationCount=count-previousCount;
        manager.putPreferenceIntValues(ApplicationData.NOTIFICATION_BADGE_COUNT,notificationCount);
        badgeNumber=notificationCount;
        updateHotCount(badgeNumber);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.notification) {
            Intent i = new Intent(HomeActivity.this, NotificationActivity.class);
            startActivityForResult(i, ApplicationData.REQUEST_CODE_NOTIFICATION);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.feed:
                tvTitle.setText("Feeds");
                feedimg.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
                discoverimg.clearColorFilter();//setColorFilter(R.color.tint, PorterDuff.Mode.SRC_IN);
                profileimg.clearColorFilter();//setColorFilter(R.color.tint, PorterDuff.Mode.SRC_IN);
                friendsimg.clearColorFilter();//setColorFilter(R.color.tint, PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(getResources().getColor(R.color.selectedTab));
                tvdis.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvpro.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvfrd.setTextColor(getResources().getColor(R.color.unSelectedTab));
                fr = new FeedFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();
                break;

            case R.id.discover:
                tvTitle.setText("Discover");
                feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                discoverimg.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
                profileimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                friendsimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvdis.setTextColor(getResources().getColor(R.color.selectedTab));
                tvpro.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvfrd.setTextColor(getResources().getColor(R.color.unSelectedTab));

                fr = new DiscoverFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();


                break;
            case R.id.profile:
                tvTitle.setText("Profile");
                feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                discoverimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                profileimg.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
                friendsimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvdis.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvpro.setTextColor(getResources().getColor(R.color.selectedTab));
                tvfrd.setTextColor(getResources().getColor(R.color.unSelectedTab));
                fr = new ProfileFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();
                break;
            case R.id.friends:
                tvTitle.setText("Friends");
                feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                discoverimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                profileimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                friendsimg.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvdis.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvpro.setTextColor(getResources().getColor(R.color.unSelectedTab));
                tvfrd.setTextColor(getResources().getColor(R.color.selectedTab));
                /*Intent intent2 = new Intent(HomeActivity.this, MembershipActivity.class);
                startActivity(intent2);*/
                fr = new FriendsFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();
                break;


        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }



    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override abstract public void onClick(View v);

        @Override public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }
}
