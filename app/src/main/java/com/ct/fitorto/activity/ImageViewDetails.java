package com.ct.fitorto.activity;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.FeedAdapter;
import com.ct.fitorto.custom.CustomTextView;
import com.ct.fitorto.model.Feed;
import com.ct.fitorto.network.ShareImageAsynctask;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;


/**
 * Created by arvindshukla on 03/08/16.
 */
public class ImageViewDetails extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private LikeButton likebtn;
    private TextSwitcher tv_like;
    private LinearLayout llShare;
    private Feed feed;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_imageview);
        initView();
        feed = getIntent().getParcelableExtra(ApplicationData.FEED);
        initDataset(feed);
    }

    private void initDataset(final Feed feed) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setTransitionName(getString(R.string.activity_image_trans));
        }
        if (!TextUtils.isEmpty(feed.getImageLink())) {
            Picasso.with(this)
                    .load(feed.getImageLink())
                    .fit()
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // TODO Auto-generated method stub

                        }
                    });
            //Picasso.with(context).load(feed.getImageLink()).into(holder.display);
        } else {

        }

        if (feed.getIsLike().equals("1")) {
            likebtn.setLiked(true);
            // holder.like_btn.setImageResource(R.drawable.ic_like);
        } else {
            likebtn.setLiked(false);
            // holder.like_btn.setImageResource(R.drawable.ic_dislike);
        }

        tv_like.setText(feed.getLikes());
        likebtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                FeedAdapter.likeFeed(preferenceManager, feed.getFeedID(), "1", "1", "1", likebtn, feed, tv_like);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                FeedAdapter.likeFeed(preferenceManager, feed.getFeedID(), "0", "1", "1", likebtn, feed, tv_like);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onBackPressed();
                        break;
                    case MotionEvent.ACTION_UP:
                        onBackPressed();
                        break;
                }
                return true;

            }
        });
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        likebtn = (LikeButton) findViewById(R.id.likebtn);
        tv_like = (TextSwitcher) findViewById(R.id.tv_like);
        llShare = (LinearLayout) findViewById(R.id.llShare);
        preferenceManager = new PreferenceManager(this);
        llShare.setOnClickListener(this);
        tv_like.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                CustomTextView textView = new CustomTextView(ImageViewDetails.this);
                textView.setTextColor(getResources().getColor(android.R.color.white));
                return textView;
            }
        });
        findViewById(R.id.btnShare).getBackground().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llShare:
                new ShareImageAsynctask(this, feed).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
