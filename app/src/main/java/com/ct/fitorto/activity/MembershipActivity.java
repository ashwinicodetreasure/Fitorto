package com.ct.fitorto.activity;

/**
 * Created by Ashwini on 5/17/2016.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.Membership_Slider_Adapter;
import com.ct.fitorto.flowlayou.FlowLayout;
import com.ct.fitorto.model.Package;
import com.ct.fitorto.model.Schedule;
import com.ct.fitorto.model.Search;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MembershipActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mPager;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    //private  final Integer[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.a};
    // private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private Search testing;
    // private AmenitisAdapter adapt;
    // private EquipmentAdapter adapt1;
    //private ArrayList<Schedule> schedule=new ArrayList<>();
    private Schedule schedule;
    private TextView expand, member;
    private TextView tvLoc;
    private TextView tvTime;
    TextView zumba;


    private ExpandableRelativeLayout expand_panel, expand_pane2;
    private LinearLayout llEquipmentContainer;
    private LinearLayout llAmenitiContainer;
    private FlowLayout mFlowLayout;

    // private static final int MY_BUTTON = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.membership_main_layout);
        testing = (Search) getIntent().getParcelableExtra("memberItem");
        setToolbar();
        init();
        expandableWindow();
        setTime();
        setPackage();
        setAmenities();
        setEquipment();
        locationMap();
        setflow();


        if (testing != null) {
            tvLoc = (TextView) findViewById(R.id.location);
            tvLoc.setText(testing.getAddress());
            TextView gender = (TextView) findViewById(R.id.tvgender);
            gender.setText(testing.getGender());
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setflow() {

        ArrayList aList = new ArrayList(Arrays.asList(testing.getCategory().split(",")));

        if (aList.size() > 0) {
            Log.d("Logs", "Schedule:" + aList);
            setcategory(aList);
        }

    }

    private void setcategory(List<String> sizeArrayList) {
        mFlowLayout = (FlowLayout) findViewById(R.id.flow);
        if (sizeArrayList != null && sizeArrayList.size() > 0) {

            for (String size : sizeArrayList) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final TextView equipment1 = (TextView) inflater.inflate(R.layout.category_list_item, mFlowLayout, false);
                equipment1.setText(size);
                mFlowLayout.addView(equipment1);
            }
        }
    }


    private void locationMap() {
        final String lat = testing.getLatitude().toString();
        final String lag = testing.getLongitude().toString();
        String url = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lag + "&markers=icon:http://tinyurl.com/2ftvtt6|" + lat + "," + lag + "&zoom=15&size=500x500&sensor=true";
        ImageView loction = (ImageView) findViewById(R.id.location_image);
        Picasso.with(MembershipActivity.this).load(url).into(loction);
        loction.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageButton locbtn = (ImageButton) findViewById(R.id.locbtn);
        locbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" + (lat + "," + lag)));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        loction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" + (lat + "," + lag)));
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setEquipment() {
        if (testing.getEquipments().size() > 0) {
            setSizeOptionLayout(testing.getEquipments());

        }
    }

    public void setAmenit(List<String> sizeArrayList) {
        llAmenitiContainer = (LinearLayout) findViewById(R.id.llAmenitiContainer);
        if (sizeArrayList != null && sizeArrayList.size() > 0) {

            for (String size : sizeArrayList) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout equipment = (LinearLayout) inflater.inflate(R.layout.amenites_list_item, llAmenitiContainer, false);
                final TextView ameniti = (TextView) equipment.findViewById(R.id.ameniti);
                ameniti.setText(size);
                llAmenitiContainer.addView(equipment);
            }
        }
    }

    public void setSizeOptionLayout(List<String> sizeArrayList) {
        llEquipmentContainer = (LinearLayout) findViewById(R.id.llEquipmentContainer);
        if (sizeArrayList != null && sizeArrayList.size() > 0) {

            for (String size : sizeArrayList) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout equipment = (LinearLayout) inflater.inflate(R.layout.equipment_list_item, llEquipmentContainer, false);
                final TextView equipment1 = (TextView) equipment.findViewById(R.id.tv);
                equipment1.setText(size);
                llEquipmentContainer.addView(equipment);
            }
        }
    }

    private void setAmenities() {

        List<String> AmentiesList = testing.getAmenties();
        if (AmentiesList.size() > 0) {
            setAmenit(testing.getAmenties());
        }


    }

    private void setPackage() {
        TextView monthly;
        TextView tmonthly;
        TextView smonthly;
        TextView yyeraly = null;
        List<Package> PackageList = testing.getPackages();
        for (Package plist : PackageList) {

            if (!TextUtils.isEmpty(plist.getOneMonth())) {
                monthly = (TextView) findViewById(R.id.oneMonth);
                monthly.setText(plist.getOneMonth() + "/-");
            } else {
                TableRow year = (TableRow) findViewById(R.id.month);
                year.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(plist.getThreeMonth())) {
                tmonthly = (TextView) findViewById(R.id.threMonths);
                tmonthly.setText(plist.getThreeMonth() + "/-");
            } else {
                TableRow year = (TableRow) findViewById(R.id.tmonth);
                year.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(plist.getSixMonth())) {
                smonthly = (TextView) findViewById(R.id.sixMonth);
                smonthly.setText(plist.getSixMonth() + "/-");
            } else {
                TableRow year = (TableRow) findViewById(R.id.smonth);
                year.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(plist.getOneYear())) {
                yyeraly = (TextView) findViewById(R.id.yeraly);
                yyeraly.setText(plist.getOneYear() + "/-");
            } else {
                TableRow year = (TableRow) findViewById(R.id.yearly);
                year.setVisibility(View.GONE);
            }

        }
    }

    private void setTime() {
        List<Schedule> ScheduleList = testing.getSchedule();
        for (Schedule slist : ScheduleList) {
            final String monTo = slist.getMonTo();
            final String tueTo = slist.getTueTo();
            final String WedTo = slist.getWedTo();
            final String ThuTo = slist.getThuTo();
            final String FriTo = slist.getFriTo();
            final String SatTo = slist.getSatTo();
            final String MonFrom = slist.getMonFrom();
            final String TueFrom = slist.getTueFrom();
            final String WedFrom = slist.getWedFrom();
            final String ThuFrom = slist.getThuFrom();
            final String FriFrom = slist.getFriFrom();
            final String SatFrom = slist.getSatFrom();

            try {
                final SimpleDateFormat monT = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj1 = monT.parse(MonFrom);

                final SimpleDateFormat MonFr = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj2 = MonFr.parse(monTo);

                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj3 = sdf.parse(TueFrom);

                final SimpleDateFormat sdf1 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj4 = sdf1.parse(tueTo);

                final SimpleDateFormat sdf2 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj5 = sdf2.parse(ThuFrom);

                final SimpleDateFormat sdf3 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj6 = sdf3.parse(ThuTo);

                final SimpleDateFormat sdf4 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj7 = sdf4.parse(FriFrom);

                final SimpleDateFormat sdf5 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj8 = sdf5.parse(FriTo);

                final SimpleDateFormat sdf6 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj9 = sdf6.parse(SatFrom);

                final SimpleDateFormat sdf7 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj10 = sdf7.parse(SatTo);

                final SimpleDateFormat sdf8 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj11 = sdf6.parse(WedFrom);

                final SimpleDateFormat sdf9 = new SimpleDateFormat("H:mm");
                final java.util.Date dateObj12 = sdf7.parse(WedTo);

                SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
                Date date = new Date();
                String dayName = sdf_.format(date);
                TextView time1 = (TextView) findViewById(R.id.content);
                time1.setText(dayName + " : " + new SimpleDateFormat("K:mm").format(dateObj1) + "\t" + "To" + "\t" + new SimpleDateFormat("K:mm").format(dateObj2));

                TextView alltime = (TextView) findViewById(R.id.allTime);
                alltime.setText("Mon: " + new SimpleDateFormat("K:mm").format(dateObj1) + " " + "To" + " " + new SimpleDateFormat("K:mm").format(dateObj2) + "\t" + "|" + "\t" + "Tue: " + new SimpleDateFormat("K:mm").format(dateObj3) + " " + "To" + " " + new SimpleDateFormat("K:mm").format(dateObj3));

                TextView alltime1 = (TextView) findViewById(R.id.allTime1);
                alltime1.setText("Wed: " + new SimpleDateFormat("K:mm").format(dateObj11) + " " + "To" + " " + new SimpleDateFormat("K:mm").format(dateObj12) + "\t" + "|" + "\t" + "Thu: " + new SimpleDateFormat("K:mm").format(dateObj5) + " " + "To" + " " + new SimpleDateFormat("K:mm").format(dateObj6));

                TextView alltime2 = (TextView) findViewById(R.id.allTime2);
                alltime2.setText("Fri\t\t: " + new SimpleDateFormat("K:mm").format(dateObj7) + " " + "To" + " " + new SimpleDateFormat("K:mm").format(dateObj8) + "\t" + "|" + "\t" + "Sat: " + new SimpleDateFormat("K:mm").format(dateObj9) + " " + "To" + " " + new SimpleDateFormat("K:mm").format(dateObj10));


            } catch (final ParseException e) {
                e.printStackTrace();
            }
            //Log.d("Logs", "Schedule:" + slist.getSatTo());

        }
    }

    private void expandableWindow() {
        expand = (TextView) findViewById(R.id.expand);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand_panel = (ExpandableRelativeLayout) findViewById(R.id.expanpanel);
                expand_panel.toggle();

            }
        });
       /* member = (TextView) findViewById(R.id.gym);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand_pane2 = (ExpandableRelativeLayout) findViewById(R.id.expanpane2);
                expand_pane2.toggle();

            }
        });*/
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.gym_toolabar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(testing.getGymName());
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.mem_pager);
        if (testing.getImages().size() > 0) {

            mPager.setAdapter(new Membership_Slider_Adapter(MembershipActivity.this, testing.getImages()));
            CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.mem_indicator);
            indicator.setViewPager(mPager);
            final float density = getResources().getDisplayMetrics().density;
            indicator.setRadius(5 * density);
            NUM_PAGES = testing.getImages().size();

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 3000);

            // Pager listener over indicator
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gym_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share body";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.like:
                item.setChecked(true);
                if (!testing.getIsFavorite().equals("0")) {

                    item.setIcon(R.drawable.rhearts);
                } else {
                    item.setIcon(R.drawable.hearts);
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
