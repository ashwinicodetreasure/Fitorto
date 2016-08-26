package com.ct.fitorto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseFragment;
import com.ct.fitorto.custom.MaterialSearchView;
import com.ct.fitorto.model.Follower;
import com.ct.fitorto.model.Following;
import com.ct.fitorto.model.JsonResponseFriends;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ApplicationUtility;
import com.ct.fitorto.preferences.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendsFragment extends BaseFragment {

    public static final int ID = 3;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Following> followings = new ArrayList<>();
    private ArrayList<Follower> followers = new ArrayList<>();
    private PreferenceManager manager;
    private MaterialSearchView searchView;
    private Call<JsonResponseFriends> response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_friends, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
        getActivity().setTitle("");
        manager = new PreferenceManager(getActivity());
        iniDataset();
    }

    private void iniDataset() {
        if (ApplicationUtility.checkConnection(getActivity())) {
            showProgressDialog("Please wait...", false);
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            response = ApiClientMain.getApiClient().getFriendsList(userId);
            response.enqueue(new Callback<JsonResponseFriends>() {
                @Override
                public void onResponse(Call<JsonResponseFriends> call, Response<JsonResponseFriends> response) {
                    cancelProgressDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getFollowers().size() > 0) {
                                followers.addAll(response.body().getFollowers());
                            }
                            if (response.body().getFollowing().size() > 0) {
                                followings.addAll(response.body().getFollowing());

                            }
                            setupViewPager(viewPager);
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseFriends> call, Throwable t) {
                    cancelProgressDialog();
                    if(!call.isCanceled()){
                        Toast.makeText(getActivity(), "Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "No internet found.Check your connection or try again", Toast.LENGTH_SHORT).show();
        }

    }

    private void initLayout(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(FollowerFragment.getInstance(followers), "Followers");
        adapter.addFragment(FollowingFragment.getInstance(followings), "Following");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(response!=null){
            response.cancel();
        }
    }

}
