package com.ct.fitorto.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.FriendsProfileActivity;
import com.ct.fitorto.activity.SearchFriendActivity;
import com.ct.fitorto.activity.ShareActivity;
import com.ct.fitorto.adapter.FollowingAdapter;
import com.ct.fitorto.custom.MaterialSearchView;
import com.ct.fitorto.model.Following;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

/**
 * Created by Ashwini on 3/28/2016.
 */

public class FollowingFragment extends Fragment implements FollowingAdapter.OnItemClickListener {

    private RecyclerView recycler_view;
    private ArrayList<Following> followings = new ArrayList<>();
    private EditText searchViewLayout;
    private TextView tvEmpty;
    private LinearLayout linearLayout;

    public static FollowingFragment getInstance(ArrayList<Following> followers) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ApplicationData.FOLLOWING_LIST, followers);
        fragment.setArguments(bundle);
        return fragment;
    }

    public FollowingFragment() {

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
    }

    private void initLayout(View view) {
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        searchViewLayout = (EditText) view.findViewById(R.id.search);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmpty);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(manager);
        followings = getArguments().getParcelableArrayList(ApplicationData.FOLLOWING_LIST);
        linearLayout= (LinearLayout) view.findViewById(R.id.linearLayout);
        if (followings != null) {
            if (followings.size() > 0) {
                tvEmpty.setVisibility(View.GONE);
                FollowingAdapter followerAdapter = new FollowingAdapter(getActivity(), followings);
                recycler_view.setAdapter(followerAdapter);
                followerAdapter.setOnItemClickListener(this);
            } else {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }

        searchViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent i = new Intent(getActivity(), SearchFriendActivity.class);
                startActivity(i);*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(getActivity(), linearLayout, linearLayout.getTransitionName());
                    startActivity(new Intent(getActivity(), SearchFriendActivity.class),/* ApplicationData.FEED_REQUEST_CODE,*/ options.toBundle());
                }else {
                    Intent i = new Intent(getActivity(), SearchFriendActivity.class);
                    startActivity(i);
                }
                // startActivityForResult(i,ApplicationData.SEARCH_FRIEND_CODE);
            }
        });
    }

    @Override
    public void onItemClick(View view, Following position) {
        Intent i = new Intent(getActivity(), FriendsProfileActivity.class);
        i.putExtra(ApplicationData.FRIEND_ID, position.getUserID());
        startActivityForResult(i, ApplicationData.RESULT_CODE_FRIEND);
    }

}
