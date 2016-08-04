package com.ct.fitorto.adapter;

/**
 * Created by Ashwini on 5/17/2016.
 */

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ct.fitorto.R;
import com.ct.fitorto.model.GynImages;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Membership_Slider_Adapter extends PagerAdapter {


    private List<GynImages> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public Membership_Slider_Adapter(Context context, List<GynImages> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.membership_slider, view, false);
        GynImages image = IMAGES.get(position);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.mem_image);


        //imageView.setImageResource(IMAGES.get(position));
        Picasso.with(context)
                .load(image.getImageLink())
                .into(imageView);

        view.addView(imageLayout, 0);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
