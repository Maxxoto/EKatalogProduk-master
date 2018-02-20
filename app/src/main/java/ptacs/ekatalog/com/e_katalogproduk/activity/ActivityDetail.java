package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.DetailAdapter;
import ptacs.ekatalog.com.e_katalogproduk.adapter.ListAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

import ptacs.ekatalog.com.e_katalogproduk.R;

public class ActivityDetail extends AppCompatActivity {


    private SwipeRefreshLayout swLayout4;
    private LinearLayout llayout4;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DetailAdapter adapter;
    private DBHandler dbHandler;
    private List<Produk> detailList = new ArrayList<>();



    String mTipeProduk;
    String mMerkProduk;
    String mJenisProduk;
    String mKelompokProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mTipeProduk = bundle.getString(Constant.BUNDLE_TIPE_PRODUK);
            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK);
            mJenisProduk = bundle.getString(Constant.BUNDLE_JENIS_PRODUK);
            mKelompokProduk = bundle.getString(Constant.BUNDLE_KELOMPOK_PRODUK);

            dbHandler = new DBHandler(this);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(mTipeProduk);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbHandler = new DBHandler(this);
        }

        initRecyclerView();
        cekDataRecyclerView();
        }
    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_detail);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHandler = new DBHandler(ActivityDetail.this);
        detailList = dbHandler.getDetailProduk(mTipeProduk,mMerkProduk,mJenisProduk,mKelompokProduk);
        adapter = new DetailAdapter(detailList, ActivityDetail.this);
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


                        }
                    })
            );
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

