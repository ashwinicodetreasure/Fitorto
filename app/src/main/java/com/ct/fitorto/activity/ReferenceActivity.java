package com.ct.fitorto.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.RefernceAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.Coupon;
import com.ct.fitorto.model.JsonCoupon;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferenceActivity extends BaseActivity {


    private TextView tvPoint;
    private TextView tvUserName;
    private RecyclerView mRecyclerView;
    private PreferenceManager manager;
    private RefernceAdapter adapter;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        manager = new PreferenceManager(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        String name = manager.getPreferenceValues(manager.PREF_CLIENT_NAME);
        tvUserName.setText(name);
        initRecyclerData();
    }

    private void initRecyclerData() {
        showProgressDialog("Please wait...", false);
        String userid = manager.getPreferenceValues(manager.PREF_USER_UserId);
        Call<JsonCoupon> call = ApiClientMain.getApiClient().userCoupons(userid);
        call.enqueue(new Callback<JsonCoupon>() {
            @Override
            public void onResponse(Call<JsonCoupon> call, Response<JsonCoupon> response) {
                cancelProgressDialog();
                if (response.body() != null) {
                    JsonCoupon coupon = response.body();
                    if (coupon.getData().size() > 0) {
                        updateData(coupon.getData());
                        updatePointsText(coupon.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonCoupon> call, Throwable t) {
                cancelProgressDialog();
            }
        });
    }

    private void updateData(ArrayList<Coupon> data) {
        adapter = new RefernceAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
    }

    public void updatePointsText(ArrayList<Coupon> data) {
        for (Coupon coupon :
                data) {
            if (coupon.getCouponType().contains("Points")) {
                if (!TextUtils.isEmpty(coupon.getBenifit())) {
                    points = Integer.parseInt(coupon.getBenifit());
                }
            }
        }
        tvPoint.setText("" + points);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.discover_recycler);
        tvPoint = (TextView) findViewById(R.id.tvPoint);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
    }


}
