package com.ct.fitorto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.FriendsProfileActivity;
import com.ct.fitorto.activity.SearchFriendActivity;
import com.ct.fitorto.adapter.FollowerAdapter;
import com.ct.fitorto.custom.MaterialSearchView;
import com.ct.fitorto.model.Follower;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

/**
 * Created by Ashwini on 3/28/2016.
 */
public class FollowerFragment extends Fragment implements FollowerAdapter.OnItemClickListener {

    private RecyclerView recycler_view;
    private ArrayList<Follower> followings = new ArrayList<>();
    private MaterialSearchView searchView;
    private TextView searchViewLayout;
    private TextView tvEmpty;

    public static FollowerFragment getInstance(ArrayList<Follower> followers) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ApplicationData.FOLLOWERS_LIST, followers);
        fragment.setArguments(bundle);
        return fragment;
    }

    public FollowerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLayout(view);
        // initSearchBar();
    }

    private void initLayout(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        searchViewLayout = (TextView) view.findViewById(R.id.search);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(manager);
        followings = getArguments().getParcelableArrayList(ApplicationData.FOLLOWERS_LIST);
        if (followings != null) {
            if (followings.size() > 0) {
                tvEmpty.setVisibility(View.GONE);
                FollowerAdapter followerAdapter = new FollowerAdapter(getActivity(), followings);
                recycler_view.setAdapter(followerAdapter);
                followerAdapter.setOnItemClickListener(this);
            } else {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }

        searchViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchFriendActivity.class);
                startActivity(i);
                // startActivityForResult(i,ApplicationData.SEARCH_FRIEND_CODE);
            }
        });
    }

    @Override
    public void onItemClick(View view, Follower position) {
        Intent i=new Intent(getActivity(),FriendsProfileActivity.class);
        i.putExtra(ApplicationData.FRIEND_ID,position.getUserID());
        startActivityForResult(i,ApplicationData.RESULT_CODE_FRIEND);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.friends_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            //Do whatever you want to do
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void initSearchBar() {
        searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                   *//* Intent i = new Intent(MainActivity.this, DiscoverActivity.class);
                    i.putExtra(ApplicationData.KEYWORD, query);
                    startActivity(i);*//*
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // String q=newText;
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }*/
}
