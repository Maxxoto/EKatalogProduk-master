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
import ptacs.ekatalog.com.e_katalogproduk.adapter.ProdukAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

 public class ActivityKategori extends AppCompatActivity {

     private SwipeRefreshLayout swLayout;
     private LinearLayout llayout;
     private RecyclerView recyclerView;
     private LinearLayoutManager layoutManager;
     private ProdukAdapter adapter;
     private DBHandler dbHandler;
     private TextView txt_resultadapter;
     private TextView txt_judul;
     private List<Produk> produkList = new ArrayList<>();

     String mMerkProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        TextView Data1 = findViewById(R.id.txt_Data1);





        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();

            mMerkProduk = bundle.getString(Constant.BUNDLE_MERK_PRODUK); //MERK kategori
            Data1.setText(mMerkProduk);

//            dbHandler.getKategoryProduk(mMerkProduk);
//        }

        }


        }

    }




