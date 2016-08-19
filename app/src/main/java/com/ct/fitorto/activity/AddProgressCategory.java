package com.ct.fitorto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 8/19/16.
 */
public class AddProgressCategory extends BaseActivity implements View.OnClickListener {


    private TextView edtCategory;
    private EditText edtValue;
    private TextView edtUnit;
    private Button btnAdd;
    private PreferenceManager manager;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_progress_category);
        initToolbar(true);
        initView();
    }

    private void initDataSet() {
        edtCategory.setError(null);
        edtUnit.setError(null);
        edtValue.setError(null);

        boolean cancel = false;
        View focusView = null;


        final String category = edtCategory.getText().toString();
        final String unit = edtUnit.getText().toString();
        final String value = edtValue.getText().toString();

        if (TextUtils.isEmpty(category)) {
            focusView = edtCategory;
            cancel = true;
            edtCategory.setError("Please add category");
        }

        if (TextUtils.isEmpty(unit)) {
            focusView = edtUnit;
            cancel = true;
            edtUnit.setError("Please add unit");
        }

        if (TextUtils.isEmpty(value)) {
            focusView = edtValue;
            cancel = true;
            edtValue.setError("Please add value");
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            showProgressDialog("Please wait...", false);
            String userId = manager.getPreferenceValues(manager.PREF_USER_UserId);
            Call<JsonResponseFollow> call = ApiClientMain.getApiClient().addProgress(userId, category, value, unit);
            call.enqueue(new Callback<JsonResponseFollow>() {
                @Override
                public void onResponse(Call<JsonResponseFollow> call, Response<JsonResponseFollow> response) {
                    cancelProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            finishThis(category, value, unit);
                            //sendResult(ApplicationData.REQUEST_CODE_PROGRESS, value, category, unit);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseFollow> call, Throwable t) {
                    cancelProgressDialog();
                    Toast.makeText(AddProgressCategory.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void finishThis(String category, String value, String unit) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ApplicationData.PROGRESS_CATEGORY, category);
        returnIntent.putExtra(ApplicationData.PROGRESS_UNIT, unit);
        returnIntent.putExtra(ApplicationData.PROGRESS_VALUE, value);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void initView() {
        edtCategory = (TextView) findViewById(R.id.edtCategory);
        edtValue = (EditText) findViewById(R.id.edtValue);
        edtUnit = (TextView) findViewById(R.id.edtUnit);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        manager = new PreferenceManager(this);
    }

    @Override
    public void onClick(View view) {
        initDataSet();
    }
}
