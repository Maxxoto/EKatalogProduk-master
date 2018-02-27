package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.ListAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;


public class ActivityList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private SwipeRefreshLayout swLayout3;
    private LinearLayout llayout3;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ListAdapter adapter;
    private DBHandler dbHandler;
    private List<Produk> listList = new ArrayList<>();

    String mJenisProduk;
    String mMerkProduk;
    String mKelompokProduk;
    String mSubKelompokProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mJenisProduk = bundle.getString(Constant.BUNDLE_JENIS_PRODUK);
            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK);
            mKelompokProduk = bundle.getString(Constant.BUNDLE_KELOMPOK_PRODUK);
            mSubKelompokProduk = bundle.getString(Constant.BUNDLE_SUBKELOMPOK_PRODUK);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(mSubKelompokProduk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHandler = new DBHandler(this);
            }

        initRecyclerView();
        cekDataRecyclerView();

        }
    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHandler = new DBHandler(ActivityList.this);
        listList = dbHandler.getListProduk(mJenisProduk,mMerkProduk,mKelompokProduk,mSubKelompokProduk);
        adapter = new ListAdapter(listList, ActivityList.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
        for (Produk produk : listList) {
            String Tipe = produk.getTipe_produk().toLowerCase();
            if (Tipe.contains(newText)){
                newList.add(produk);
            }
        }
        adapter.setFilter(newList);
        return true;

    }



    private void cekDataRecyclerView() {

        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            Bundle bundle = new Bundle();

                            //COMMIT MAS INDRA CS

                            bundle.putString(Constant.BUNDLE_TIPE_PRODUK, adapter.getItem(position).getTipe_produk());
                            bundle.putString(Constant.BUNDLE_MERK_PRODUK, adapter.getItem(position).getMerk_produk());
                            bundle.putString(Constant.BUNDLE_JENIS_PRODUK, adapter.getItem(position).getJenis_produk());
                            bundle.putString(Constant.BUNDLE_KELOMPOK_PRODUK, adapter.getItem(position).getKelompok_produk());
                            bundle.putString(Constant.BUNDLE_SUBKELOMPOK_PRODUK, adapter.getItem(position).getSubkelompok_produk());


                            Intent intent = new Intent(ActivityList.this, ActivityDetail.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    })
            );
        }

        swLayout3 = (SwipeRefreshLayout) findViewById(R.id.sw_layout3);
        llayout3 = (LinearLayout) findViewById(R.id.llayout3);

        //Mengeset warna yang berputar
        swLayout3.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        //Setting Listener yang akan dijalankan saat layar difresh
        swLayout3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
            void refreshItem(){
                initRecyclerView();
                onItemLoad();
            }
            void onItemLoad(){
                swLayout3.setRefreshing(false);
            }
        });
    }




}

