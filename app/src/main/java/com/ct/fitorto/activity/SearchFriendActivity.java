package com.ct.fitorto.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.ct.fitorto.R;
import com.ct.fitorto.adapter.SearchResultAdapter;
import com.ct.fitorto.model.Friends;
import com.ct.fitorto.model.JsonResponseSearchFriends;
import com.ct.fitorto.network.ApiClientMain;
import com.ct.fitorto.preferences.PreferenceManager;
import com.ct.fitorto.utils.ApplicationData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by codetreasure on 6/21/16.
 */
public class SearchFriendActivity extends AppCompatActivity implements SearchResultAdapter.OnItemClickListener {
    private RecyclerView rview;
    private EditText search;
    private ImageButton clear;
    private PreferenceManager preferenceManager;
    private LinearLayoutManager llayout;
    private SearchResultAdapter adapter;
    private ArrayList<Friends> arrayList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_friend_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Search");
        }
        search = (EditText) findViewById(R.id.edtSearch);//searchthrough edittext
//        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        llayout = new LinearLayoutManager(this);
        rview = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rview.setLayoutManager(llayout);
        clear = (ImageButton) findViewById(R.id.btn_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setText("");
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    searchByKeywords(s.toString());
                } else {
                    if (adapter != null) {
                        if (arrayList.size() > 0) {
                            arrayList.clear();
                            adapter = new SearchResultAdapter(SearchFriendActivity.this, arrayList);
                            rview.setAdapter(adapter);
                        }
                    }
                }
            }
        });
    }

    private void searchByKeywords(String str) {
        progressBar.setVisibility(View.VISIBLE);
        preferenceManager = new PreferenceManager(SearchFriendActivity.this);
        String userId = preferenceManager.getPreferenceValues(preferenceManager.PREF_USER_UserId);
        if (!TextUtils.isEmpty(str)) {
            Call<JsonResponseSearchFriends> call = ApiClientMain.getApiClient().searchFriends(userId, str);
            call.enqueue(new Callback<JsonResponseSearchFriends>() {
                @Override
                public void onResponse(Call<JsonResponseSearchFriends> call, Response<JsonResponseSearchFriends> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.body() != null) {
                        if (response.body().getStatus().equals("1")) {
                            if (response.body().getData().size() > 0) {
                                updateFriendsArrayList(response.body().getData());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonResponseSearchFriends> call, Throwable t) {

                }
            });
        }
    }

    private void updateFriendsArrayList(ArrayList<Friends> data) {
        arrayList.clear();
        arrayList.addAll(data);
        adapter = new SearchResultAdapter(SearchFriendActivity.this, arrayList);
        rview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, Friends position) {
        Intent i = new Intent(this, FriendsProfileActivity.class);
        i.putExtra(ApplicationData.FRIEND_ID, position.getUserID());
        startActivityForResult(i, ApplicationData.RESULT_CODE_FRIEND);
    }
}
