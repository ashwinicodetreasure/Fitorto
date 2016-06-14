package com.ct.fitorto.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.ShareActivity;
import com.ct.fitorto.adapter.RecyclerFeedAdapter;
import com.ct.fitorto.model.Feed;
import com.ct.fitorto.model.JsonResponseFeed;
import com.ct.fitorto.network.ApiClientMain;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ashwini on 5/16/2016.
 */
public class Feed_Fragment extends Fragment {
    private LinearLayoutManager llayout;
    private FloatingActionButton fab;
    private RecyclerFeedAdapter adapter;
    private  RecyclerView rview;
    private ProgressDialog pDialog;

    private ArrayList<Feed> feed=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.feed_recycler, container, false);
        getActivity().setTitle("News Feeds");

        llayout = new LinearLayoutManager(getActivity());
        rview = (RecyclerView) view.findViewById(R.id.discover_recycler);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(llayout);
        pDialog = new ProgressDialog(getActivity());

        pDialog.setMessage("loading ...");
        pDialog.show();
        //RecyclerFeedAdapter radapter = new RecyclerFeedAdapter(getActivity(), listItem);
        //rview.setAdapter(radapter);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), ShareActivity.class);
                    startActivity(intent);
                }
            });

        Call<JsonResponseFeed> response = ApiClientMain.getApiClient().getResponseFedd("1");
        response.enqueue(new Callback<JsonResponseFeed>() {
            @Override
            public void onResponse(Call<JsonResponseFeed> call, Response<JsonResponseFeed> response) {

                if (response.isSuccessful()) {



                    JsonResponseFeed jsonResponse = response.body();
                    feed = new ArrayList<>(jsonResponse.getData());
                    adapter = new RecyclerFeedAdapter(getActivity(), feed);
                    rview.setAdapter(adapter);
                    pDialog.dismiss();


                } else {
                    Toast.makeText(getActivity(), "Error darim else onresponse", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JsonResponseFeed> call, Throwable t) {

            }
        });
        return view;
    }







}
