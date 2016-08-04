package com.ct.fitorto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.JsonResponseKeywords;
import com.ct.fitorto.model.Location;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 7/1/16.
 */
public class ChangeLocationActivity extends BaseActivity {

    private ListView clist;
    List<Location> citylist = new ArrayList<Location>();
    private PreferenceManager preferenceManager;
    LocationAdapter adapt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
        initToolbar(true);

        preferenceManager = new PreferenceManager(ChangeLocationActivity.this);

        clist = (ListView) findViewById(R.id.city_listview);
        Call<JsonResponseKeywords> call = ApiClientMain.getApiClient().getResponseKeywordsCall();
        call.enqueue(new Callback<JsonResponseKeywords>() {
            @Override
            public void onResponse(Call<JsonResponseKeywords> call, Response<JsonResponseKeywords> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getData().size() > 0) {
                            citylist.addAll(response.body().getData());   //Always addall for arraylist
                            if (citylist.size() > 0) {
                                adapt = new LocationAdapter(ChangeLocationActivity.this, R.layout.city_list_item, citylist);
                                clist.setAdapter(adapt);
                            }
                        }
                    }
                }
                clist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Location city = (Location) adapterView.getItemAtPosition(i);
                        preferenceManager.putPreferenceValues(preferenceManager.ADDRESS, city.getArea()+","+city.getCity());
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_City, city.getCity());
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_AREA, city.getArea());
                        finishThis();
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonResponseKeywords> call, Throwable t) {

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

