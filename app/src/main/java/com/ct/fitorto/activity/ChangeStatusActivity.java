package com.ct.fitorto.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.StatusAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.model.FitortoStatus;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;
import java.util.ArrayList;


/**
 * Created by arvind on 8/15/16.
 */
public class ChangeStatusActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private EditText edtStatus;
    private ImageButton ibEdit;
    private ListView lvStatus;
    private StatusAdapter statusAdapter;
    ArrayList<FitortoStatus> statusArrayList=new ArrayList<>();
    private PreferenceManager manager;
    private boolean isEditable=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);
        initToolbar(true);
        initView();
        initDataSet();
    }

    private void initDataSet() {
        statusArrayList.add(new FitortoStatus("At the gym"));
        statusArrayList.add(new FitortoStatus("Cheat day"));
        statusArrayList.add(new FitortoStatus("Rest day"));
        statusArrayList.add(new FitortoStatus("Injured"));
        statusArrayList.add(new FitortoStatus("Toning in process"));
        statusArrayList.add(new FitortoStatus("Dancing my fat out"));
        statusArrayList.add(new FitortoStatus("Gym rat for life"));
        statusArrayList.add(new FitortoStatus("Keep calm and yoga"));
        statusAdapter=new StatusAdapter(this,R.layout.city_list_item,statusArrayList);
        lvStatus.setAdapter(statusAdapter);
        lvStatus.setOnItemClickListener(this);

        String status=manager.getPreferenceValues(ApplicationData.STATUS);
        if(!TextUtils.isEmpty(status)){
            edtStatus.setText(status);
        }
        ibEdit.setColorFilter(getResources().getColor(R.color.selectedTab), PorterDuff.Mode.SRC_IN);
        ibEdit.setOnClickListener(this);
    }

    private void initView() {
        edtStatus= (EditText) findViewById(R.id.edtStatus);
        ibEdit= (ImageButton) findViewById(R.id.ibEdit);
        lvStatus= (ListView) findViewById(R.id.lvStatus);
        manager=new PreferenceManager(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        FitortoStatus status=statusArrayList.get(i);
        manager.putPreferenceValues(ApplicationData.STATUS,status.getStatus());
        finishThis();
    }

    private void finishThis() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(isEditable==false){
            ibEdit.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_save));
            edtStatus.setFocusable(true);
            edtStatus.setFocusableInTouchMode(true);
            edtStatus.setClickable(true);
            isEditable= true;
        }else {
            String status=edtStatus.getText().toString();
            if(!TextUtils.isEmpty(status)){
                manager.putPreferenceValues(ApplicationData.STATUS,status);
                ibEdit.setImageDrawable(getResources().getDrawable(R.drawable.ic_pencil));
                edtStatus.setFocusable(false);
                edtStatus.setFocusableInTouchMode(false);
                edtStatus.setClickable(false);
                isEditable= false;
            }else{
                edtStatus.setError("Please write something");
            }
        }
    }
}
