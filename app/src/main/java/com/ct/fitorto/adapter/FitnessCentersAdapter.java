package com.ct.fitorto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.MyGymActivity;
import com.ct.fitorto.model.Gym;
import com.ct.fitorto.model.JsonAttendance;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.model.ProgressDetail;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.recycler_holder.ProfileHolders;
import com.ct.fitorto.utils.ApplicationData;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class FitnessCentersAdapter extends RecyclerView.Adapter<FitnessCentersAdapter.ProfileHolders> {

    private List<Gym> profilelist;
    private Context context;
    public OnItemClickListener onItemClickListener;


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
    public void onBindViewHolder(final ProfileHolders holder, int position) {
        final Gym gym = profilelist.get(position);

        if (!TextUtils.isEmpty(gym.getLogo())) {
            Picasso.with(context)
                    .load(gym.getLogo())
                    .into(holder.gymimage);
        } else {
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

        if (gym.getMyPackages().size() > 0) {
            if (gym.getIsCheckOut() == 1) {
                holder.checkedin.setText("CHECK OUT");
            } else {
                holder.checkedin.setText("CHECK IN");
            }
            holder.checkedin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gym.getIsCheckOut() == 1) {
                        checkInMember(gym.getGymID(), "0", holder.checkedin, gym);
                    } else {
                        checkInMember(gym.getGymID(), "1", holder.checkedin, gym);
                    }
                }
            });
        } else {
            holder.checkedin.setText("VIEW");
            holder.checkedin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MyGymActivity.class);
                    intent.putExtra(ApplicationData.FITNESS_CENTER_DETAILS, gym);
                    context.startActivity(intent);
                }
            });
        }


        holder.itemView.setTag(gym);
    }

    private void checkInMember(String gymID, String flag, final TextView checkedin, final Gym gym) {
        PreferenceManager manager = new PreferenceManager(context);
        String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
        Call<JsonAttendance> call = ApiClientMain.getApiClient().checkIn(userId, gymID, flag);
        call.enqueue(new Callback<JsonAttendance>() {
            @Override
            public void onResponse(Call<JsonAttendance> call, Response<JsonAttendance> response) {
                if (response.body() != null) {
                    if (response.body().getData().size() > 0) {
                        if (response.body().getData().get(0).getIsCheckOut().equals("1")) {
                            checkedin.setText("CHECK OUT");
                            gym.setIsCheckOut(1);
                        } else {
                            checkedin.setText("CHECK IN");
                            gym.setIsCheckOut(0);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonAttendance> call, Throwable t) {

            }
        });
    }

    public class ProfileHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView gymimage;
        public TextView gymtitle;
        public TextView gymstatus;
        public TextView checkedin;

        public ProfileHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            gymimage = (CircleImageView) itemView.findViewById(R.id.gym_image);
            gymtitle = (TextView) itemView.findViewById((R.id.gym_name));
            gymstatus = (TextView) itemView.findViewById(R.id.gym_status);
            checkedin = (TextView) itemView.findViewById(R.id.checkedin);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, (Gym) view.getTag());
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.profilelist.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Gym category);
    }
}
