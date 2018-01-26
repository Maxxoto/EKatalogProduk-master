package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.adapter.ProdukAdapter;
import ptacs.ekatalog.com.e_katalogproduk.helper.Constant;
import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
import ptacs.ekatalog.com.e_katalogproduk.helper.RecyclerItemClickListener;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

public class ActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SwipeRefreshLayout swLayout;
    private LinearLayout llayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ProdukAdapter adapter;
    private DBHandler dbHandler;
    private TextView txt_resultadapter;
    private TextView txt_judul;
    private List<Produk> produkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //INITIALISASI


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initComponents();
        initRecyclerView();
        cekDataRecyclerView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_tambahdata);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, TambahProdukActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_import) {

        } else if (id == R.id.nav_export) {

        } else if (id == R.id.nav_purge) {
            dbHandler.hapusSemuaDataProduk();
            Toast.makeText(ActivityMenu.this,"Berhasil menghapus semua produk",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_aboutus) {

        } else if (id == R.id.nav_contactus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //CODING RVIEW

    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.rv_produk);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dbHandler = new DBHandler(ActivityMenu.this);
        produkList = dbHandler.getSemuaProduk();
        adapter = new ProdukAdapter(produkList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initComponents(){
        txt_resultadapter = (TextView) findViewById(R.id.txt_resultadapter);
        txt_judul = (TextView) findViewById(R.id.txt_judul);
    }

    // FUNGSI INI UNTUK MENGECEK APAKAH ADA DATA DI DALEM RECYCLERVIEW ATAU TIDAK
    private void cekDataRecyclerView(){

        if (adapter.getItemCount() == 0){
            txt_resultadapter.setVisibility(View.VISIBLE);
            txt_judul.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txt_resultadapter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);txt_judul.setVisibility(View.VISIBLE);

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // TODO Handle item click

                                Bundle bundle = new Bundle();

                                //COMMIT MAS INDRA CS

                                bundle.putString(Constant.BUNDLE_MERK_PRODUK, adapter.getItem(position).getMerk_produk());
                                //
                                //
                                //
                                //
                                //

                                Intent intent = new Intent(ActivityMenu.this, ActivityKategori.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                        }
                    })
            );
        }


    //

    //Initialisasi SwipeRefreshL
    swLayout = (SwipeRefreshLayout) findViewById(R.id.sw_layout);
    llayout = (LinearLayout) findViewById(R.id.ll_Layout);
    txt_resultadapter = (TextView) findViewById(R.id.txt_resultadapter);

    //Mengeset warna yang berputar
        swLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);

    //Setting Listener yang akan dijalankan saat layar difresh
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshItem();
        }
            void refreshItem(){
                initRecyclerView();
                cekDataRecyclerView();
                onItemLoad();
        }
            void onItemLoad(){
                swLayout.setRefreshing(false);
        }
});
        }


        }






