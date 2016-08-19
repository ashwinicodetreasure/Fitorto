package com.ct.fitorto.adapter;

import android.content.Intent;
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
import com.ct.fitorto.activity.AddProgressCategory;
import com.ct.fitorto.fragments.AddProgressDialogFragment;
import com.ct.fitorto.model.Detail;
import com.ct.fitorto.model.ProgressDetail;
import com.ct.fitorto.utils.ApplicationData;
import com.ct.fitorto.utils.DateTimeUtils;

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
    public OnItemClickListener onItemClickListener;

    public ProgressAdapter(Fragment fragment, FragmentActivity context, List<ProgressDetail> cardlist) {
        this.fragment = fragment;
        this.context = context;
        this.cardlist = cardlist;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public interface OnItemClickListener {
        void onItemClick(View view, ProgressDetail category);
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
                    Intent intent = new Intent(context, AddProgressCategory.class);
                    fragment.startActivityForResult(intent, ApplicationData.REQUEST_CODE_ADD_PROGRESS);
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
                    } else {
                        mHolder.currentw.setText("");
                    }


                    if (!TextUtils.isEmpty(detail.getDateTime())) {
                        mHolder.currentd.setText(DateTimeUtils.formateDate(detail.getDateTime()));
                    } else {
                        mHolder.currentd.setText("");
                    }

                    if (previousDetail != null) {
                        if (!TextUtils.isEmpty(detail.getDateTime())) {
                            mHolder.oldd.setText(DateTimeUtils.formateDate(previousDetail.getDateTime()));
                        } else {
                            mHolder.oldd.setText("");
                        }

                        if (!TextUtils.isEmpty(detail.getValue())) {
                            mHolder.oldw.setText(previousDetail.getValue() + " " + previousDetail.getUnit());
                        } else {
                            mHolder.oldw.setText("");
                        }
                    } else {
                        mHolder.oldd.setText("");
                        mHolder.oldw.setText("");
                    }


                    if (!TextUtils.isEmpty(progressDetail.getCategory())) {
                        mHolder.tvwh.setText(progressDetail.getCategory());
                    } else {
                        mHolder.tvwh.setText("");
                    }

                    mHolder.add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AddProgressDialogFragment.newInstance(context, fragment, progressDetail.getCategory(), detail.getUnit(), position);
                        }
                    });
                    mHolder.itemView.setTag(progressDetail);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int size = cardlist.size();
        return (position == size) ? Footer : Normal;
    }


    @Override
    public int getItemCount() {
        return this.cardlist.size() + 1;
    }

    public class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView currentw;
        public TextView currentd;
        public TextView oldd;
        public TextView oldw;
        public TextView tvwh;
        public TextView add;


        public CardHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            currentw = (TextView) itemView.findViewById((R.id.current_w));
            currentd = (TextView) itemView.findViewById(R.id.card_date);
            oldd = (TextView) itemView.findViewById((R.id.pre_date));
            oldw = (TextView) itemView.findViewById((R.id.old_w));
            tvwh = (TextView) itemView.findViewById((R.id.tv_wh));
            add = (TextView) itemView.findViewById(R.id.add);

        }


        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, (ProgressDetail) view.getTag());
            }
        }
    }


}
