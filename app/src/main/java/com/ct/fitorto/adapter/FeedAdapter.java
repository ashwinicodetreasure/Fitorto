package com.ct.fitorto.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ct.fitorto.R;
import com.ct.fitorto.activity.FriendsProfileActivity;
import com.ct.fitorto.activity.ImageViewDetails;
import com.ct.fitorto.custom.CustomTextView;
import com.ct.fitorto.model.Feed;
import com.ct.fitorto.model.JsonResponselikeshare;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.network.ShareImageAsynctask;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.ct.fitorto.utils.DateTimeUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Feed> itemList;
    private Activity context;
    private PreferenceManager preferenceManager;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public FeedAdapter(Activity context, List<Feed> itemList) {
        this.context = context;
        this.itemList = itemList;
        preferenceManager = new PreferenceManager(context);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Feed feed = itemList.get(position);

        if (!TextUtils.isEmpty(feed.getUserProfilePic())) {
            Picasso.with(context).load(feed.getUserProfilePic()).rotate(-90).into(holder.image);
        } else {
            Picasso.with(context).load(R.drawable.ic_profile).rotate(-90).into(holder.image);
        }
        holder.name.setText(feed.getUserName());
        if (!TextUtils.isEmpty(feed.getFeed())) {
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(feed.getFeed());
        } else {
            holder.content.setVisibility(View.GONE);
        }

        try {
            if (!TextUtils.isEmpty(feed.getCreated())) {
                holder.time.setText(DateTimeUtils.formatToYesterdayOrToday(context, feed.getCreated()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


     /*   if (!TextUtils.isEmpty(feed.getImageLink())) {
            holder.display.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(feed.getImageLink())
                    .into(holder.display, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            holder.display.setLayoutParams(layoutParams);
                        }

                        @Override
                        public void onError() {
                            // TODO Auto-generated method stub

                        }
                    });
            //Picasso.with(context).load(feed.getImageLink()).into(holder.display);
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.display.setVisibility(View.GONE);
        }*/
        if (!TextUtils.isEmpty(feed.getImageLink())) {
            holder.display.setVisibility(View.VISIBLE);
            holder.display.setImageURI(feed.getImageLink());
        } else {
            holder.display.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(feed.getUrl())) {
            holder.link.setVisibility(View.VISIBLE);
            holder.link.setText(feed.getUrl());
        } else {
            holder.link.setVisibility(View.GONE);
        }
        holder.like.setText(feed.getLikes());
        //holder.share.setText(feed.getShares() + " shares");

        if (feed.getIsLike().equals("1")) {
            holder.like_btn.setLiked(true);
            // holder.like_btn.setImageResource(R.drawable.ic_like);
        } else {
            holder.like_btn.setLiked(false);
            // holder.like_btn.setImageResource(R.drawable.ic_dislike);
        }

        holder.like_btn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeFeed(preferenceManager, feed.getFeedID(), "1", "1", "1", holder.like_btn, feed, holder.like);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeFeed(preferenceManager, feed.getFeedID(), "0", "1", "1", holder.like_btn, feed, holder.like);

            }
        });

        holder.rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FriendsProfileActivity.class);
                i.putExtra(ApplicationData.FRIEND_ID, feed.getUserID());
                context.startActivityForResult(i, ApplicationData.RESULT_CODE_FRIEND);
            }
        });

       /* holder.llLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // view.startAnimation(holder.animScale);
                if (feed.getIsLike().equals("1")) {
                    likeFeed(feed.getFeedID(), "0", "1", "1", holder.like_btn, feed, holder.like);
                } else {
                    likeFeed(feed.getFeedID(), "1", "1", "1", holder.like_btn, feed, holder.like);
                }
            }
        });*/

        holder.llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showShareOption(position);
                new ShareImageAsynctask(context, feed).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
            }
        });

        holder.display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageViewDetails.class);
                intent.putExtra(ApplicationData.FEED, feed);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(context, holder.display, context.getResources().getString(R.string.activity_image_trans));
                    context.startActivity(intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
    }


    public void showShareOption(int position) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.select_dialog_item);
        arrayAdapter.add("Share On Fitorto");
        arrayAdapter.add("Share On Others");
        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        switch (which) {
                            case 0:

                                break;
                            case 1:
                                break;
                        }
                        /*AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                context);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();*/
                    }
                });
        builderSingle.show();
    }


    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public TextView content;
        public TextView time;
        public SimpleDraweeView display;
        public TextView link;
        public TextSwitcher like;
        public LikeButton like_btn;
        public Button sharebtn;
        public ProgressBar progressBar;
        public LinearLayout llLikes;
        public LinearLayout llShare;
        public TextView share;
        public Animation animScale;
        private View viewBlackTint;
        private RelativeLayout rlUser;
        private ImageButton btnShare;

        public ViewHolder(final View itemview) {
            super(itemview);

            image = (ImageView) itemview.findViewById(R.id.ivprofil);
            name = (TextView) itemview.findViewById(R.id.tv_title);
            content = (TextView) itemview.findViewById(R.id.tv_content);
            time = (TextView) itemview.findViewById(R.id.tvtime);
            display = (SimpleDraweeView) itemview.findViewById(R.id.ivdis);
            link = (TextView) itemview.findViewById(R.id.tv_link);
            like = (TextSwitcher) itemview.findViewById(R.id.tv_like);
            like_btn = (LikeButton) itemview.findViewById(R.id.likebtn);
            sharebtn = (Button) itemview.findViewById(R.id.sharebtn);
            progressBar = (ProgressBar) itemview.findViewById(R.id.progressBar);
            share = (TextView) itemview.findViewById(R.id.tvShare);
            llLikes = (LinearLayout) itemview.findViewById(R.id.llLikes);
            llShare = (LinearLayout) itemview.findViewById(R.id.llShare);
            rlUser = (RelativeLayout) itemview.findViewById(R.id.rlUser);
            btnShare = (ImageButton) itemview.findViewById(R.id.btnShare);
            btnShare.setVisibility(View.VISIBLE);
            animScale = AnimationUtils.loadAnimation(itemview.getContext(), R.anim.anim_scale);


            like.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    CustomTextView textView = new CustomTextView(itemview.getContext());
                    return textView;
                }
            });

            like.setInAnimation(itemview.getContext(), R.anim.anim_up);
            like.setOutAnimation(itemview.getContext(), R.anim.anim_down);

            display.getHierarchy().setProgressBarImage(ContextCompat.getDrawable(itemview.getContext(), R.drawable.circular_progress_bar));
        }


    }


    public static void likeFeed(PreferenceManager preferenceManager, final String feedId, String flag, String isLike, String isUser, final LikeButton button, final Feed feed, final TextSwitcher tvLike) {
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        Call<JsonResponselikeshare> response = ApiClientMain.getApiClient().getResponselikeshare(userId, feedId, flag, isLike, isUser);
        response.enqueue(new Callback<JsonResponselikeshare>() {

            @Override
            public void onResponse(Call<JsonResponselikeshare> call, Response<JsonResponselikeshare> response) {
                JsonResponselikeshare resp = response.body();
                if (resp != null) {
                    if (resp.getMsg().equals("Like done successfully!")) {
                        button.setLiked(true);
                        feed.setIsLike("1");
                        tvLike.setText(" " + resp.getCount());
                        feed.setLikes(String.valueOf(resp.getCount()));
                    } else {
                        button.setLiked(false);
                        feed.setIsLike("0");
                        tvLike.setText(" " + resp.getCount());
                        feed.setLikes(String.valueOf(resp.getCount()));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponselikeshare> call, Throwable t) {
                //Log.d("Error", t.getMessage());
                //Toast.makeText(itemview.getContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}