package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.DetailAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;


public class ActivitySearch extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SwipeRefreshLayout swLayoutsearch;
    private LinearLayout llayoutsearch;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DetailAdapter adapter;
    private DBHandler dbHandler;
    private List<Produk> searchList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initRecyclerView();
        cekDataRecyclerView();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle("Search Produk");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbHandler = new DBHandler(this);


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<Produk> newList = new ArrayList<>();
        for (Produk produk : searchList) {
            String Nama = produk.getNama_produk().toLowerCase();
            if (Nama.contains(newText)){
                newList.add(produk);
            }
        }
        adapter.setFilter(newList);
        return true;

    }

    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHandler = new DBHandler(ActivitySearch.this);
        searchList = dbHandler.getSearchProduk();
        adapter = new DetailAdapter(searchList, ActivitySearch.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void cekDataRecyclerView() {

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                    recyclerView, new RecyclerItemClickListener.ClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }

                @Override
                public void onItemLongClick(View view, int position) {
                    Toast.makeText(ActivitySearch.this,"Ini Long Click",Toast.LENGTH_SHORT).show();
                }
            }));
        }

        swLayoutsearch = (SwipeRefreshLayout) findViewById(R.id.sw_layoutsearch);
        llayoutsearch = (LinearLayout) findViewById(R.id.llayoutsearch);

        //Mengeset warna yang berputar
        swLayoutsearch.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        //Setting Listener yang akan dijalankan saat layar difresh
        swLayoutsearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
            void refreshItem(){
                initRecyclerView();
                onItemLoad();
            }
            void onItemLoad(){
                swLayoutsearch.setRefreshing(false);
            }
        });
    }

}

