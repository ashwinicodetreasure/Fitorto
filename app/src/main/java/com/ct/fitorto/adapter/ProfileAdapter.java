package com.ct.fitorto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ct.fitorto.R;
import com.ct.fitorto.object.ProfileObject;
import com.ct.fitorto.recycler_holder.ProfileHolders;

import java.util.List;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileHolders> {
    private List<ProfileObject>profilelist;
    private Context context;

    public ProfileAdapter(Context context, List<ProfileObject> profilelist) {
        this.context = context;
        this.profilelist = profilelist;
    }

    @Override
    public ProfileHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_fragment, null);
        ProfileHolders rcv = new ProfileHolders(layoutView);
        return rcv;

    }

    @Override
    public void onBindViewHolder(ProfileHolders holder, int position) {
        holder.gymimage.setImageResource(profilelist.get(position).getGym_photo());
        holder.gymtitle.setText(profilelist.get(position).getGym_title());


    }

    @Override
    public int getItemCount() {
        return this.profilelist.size();    }
}
