package com.ct.fitorto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ct.fitorto.R;
import com.ct.fitorto.Utility.AvenuesParams;
import com.ct.fitorto.Utility.ServiceUtility;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.ccavanue.InitialActivity;
import com.ct.fitorto.ccavanue.WebViewActivity;
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
    private EditText spPrice;
    private Button btnApply;
    private String userID;
    private PreferenceManager manager;
    private Search search;
    private ArrayList<Package> packageList = new ArrayList<>();
    private String packageId;
    private EditText accessCode, merchantId, currency, amount, orderId, rsaKeyUrl, redirectUrl, cancelUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_gym);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        orderId.setText(randomNum.toString());
        userID = manager.getPreferenceValues(manager.PREF_USER_UserId);
        search = getIntent().getParcelableExtra(ApplicationData.SEARCH_RESULT);
        if (search != null) {
            String userName = manager.getPreferenceValues(manager.PREF_CLIENT_NAME);
            edtUserName.setText(userName);
            edtGymName.setText(search.getGymName());
            String gymID = search.getGymID();
            if (search.getPackages().size() > 0) {
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
                spPrice.setText(aPackage.getCost());
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
        spPrice = (EditText) findViewById(R.id.spPrice);
        btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(this);


        accessCode = (EditText) findViewById(R.id.accessCode);
        merchantId = (EditText) findViewById(R.id.merchantId);
        orderId = (EditText) findViewById(R.id.orderId);
        currency = (EditText) findViewById(R.id.currency);
        amount = (EditText) findViewById(R.id.amount);
        rsaKeyUrl = (EditText) findViewById(R.id.rsaUrl);
        redirectUrl = (EditText) findViewById(R.id.redirectUrl);
        cancelUrl = (EditText) findViewById(R.id.cancelUrl);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnApply) {
           /* Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);*/
            String vAccessCode = ServiceUtility.chkNull(accessCode.getText()).toString().trim();
            String vMerchantId = ServiceUtility.chkNull(merchantId.getText()).toString().trim();
            String vCurrency = ServiceUtility.chkNull(currency.getText()).toString().trim();
            String vAmount = ServiceUtility.chkNull(spPrice.getText()).toString().trim();
            if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode.getText()).toString().trim());
                intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId.getText()).toString().trim());
                intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId.getText()).toString().trim());
                intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency.getText()).toString().trim());
                intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(spPrice.getText()).toString().trim());
                //intent.putExtra(AvenuesParams.AMOUNT, "1");//TODO Remove Dummy amont
                intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl.getText()).toString().trim());
                intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl.getText()).toString().trim());
                intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl.getText()).toString().trim());
                intent.putExtra(ApplicationData.GYM_ID,search.getGymID());
                startActivity(intent);
            } else {
                Toast.makeText(this, "All parameters are mandatory.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
