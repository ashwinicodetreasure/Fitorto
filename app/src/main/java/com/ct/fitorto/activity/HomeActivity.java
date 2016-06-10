package com.ct.fitorto.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.fragments.Discover_Fragment;
import com.ct.fitorto.fragments.Feed_Fragment;
import com.ct.fitorto.fragments.Profile_Fragment;


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

    private  FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment fr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setTitle("Discover");
            //getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fr = new Discover_Fragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.show_fragment, fr);
        ft.commit();


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

        feedimg=(ImageView)findViewById(R.id.feedimg);
        discoverimg=(ImageView)findViewById(R.id.discoverimg);
        profileimg=(ImageView)findViewById(R.id.profileimg);
        friendsimg=(ImageView)findViewById(R.id.friendsimg);

        tvfeed=(TextView) findViewById(R.id.tvfeed);
        tvdis=(TextView)findViewById(R.id.tvdis);
        tvpro=(TextView)findViewById(R.id.tvpro);
        tvfrd=(TextView)findViewById(R.id.tvfrd);

        discoverimg.setColorFilter(Color.parseColor("#388E3C"), PorterDuff.Mode.SRC_IN);
        tvdis.setTextColor(Color.parseColor("#388E3C"));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.discover_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.notification) {


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.feed:

                feedimg.setColorFilter(Color.parseColor("#388E3C"), PorterDuff.Mode.SRC_IN);
                discoverimg.clearColorFilter();//setColorFilter(R.color.tint, PorterDuff.Mode.SRC_IN);
                profileimg.clearColorFilter();//setColorFilter(R.color.tint, PorterDuff.Mode.SRC_IN);
                friendsimg.clearColorFilter();//setColorFilter(R.color.tint, PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(Color.parseColor("#388E3C"));
                tvdis.setTextColor(Color.parseColor("#000000"));
                tvpro.setTextColor(Color.parseColor("#000000"));
                tvfrd.setTextColor(Color.parseColor("#000000"));



                fr = new Feed_Fragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();
                break;

            case R.id.discover:
                feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                discoverimg.setColorFilter(Color.parseColor("#388E3C"), PorterDuff.Mode.SRC_IN);
                profileimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                friendsimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(Color.parseColor("#000000"));
                tvdis.setTextColor(Color.parseColor("#388E3C"));
                tvpro.setTextColor(Color.parseColor("#000000"));
                tvfrd.setTextColor(Color.parseColor("#000000"));

                fr = new Discover_Fragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();


                break;
            case R.id.profile:
                feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                discoverimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                profileimg.setColorFilter(Color.parseColor("#388E3C"), PorterDuff.Mode.SRC_IN);
                friendsimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(Color.parseColor("#000000"));
                tvdis.setTextColor(Color.parseColor("#000000"));
                tvpro.setTextColor(Color.parseColor("#388E3C"));
                tvfrd.setTextColor(Color.parseColor("#000000"));
                fr = new Profile_Fragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.show_fragment, fr);
                ft.commit();
                break;
            case R.id.friends:
                feedimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                discoverimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                profileimg.clearColorFilter();//setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);
                friendsimg.setColorFilter(Color.parseColor("#388E3C"), PorterDuff.Mode.SRC_IN);
                tvfeed.setTextColor(Color.parseColor("#000000"));
                tvdis.setTextColor(Color.parseColor("#000000"));
                tvpro.setTextColor(Color.parseColor("#000000"));
                tvfrd.setTextColor(Color.parseColor("#388E3C"));
                /*Intent intent2 = new Intent(HomeActivity.this, MembershipActivity.class);
                startActivity(intent2);*/
                Toast.makeText(HomeActivity.this, "Add Activity", Toast.LENGTH_SHORT).show();
                break;


        }

    }


}
