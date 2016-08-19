package com.ct.fitorto.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.fragments.AddProgressDialogFragment;
import com.ct.fitorto.model.Detail;
import com.ct.fitorto.model.ProgressDetail;
import com.ct.fitorto.recycler_holder.CardHolder;
import com.ct.fitorto.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by Ashwini on 5/21/2016.
 */
public class ProgressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProgressDetail> cardlist;
    private FragmentActivity context;
    public static final int Normal = 1;
    public static final int Footer = 2;
    public static final int Header = 3;
    public Fragment fragment;

    public ProgressAdapter(Fragment fragment, FragmentActivity context, List<ProgressDetail> cardlist) {
        this.fragment = fragment;
        this.context = context;
        this.cardlist = cardlist;
    }

    public static class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Button btnAddProgress;

        public FooterHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            btnAddProgress = (Button) itemView.findViewById((R.id.btnAddProgress));
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(v.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Normal) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_card, null);
            CardHolder rcv = new CardHolder(layoutView);
            return rcv;
        } else {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_progress_footer, null);
            FooterHolder footerHolder = new FooterHolder(layoutView);
            return footerHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == Footer) {
            FooterHolder mHolder = (FooterHolder) holder;
            mHolder.btnAddProgress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            CardHolder mHolder = (CardHolder) holder;
            final ProgressDetail progressDetail = cardlist.get(position);
            Detail previousDetail = null;
            if (progressDetail != null) {
                final Detail detail = progressDetail.getDetails().get(0);
                if (progressDetail.getDetails().size() > 1) {
                    previousDetail = progressDetail.getDetails().get(1);
                }

                if (detail != null) {
                    if (!TextUtils.isEmpty(detail.getValue())) {
                        mHolder.currentw.setText(detail.getValue() + " " + detail.getUnit());
                    }


                    if (!TextUtils.isEmpty(detail.getDateTime())) {
                        mHolder.currentd.setText(DateTimeUtils.formateDate(detail.getDateTime()));
                    }

                    if (previousDetail != null) {
                        if (!TextUtils.isEmpty(detail.getDateTime())) {
                            mHolder.oldd.setText(DateTimeUtils.formateDate(previousDetail.getDateTime()));
                        }

                        if (!TextUtils.isEmpty(detail.getValue())) {
                            mHolder.oldw.setText(previousDetail.getValue() + " " + previousDetail.getUnit());
                        }
                    }


                    if (!TextUtils.isEmpty(progressDetail.getCategory())) {
                        mHolder.tvwh.setText(progressDetail.getCategory());
                    }

                    final Detail finalPreviousDetail = previousDetail;
                    mHolder.add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AddProgressDialogFragment.newInstance(context,fragment, progressDetail.getCategory(), detail.getUnit(),position);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == cardlist.size()) ? Footer : Normal;
    }


    @Override
    public int getItemCount() {
        return this.cardlist.size();
    }


}
