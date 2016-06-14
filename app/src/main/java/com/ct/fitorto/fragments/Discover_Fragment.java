package com.ct.fitorto.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.SearchActivity;
import com.ct.fitorto.adapter.RecyclerViewAdapter;
import com.ct.fitorto.model.FitortoCategory;
import com.ct.fitorto.model.JsonResponseCategory;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 5/17/2016.
 */
public class Discover_Fragment extends Fragment {
    private GridLayoutManager lLayout;
    private RecyclerView rView;
    private RecyclerViewAdapter adapter;
    private TextView searchbtn;
    private ArrayList<FitortoCategory> data;//= new ArrayList<FitortoCategory>();
    private PreferenceManager preferenceManager;
    ProgressDialog pDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preferenceManager = new PreferenceManager(getActivity());
        View view = inflater.inflate(R.layout.discover_recycler, null);
        getActivity().setTitle("Discover");
        lLayout = new GridLayoutManager(getActivity(), 2);
        rView = (RecyclerView) view.findViewById(R.id.discover_recycler);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        pDialog = new ProgressDialog(getActivity());

        pDialog.setMessage("loading ...");
        pDialog.show();

        //loadJSON();
        searchbtn=(TextView)view.findViewById(R.id.search) ;
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);

            }
        });
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        Call<JsonResponseCategory> response = ApiClientMain.getApiClient().category(city);
        response.enqueue(new Callback<JsonResponseCategory>() {
            @Override
            public void onResponse(Call<JsonResponseCategory> call, Response<JsonResponseCategory> response) {

                if (response.isSuccessful()) {
                    JsonResponseCategory jsonResponse = response.body();
                    data = new ArrayList<>(jsonResponse.getData());
                    adapter = new RecyclerViewAdapter(getActivity(), data);
                    rView.setAdapter(adapter);
                    pDialog.dismiss();

                } else
                {
                    Toast.makeText(getActivity(), "Error darim else onresponse", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JsonResponseCategory> call, Throwable t) {

            }
        });


        return view;
    }




    /*private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient request = retrofit.create(ApiClient.class);
        Call<JsonResponseCategory> call = request.category("Mumbai");
        call.enqueue(new Callback<JsonResponseCategory>() {
            @Override
            public void onResponse(Call<JsonResponseCategory> call, Response<JsonResponseCategory> response) {

                if (response.isSuccessful()) {
                    JsonResponseCategory jsonResponse = response.body();
                    data = new ArrayList<>(jsonResponse.getData());
                    adapter = new RecyclerViewAdapter(getActivity(), data);
                    rView.setAdapter(adapter);

                } else {
                    Toast.makeText(getActivity(), "Error darim else onresponse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonResponseCategory> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }*/
}
