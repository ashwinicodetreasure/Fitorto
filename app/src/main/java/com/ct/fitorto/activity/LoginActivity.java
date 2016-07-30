package com.ct.fitorto.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.custom.GradientBackgroundPainter;
import com.ct.fitorto.model.FitortoUser;
import com.ct.fitorto.preferences.PreferenceManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvindshukla on 30/07/16.
 */
public class LoginActivity extends AppCompatActivity {

    private GradientBackgroundPainter gradientBackgroundPainter;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 356;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button loginbtn;
    private Button goolgeLogin;
    private TextView skipbtn;
    private Button signup;
    private Button fbLogin;
    private Dialog login;
    private List<FitortoUser> user = new ArrayList<>();
    private String projectId = "251491466859";

    //private List<FitortoUser> data=new ArrayList<>();
    private String username = "";
    private String password = "";
    private PreferenceManager preferenceManager;
    Permission[] permissions = new Permission[]{
            Permission.USER_STATUS,
            Permission.EMAIL,
    };
    private SimpleFacebook mSimpleFacebook;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View backgroundImage = findViewById(R.id.root_view);

        final int[] drawables = new int[3];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;

        gradientBackgroundPainter = new GradientBackgroundPainter(backgroundImage, drawables);
        gradientBackgroundPainter.start();
    }
}
