package com.ct.fitorto.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseUserProfile;
import com.ct.fitorto.model.JsonResponseUserUpdate;
import com.ct.fitorto.model.Package;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 6/27/2016.
 */
public class EditUserProfileActivity extends BaseActivity {
    // private Spinner genderspinner;
    private PreferenceManager preferenceManager;
    private EditText edtName, edstatus, edphone;
    private Spinner spGender;
    private TextView edemail;
    private String gender;

    //private String[] state = {"Male", "Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);
        initToolbar(true);
        preferenceManager = new PreferenceManager(EditUserProfileActivity.this);
        initView();
        initDataSet();
        genderSelection();
    }

    private void updateData() {
        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        String name = edtName.getText().toString();
        String status = edstatus.getText().toString();
        String phone = edphone.getText().toString();
        showProgressDialog("Please Wait...", false);
        Call<JsonResponseUserUpdate> response = ApiClientMain.getApiClient().getResponseUserUpdate(userid, name, phone, "", gender, "", "", status);
        response.enqueue(new Callback<JsonResponseUserUpdate>() {
            @Override
            public void onResponse(Call<JsonResponseUserUpdate> call, Response<JsonResponseUserUpdate> response) {
                JsonResponseUserUpdate resp = response.body();
                cancelProgressDialog();
                //for (FitortoUser user : resp.getData())  this code check for each
                if (resp != null) {
                    if (resp.equals(1)) {
                        Toast.makeText(EditUserProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditUserProfileActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseUserUpdate> call, Throwable t) {
                cancelProgressDialog();
            }
        });
    }


    private void initView() {
        spGender = (Spinner) findViewById(R.id.gender);
        edtName = (EditText) findViewById(R.id.name);
        edstatus = (EditText) findViewById(R.id.status);
        edemail = (TextView) findViewById(R.id.mail);
        edphone = (EditText) findViewById(R.id.phone);
    }

    private void initDataSet() {
        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        showProgressDialog("Please Wait...", false);
        Call<JsonResponseUserProfile> response = ApiClientMain.getApiClient().getResponseUserprofile(userid);
        response.enqueue(new Callback<JsonResponseUserProfile>() {
            @Override
            public void onResponse(Call<JsonResponseUserProfile> call, Response<JsonResponseUserProfile> response) {
                cancelProgressDialog();
                JsonResponseUserProfile resp = response.body();
                //for (FitortoUser user : resp.getData())  this code check for each
                if (resp != null) {        //checking if response is not null
                    if (resp.getData().size() > 0) {
                        FitortoUser user = resp.getData().get(0);           //retrieving user data
                        if (user != null) {
                          /*  if (!TextUtils.isEmpty(user.getName())) {
                                edfullname.setText(user.getName());
                            }*/
                            if (!TextUtils.isEmpty(user.getName())) {
                                edtName.setText(user.getName());
                            } else {
                                edtName.setText("No name");

                            }

                            if (!TextUtils.isEmpty(user.getStatus())) {
                                edstatus.setText(user.getStatus());
                            } else {
                                edstatus.setText("No Status");
                            }

                            if (!TextUtils.isEmpty(user.getEmailID())) {
                                edemail.setText(user.getEmailID());
                            } else {
                                edemail.setText("No Email");
                            }

                            if (!TextUtils.isEmpty(user.getPhoneNo())) {
                                edphone.setText(user.getPhoneNo());
                            } else {
                                edphone.setText("No Number");
                            }

                            if (!TextUtils.isEmpty(user.getGender())) {
                                setGenderDropDown(user.getGender());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseUserProfile> call, Throwable t) {
                cancelProgressDialog();
                //  Log.d("Error", "failed");
                // Toast.makeText(EditUserProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void genderSelection() {
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(EditUserProfileActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.selectGender));
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter_state);
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = (String) spGender.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setGenderDropDown(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            spGender.setSelection(0);
        } else if (gender.equalsIgnoreCase("Female")) {
            spGender.setSelection(1);
        } else {
            spGender.setSelection(2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_update, menu);
        MenuItem mi = (MenuItem) findViewById(R.id.action_done);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                updateData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
