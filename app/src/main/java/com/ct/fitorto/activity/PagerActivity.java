package com.ct.fitorto.activity;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ct.fitorto.R;
import com.ct.fitorto.custom.Typewriter;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.ct.fitorto.utils.Utils;


public class PagerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    ImageButton mNextBtn;
    Button mSkipBtn, mFinishBtn;

    ImageView zero, one, two,three;
    ImageView[] indicators;

    int lastLeftValue = 0;

    CoordinatorLayout mCoordinator;


    static final String TAG = "PagerActivity";

    int page = 0;   //  to track page position
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        }



        setContentView(R.layout.activity_pager);
        preferenceManager = new PreferenceManager(PagerActivity.this);
        boolean isFirstTime=preferenceManager.getPreferenceBoolValues(ApplicationData.PREF_USER_FIRST_TIME);
        if(!isFirstTime){
            Intent i = new Intent(PagerActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn.setImageDrawable(
                    Utils.tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            );

        mSkipBtn = (Button) findViewById(R.id.intro_btn_skip);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);

        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        three= (ImageView) findViewById(R.id.intro_indicator_3);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);


        indicators = new ImageView[]{zero, one, two,three};

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int color1 = ContextCompat.getColor(this, R.color.cyan);
        final int color2 = ContextCompat.getColor(this, R.color.orange);
        final int color3 = ContextCompat.getColor(this, R.color.green);
        final int color4=ContextCompat.getColor(this,R.color.color7);
        final int[] colorList = new int[]{color1, color2, color3,color4};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                /*
                color update
                 */
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 3 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);

            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                updateIndicators(page);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                    case 3:
                        mViewPager.setBackgroundColor(color4);
                        break;
                }
                mNextBtn.setVisibility(position == 3 ? View.GONE : View.VISIBLE);
                mFinishBtn.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
            }
        });

        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.putPreferenceBoolValues(ApplicationData.PREF_USER_FIRST_TIME, false);
                Intent i = new Intent(PagerActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
               // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.putPreferenceBoolValues(ApplicationData.PREF_USER_FIRST_TIME, false);
                Intent i = new Intent(PagerActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //Utils.saveSharedSetting(PagerActivity.this, ApplicationData.PREF_USER_FIRST_TIME, "false");
            }
        });

    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        ImageView img;
        int[] bgs = new int[]{R.drawable.ic_gymnasium, R.drawable.graph, R.drawable.network,R.drawable.feed_vector_drwable};
        String[] title = new String[]{"Connect with your fitness centers.", "Track your fitness", "Refer a friend and get points.","Read and share daily fitness related feeds."};

        //  String[] desc=new String[]{""}
        public PlaceholderFragment() {
        }

        private Typewriter textView;
        private Handler mHandler = new Handler();
        private int delay = 80;

        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private ImageView img4;
        private RelativeLayout rlScreen1;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
            textView = (Typewriter) rootView.findViewById(R.id.section_label);
            img = (ImageView) rootView.findViewById(R.id.section_img);
            img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
            textView.setCharacterDelay(delay);

            img1= (ImageView) rootView.findViewById(R.id.img1);
            img2= (ImageView) rootView.findViewById(R.id.img2);
            img3= (ImageView) rootView.findViewById(R.id.img3);
            img4= (ImageView) rootView.findViewById(R.id.img4);
            rlScreen1= (RelativeLayout) rootView.findViewById(R.id.rlScreen1);
          //  chart= (LineChartView) rootView.findViewById(R.id.chart);
            int viewPagerItem = (getArguments().getInt(ARG_SECTION_NUMBER) - 1);
            if (viewPagerItem == 0) {
                initTextData();
                animateTranslateImages();
            }
            return rootView;
        }

        private void animateTranslateImages() {
            rlScreen1.setVisibility(View.VISIBLE);
            img.setVisibility(View.INVISIBLE);
            Animation mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_top);
            Animation mAnimation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_right);
            Animation mAnimation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_left);
            Animation mAnimation3 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_bottom);
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
            img3.setVisibility(View.INVISIBLE);
            img4.setVisibility(View.INVISIBLE);
            img1.startAnimation(mAnimation);
            img2.startAnimation(mAnimation1);
            img3.startAnimation(mAnimation2);
            img4.startAnimation(mAnimation3);
            mAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    img1.setVisibility(View.VISIBLE);
                    img2.setVisibility(View.VISIBLE);
                    img3.setVisibility(View.VISIBLE);
                    img4.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }
            });
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser) {
                if (textView != null) {
                    initTextData();
                }
            }
        }

        public void initTextData() {
            final int index = (getArguments().getInt(ARG_SECTION_NUMBER) - 1);
            textView.animateText(title[index]);
            int i = delay * (title[getArguments().getInt(ARG_SECTION_NUMBER) - 1]).length();
            /*final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.removeMessages(0);
                    mViewPager.setCurrentItem(index + 1, true);
                    //  Toast.makeText(getActivity(), "Toast"+(getArguments().getInt(ARG_SECTION_NUMBER) - 1), Toast.LENGTH_SHORT).show();
                }
            }, i + 800);*/
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
            }
            return null;
        }

    }


}
