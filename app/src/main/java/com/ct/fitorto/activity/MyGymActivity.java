package com.ct.fitorto.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.Membership_Slider_Adapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.custom.CirclePageIndicator;
import com.ct.fitorto.flowlayout.FlowLayout;
import com.ct.fitorto.model.Friday;
import com.ct.fitorto.model.Gym;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.model.Monday;
import com.ct.fitorto.model.Package;
import com.ct.fitorto.model.Saturday;
import com.ct.fitorto.model.Schedule;
import com.ct.fitorto.model.Search;
import com.ct.fitorto.model.Sunday;
import com.ct.fitorto.model.Thursday;
import com.ct.fitorto.model.Tuesday;
import com.ct.fitorto.model.Wednesday;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.ct.fitorto.utils.DateTimeUtils;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 8/21/16.
 */
public class MyGymActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager mPager;
    private int currentPage = 0;
    private int NUM_PAGES = 0;
    private Gym search;
    private Schedule schedule;
    private TextView expand, member;
    private TextView tvLoc;
    private TextView tvTime;
    TextView zumba;
    private ExpandableRelativeLayout expand_panel, expand_pane2;
    private LinearLayout llEquipmentContainer;
    private LinearLayout llAmenitiContainer;
    private FlowLayout mFlowLayout;
    private PreferenceManager manager;
    private LinearLayout llPackageContainer;

    // private static final int MY_BUTTON = 9000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership_main_layout);
        search = getIntent().getParcelableExtra(ApplicationData.FITNESS_CENTER_DETAILS);
        manager = new PreferenceManager(this);
        if (search != null) {
            initImageSlider();
            setToolbar();
            expandableWindow();
            setScheduleLayout();
            setPackageLayout(search.getPackages());
            setAmenities();
            setEquipment();
            locationMapLayout();
            setInfoLayout();
            tvLoc = (TextView) findViewById(R.id.location);
            tvLoc.setText(search.getAddress());
            TextView gender = (TextView) findViewById(R.id.tvgender);
            findViewById(R.id.buy_mem).setOnClickListener(this);
            gender.setText(search.getGender());
        }
    }

    private void setPackageLayout(List<Package> packageList) {
        llPackageContainer = (LinearLayout) findViewById(R.id.llPackageContainer);
        if (packageList != null && packageList.size() > 0) {
            for (Package aPackage : packageList) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.list_item_package, llPackageContainer, false);
                TextView tvSession = (TextView) view.findViewById(R.id.tvSession);
                TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
                tvSession.setText(aPackage.getPackageType() + " " + aPackage.getPackageName());
                tvPrice.setText(aPackage.getCost());
                llPackageContainer.addView(view);
            }
        }
    }


    private void setInfoLayout() {
        if (!TextUtils.isEmpty(search.getCategory())) {
            ArrayList aList = new ArrayList(Arrays.asList(search.getCategory().split(",")));
            if (aList.size() > 0) {
                setCategory(aList);
            }
        }
    }

    private void setCategory(List<String> sizeArrayList) {
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


    private void locationMapLayout() {
        final String lat = search.getLatitude().toString();
        final String lag = search.getLongitude().toString();
        String url = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lag + "&markers=icon:http://tinyurl.com/2ftvtt6|" + lat + "," + lag + "&zoom=15&size=500x500&sensor=true";
        ImageView location = (ImageView) findViewById(R.id.location_image);
        Picasso.with(MyGymActivity.this).load(url).into(location);
        location.setScaleType(ImageView.ScaleType.FIT_XY);
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


        location.setOnClickListener(new View.OnClickListener() {
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
        if (search.getEquipments() != null) {
            if (search.getEquipments().size() > 0) {
                setEquipmentLayout(search.getEquipments());
            }
        }
    }

    public void setAmentiesLayout(List<String> sizeArrayList) {
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

    public void setEquipmentLayout(List<String> sizeArrayList) {
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
        if (search.getAmenties() != null) {
            if (search.getAmenties().size() > 0) {
                setAmentiesLayout(search.getAmenties());
            }
        }
    }

  /*  private void setPackageLayout() {
        TextView monthly;
        TextView threeMonth;
        TextView sixMonth;
        TextView yearly;
        if (search.getPackages() != null) {
            if (search.getPackages().size() > 0) {
                List<Package> PackageList = search.getPackages();
                for (Package plist : PackageList) {

                    if (!TextUtils.isEmpty(plist.getOneMonth())) {
                        monthly = (TextView) findViewById(R.id.oneMonth);
                        monthly.setText(plist.getOneMonth() + "/-");
                    } else {
                        TableRow year = (TableRow) findViewById(R.id.month);
                        year.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(plist.getThreeMonth())) {
                        threeMonth = (TextView) findViewById(R.id.threMonths);
                        threeMonth.setText(plist.getThreeMonth() + "/-");
                    } else {
                        TableRow year = (TableRow) findViewById(R.id.tmonth);
                        year.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(plist.getSixMonth())) {
                        sixMonth = (TextView) findViewById(R.id.sixMonth);
                        sixMonth.setText(plist.getSixMonth() + "/-");
                    } else {
                        TableRow year = (TableRow) findViewById(R.id.smonth);
                        year.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(plist.getOneYear())) {
                        yearly = (TextView) findViewById(R.id.yeraly);
                        yearly.setText(plist.getOneYear() + "/-");
                    } else {
                        TableRow year = (TableRow) findViewById(R.id.yearly);
                        year.setVisibility(View.GONE);
                    }
                }
            }
        }
    }*/

    private void setScheduleLayout() {
        TextView todayDay = (TextView) findViewById(R.id.content);
        TextView mon = (TextView) findViewById(R.id.tvMon);
        TextView tue = (TextView) findViewById(R.id.tvTue);
        TextView wed = (TextView) findViewById(R.id.tvWed);
        TextView thu = (TextView) findViewById(R.id.tvThu);
        TextView fri = (TextView) findViewById(R.id.tvFri);
        TextView sat = (TextView) findViewById(R.id.tvSat);
        TextView sun = (TextView) findViewById(R.id.tvSun);

        LinearLayout llTodayContainer = (LinearLayout) findViewById(R.id.llTodayContainer);
        LinearLayout llMonContainer = (LinearLayout) findViewById(R.id.llMonContainer);
        LinearLayout llTueContainer = (LinearLayout) findViewById(R.id.llTueContainer);
        LinearLayout llWedContainer = (LinearLayout) findViewById(R.id.llWedContainer);
        LinearLayout llThuContainer = (LinearLayout) findViewById(R.id.llThuContainer);
        LinearLayout llFriContainer = (LinearLayout) findViewById(R.id.llFriContainer);
        LinearLayout llSatContainer = (LinearLayout) findViewById(R.id.llSatContainer);
        LinearLayout llSunContainer = (LinearLayout) findViewById(R.id.llSunContainer);

        if (search.getSchedule() != null) {
            if (search.getSchedule().size() > 0) {
                setMonLayout(todayDay, llTodayContainer, search.getSchedule().get(0));
                setMonLayout(mon, llMonContainer, search.getSchedule().get(0));
                setTueLayout(tue, llTueContainer, search.getSchedule().get(1));
                setWedLayout(wed, llWedContainer, search.getSchedule().get(2));
                setThuLayout(thu, llThuContainer, search.getSchedule().get(3));
                setFriLayout(fri, llFriContainer, search.getSchedule().get(4));
                setSatLayout(sat, llSatContainer, search.getSchedule().get(5));
                setSunLayout(sun, llSunContainer, search.getSchedule().get(6));
            }
        }
    }

    private void setMonLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getMonday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Monday monday : schedule.getMonday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }


    private void setTueLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getTuesday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Tuesday monday : schedule.getTuesday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }


    private void setWedLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getWednesday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Wednesday monday : schedule.getWednesday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }


    private void setThuLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getThursday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Thursday monday : schedule.getThursday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }


    private void setFriLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getFriday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Friday monday : schedule.getFriday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }

    private void setSatLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getSaturday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Saturday monday : schedule.getSaturday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }

    private void setSunLayout(TextView todayDay, LinearLayout llTodayContainer, Schedule schedule) {
        /*Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());*/
        if (schedule != null && schedule.getSunday().size() > 0) {
            todayDay.setVisibility(View.VISIBLE);
            llTodayContainer.setVisibility(View.VISIBLE);
            for (Sunday monday : schedule.getSunday()) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.list_item_timing, llTodayContainer, false);
                TextView tvTiming = (TextView) layout.findViewById(R.id.tvTiming);
                tvTiming.setText(DateTimeUtils.formatTime(monday.getFrom()) + " - " + DateTimeUtils.formatTime(monday.getTo()));
                TextView tvCategory = (TextView) layout.findViewById(R.id.tvCategory);
                tvCategory.setText(monday.getCategory());
                llTodayContainer.addView(layout);
            }
        } else {
            todayDay.setVisibility(View.GONE);
            llTodayContainer.setVisibility(View.GONE);
        }
    }

    /*private void setTime() {
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
    }*/

    private void expandableWindow() {
        expand = (TextView) findViewById(R.id.expand);
        expand_panel = (ExpandableRelativeLayout) findViewById(R.id.expanpanel);
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand_panel.toggle();

            }
        });
        expand_panel.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            // You can get notification that your expandable layout is going to open or close.
            // So, you can set the animation synchronized with expanding animation.
            @Override
            public void onPreOpen() {
            }

            @Override
            public void onPreClose() {
            }

            @Override
            public void onOpened() {
                expand.setText("Hide All Timing");
            }

            @Override
            public void onClosed() {
                expand.setText("Show All Timing");
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
            getSupportActionBar().setTitle(search.getGymName());
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

    private void initImageSlider() {
        mPager = (ViewPager) findViewById(R.id.mem_pager);
        if (search.getImages().size() > 0) {
            mPager.setAdapter(new Membership_Slider_Adapter(MyGymActivity.this, search.getImages()));
            CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.mem_indicator);
            indicator.setViewPager(mPager);
            final float density = getResources().getDisplayMetrics().density;
            indicator.setRadius(5 * density);
            NUM_PAGES = search.getImages().size();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gym_menu, menu);
        MenuItem mi = (MenuItem) menu.findItem(R.id.like);
        if (search.getIsFavorite().equals("1")) {
            mi.setIcon(R.drawable.rhearts);
        } else {
            mi.setIcon(R.drawable.hearts);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.share:
                String userFitortoId = manager.getPreferenceValues(manager.FITORTO_ID);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "I have an Fitorto App for you to benefit your fitness. Register with " + search.getGymName() + " with my code " + userFitortoId + " to avail the coupon! " + "https://goo.gl/EX0k52"; //Todo change link with playstore link
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Fitorto Fitness App");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.like:
                // item.setChecked(true);
                if (search.getIsFavorite().equals("0")) {
                    item.setIcon(R.drawable.rhearts);
                    search.setIsFavorite("1");
                    markFitnessCenterFavourite(search.getGymID(), "1", item);
                } else {
                    item.setIcon(R.drawable.hearts);
                    search.setIsFavorite("0");
                    markFitnessCenterFavourite(search.getGymID(), "0", item);
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buy_mem) {
            Intent intent = new Intent(this, BookGymActivity.class);
            intent.putExtra(ApplicationData.MY_GYM_RESULT,search);
            startActivity(intent);
        }
    }


    public void markFitnessCenterFavourite(String gymId, String flag, final MenuItem item) {
        String userID = manager.getPreferenceValues(manager.PREF_USER_UserId);
        Call<JsonResponseFollow> response = ApiClientMain.getApiClient().setFavorite(userID, gymId, flag);
        response.enqueue(new Callback<JsonResponseFollow>() {
            @Override
            public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {
                if (response.body() != null) {
                    if (response.body().getMsg().contains("Marked as Favorite!")) {
                        item.setChecked(true);
                    } else {
                        item.setChecked(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseFollow> call, Throwable t) {

            }
        });

    }
}
