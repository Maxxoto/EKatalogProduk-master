package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Intent;
import android.os.Bundle;
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
import ptacs.ekatalog.com.e_katalogproduk.adapter.SubkelompokAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

public class ActivitySubkelompok extends AppCompatActivity {
    private SwipeRefreshLayout swLayoutsubkelompok;
    private LinearLayout llayoutsubkelompok;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SubkelompokAdapter adapter;
    private DBHandler dbHandler;
    private List<Produk> subkelompokList = new ArrayList<>();
    String mJenisProduk;
    String mMerkProduk;
    String mKelompok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subkelompok);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mJenisProduk = bundle.getString(Constant.BUNDLE_JENIS_PRODUK); //MERK kategori
            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK);
            mKelompok = bundle.getString(Constant.BUNDLE_KELOMPOK_PRODUK);
            //Toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(mKelompok);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbHandler = new DBHandler(this);
        }

        initRecyclerView();
        cekDataRecyclerView();

        //dbHandler.getKategoryProduk(mMerkProduk);
    }

    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_subkelompok);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHandler = new DBHandler(ActivitySubkelompok.this);
        subkelompokList = dbHandler.getSubkelompokProduk(mMerkProduk,mJenisProduk,mKelompok);
        adapter = new SubkelompokAdapter(subkelompokList);
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
                            bundle.putString(Constant.BUNDLE_SUBKELOMPOK_PRODUK, adapter.getItem(position).getSubkelompok_produk());

                            Intent intent = new Intent(ActivitySubkelompok.this, ActivityList.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    })
            );
        }
        swLayoutsubkelompok = (SwipeRefreshLayout) findViewById(R.id.sw_layoutsubkelompok);
        llayoutsubkelompok = (LinearLayout) findViewById(R.id.llayoutsubkelompok);

        //Mengeset warna yang berputar
        swLayoutsubkelompok.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

        //Setting Listener yang akan dijalankan saat layar difresh
        swLayoutsubkelompok.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItem();
            }
            void refreshItem(){
                initRecyclerView();
                onItemLoad();
            }
            void onItemLoad(){
                swLayoutsubkelompok.setRefreshing(false);
            }
        });
    }

}
