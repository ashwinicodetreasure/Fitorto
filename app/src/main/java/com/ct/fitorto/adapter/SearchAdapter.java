package com.ct.fitorto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.flowlayout.FlowLayout;
import com.ct.fitorto.model.GynImages;
import com.ct.fitorto.model.Package;
import com.ct.fitorto.model.Search;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Search item);
    }

    private ArrayList<Search> items;
    private OnItemClickListener listener;

    public SearchAdapter(OnItemClickListener listener, ArrayList<Search> items) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gymaname;
        private TextView gymloaction;
        private TextView gymrating;
        private ImageView imageButton;
        private TextView pack;
        private FlowLayout mFlowLayout;
        private LinearLayout imgContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            gymaname = (TextView) itemView.findViewById(R.id.title);
            gymloaction = (TextView) itemView.findViewById(R.id.location);
            gymrating = (TextView) itemView.findViewById(R.id.textView10);
            imageButton = (ImageView) itemView.findViewById(R.id.imageButton);
            pack = (TextView) itemView.findViewById(R.id.textView11);
            mFlowLayout = (FlowLayout) itemView.findViewById(R.id.flow);
            imgContainer = (LinearLayout) itemView.findViewById(R.id.imgContainer);
        }

        private void setcategory(List<String> sizeArrayList) {
            mFlowLayout.removeAllViews();
            if (sizeArrayList != null && sizeArrayList.size() > 0) {
                int limit = 0;
                for (String size : sizeArrayList) {
                    if (limit < 3) {
                        LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final TextView equipment1 = (TextView) inflater.inflate(R.layout.search_layout_button, mFlowLayout, false);
                        equipment1.setText(size);
                        mFlowLayout.addView(equipment1);
                    }
                    limit++;
                }
            }
        }


        private void setGymImageList(List<GynImages> sizeArrayList) {
            imgContainer.removeAllViews();
            if (sizeArrayList != null && sizeArrayList.size() > 0) {
                int limit = 0;
                for (GynImages images : sizeArrayList) {
                    if (limit < 3) {
                        LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final ImageView imageView = (ImageView) inflater.inflate(R.layout.list_item_fitness_center_image, imgContainer, false);
                        if (!TextUtils.isEmpty(images.getImageLink())) {
                            Picasso.with(itemView.getContext())
                                    .load(images.getImageLink())
                                    .placeholder(R.drawable.ic_gymnasium)
                                    .into(imageView);
                        } else {
                            Picasso.with(itemView.getContext())
                                    .load(R.drawable.ic_gymnasium)
                                    .placeholder(R.drawable.ic_gymnasium)
                                    .into(imageView);
                        }
                        imgContainer.addView(imageView);
                    }
                    limit++;
                }
            }
        }

        public void bind(final Search item, final OnItemClickListener listener) {

            List<Package> fu = new ArrayList<>();
            fu.addAll(item.getPackages());
            List<GynImages> gi = new ArrayList<>();
            gi.addAll(item.getImages());
            ArrayList aList = new ArrayList(Arrays.asList(item.getCategory().split(",")));
            if (aList.size() > 0) {
                mFlowLayout.setVisibility(View.VISIBLE);
                setcategory(aList);
            } else {
                mFlowLayout.setVisibility(View.GONE);
            }


            if (fu.size() > 0) {
                pack.setText("Rs." + fu.get(0).getOneMonth() + "/-");
            }


            if (TextUtils.isEmpty(item.getGymName())) {
            } else {
                gymaname.setText(item.getGymName());
            }


            if (TextUtils.isEmpty(item.getArea())) {
            } else {
                gymloaction.setText(item.getArea());
            }

            if (item.getRating().equals("0")) {
                imageButton.setImageResource(R.drawable.blank);
            } else {
                imageButton.setImageResource(R.drawable.star);

            }
            if (TextUtils.isEmpty(item.getRating())) {
            } else {
                gymrating.setText(item.getRating());
            }

            if (gi.size() > 0) {
                imgContainer.setVisibility(View.VISIBLE);
                setGymImageList(gi);
            } else {
                imgContainer.setVisibility(View.GONE);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}