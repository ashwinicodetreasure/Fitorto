package com.ct.fitorto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.NotificationAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.JsonResponseNotification;
import com.ct.fitorto.model.JsonResponseSearch;
import com.ct.fitorto.model.Notifications;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 7/7/16.
 */
public class NotificationActivity extends BaseActivity implements NotificationAdapter.OnItemClickListener {

    private PreferenceManager manager;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private TextView tvEmptyView;
    private LinearLayout empty_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        if (ApplicationUtility.checkConnection(this)) {
            empty_view.setVisibility(View.GONE);
            showProgressDialog("Please wait...", false);
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            Call<JsonResponseNotification> call = ApiClientMain.getApiClient().getNotification(userId);
            call.enqueue(new Callback<JsonResponseNotification>() {
                @Override
                public void onResponse(Call<JsonResponseNotification> call, Response<JsonResponseNotification> response) {
                    cancelProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getData().size() > 0) {
                            tvEmptyView.setVisibility(View.GONE);
                            updateData(response.body().getData());
                        } else {
                            mRecyclerView.setVisibility(View.GONE);
                            tvEmptyView.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseNotification> call, Throwable t) {
                    cancelProgressDialog();
                }
            });
        } else {
            empty_view.setVisibility(View.VISIBLE);
            ImageView ivRetry = (ImageView) findViewById(R.id.ivRetry);
            ivRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initDataSet();
                }
            });
        }

    }

    private void updateData(ArrayList<Notifications> data) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter = new NotificationAdapter(NotificationActivity.this, data);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        manager = new PreferenceManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        tvEmptyView = (TextView) findViewById(R.id.tvEmpty);
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
               /* Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homeIntent);*/
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onItemClick(View view, Notifications position) {
        if (position.getNotificationCategory() == 4) {
            Intent intent = new Intent(this, ReferenceActivity.class);
            startActivity(intent);
        }
    }
}
