package com.ct.fitorto.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ct.fitorto.R;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.Package;
import com.ct.fitorto.model.Search;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

/**
 * Created by codetreasure on 8/9/16.
 */
public class BookGymActivity extends BaseActivity implements View.OnClickListener {

    private EditText edtUserName;
    private EditText edtGymName;
    private Spinner spPackage;
    private Spinner spPrice;
    private Button btnApply;
    private String userID;
    private PreferenceManager manager;
    private Search search;
    private ArrayList<Package> packageList=new ArrayList<>();
    private String packageId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_gym);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        userID = manager.getPreferenceValues(manager.PREF_USER_UserId);
        search=getIntent().getParcelableExtra(ApplicationData.SEARCH_RESULT);
        if(search!=null){
            edtGymName.setText(search.getGymName());
            String gymID=search.getGymID();
            if(search.getPackages().size()>0){
                displayPackageSpinnerList(search.getPackages());
            }
        }
    }

    private void displayPackageSpinnerList(ArrayList<Package> packages) {
        ArrayAdapter<Package> spinnerArrayAdapter = new ArrayAdapter(this,
                R.layout.simple_spinner_item, packages);
        spPackage.setAdapter(spinnerArrayAdapter);
        spPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Package aPackage = (Package) spPackage.getSelectedItem();
                //packageId = aPackage.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    private void initView() {
        manager = new PreferenceManager(this);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtGymName = (EditText) findViewById(R.id.edtGymName);
        spPackage = (Spinner) findViewById(R.id.spPackage);
        spPrice = (Spinner) findViewById(R.id.spPrice);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnApply) {

        }
    }
}
