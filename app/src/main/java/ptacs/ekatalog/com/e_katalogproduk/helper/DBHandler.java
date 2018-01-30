package ptacs.ekatalog.com.e_katalogproduk.helper;

/**
 * Created by Maxoto on 1/15/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "db_ekatalog"; // NAMA DATABASE
    private static final String TABLE_PRODUK = "tb_produk"; // NAMA TABEL
    private static final String COLUMN_ID = "id_produk"; // NAMA KOLOM ID
    private static final String COLUMN_KD = "kd_produk"; // KODE PRODUK
    private static final String COLUMN_NAMA = "nama_produk"; // NAMA KOLOM NAMA
    private static final String COLUMN_MERK = "merk_produk"; //MERK PRODUK
    private static final String COLUMN_JENIS = "jenis_produk"; //JENIS PRODUK
    private static final String COLUMN_VARIASI = "variasi_produk"; //VARIASI PRODUK
    private static final String COLUMN_FOTO = "foto_produk"; // FOTO PRODUK

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // TODO : LANJUT SECTION 2
    // FUNGSI UNTUK MEMBUAT DATABASENYA
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE "
                + TABLE_PRODUK +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_KD + " TEXT,"
                + COLUMN_NAMA + " TEXT, "
                + COLUMN_MERK + " TEXT, "
                + COLUMN_JENIS + " TEXT, "
                + COLUMN_VARIASI + " TEXT, "
                + COLUMN_FOTO + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // FUNGSI UNTUK MENGECEK DATABASE ADA ATAU TIDAK.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUK);
        onCreate(db);
    }

    // FUNGSI UNTUK TAMBAH DATA PRODUK
    public void tambahProduk(Produk produk){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_KD, produk.getKd_produk());
        values.put(COLUMN_NAMA, produk.getNama_produk());
        values.put(COLUMN_MERK, produk.getMerk_produk());
        values.put(COLUMN_JENIS, produk.getJenis_produk());
        values.put(COLUMN_VARIASI, produk.getVariasi_produk());
        values.put(COLUMN_FOTO, produk.getFoto_produk());

        db.insert(TABLE_PRODUK, null, values);
        db.close();
    }

    // FUNGSI UNTUK AMBIL 1 DATA PRODUK
    public Produk getProduk(int id_produk){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUK, new String[]{COLUMN_ID, COLUMN_KD ,COLUMN_NAMA, COLUMN_MERK
                ,COLUMN_JENIS, COLUMN_VARIASI , COLUMN_FOTO },
                COLUMN_ID + "=?", new String[]{String.valueOf(id_produk)}, null, null,null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Produk produk = new Produk(cursor.getString(1), cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6));
        return produk;
    }

    // FUNGSI UNTUK AMBIL SEMUA DATA PRODUK
    public List<Produk> getSemuaProduk(){
        List<Produk> produkList = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABLE_PRODUK + " GROUP BY " + COLUMN_MERK + " ORDER BY merk_produk ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Produk produk = new Produk(cursor.getString(1), cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6));
                produkList.add(produk);
            } while (cursor.moveToNext());
        }
        return produkList;
    }

    // FUNGSI MENGHITUNG ADA BEBERAPA DATA
    public int getProdukCount(){
        String countQuery = "SELECT * FROM " + TABLE_PRODUK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    // FUNGSI UPDATE DATA PRODUK
    public int updateDataProduk(Produk produk) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_KD, produk.getKd_produk());
        values.put(COLUMN_NAMA, produk.getNama_produk());
        values.put(COLUMN_MERK, produk.getMerk_produk());
        values.put(COLUMN_JENIS, produk.getJenis_produk());
        values.put(COLUMN_VARIASI, produk.getVariasi_produk());
        values.put(COLUMN_FOTO, produk.getFoto_produk());

        return db.update(TABLE_PRODUK, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(produk.getId())});
    }

    // FUNGSI HAPUS DATA 1 PRODUK
    public void hapusDataProduk(Produk produk) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUK, COLUMN_ID + " = ?",
                new String[]{String.valueOf(produk.getId())});
        db.close();
    }

    // FUNGSI UNTUK MENGHAPUS SEMUA DATA PRODUK
    public void hapusSemuaDataProduk(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_PRODUK);
    }

    //FUNGSI MENGAMBIL DATA WHERE DI ACTIVITY KATEGORY
    public List<Produk> getKategoryProduk(String mMerkProduk) {

        List<Produk> kategoriList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK + " WHERE " + COLUMN_MERK + " =? " ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{ mMerkProduk } );

        if (cursor.moveToFirst()) {
            do {
                Produk kategori = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6));
                kategoriList.add(kategori);
            } while (cursor.moveToNext());
        }
        return kategoriList;


    }

}


