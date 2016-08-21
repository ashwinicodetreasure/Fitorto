package com.ct.fitorto.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ct.fitorto.R;
import com.ct.fitorto.model.Coupon;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.network.ApiClientMain;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 8/20/16.
 */
public class RefernceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Coupon> cardlist;
    private Context context;

    public RefernceAdapter(Context context, List<Coupon> cardlist) {
        this.context = context;
        this.cardlist = cardlist;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reference, null);
        CardHolder rcv = new CardHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CardHolder mHolder = (CardHolder) holder;
        final Coupon coupon = cardlist.get(position);
        if (coupon != null) {
            if (!TextUtils.isEmpty(coupon.getCouponType())) {
                mHolder.tvValue.setText(coupon.getCouponType() + " " + coupon.getBenifit());
                if (coupon.getCouponType().contains("Days")) {
                    if(coupon.getIsUsed().equals("1")){
                        mHolder.btnRedeem.setVisibility(View.INVISIBLE);
                    }else {
                        mHolder.btnRedeem.setVisibility(View.VISIBLE);
                    }
                } else {
                    mHolder.btnRedeem.setVisibility(View.INVISIBLE);
                }
            } else {
                mHolder.tvValue.setText("");
            }
            mHolder.btnRedeem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reedeemUserPoint(coupon.getUserCouponID(), "1", mHolder.btnRedeem);
                }
            });
           /* if (!TextUtils.isEmpty(coupon.getBenifit())) {
                mHolder.tvDate.setText(coupon.getBenifit());
            } else {
                mHolder.tvDate.setText("");
            }*/
            mHolder.itemView.setTag(coupon);
        }
    }

    private void reedeemUserPoint(String couponID, String flag, final Button button) {
        Call<JsonResponseFollow> call = ApiClientMain.getApiClient().useUserCoupons(couponID, flag);
        call.enqueue(new Callback<JsonResponseFollow>() {
            @Override
            public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equals("1")) {
                        button.setVisibility(View.INVISIBLE);
                        showDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseFollow> call, Throwable t) {
                Toast.makeText(context, "Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        new AlertDialog.Builder(context)
                .setTitle("Information")
                .setMessage(context.getString(R.string.txt_redeem_msg))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    @Override
    public int getItemCount() {
        return this.cardlist.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        public TextView tvValue;
        // public TextView tvDate;
        public Button btnRedeem;


        public CardHolder(View itemView) {
            super(itemView);
            tvValue = (TextView) itemView.findViewById((R.id.tvValue));
            btnRedeem = (Button) itemView.findViewById(R.id.btnRedeem);
            // tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }

    }


}
