package com.ct.fitorto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.CityAdapter;
import com.ct.fitorto.model.City;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ashwini on 5/26/2016.
 */
public class CityActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView clist;
    List<City> citylist=new ArrayList<>();
    private PreferenceManager preferenceManager;
    CityAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Select City");
            //getSupportActionBar().setHomeButtonEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        preferenceManager = new PreferenceManager(CityActivity.this);

        clist = (ListView) findViewById(R.id.city_listview);


        Call<JsonResponseUser> call = ApiClientMain.getApiClient().city("sunil@gmail.com","123456");
        call.enqueue(new Callback<JsonResponseUser>() {
            @Override
            public void onResponse(Call<JsonResponseUser> call, Response<JsonResponseUser> response) {
                if (response.isSuccessful()) {
                    if(response.body().getCities().size()>0){
                        citylist.addAll(response.body().getCities());   //Always addall for arraylist
                        if (citylist.size() > 0) {
                            adapt = new CityAdapter(CityActivity.this, R.layout.city_list_item, citylist);
                            clist.setAdapter(adapt);
                        }
                    }

                }
                clist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        preferenceManager.putPreferenceValues(preferenceManager.PREF_City, citylist.get(i).toString());

                        Intent link=new Intent(CityActivity.this,HomeActivity.class);
                        link.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        //link.putExtra("res",lp.get(i));
                        startActivity(link);

                    }
                });
            }

            @Override
            public void onFailure(Call<JsonResponseUser> call, Throwable t) {

            }
        });



    }


    @Override
    public void onClick(View v) {

    }
}
