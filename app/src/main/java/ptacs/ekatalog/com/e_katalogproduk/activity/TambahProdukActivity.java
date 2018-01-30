package ptacs.ekatalog.com.e_katalogproduk.activity;

        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import ptacs.ekatalog.com.e_katalogproduk.R;
        import ptacs.ekatalog.com.e_katalogproduk.adapter.ProdukAdapter;
        import ptacs.ekatalog.com.e_katalogproduk.helper.DBHandler;
        import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

        import java.util.List;

public class TambahProdukActivity extends AppCompatActivity {

    private EditText et_kd;
    private EditText et_nama;
    private EditText et_merk;
    private EditText et_jenis;
    private EditText et_variasi;
    private EditText et_foto;
    private Button button_tambahdata;

    private DBHandler dbHandler;
    private ProdukAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk);

        dbHandler = new DBHandler(this);
        initComponents();
    }

    private void initComponents(){
        et_kd = (EditText) findViewById(R.id.et_kd);
        et_nama = (EditText) findViewById(R.id.et_nama);
        et_merk = (EditText) findViewById(R.id.et_merk);
        et_jenis = (EditText) findViewById(R.id.et_jenis);
        et_variasi = (EditText) findViewById(R.id.et_variasi);
        et_foto = (EditText) findViewById(R.id.et_foto);

        button_tambahdata = (Button) findViewById(R.id.button_tambahdata);

        button_tambahdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiForm();
                //Snackbar.make( v ,"Data Berhasil Ditambahkan",Snackbar.LENGTH_LONG)
                 //       .setAction("Action",null).show();

            }
        });
    }

    // FUNGSI INI UNTUK MEMVALIDASI FORM JIKA ADA YANG KOSONG ATAU TIDAK
    // LALU DILANJUT UNTUK MENJALANKAN PERINTAH SELANJUTNYA
    private void validasiForm(){
        String form_kd = et_kd.getText().toString();
        String form_nama = et_nama.getText().toString();
        String form_merk = et_merk.getText().toString();
        String form_jenis = et_jenis.getText().toString();
        String form_variasi = et_variasi.getText().toString();
        String form_foto = et_foto.getText().toString();

        if (form_nama.isEmpty()){
            et_nama.setError("Tolong Lengkapi Data Terlebih Dahulu");
            et_nama.requestFocus();
        } if (form_kd.isEmpty()){
            et_kd.setError("Tolong Lengkapi Data Terlebih Dahulu");
            et_kd.requestFocus();
        } if (form_merk.isEmpty()){
            et_merk.setError("Tolong Lengkapi Data Terlebih Dahulu");
            et_merk.requestFocus();
        } if (form_jenis.isEmpty()){
            et_jenis.setError("Tolong Lengkapi Data Terlebih Dahulu");
            et_jenis.requestFocus();
        } if (form_variasi.isEmpty()){
            et_variasi.setError("Tolong Lengkapi Data Terlebih Dahulu");
            et_variasi.requestFocus();
        } if (form_foto.isEmpty()){
            et_foto.setError("Tolong Lengkapi Data Terlebih Dahulu");
            et_foto.requestFocus();
        } else {
            dbHandler.tambahProduk(new Produk(form_kd, form_nama,form_merk,form_jenis,form_variasi,form_foto));
            List<Produk> produkList = dbHandler.getSemuaProduk();
            adapter = new ProdukAdapter(produkList);
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();;

            //MENGOSONGKAN FORM
            et_kd.setText("");
            et_nama.setText("");
            et_merk.setText("");
            et_jenis.setText("");
            et_variasi.setText("");
            et_foto.setText("");


        }
    }

}

