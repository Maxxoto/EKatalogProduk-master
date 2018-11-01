package ptacs.ekatalog.com.e_katalogproduk.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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

import com.ajts.androidmads.library.ExcelToSQLite;
import com.ajts.androidmads.library.SQLiteToExcel;

import com.hypertrack.core_android_sdk.HyperTrackCore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


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
    private AlertDialog.Builder alertDialogBuilder;
    private List<Produk> produkList = new ArrayList<>();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //INITIALISASI



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        HyperTrackCore.initialize(this, "pk_d62a7cec5c6c4d026b9f7b054be36096dae95b04");



        alertDialogBuilder = new AlertDialog.Builder(this);


        initComponents();
        initRecyclerView();
        cekDataRecyclerView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            //super.onBackPressed();
            Exit();
        }
    }


    private void CheckPermission (){
    Dexter.withActivity(this)
            .withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
//            Manifest.permission.GET_ACCOUNTS,
//            Manifest.permission.ACCESS_WIFI_STATE,
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.WAKE_LOCK
                    )
                .withListener(new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
            // check if all permissions are granted
            if (report.areAllPermissionsGranted()) {
                // do you work now
                Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                //Openfiledialog
                OpenFilePicker();

            }

            // check for permanent denial of any permission
            if (report.isAnyPermissionPermanentlyDenied()) {
                // permission is denied permenantly, navigate user to app settings
                showSettingsDialog();
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
            token.continuePermissionRequest();
        }
    })

            .withErrorListener(new PermissionRequestErrorListener() {
        @Override
        public void onError(DexterError error){
            Toast.makeText(getApplicationContext(),"Error Occured !" + error.toString(),Toast.LENGTH_SHORT).show();
        }

    })
            .onSameThread()
        .check();
    }

    //TODO PERMISSION BUILDER

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenu.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);

    }

    private void Exit() {
        alertDialogBuilder.setTitle("Tutup Aplikasi");
        alertDialogBuilder
                .setMessage("Apakah anda ingin menutup aplikasi ?")
                .setCancelable(false)
                .setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityMenu.this.finish();
                            }
                        })
                .setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();

    }

    //TUTUP ONBACK
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
            CheckPermission();
            //OpenFilePicker();
        } else if (id == R.id.nav_export) {
            ExportData();
            Toast.makeText(ActivityMenu.this, "Data Berhasil di Export", Toast.LENGTH_LONG).show();
            //TODO ONCLICK EXPORT
        } else if (id == R.id.nav_purge) {
            dbHandler.hapusSemuaDataProduk();
            Toast.makeText(ActivityMenu.this, "Berhasil menghapus semua data produk", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_find){
            Intent intent = new Intent(ActivityMenu.this, ActivitySearch.class);
            startActivity(intent);

        } else if (id == R.id.nav_aboutus) {

        } else if (id == R.id.nav_contactus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //CODING RVIEW

    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.rv_produk);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dbHandler = new DBHandler(ActivityMenu.this);
        produkList = dbHandler.getSemuaProduk();
        adapter = new ProdukAdapter(produkList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void OpenFilePicker(){
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.xls$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(false) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            File file = new File(filePath);
            if (!file.exists()) {
                Toast.makeText(ActivityMenu.this, "Tidak ada File !", Toast.LENGTH_LONG).show();
                return;
            }

            ExcelToSQLite excelToSQLite = new ExcelToSQLite(getApplicationContext(), DBHandler.DATABASE_NAME, false);

            excelToSQLite.importFromFile(filePath, new ExcelToSQLite.ImportListener() {
                @Override
                public void onStart() {



                        pDialog = new ProgressDialog(ActivityMenu.this);
                        pDialog.setMessage("Sedang Mengupload");
                        pDialog.setTitle("Mohon Menunggu....");
                        pDialog.setIndeterminate(false);
                        pDialog.setCancelable(true);
                        pDialog.show();


                }

                @Override
                public void onCompleted(String dbName) {
                    pDialog.dismiss();
                    Restartapp();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ActivityMenu.this, "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void Restartapp(){
        Intent mStartActivity = new Intent(this, ActivityMenu.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }



    private void ExportData(){
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/BackupKatalog/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        // Export SQLite DB as EXCEL FILE
        SQLiteToExcel sqliteToExcel = new SQLiteToExcel(getApplicationContext(), DBHandler.DATABASE_NAME, directory_path);
        sqliteToExcel.exportAllTables("katalogbarang.xls", new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void initComponents(){
        txt_resultadapter = (TextView) findViewById(R.id.txt_resultadapter);
        txt_judul = (TextView) findViewById(R.id.txt_judul);
    }

    // FUNGSI INI UNTUK MENGECEK APAKAH ADA DATA DI DALEM RECYCLERVIEW ATAU TIDAK
    public void ClickListener(){
        recyclerView.setVisibility(View.VISIBLE);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                recyclerView, new RecyclerItemClickListener.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();

                //COMMIT MAS INDRA CS

                bundle.putString(Constant.BUNDLE_MERK_PRODUK, adapter.getItem(position).getMerk_produk());

                Intent intent = new Intent(ActivityMenu.this, ActivityKategori.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ActivityMenu.this,"Ini Long Click",Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void cekDataRecyclerView(){

        if (adapter.getItemCount() == 0) {
            txt_resultadapter.setVisibility(View.GONE);
//            txt_judul.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }else {
            txt_resultadapter.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
//            txt_judul.setVisibility(View.VISIBLE);
            ClickListener();
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
                onItemLoad();
        }
            void onItemLoad(){
                swLayout.setRefreshing(false);
        }
});
        }




        }






