package com.ct.fitorto.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.ShareActivity;
import com.ct.fitorto.adapter.FeedAdapter;
import com.ct.fitorto.baseclass.BaseFragment;
import com.ct.fitorto.model.Feed;
import com.ct.fitorto.model.JsonResponseFeed;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ashwini on 5/16/2016.
 */
public class FeedFragment extends BaseFragment {

    private LinearLayoutManager llayout;
    private FloatingActionButton fab;
    private FeedAdapter adapter;
    private RecyclerView rview;
    private ProgressDialog pDialog;
    private PreferenceManager preferenceManager;
    private ArrayList<Feed> feed = new ArrayList<>();
    private LinearLayout empty_view;
    private ImageView ivNoInterNet;
    private TextView tvEmpty;
    private ImageButton ivRetry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        getActivity().setTitle("");

        llayout = new LinearLayoutManager(getActivity());
        rview = (RecyclerView) view.findViewById(R.id.discover_recycler);
        empty_view = (LinearLayout) view.findViewById(R.id.empty_view);
        ivNoInterNet = (ImageView) view.findViewById(R.id.ivNoInterNet);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);
        ivRetry = (ImageButton) view.findViewById(R.id.ivRetry);
        preferenceManager = new PreferenceManager(getActivity());
        rview.setHasFixedSize(true);
        rview.setLayoutManager(llayout);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ShareActivity.class);
                startActivityForResult(intent, ApplicationData.FEED_REQUEST_CODE);
            }
        });
        getFeedData(true);
        return view;
    }

    public void getFeedData(final boolean showProgressbar) {
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        if (userId.equals("0")) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Please login to see daily news feeds from friends and fitness centers.");
        } else {
            if (ApplicationUtility.checkConnection(getActivity())) {
                if (showProgressbar) {
                    showProgressDialog("Please wait...", false);
                }
                empty_view.setVisibility(View.GONE);
                ivRetry.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.GONE);
                ivNoInterNet.setVisibility(View.GONE);

                Call<JsonResponseFeed> response = ApiClientMain.getApiClient().getResponseFedd("1");
                response.enqueue(new Callback<JsonResponseFeed>() {
                    @Override
                    public void onResponse(Call<JsonResponseFeed> call, Response<JsonResponseFeed> response) {
                        if (showProgressbar) {
                            cancelProgressDialog();
                        }

                        if (response.isSuccessful()) {
                            JsonResponseFeed jsonResponse = response.body();
                            if (jsonResponse != null) {
                                if (jsonResponse.getData().size() > 0) {
                                    feed.addAll(jsonResponse.getData());
                                    adapter = new FeedAdapter(getActivity(), feed);
                                    rview.setAdapter(adapter);
                                } else {
                                    tvEmpty.setVisibility(View.VISIBLE);
                                    tvEmpty.setText(getActivity().getString(R.string.feed_empty_text));
                                }
                            }
                        } else {
                            tvEmpty.setVisibility(View.VISIBLE);
                            tvEmpty.setText(getActivity().getString(R.string.feed_empty_text));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponseFeed> call, Throwable t) {
                        if (showProgressbar) {
                            cancelProgressDialog();
                        }
                        empty_view.setVisibility(View.VISIBLE);
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText("Something went wrong.Please try again");
                        ivRetry.setVisibility(View.VISIBLE);
                        ivNoInterNet.setVisibility(View.GONE);
                        ivRetry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getFeedData(true);
                            }
                        });
                    }
                });
            } else {
                empty_view.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("No internet found.Check your connection or try again");
                ivRetry.setVisibility(View.VISIBLE);
                ivNoInterNet.setVisibility(View.VISIBLE);
                ivRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getFeedData(true);
                    }
                });
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFeedData(false);
    }
}
