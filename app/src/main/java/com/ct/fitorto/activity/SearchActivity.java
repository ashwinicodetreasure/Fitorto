package com.ct.fitorto.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.fitorto.utils.HistoryOpenHelper;
import com.ct.fitorto.R;
import com.ct.fitorto.adapter.HistoryAdapter;
import com.ct.fitorto.baseclass.BaseActivity;
import com.ct.fitorto.custom.CustomTextView;
import com.ct.fitorto.model.JsonResponseSearch;
import com.ct.fitorto.model.Search;
import com.ct.fitorto.model.categoryName;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ashwini on 5/26/2016.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    // private EditText search;
    private ListView slist;
    // private Button searchbtn;
    ArrayList<Search> lp = new ArrayList<>();
    private ArrayList<categoryName> autoComplete = new ArrayList<>();
    // ListviewAdapter adapt;
    private HistoryAdapter adapter;
    private HistoryAdapter adapter_ob;
    private HistoryOpenHelper helper_ob;
    private HistoryOpenHelper helper;
    private Cursor cursor;
    private AutoCompleteTextView search;
    private Button clear;
    private PreferenceManager preferenceManager;
    private TextView tvLocation;
    private ImageButton ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        search = (AutoCompleteTextView) findViewById(R.id.edtitem);//searchthrough edittext
        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        CustomTextView.applyFont(this, search, "Roboto-Regular.ttf");
        clear = (Button) findViewById(R.id.btn_clear);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        ivBack = (ImageButton) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        clear.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_UNSPECIFIED:

                }
                return false;
            }
        });
        autoSearch();
        // autoComplete();
        displayHistory();
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryName str = (categoryName) parent.getItemAtPosition(position);
                autoCompleteOnClick(str.getCategoryName());
            }
        });

        preferenceManager = new PreferenceManager(SearchActivity.this);
        String address = preferenceManager.getPreferenceValues(preferenceManager.ADDRESS);
        if (!TextUtils.isEmpty(address)) {
            tvLocation.setText(address);
            tvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(SearchActivity.this, ChangeLocationActivity.class);
                    startActivityForResult(i, ApplicationData.ACTIVITY_RESULT_LOCATION);
                }
            });
        }

    }

    private void autoCompleteOnClick(String str) {
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        String Area = preferenceManager.getPreferenceValues(preferenceManager.PREF_AREA);
        //  String str = search.getText().toString().trim();
        if (!TextUtils.isEmpty(str)) {

            Call<JsonResponseSearch> call = ApiClientMain.getApiClient().search(userId, Area, city, str);
            call.enqueue(new Callback<JsonResponseSearch>() {
                @Override
                public void onResponse(Call<JsonResponseSearch> call, final Response<JsonResponseSearch> response) {
                    if (response.isSuccessful()) {
                        //if(response.body().getData().size()>0) {
                        lp.addAll(response.body().getData());//Always addall for arraylist
                        if (lp.size() > 0) {
                            Intent link = new Intent(SearchActivity.this, SearchResultActivity.class);
                            link.putParcelableArrayListExtra("searchItem", (ArrayList<? extends Parcelable>) lp);
                            startActivity(link);
                            lp.clear();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseSearch> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(SearchActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
        }

    }


    private void clearSearch() {
        search.setText("");
    }

    private void autoSearch() {
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_UNSPECIFIED:
                    case EditorInfo.IME_ACTION_SEARCH:
                        if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                            setDataResultActivity();
                            insert();
                            //displayHistory();
                            search.setText("");
                        }
                        return true;
                    default:

                        return false;
                }
            }
        });
    }

   /* private void autoComplete() {
        Call<JsonResponseKeywords> call = ApiClientMain.getApiClient().getResponseKeywordsCall();
        call.enqueue(new Callback<JsonResponseKeywords>() {
            @Override
            public void onResponse(Call<JsonResponseKeywords> call, final Response<JsonResponseKeywords> response) {

                if (response.isSuccessful()) {
                    //if(response.body().getData().size()>0) {
                    autoComplete.addAll(response.body().getData());//Always addall for arraylist
                    if (autoComplete.size() > 0) {
                        AutoCompleteAdapter adapter = new AutoCompleteAdapter(SearchActivity.this, R.layout.autocomplete_item, R.id.autoitem, autoComplete);
                        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.edtitem);
                        autoCompleteTextView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonResponseKeywords> call, Throwable t) {

            }


        });

    }*/

    private void setDataResultActivity() {
        preferenceManager = new PreferenceManager(SearchActivity.this);
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
        String Area = preferenceManager.getPreferenceValues(preferenceManager.PREF_AREA);
        String str = search.getText().toString().trim();
        if (!TextUtils.isEmpty(str)) {
            showProgressDialog("Please Wait...", false);
            Call<JsonResponseSearch> call = ApiClientMain.getApiClient().search(userId, Area, city, str);
            call.enqueue(new Callback<JsonResponseSearch>() {
                @Override
                public void onResponse(Call<JsonResponseSearch> call, final Response<JsonResponseSearch> response) {
                    cancelProgressDialog();
                    if (response.isSuccessful()) {
                        //if(response.body().getData().size()>0) {
                        lp.addAll(response.body().getData());//Always addall for arraylist
                        // if (lp.size() > 0) {
                        Intent link = new Intent(SearchActivity.this, SearchResultActivity.class);
                        link.putParcelableArrayListExtra(ApplicationData.SEARCH_RESULT, (ArrayList<? extends Parcelable>) lp);
                        startActivity(link);
                        lp.clear();
                        // }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseSearch> call, Throwable t) {
                    cancelProgressDialog();
                }
            });
        } else {
            Toast.makeText(SearchActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
        }

    }

    //history database related
    public void insert() {
        adapter = new HistoryAdapter(this);
        String history = search.getText().toString();
        if (!TextUtils.isEmpty(history)) {
            long val = adapter.insertDetails(history);
            //  Toast.makeText(getApplicationContext(), Long.toString(val), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(SearchActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();

    }

    public void displayHistory() {
        slist = (ListView) findViewById(R.id.search_list);
        adapter_ob = new HistoryAdapter(this);
        String[] from = {helper_ob.FNAME};
        int[] to = {R.id.searchitem};
        cursor = adapter_ob.queryName();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.suggestion_list_item, cursor, from, to);
        slist.setAdapter(cursorAdapter);
        slist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Bundle passdata = new Bundle();
                Cursor listCursor = (Cursor) arg0.getItemAtPosition(arg2);
                String str = listCursor.getString(listCursor.getColumnIndex(helper_ob.FNAME));
                //Toast.makeText(getApplicationContext(), str, 500).show();
                preferenceManager = new PreferenceManager(SearchActivity.this);
                String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
                String city = preferenceManager.getPreferenceValues(preferenceManager.PREF_City);
                String Area = preferenceManager.getPreferenceValues(preferenceManager.PREF_AREA);

                if (!TextUtils.isEmpty(str)) {
                    showProgressDialog("Please Wait...", false);
                    Call<JsonResponseSearch> call = ApiClientMain.getApiClient().search(userId, Area, city, str);
                    call.enqueue(new Callback<JsonResponseSearch>() {
                        @Override
                        public void onResponse(Call<JsonResponseSearch> call, final Response<JsonResponseSearch> response) {
                            cancelProgressDialog();
                            if (response.isSuccessful()) {
                                //if(response.body().getData().size()>0) {
                                lp.addAll(response.body().getData());//Always addall for arraylist
                                //if (lp.size() > 0) {
                                Intent link = new Intent(SearchActivity.this, SearchResultActivity.class);
                                link.putParcelableArrayListExtra(ApplicationData.SEARCH_RESULT, (ArrayList<? extends Parcelable>) lp);
                                startActivity(link);
                                lp.clear();
                                // }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonResponseSearch> call, Throwable t) {
                            cancelProgressDialog();
                        }
                    });
                } else {
                    Toast.makeText(SearchActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteHistory() {
        adapter.delete();
        finish();
    }

    //end daatabase related


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                clearSearch();
                break;
            case R.id.ivBack:
                onBackPressed();
                break;

           /*case R.id.btnSearch:
                //  if(!TextUtils.isEmpty(search.getText().toString())){
                setDataResultActivity();//}
                insert();

                //else{

                // }
                break;*/

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String address = preferenceManager.getPreferenceValues(preferenceManager.ADDRESS);
        if (!TextUtils.isEmpty(address)) {
            tvLocation.setText(address);
        }
    }
}
