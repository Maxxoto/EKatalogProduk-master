package ptacs.ekatalog.com.e_katalogproduk.activity;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.DetailAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

public class ActivityDetail extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SwipeRefreshLayout swLayout4;
    private LinearLayout llayout4;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DetailAdapter adapter;
    private DBHandler dbHandler;
    private List<Produk> detailList = new ArrayList<>();


    String mJenisProduk;
    String mMerkProduk;
    String mKelompokProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK);
            mJenisProduk = bundle.getString(Constant.BUNDLE_JENIS_PRODUK);
            mKelompokProduk = bundle.getString(Constant.BUNDLE_KELOMPOK_PRODUK);

            dbHandler = new DBHandler(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(mKelompokProduk);


            //TODO : INI TOMBOL BACK
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbHandler = new DBHandler(this);
        }

        initRecyclerView();
        cekDataRecyclerView();

        }

    @Override
        public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) search.getActionView();
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
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
        for (Produk produk : detailList) {
            String Tipe = produk.getNama_produk().toLowerCase();
            if (Tipe.contains(newText)){
                newList.add(produk);
            }
        }
        adapter.setFilter(newList);
        return true;
    }



    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_detail);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHandler = new DBHandler(ActivityDetail.this);
        detailList = dbHandler.getDetailProduk(mMerkProduk,mJenisProduk,mKelompokProduk);
        adapter = new DetailAdapter(detailList, ActivityDetail.this);
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
                    Toast.makeText(ActivityDetail.this,"Ini Long Click",Toast.LENGTH_SHORT).show();
                }
            }));
        }

        swLayout4 = (SwipeRefreshLayout) findViewById(R.id.sw_layout4);
        llayout4 = (LinearLayout) findViewById(R.id.llayout4);

        //Mengeset warna yang berputar
        swLayout4.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        //Setting Listener yang akan dijalankan saat layar difresh
        swLayout4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
            void refreshItem(){
                initRecyclerView();
                onItemLoad();
            }
            void onItemLoad(){
                swLayout4.setRefreshing(false);
            }
        });
    }

    }

