package com.ct.fitorto.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.SearchAdapter;
import com.ct.fitorto.custom.DividerItemDecoration;
import com.ct.fitorto.model.Filter;
import com.ct.fitorto.model.Search;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class SearchResultActivity extends AppCompatActivity implements SearchAdapter.OnItemClickListener, View.OnClickListener {

    private LinearLayoutManager lLayout;
    private RecyclerView mRecyclerView;
    private SearchAdapter adapter;
    private ArrayList<Search> arrayList = new ArrayList<Search>();
    private PreferenceManager preferenceManager;
    private FloatingActionButton fabFilter;
    protected static Filter mFilter;
    private TextView mEmptyView;
    private String title;
    private ArrayList<Search> temp_arraylist = new ArrayList<Search>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult_activity);
        setToolbar();
        lLayout = new LinearLayoutManager(SearchResultActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_recycler);
        fabFilter = (FloatingActionButton) findViewById(R.id.fabFilter);
        mEmptyView = (TextView) findViewById(R.id.tvEmpty);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(lLayout);

        fabFilter.setOnClickListener(this);
        initDataSet();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //ArrayList aList = new ArrayList(Arrays.asList(testing.add(testing.get().getCategory().split(","))));
            getSupportActionBar().setTitle("Search Result");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchResultActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    private void initDataSet() {
        arrayList = getIntent().getParcelableArrayListExtra(ApplicationData.SEARCH_RESULT);
        //title=getIntent().getStringExtra(ApplicationData.SPECIALIST);
        setTitle(title);
        if (arrayList.size() > 0) {
            temp_arraylist.clear();
            temp_arraylist.addAll(arrayList);
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter = new SearchAdapter(SearchResultActivity.this, arrayList);
            mRecyclerView.setAdapter(adapter);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            fabFilter.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_result_menu, menu);
        preferenceManager = new PreferenceManager(SearchResultActivity.this);
        String city = preferenceManager.getPreferenceValues(preferenceManager.ADDRESS);
        if (!TextUtils.isEmpty(city)) {
            MenuItem item = menu.getItem(0);
            SpannableString s = new SpannableString(city);
            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
            item.setTitle(s);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cityname:
                Intent i = new Intent(SearchResultActivity.this, CityActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Search item) {
        Intent link = new Intent(SearchResultActivity.this, MembershipActivity.class);
        link.putExtra(ApplicationData.FITNESS_CENTER_DETAILS, item);
        startActivity(link);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabFilter:
                Intent i = new Intent(SearchResultActivity.this, FilterActivity.class);
                startActivityForResult(i, ApplicationData.REQUEST_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApplicationData.REQUEST_CODE && data != null) {
            /*int filter = data.getIntExtra(FilterActivity.FILTER_SORT, 0);
            int filterFees = data.getIntExtra(FilterActivity.FILTER_FEES, 0);
            int filterRating = data.getIntExtra(FilterActivity.FILTER_RATING, 0);*/
            if (mFilter == null) {
                initDataSet();
            } else {
                int filter = mFilter.getSortBy().getId();
                int filterFees = mFilter.getFees().getId();
                int filterRating = mFilter.getRating().getId();
                if (filter != 0) {
                    caseHandleFilter(filter);
                }
                if (filterRating != 0) {
                    caseHandleFilter(filterRating);
                    // caseSortArrayListByRating(filterRating);
                }
                if (filter != 0) {
                    caseHandleFilter(filterFees);
                }
            }
        }
    }


    private void caseSortArrayListByRating(float filterRating) {
        filterByRating(filterRating, 5);
    }

    private void caseHandleFilter(int filter) {
        switch (filter) {
            case R.id.btn_fees:
                Collections.sort(arrayList, new FeesComparator());
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_below_200:
                getFilterResult(1, 500);
                break;
            case R.id.btn_between_500:
                getFilterResult(500, 1000);
                break;
            case R.id.btn_above_500:
                getFilterResult(1000, 100000);
                break;
            case R.id.btn_1:
                getFilterResult(0.5f, 5f);
                break;
            case R.id.btn_2:
                getFilterResult(1.5f, 5f);
                break;
            case R.id.btn_3:
                getFilterResult(2.5f, 5f);
                break;
            case R.id.btn_4:
                getFilterResult(3.5f, 5f);
                break;
            case R.id.btn_5:
                getFilterResult(4.5f, 5f);
                break;
        }
    }

    public void sortArrayList() {
        Collections.sort(temp_arraylist, new ProductRatingSort());
        adapter = new SearchAdapter(SearchResultActivity.this, temp_arraylist);
        //adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void getFilterResult(float objMin, float objMax) {
        ArrayList<Search> arraylist = new ArrayList<Search>();
        Collections.sort(temp_arraylist, new FeesComparator());
        for (int i = 0; i < temp_arraylist.size(); i++) {
            int price = (Integer.parseInt(temp_arraylist.get(i).getPackages().get(0).getOneMonth()));
            if (price >= objMin && price <= objMax) {
                arraylist.add(temp_arraylist.get(i));
            }
        }
        if (arraylist.size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter = new SearchAdapter(SearchResultActivity.this, arraylist);
            //  myDoctorAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    public void getRatingFilterResult(double objMin, double objMax) {
        //ArrayList<MyDoctor> temp_arraylist = new ArrayList<MyDoctor>();
        temp_arraylist.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            double price = (Double.parseDouble(arrayList.get(i).getRating()));
            if (price >= objMin && price <= objMax) {
                temp_arraylist.add(arrayList.get(i));
            }
        }
        if (temp_arraylist.size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter = new SearchAdapter(SearchResultActivity.this, temp_arraylist);
            //adapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    public void filterByRating(float objMin, float objMax) {
        temp_arraylist.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            float price = (Float.parseFloat(arrayList.get(i).getRating()));
            if (price >= objMin && price <= objMax) {
                temp_arraylist.add(arrayList.get(i));
            }
        }
        if (temp_arraylist.size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            adapter = new SearchAdapter(SearchResultActivity.this, temp_arraylist);
            // adapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    public class FeesComparator implements Comparator<Search> {
        @Override
        public int compare(Search o1, Search o2) {
            return o1.getPackages().get(0).getOneMonth().compareTo(o2.getPackages().get(0).getOneMonth());
        }
    }

    public class RatingComparator implements Comparator<Search> {
        @Override
        public int compare(Search o1, Search o2) {
            return Double.compare(Double.valueOf(o1.getRating()), Double.valueOf(o2.getRating()));
        }
    }

    public class ProductRatingSort implements Comparator<Search> {

        @Override
        public int compare(Search arg0, Search arg1) {
            // TODO Auto-generated method stub
            float f = Float.valueOf(arg0.getRating());
            float s = Float.valueOf(arg1.getRating());
            return f < s ? 1
                    : f > s ? -1
                    : 0;
        }
    }

}
