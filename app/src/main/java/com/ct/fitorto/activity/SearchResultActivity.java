package com.ct.fitorto.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.SearchAdapter;
import com.ct.fitorto.model.Search;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class SearchResultActivity extends AppCompatActivity implements SearchAdapter.OnItemClickListener {
    private LinearLayoutManager lLayout;
    private RecyclerView rView;
    private SearchAdapter adapter;
    private ArrayList<Search> testing = new ArrayList<Search>();
    private ProgressDialog pDialog;
    private PreferenceManager preferenceManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult_activity);

        setToolbar();

        lLayout = new LinearLayoutManager(SearchResultActivity.this);
        rView = (RecyclerView) findViewById(R.id.search_recycler);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);


        pDialog = new ProgressDialog(SearchResultActivity.this);
        pDialog.setMessage("loading ...");

        pDialog.show();
        testing.addAll(getIntent().<Search>getParcelableArrayListExtra("searchItem"));

        if (testing.size() > 0) {

            adapter = new SearchAdapter(SearchResultActivity.this, testing);
            Log.d("Logs", "Search:" + adapter);
            rView.setAdapter(adapter);
            pDialog.dismiss();


        }


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_result_menu, menu);
        preferenceManager = new PreferenceManager(SearchResultActivity.this);
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);

        if (!TextUtils.isEmpty(city)) {

            MenuItem item = menu.getItem(0);
            SpannableString s = new SpannableString(city);
            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
            item.setTitle(s);

        }

        /* MenuItem mi = (MenuItem)findViewById(R.id.like);
        mi.setChecked(true);*/
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
        link.putExtra("memberItem", item);
        startActivity(link);


    }


}
