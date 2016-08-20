package com.ct.fitorto.adapter;

import android.content.Context;
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
 * Created by codetreasure on 8/20/16.
 */
public class ProgressDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Detail> cardlist;
    private Context context;

    public ProgressDetailAdapter(Context context, List<Detail> cardlist) {
        this.context = context;
        this.cardlist = cardlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_progress_detail, null);
        CardHolder rcv = new CardHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CardHolder mHolder = (CardHolder) holder;
        final Detail detail = cardlist.get(position);
        if (detail != null) {
            if (!TextUtils.isEmpty(detail.getValue())) {
                mHolder.tvValue.setText(detail.getValue());
            } else {
                mHolder.tvValue.setText("");
            }

            if (!TextUtils.isEmpty(detail.getDateTime())) {
                mHolder.tvDate.setText(DateTimeUtils.formateDate(detail.getDateTime()));
            } else {
                mHolder.tvDate.setText("");
            }
            mHolder.itemView.setTag(detail);
        }
    }


    @Override
    public int getItemCount() {
        return this.cardlist.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        public TextView tvValue;
        public TextView tvDate;


        public CardHolder(View itemView) {
            super(itemView);
            tvValue = (TextView) itemView.findViewById((R.id.tvValue));
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }

    }


}
