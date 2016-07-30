package com.ct.fitorto.baseclass;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by admin on 11/20/2015.
 */
public class BaseFragment extends Fragment {

    protected Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void showProgressDialog(String message, Boolean cancelable) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(String.valueOf(message));
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.show();
    }

    protected void cancelProgressDialog() {
        try {
            if ((this.mProgressDialog != null) && this.mProgressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            this.mProgressDialog = null;
        }
    }

}

