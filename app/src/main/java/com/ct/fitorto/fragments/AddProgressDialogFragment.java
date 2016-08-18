package com.ct.fitorto.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 8/17/16.
 */
public class AddProgressDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView tvCategory;
    private EditText edtValue;
    private TextView tvUnit;
    private Button btnSave;
    private PreferenceManager manager;

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

    public static AddProgressDialogFragment newInstance(Fragment fragment, String category, String unit) {
        AddProgressDialogFragment f = new AddProgressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ApplicationData.PROGRESS_CATEGORY, category);
        bundle.putString(ApplicationData.PROGRESS_UNIT, unit);
        f.setTargetFragment(fragment, ApplicationData.REQUEST_CODE_PROGRESS);
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
        initDataSet();
        return rootView;
    }

    private void initDataSet() {
        String category = getArguments().getString(ApplicationData.PROGRESS_CATEGORY);
        String unit = getArguments().getString(ApplicationData.PROGRESS_UNIT);
        if (!TextUtils.isEmpty(category)) {
            tvCategory.setText(category);
        }
        if (!TextUtils.isEmpty(unit)) {
            tvUnit.setText(unit);
        }

    }

    private void initView(View rootView) {
        tvCategory = (TextView) rootView.findViewById(R.id.tvCategory);
        edtValue = (EditText) rootView.findViewById(R.id.edtValue);
        tvUnit = (TextView) rootView.findViewById(R.id.tvUnit);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        manager = new PreferenceManager(getActivity());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSave) {
            String value = edtValue.getText().toString();
            if (!TextUtils.isEmpty(value)) {
                String category = tvCategory.getText().toString();
                String unit = tvUnit.getText().toString();
                uploadProgress(value, category, unit);
            } else {
                Toast.makeText(getActivity(), "Please enter some value", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void uploadProgress(String value, String category, String unit) {
        String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
        Call<JsonResponseFollow> call = ApiClientMain.getApiClient().addProgress(userId, category, value, unit);
        call.enqueue(new Callback<JsonResponseFollow>() {
            @Override
            public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {
                dismiss();
            }

            @Override
            public void onFailure(Call<JsonResponseFollow> call, Throwable t) {
                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void sendResult(int REQUEST_CODE) {
        Intent intent = new Intent();
        intent.putExtra(ApplicationData.PROGRESS_CODE, "Refresh");
        getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }
}
