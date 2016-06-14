package com.ct.fitorto.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ashwini on 4/28/2016.
 */
public class SignupActivity extends Activity implements View.OnClickListener {
    private EditText user_email;
    private EditText user_pass;
    private EditText user_name;
    private EditText user_num;
    private AppCompatButton signup;
    private TextView allogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        user_email=(EditText)findViewById(R.id.input_email);
        user_pass=(EditText)findViewById(R.id.input_password);
        user_name=(EditText)findViewById(R.id.input_name);
        user_num=(EditText)findViewById(R.id.phone_no);




        allogin=(TextView) findViewById(R.id.link_login);
        allogin.setOnClickListener(this);

        signup=(AppCompatButton)findViewById(R.id.btn_signup);
        signup.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_signup:

                    registerUser();

                break;
            case R.id.link_login:
                Intent i = new Intent(SignupActivity.this, SlidingActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
                break;


        }}

    private void registerUser() {

        String userName=user_name.getText().toString().trim();
        String emailID=user_email.getText().toString().trim();
        String password=user_pass.getText().toString().trim();
        String phoneNo=user_num.getText().toString().trim();


        String MobilePattern = "[0-9]{10}";

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean cancel = false;

        if(userName.length() > 25){
            Toast.makeText(getApplicationContext(), "pls enter less the 25 characher in user name", Toast.LENGTH_SHORT).show();
            cancel= true;
        }



        else if(userName.length() == 0 || phoneNo.length() == 0 || emailID.length() ==0 ||password.length()==0){
            Toast.makeText(getApplicationContext(), "pls fill the empty fields", Toast.LENGTH_SHORT).show();
            cancel=false;
        }


        else if(!emailID.matches(emailPattern))
        {
            Toast.makeText(getApplicationContext(),"Please Enter Valid Email Adress",Toast.LENGTH_SHORT).show();


            cancel= false;
        }
        else if(userName.matches(MobilePattern))
        {
            Toast.makeText(getApplicationContext(), "phone number is valid",Toast.LENGTH_SHORT).show();

            cancel= true;
        }
        else if(!phoneNo.matches(MobilePattern)) {
            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();


            cancel= false;
        } else
            registerUserData();
    }

    private void registerUserData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<JsonResponseUser> response = apiClient.operation(user_email.getText().toString(), user_email.getText().toString(),
                user_pass.getText().toString(), user_num.getText().toString());

        response.enqueue(new Callback<JsonResponseUser>() {
            @Override
            public void onResponse(Call<JsonResponseUser> call, retrofit2.Response<JsonResponseUser> response) {

                JsonResponseUser resp = response.body();
                List<FitortoUser> fu= (ArrayList<FitortoUser>) resp.getData();
                if(resp.getData().size()>0)




                Toast.makeText(SignupActivity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonResponseUser> call, Throwable t) {


                Log.d("Error", "failed");
                Toast.makeText(SignupActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }





}



