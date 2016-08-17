package com.ct.fitorto.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ct.fitorto.R;

/**
 * Created by codetreasure on 8/17/16.
 */
public class AddProgressDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView tvCategory;
    private EditText edtValue;
    private TextView tvUnit;
    private Button btnSave;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static AddProgressDialogFragment newInstance() {
        AddProgressDialogFragment f = new AddProgressDialogFragment();
        Bundle bundle = new Bundle();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.activity_add_progress, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        tvCategory= (TextView) rootView.findViewById(R.id.tvCategory);
        edtValue= (EditText) rootView.findViewById(R.id.edtValue);
        tvUnit= (TextView) rootView.findViewById(R.id.tvUnit);
        btnSave= (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btnSave){

        }
    }
}
