package com.ct.fitorto.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.ProfileAdapter;
import com.ct.fitorto.object.ProfileObject;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ashwini on 5/21/2016.
 */
public class Profile_Fragment extends Fragment {
    private LinearLayoutManager llayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_recycler, container, false);
        getActivity().setTitle("Profile");


        setHasOptionsMenu(true);
        List<ProfileObject> listItem = getAllItemList();
        llayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView rview = (RecyclerView) view.findViewById(R.id.profile_recycler);
        rview.setHasFixedSize(true);
        rview.setLayoutManager(llayout);
        ProfileAdapter radapter = new ProfileAdapter(getActivity(), listItem);
        rview.setAdapter(radapter);
        return view;
    }

    private List<ProfileObject> getAllItemList() {
        List<ProfileObject>allitem=new ArrayList<ProfileObject>();
        allitem.add(new ProfileObject(R.drawable.gym,"Roxy"));
        allitem.add(new ProfileObject(R.drawable.gym,"Roxy"));

        allitem.add(new ProfileObject(R.drawable.gym,"Roxy"));

        allitem.add(new ProfileObject(R.drawable.gym,"Roxy"));

        return allitem;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        menu.clear();
        inflater.inflate(R.menu.profie_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wallet:
                // Not implemented here
                return false;
            case R.id.setting:
                // Do Fragment menu item stuff here
                return true;
            default:
                break;
        }

        return false;
    }
}
