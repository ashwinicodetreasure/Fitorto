package com.ct.fitorto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.SearchActivity;
import com.ct.fitorto.activity.SearchResultActivity;
import com.ct.fitorto.adapter.DiscoverAdapter;
import com.ct.fitorto.baseclass.BaseFragment;
import com.ct.fitorto.model.FitortoCategory;
import com.ct.fitorto.model.JsonResponseCategory;
import com.ct.fitorto.model.JsonResponseSearch;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 5/17/2016.
 */
public class DiscoverFragment extends BaseFragment implements DiscoverAdapter.OnItemClickListener {

    private GridLayoutManager lLayout;
    private RecyclerView rView;
    private DiscoverAdapter adapter;
    private TextView searchbtn;
    private ArrayList<FitortoCategory> data = new ArrayList<FitortoCategory>();
    private PreferenceManager preferenceManager;
    private LinearLayout empty_view;
    private ImageView ivNoInterNet;
    private TextView tvEmpty;
    private ImageButton ivRetry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preferenceManager = new PreferenceManager(getActivity());
        View view = inflater.inflate(R.layout.discover_fragment, null);
        getActivity().setTitle("");
        lLayout = new GridLayoutManager(getActivity(), 2);
        rView = (RecyclerView) view.findViewById(R.id.discover_recycler);
        empty_view = (LinearLayout) view.findViewById(R.id.empty_view);
        ivNoInterNet = (ImageView) view.findViewById(R.id.ivNoInterNet);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);
        ivRetry = (ImageButton) view.findViewById(R.id.ivRetry);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        searchbtn = (TextView) view.findViewById(R.id.search);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);

            }
        });
        getDiscoverData();
        return view;
    }

    public void getDiscoverData() {
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        if (ApplicationUtility.checkConnection(getActivity())) {
            showProgressDialog("Please wait...", false);
            empty_view.setVisibility(View.GONE);
            ivRetry.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.GONE);
            ivNoInterNet.setVisibility(View.GONE);
            Call<JsonResponseCategory> response = ApiClientMain.getApiClient().category(city);
            response.enqueue(new Callback<JsonResponseCategory>() {
                @Override
                public void onResponse(Call<JsonResponseCategory> call, Response<JsonResponseCategory> response) {
                    cancelProgressDialog();
                    if (response.isSuccessful()) {
                        JsonResponseCategory jsonResponse = response.body();
                        if (jsonResponse != null) {
                            data.addAll(jsonResponse.getData());
                            if (data.size() > 0) {
                                setDiscoverAdapter(data);
                            } else {
                                tvEmpty.setVisibility(View.VISIBLE);
                                tvEmpty.setText(getActivity().getString(R.string.discover_empty_text));
                            }
                        }
                    } else {
                        tvEmpty.setVisibility(View.VISIBLE);
                        tvEmpty.setText(getActivity().getString(R.string.discover_empty_text));
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseCategory> call, Throwable t) {
                    cancelProgressDialog();
                    empty_view.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText("Something went wrong.Please try again");
                    ivRetry.setVisibility(View.VISIBLE);
                    ivNoInterNet.setVisibility(View.GONE);
                    ivRetry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getDiscoverData();
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
                    getDiscoverData();
                }
            });
        }

    }

    private void setDiscoverAdapter(ArrayList<FitortoCategory> data) {
        adapter = new DiscoverAdapter(getActivity(), data);
        rView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, FitortoCategory category) {
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        String locality = preferenceManager.getPreferenceValues(preferenceManager.PREF_LOCALITY);
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        if (ApplicationUtility.checkConnection(getActivity())) {
            if (!TextUtils.isEmpty(category.getCategoryName())) {
                showProgressDialog("Please wait...", false);
                Call<JsonResponseSearch> call = ApiClientMain.getApiClient().search(userId, locality, city, category.getCategoryName());
                call.enqueue(new Callback<JsonResponseSearch>() {
                    @Override
                    public void onResponse(Call<JsonResponseSearch> call, final Response<JsonResponseSearch> response) {
                        cancelProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    Intent link = new Intent(getActivity(), SearchResultActivity.class);
                                    link.putParcelableArrayListExtra("searchItem", response.body().getData());
                                    startActivity(link);
                                } else {
                                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponseSearch> call, Throwable t) {
                        cancelProgressDialog();
                        Toast.makeText(getActivity(), "Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //Toast.makeText(getActivity(), "Please Enter Text", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "No internet connection.Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
