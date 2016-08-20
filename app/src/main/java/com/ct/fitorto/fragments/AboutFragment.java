package com.ct.fitorto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.ProgressDetailActivity;
import com.ct.fitorto.adapter.ProgressAdapter;
import com.ct.fitorto.adapter.FitnessCentersAdapter;
import com.ct.fitorto.baseclass.BaseFragment;
import com.ct.fitorto.custom.DividerItemDecoration;
import com.ct.fitorto.custom.SpacesItemDecoration;
import com.ct.fitorto.model.Detail;
import com.ct.fitorto.model.Gym;
import com.ct.fitorto.model.ProgressDetail;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

/**
 * Created by Ashwini on 6/23/2016.
 */
public class AboutFragment extends BaseFragment implements ProgressAdapter.OnItemClickListener {

    private LinearLayoutManager llayout, llayout1;
    private ArrayList<Gym> data;
    private ArrayList<ProgressDetail> carddata = new ArrayList<>();

    private RecyclerView rview;
    private RecyclerView rcard;
    private PreferenceManager preferenceManager;
    private TextView tvEmpty;
    ArrayList<Gym> gymArrayList = new ArrayList<>();
    private ProgressAdapter adapter;

    public static AboutFragment getInstance(ArrayList<Gym> gymArrayList, ArrayList<ProgressDetail> progress) {
        AboutFragment fragment = new AboutFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ApplicationData.MY_FITNESS_CENTER_LIST, gymArrayList);
        bundle.putParcelableArrayList(ApplicationData.PROGRESS_LIST, progress);
        fragment.setArguments(bundle);
        return fragment;
    }

    public AboutFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pro_about_fragment, null);
        preferenceManager = new PreferenceManager(getActivity());
        // setHasOptionsMenu(true);
        initView(view);
        setFitnessCenterList();
        setProgressCardData();
        return view;
    }

    private void initView(View view) {
        llayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        llayout1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rview = (RecyclerView) view.findViewById(R.id.profile_recycler);
        rview.setHasFixedSize(true);
        rview.setNestedScrollingEnabled(false); //this code allow to user scroll on the tap of recyclerview
        rview.setLayoutManager(llayout);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);
        rcard = (RecyclerView) view.findViewById(R.id.card_recycler);
        rcard.setHasFixedSize(true);
        rcard.setNestedScrollingEnabled(false);
        rcard.setLayoutManager(llayout1);
    }

    public void setFitnessCenterList() {
        gymArrayList = getArguments().getParcelableArrayList(ApplicationData.MY_FITNESS_CENTER_LIST);
        if (gymArrayList != null && gymArrayList.size() > 0) {
            tvEmpty.setVisibility(View.GONE);
            rview.setVisibility(View.VISIBLE);
            FitnessCentersAdapter radapter = new FitnessCentersAdapter(getActivity(), gymArrayList);
            rview.setAdapter(radapter);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            rview.setVisibility(View.GONE);
        }
    }

    public void setProgressCardData() {
        ProgressDetail detail = new ProgressDetail();

        carddata = getArguments().getParcelableArrayList(ApplicationData.PROGRESS_LIST);
        if (carddata != null) {
            //tvEmpty.setVisibility(View.GONE);
            adapter = new ProgressAdapter(this, getActivity(), carddata);
            rcard.setAdapter(adapter);
            rcard.addItemDecoration(new SpacesItemDecoration(5));
            adapter.setOnItemClickListener(this);
        } else {
            //tvEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //setProgressCardData();
        if (requestCode == ApplicationData.REQUEST_CODE_PROGRESS) {
            int position = data.getIntExtra(ApplicationData.POSITION, 0);
            String value = data.getStringExtra(ApplicationData.PROGRESS_VALUE);
            String category = data.getStringExtra(ApplicationData.PROGRESS_CATEGORY);
            String unit = data.getStringExtra(ApplicationData.PROGRESS_UNIT);
            updateProgressList(position, value, category, unit);
        } else if (requestCode == ApplicationData.REQUEST_CODE_ADD_PROGRESS) {
            String value = data.getStringExtra(ApplicationData.PROGRESS_VALUE);
            String category = data.getStringExtra(ApplicationData.PROGRESS_CATEGORY);
            String unit = data.getStringExtra(ApplicationData.PROGRESS_UNIT);
            addProgressCategory(value, category, unit);
        }
    }

    private void addProgressCategory(String value, String category, String unit) {
        ProgressDetail progressDetail = new ProgressDetail();
        progressDetail.setCategory(category);
        ArrayList<Detail> details = new ArrayList<>();
        Detail detail = new Detail();
        detail.setValue(value);
        detail.setCategory(category);
        detail.setUnit(unit);
        details.add(detail);
        progressDetail.setDetails(details);
        carddata.add(progressDetail);
        adapter.notifyDataSetChanged();
    }

    private void updateProgressList(int position, String value, String category, String unit) {
        ProgressDetail progressDetail = carddata.get(position);
        Detail detail = progressDetail.getDetails().get(0);
        //Detail detail=new Detail();
        detail.setValue(value);
        detail.setCategory(category);
        detail.setUnit(unit);
        // progressDetail.setDetails(detail);
        //carddata.add(0,progressDetail);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, ProgressDetail category) {
        Intent intent = new Intent(getActivity(), ProgressDetailActivity.class);
        intent.putExtra(ApplicationData.PROGRESS_LIST, category);
        startActivity(intent);
    }
}
