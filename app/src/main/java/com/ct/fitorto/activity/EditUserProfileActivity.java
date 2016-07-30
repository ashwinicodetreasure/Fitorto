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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseUserProfile;
import com.ct.fitorto.model.JsonResponseUserUpdate;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ashwini on 6/27/2016.
 */
public class EditUserProfileActivity extends AppCompatActivity {
    // private Spinner genderspinner;
    private PreferenceManager preferenceManager;
    private EditText edname, edstatus, edphone, edgender;
    private TextView edemail;

    //private String[] state = {"Male", "Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_user_profile);
        setToolabar();
        preferenceManager = new PreferenceManager(EditUserProfileActivity.this);
        RegisterComponent();
        // genderSelection();
        displaydata();
        //updatedata();


    }

    private void updatedata() {
        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        //String fullname=edfullname.getText().toString();
        String name = edname.getText().toString();
//            String url=edurl.getText().toString();
        String status = edstatus.getText().toString();
        //String mail=edemail.getText().toString();
        String phone = edphone.getText().toString();
        String gender = edgender.getText().toString();


        Call<JsonResponseUserUpdate> response = ApiClientMain.getApiClient().getResponseUserUpdate(userid, name, phone, "", gender, "", "", status);

        response.enqueue(new Callback<JsonResponseUserUpdate>() {

            @Override
            public void onResponse(Call<JsonResponseUserUpdate> call, Response<JsonResponseUserUpdate> response) {
                JsonResponseUserUpdate resp = response.body();
                //for (FitortoUser user : resp.getData())  this code check for each
                if (resp != null) {
                    if (resp.equals(1)) {

                        Toast.makeText(EditUserProfileActivity.this, "Udated Sucessfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditUserProfileActivity.this, resp.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonResponseUserUpdate> call, Throwable t) {


                Log.d("Error", "failed");
                Toast.makeText(EditUserProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }


    private void RegisterComponent() {
        edgender = (EditText) findViewById(R.id.gender);
        //edfullname=(EditText)findViewById(R.id.fullname);
        edname = (EditText) findViewById(R.id.name);
        //edurl=(EditText)findViewById(R.id.link);
        edstatus = (EditText) findViewById(R.id.status);
        edemail = (TextView) findViewById(R.id.mail);
        edphone = (EditText) findViewById(R.id.phone);
    }

    private void displaydata() {

        final String userid = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        Call<JsonResponseUserProfile> response = ApiClientMain.getApiClient().getResponseUserprofile(userid);

        response.enqueue(new Callback<JsonResponseUserProfile>() {

            @Override
            public void onResponse(Call<JsonResponseUserProfile> call, Response<JsonResponseUserProfile> response) {
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
                                edname.setText(user.getName());

                            } else {
                                edname.setText("No name");

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
                                edgender.setText(user.getGender());
                            } else {
                                edgender.setText("update");
                            }
                        }

                    }


                }

            }

            @Override
            public void onFailure(Call<JsonResponseUserProfile> call, Throwable t) {


                Log.d("Error", "failed");
                Toast.makeText(EditUserProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }

   /* private void genderSelection() {
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(EditUserProfileActivity.this, android.R.layout.simple_spinner_item, state);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(adapter_state);

    }*/

    private void setToolabar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_update, menu);
        MenuItem mi = (MenuItem) findViewById(R.id.edit);

        return true;
    }

    public void dailog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditUserProfileActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Update Profile...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want update!");

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.ic_yes);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                // Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.edit:
                //dailog();
                updatedata();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}
