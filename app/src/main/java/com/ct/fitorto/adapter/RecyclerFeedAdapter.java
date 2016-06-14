package com.ct.fitorto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.Feed;
import com.ct.fitorto.model.JsonResponselikeshare;
import com.ct.fitorto.network.ApiClientMain;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerFeedAdapter extends RecyclerView.Adapter<RecyclerFeedAdapter.ViewHolder> {
    private List<Feed> itemList;
    private Context context;

    public RecyclerFeedAdapter(Context context, List<Feed> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_frgamnet, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Feed feed = itemList.get(position);

        if (!TextUtils.isEmpty(feed.getUserProfilePic())) {
            Picasso.with(context)
                    .load(feed.getUserProfilePic())
                    .into(holder.image);
        }

        // holder.image.setImageResource(R.drawable.profil);
        holder.name.setText(feed.getUserName());
        holder.content.setText(feed.getFeed());
        //holder.time.setText(feed.getCreated());

        String dateStr = feed.getCreated();
        DateFormat writeFormat = new SimpleDateFormat("MMM,dd,yyyy hh:mm aaa");//( "EEE MMM-dd-yyyy hh:mm aaa");

        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {if(!TextUtils.isEmpty(dateStr))
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = "";
        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        System.out.println(formattedDate);

        holder.time.setText(formattedDate);

       /* Bitmap original = BitmapFactory.decodeFile(feed.getImageLink());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.JPEG, 100, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
*/

        if (!TextUtils.isEmpty(feed.getImageLink())) {
            holder.display.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(feed.getImageLink())
                    .into(holder.display);
        } else {
            holder.display.setVisibility(View.GONE);
        }

        holder.link.setText(feed.getUrl());
        holder.like.setText(feed.getLikes() + " like");
        holder.sharebtn.setText(feed.getShares() + " shares");
        // itemList.clear();

    }


    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image;
        public TextView name;
        public TextView content;
        public TextView time;
        public ImageView display;
        public TextView link;
        public TextView like;
        public ImageButton like_btn;
        public Button sharebtn;


        public ViewHolder(final View itemview) {
            super(itemview);

            image = (CircleImageView) itemview.findViewById(R.id.ivprofil);
            name = (TextView) itemview.findViewById(R.id.tv_title);
            content = (TextView) itemview.findViewById(R.id.tv_content);
            time = (TextView) itemview.findViewById(R.id.tvtime);
            display = (ImageView) itemview.findViewById(R.id.ivdis);
            link = (TextView) itemview.findViewById(R.id.tv_link);
            like = (TextView) itemview.findViewById(R.id.tv_like);
            like_btn = (ImageButton) itemview.findViewById(R.id.likebtn);
            sharebtn = (Button) itemview.findViewById(R.id.sharebtn);

            like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Call<JsonResponselikeshare> response = ApiClientMain.getApiClient().getResponselikeshare("1", "1", "1", "1","1");
                    response.enqueue(new Callback<JsonResponselikeshare>() {

                        @Override
                        public void onResponse(Call<JsonResponselikeshare> call, Response<JsonResponselikeshare> response) {
                            JsonResponselikeshare resp = response.body();

                            if(resp.equals("0"))
                            {
                               like_btn.setImageResource(R.drawable.hearts);
                                } else {
                                like_btn.setImageResource(R.drawable.rrheart);
                                //like.setText(itemview.feed.getLikes() + " like");
                            }

                        }

                        @Override
                        public void onFailure(Call<JsonResponselikeshare> call, Throwable t) {


                            Log.d("Error", t.getMessage());
                            //Toast.makeText(itemview.getContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                        }
                    });


                }
            });


        }


    }
}