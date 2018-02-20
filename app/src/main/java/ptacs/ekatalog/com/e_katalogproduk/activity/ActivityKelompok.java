package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.KategoriAdapter;
import ptacs.ekatalog.com.e_katalogproduk.adapter.KelompokAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

public class ActivityKelompok extends AppCompatActivity {
    private SwipeRefreshLayout swLayoutkelompok;
    private LinearLayout llayoutkelompok;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private KelompokAdapter adapter;
    private DBHandler dbHandler;
    private List<Produk> kelompokList = new ArrayList<>();
    String mJenisProduk;
    String mMerkProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelompok);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mJenisProduk = bundle.getString(Constant.BUNDLE_JENIS_PRODUK); //MERK kategori
            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK);

            //Toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(mJenisProduk);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbHandler = new DBHandler(this);
        }

        initRecyclerView();
        cekDataRecyclerView();

        //dbHandler.getKategoryProduk(mMerkProduk);
    }

    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_kelompok);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHandler = new DBHandler(ActivityKelompok.this);
        kelompokList = dbHandler.getKelompokProduk(mMerkProduk,mJenisProduk);
        //kategoriList = dbHandler.getKategoryProduk();
        adapter = new KelompokAdapter(kelompokList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

                            bundle.putString(Constant.BUNDLE_MERK_PRODUK, adapter.getItem(position).getMerk_produk());
                            bundle.putString(Constant.BUNDLE_JENIS_PRODUK, adapter.getItem(position).getJenis_produk());
                            bundle.putString(Constant.BUNDLE_KELOMPOK_PRODUK, adapter.getItem(position).getKelompok_produk());

                            Intent intent = new Intent(ActivityKelompok.this, ActivityList.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    })
            );
        }
        swLayoutkelompok = (SwipeRefreshLayout) findViewById(R.id.sw_layoutkelompok);
        llayoutkelompok = (LinearLayout) findViewById(R.id.llayoutkelompok);

        //Mengeset warna yang berputar
        swLayoutkelompok.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        //Setting Listener yang akan dijalankan saat layar difresh
        swLayoutkelompok.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
            void refreshItem(){
                initRecyclerView();
                //cekDataRecyclerView();
                onItemLoad();
            }
            void onItemLoad(){
                swLayoutkelompok.setRefreshing(false);
            }
        });
    }

}
