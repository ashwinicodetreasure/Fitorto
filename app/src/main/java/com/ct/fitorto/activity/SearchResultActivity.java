package com.ct.fitorto.activity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;

        import com.ct.fitorto.R;
        import com.ct.fitorto.adapter.SearchAdapter;
        import com.ct.fitorto.model.Search;

        import java.util.ArrayList;

/**
 * Created by Ashwini on 5/27/2016.
 */
public class SearchResultActivity extends AppCompatActivity implements SearchAdapter.OnItemClickListener {
    private LinearLayoutManager lLayout;
    private RecyclerView rView;
    private SearchAdapter adapter;
    private ArrayList<Search> testing = new ArrayList<Search>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult_activity);

        setToolbar();

        lLayout = new LinearLayoutManager(SearchResultActivity.this);
        rView = (RecyclerView) findViewById(R.id.search_recycler);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        //this.getIntent().getParcelableArrayListExtra("extraextra");
        testing.addAll(getIntent().<Search>getParcelableArrayListExtra("searchItem"));

        if (testing.size() > 0) {

            adapter = new SearchAdapter(SearchResultActivity.this, testing);
            Log.d("Logs", "Search:" + adapter);
            rView.setAdapter(adapter);


        }


    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Search Result");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchResultActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onItemClick(Search item) {


        Intent link = new Intent(SearchResultActivity.this, MembershipActivity.class);
        link.putExtra("memberItem", item);
        startActivity(link);



    }


}
