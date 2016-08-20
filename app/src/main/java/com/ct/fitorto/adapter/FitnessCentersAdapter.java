package com.ct.fitorto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ct.fitorto.R;
import com.ct.fitorto.model.Gym;
import com.ct.fitorto.recycler_holder.ProfileHolders;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class FitnessCentersAdapter extends RecyclerView.Adapter<ProfileHolders> {
    private List<Gym> profilelist;
    private Context context;

    public FitnessCentersAdapter(Context context, List<Gym> profilelist) {
        this.context = context;
        this.profilelist = profilelist;
    }

    @Override
    public ProfileHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fitness_centers, null);
        ProfileHolders rcv = new ProfileHolders(layoutView);
        return rcv;

    }

    @Override
    public void onBindViewHolder(ProfileHolders holder, int position) {
        Gym gym = profilelist.get(position);

        if (!TextUtils.isEmpty(gym.getLogo())) {
            Picasso.with(context)
                    .load(gym.getLogo())
                    .into(holder.gymimage);
        }else {
            Picasso.with(context)
                    .load(R.drawable.logo_placeholder)
                    .into(holder.gymimage);
        }
        //holder.gymimage.setImageResource(profilelist.get(position).getGym_photo());
        if (!TextUtils.isEmpty(gym.getGymName())) {
            holder.gymtitle.setText(gym.getGymName());
        }

        if (!TextUtils.isEmpty(gym.getCategory())) {
            holder.gymstatus.setText(gym.getArea());
        }
    }

    @Override
    public int getItemCount() {
        return this.profilelist.size();
    }
}
