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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.KategoriAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

 public class ActivityKategori extends AppCompatActivity {

     private SwipeRefreshLayout swLayout2;
     private LinearLayout llayout2;
     private RecyclerView recyclerView;
     private LinearLayoutManager layoutManager;
     private KategoriAdapter adapter;
     private DBHandler dbHandler;
     private List<Produk> kategoriList = new ArrayList<>();
     String mMerkProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);


        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK); //MERK kategori

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(mMerkProduk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHandler = new DBHandler(this);
        }

        initRecyclerView();
        cekDataRecyclerView();

        //dbHandler.getKategoryProduk(mMerkProduk);
        }
     @Override
     public boolean onSupportNavigateUp() {
         onBackPressed();
         return true;
     }
     private void initRecyclerView(){

         recyclerView = (RecyclerView) findViewById(R.id.rv_kategori);
         recyclerView.setHasFixedSize(true);
         layoutManager = new LinearLayoutManager(this);
         recyclerView.setLayoutManager(layoutManager);
         dbHandler = new DBHandler(ActivityKategori.this);
         kategoriList = dbHandler.getKategoryProduk(mMerkProduk);
         //kategoriList = dbHandler.getKategoryProduk();
         adapter = new KategoriAdapter(kategoriList);
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
                     Bundle bundle = new Bundle();

                     //COMMIT MAS INDRA CS

                     bundle.putString(Constant.BUNDLE_JENIS_PRODUK, adapter.getItem(position).getJenis_produk());
                     bundle.putString(Constant.BUNDLE_MERK_PRODUK,adapter.getItem(position).getMerk_produk());

                     Intent intent = new Intent(ActivityKategori.this, ActivityKelompok.class);
                     intent.putExtras(bundle);
                     startActivity(intent);
                 }

                 @Override
                 public void onItemLongClick(View view, int position) {
                     Toast.makeText(ActivityKategori.this,"Ini Long Click",Toast.LENGTH_SHORT).show();
                 }
             }));
         }

         swLayout2 = (SwipeRefreshLayout) findViewById(R.id.sw_layout2);
         llayout2 = (LinearLayout) findViewById(R.id.ll_Layout);

         //Mengeset warna yang berputar
         swLayout2.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

         //Setting Listener yang akan dijalankan saat layar difresh
         swLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                 swLayout2.setRefreshing(false);
             }
         });
     }

     }





