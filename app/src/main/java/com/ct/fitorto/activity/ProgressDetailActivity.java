package com.ct.fitorto.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.FollowerAdapter;
import com.ct.fitorto.adapter.FollowingAdapter;
import com.ct.fitorto.adapter.ProgressDetailAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.Detail;
import com.ct.fitorto.model.Follower;
import com.ct.fitorto.model.Following;
import com.ct.fitorto.model.ProgressDetail;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

/**
 * Created by codetreasure on 8/20/16.
 */
public class ProgressDetailActivity extends BaseActivity {


    private PreferenceManager manager;
    private RecyclerView mRecyclerView;
    private ProgressDetailAdapter adapter;
    private TextView tvEmptyView;
    private LinearLayout empty_view;
    private ProgressDetail progressDetail;
    private ArrayList<Detail> details = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        progressDetail = getIntent().getParcelableExtra(ApplicationData.PROGRESS_LIST);
        if (progressDetail != null) {
            if (progressDetail.getDetails().size() > 0) {
                setTitle(progressDetail.getCategory()+" in "+ progressDetail.getDetails().get(0).getUnit());
                details.addAll(progressDetail.getDetails());
                adapter = new ProgressDetailAdapter(this, details);
                mRecyclerView.setAdapter(adapter);
            }
        }
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
}
