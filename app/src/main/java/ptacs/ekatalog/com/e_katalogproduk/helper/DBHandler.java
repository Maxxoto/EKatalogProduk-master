package ptacs.ekatalog.com.e_katalogproduk.helper;

/**
 * Created by Maxoto on 1/15/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.model.Produk;



public class DBHandler extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase database;
    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "db_ekatalog"; // NAMA DATABASE
    private static final String TABLE_PRODUK = "tb_produk"; // NAMA TABEL
    private static final String COLUMN_ID = "id_produk"; // NAMA KOLOM ID
    private static final String COLUMN_TIPE = "tipe_produk"; // KODE PRODUK
    private static final String COLUMN_NAMA = "nama_produk"; // NAMA KOLOM NAMA
    private static final String COLUMN_MERK = "merk_produk"; //MERK PRODUK
    private static final String COLUMN_JENIS = "jenis_produk"; //JENIS PRODUK
    private static final String COLUMN_KELOMPOK = "kelompok_produk";
    private static final String COLUMN_SUBKELOMPOK = "subkelompok_produk"; //SUBKELOMPOK PRODUK
    private static final String COLUMN_PERKEMASAN = "perkemasan_produk";
    private static final String COLUMN_STOK = "stok_produk";
    private static final String COLUMN_HARGA = "harga_produk";
    private static final String COLUMN_FOTO = "foto_produk"; // FOTO PRODUK
    private static final String COLUMN_DESC = "deskripsi_produk";

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
                + COLUMN_TIPE + " TEXT,"
                + COLUMN_NAMA + " TEXT, "
                + COLUMN_MERK + " TEXT, "
                + COLUMN_JENIS + " TEXT, "
                + COLUMN_KELOMPOK + " TEXT, "
                + COLUMN_SUBKELOMPOK + " TEXT, "
                + COLUMN_PERKEMASAN + " TEXT, "
                + COLUMN_STOK + " INTEGER, "
                + COLUMN_HARGA + " INTEGER, "
                + COLUMN_FOTO + " TEXT, "
                + COLUMN_DESC + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // FUNGSI UNTUK MENGECEK DATABASE ADA ATAU TIDAK.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUK);
        onCreate(db);
    }

    //TODO : MENAMBAHKAN DATA PADA DATABASE

    public void tambahProduk(Produk produk){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPE, produk.getTipe_produk());
        values.put(COLUMN_NAMA, produk.getNama_produk());
        values.put(COLUMN_MERK, produk.getMerk_produk());
        values.put(COLUMN_JENIS, produk.getJenis_produk());
        values.put(COLUMN_KELOMPOK, produk.getKelompok_produk());
        values.put(COLUMN_SUBKELOMPOK, produk.getSubkelompok_produk());
        values.put(COLUMN_PERKEMASAN, produk.getPerkemasan_produk());
        values.put(COLUMN_STOK, produk.getStok_produk());
        values.put(COLUMN_HARGA, produk.getHarga_produk());
        values.put(COLUMN_FOTO, produk.getFoto_produk());
        values.put(COLUMN_DESC, produk.getDeskripsi_produk());

        db.insert(TABLE_PRODUK, null, values);
        db.close();
    }

    //TODO : MENGAMBIL 1 FUNGSI DATA

    public Produk getProduk(int id_produk){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUK, new String[]{COLUMN_ID, COLUMN_TIPE ,COLUMN_NAMA, COLUMN_MERK
                ,COLUMN_JENIS,COLUMN_KELOMPOK,COLUMN_SUBKELOMPOK, COLUMN_STOK , COLUMN_HARGA , COLUMN_FOTO , COLUMN_DESC },
                COLUMN_ID + "=?", new String[]{String.valueOf(id_produk)}, null, null,null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Produk produk = new Produk(cursor.getString(1), cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
        return produk;
    }

    //TODO : FUNGSI MENGAMBIL SEMUA DATA

    public List<Produk> getSemuaProduk(){
        List<Produk> produkList = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABLE_PRODUK + " GROUP BY " + COLUMN_MERK + " ORDER BY merk_produk ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                Produk produk = new Produk(cursor.getString(1), cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                produkList.add(produk);
            } while (cursor.moveToNext());
        }
        return produkList;
    }

    //TODO : MENGHITUNG JUMLAH DATA

    public int getProdukCount(){
        String countQuery = "SELECT * FROM " + TABLE_PRODUK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    //TODO : MENGUPDATE DATA

    public int updateDataProduk(Produk produk) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPE, produk.getTipe_produk());
        values.put(COLUMN_NAMA, produk.getNama_produk());
        values.put(COLUMN_MERK, produk.getMerk_produk());
        values.put(COLUMN_JENIS, produk.getJenis_produk());
        values.put(COLUMN_KELOMPOK, produk.getKelompok_produk());
        values.put(COLUMN_SUBKELOMPOK, produk.getSubkelompok_produk());
        values.put(COLUMN_PERKEMASAN , produk.getPerkemasan_produk());
        values.put(COLUMN_STOK, produk.getStok_produk());
        values.put(COLUMN_HARGA, produk.getHarga_produk());
        values.put(COLUMN_FOTO, produk.getFoto_produk());
        values.put(COLUMN_DESC, produk.getDeskripsi_produk());

        return db.update(TABLE_PRODUK, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(produk.getId())});
    }

    //TODO : MENGHAPUS SATU DATA
    public void hapusDataProduk(Produk produk) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUK, COLUMN_ID + " = ?",
                new String[]{String.valueOf(produk.getId())});
        db.close();
    }

    //TODO : FUNGSI MENGHAPUS SEMUA DATA
    public void hapusSemuaDataProduk(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_PRODUK);
    }

    //TODO : MENGAMBIL DATA UNTUK ACTIVITY LIST KATEGORI
    public List<Produk> getKategoryProduk(String mMerkProduk) {

        List<Produk> kategoriList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK + " WHERE " + COLUMN_MERK + " =? " + " GROUP BY " + COLUMN_JENIS + " ORDER BY jenis_produk ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{ mMerkProduk } );

        if (cursor.moveToFirst()) {
            do {
                Produk kategori = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                kategoriList.add(kategori);
            } while (cursor.moveToNext());
        }
        return kategoriList;


    }


    //TODO : MENGAMBIL DATA UNTUK ACTIVITY LIST PRODUK

    public List<Produk> getListProduk(String mJenisProduk,String mMerkProduk,String mKelompokProduk,String mSubKelompokProduk) {

        List<Produk> listList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK + " WHERE " + COLUMN_JENIS + " = ? AND " + COLUMN_MERK + " = ? AND "
                + COLUMN_KELOMPOK + " = ? AND " + COLUMN_SUBKELOMPOK + " = ? "
                + " GROUP BY " + COLUMN_TIPE + " ORDER BY tipe_produk ASC " ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{ mJenisProduk,mMerkProduk,mKelompokProduk,mSubKelompokProduk } );



        if (cursor.moveToFirst()) {
            do {
                Produk list = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                listList.add(list);
            } while (cursor.moveToNext());
        }
        return listList;


    }
    //TODO : MENGAMBIL DATA UNTUK ACTIVTY DETAIL PRODUK

    public List<Produk> getDetailProduk(String mTipeProduk,String mMerkProduk, String mJenisProduk,String mKelompokProduk,String mSubKelompokProduk) {

        List<Produk> detailList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK + " WHERE " + COLUMN_TIPE + " = ? AND "
                + COLUMN_MERK + " = ? AND " + COLUMN_JENIS + " = ? AND " + COLUMN_KELOMPOK + " = ? AND "
                + COLUMN_SUBKELOMPOK + " = ? "
                + " ORDER BY nama_produk ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{mTipeProduk,mMerkProduk,mJenisProduk,mKelompokProduk,mSubKelompokProduk});

        if (cursor.moveToFirst()){
            do {
                Produk list = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                detailList.add(list);
            } while (cursor.moveToNext());
        }
            return detailList;
            }

            //TODO MENGAMBIL DATA KELOMPOK
    public List<Produk> getKelompokProduk(String mMerkProduk,String mJenisProduk) {

        List<Produk> kelompokList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK + " WHERE " + COLUMN_JENIS + " = ? AND " + COLUMN_MERK + " = ? "
                + " GROUP BY " + COLUMN_KELOMPOK + " ORDER BY kelompok_produk ASC " ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{ mJenisProduk,mMerkProduk} );

        if (cursor.moveToFirst()) {
            do {
                Produk list = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                kelompokList.add(list);
            } while (cursor.moveToNext());
        }
        return kelompokList;


    }

    public List<Produk> getSubkelompokProduk (String mMerkProduk,String mJenisProduk,String mKelompokProduk){
        List<Produk> SubkelompokList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK + " WHERE " + COLUMN_JENIS + " = ? AND " + COLUMN_MERK + " = ? AND "
                + COLUMN_KELOMPOK + " = ? "
                + " GROUP BY " + COLUMN_SUBKELOMPOK + " ORDER BY subkelompok_produk ASC " ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{ mJenisProduk,mMerkProduk,mKelompokProduk} );

        if (cursor.moveToFirst()) {
            do {
                Produk list = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                SubkelompokList.add(list);
            } while (cursor.moveToNext());
        }
        return SubkelompokList;
    }

    public List<Produk> getSearchProduk() {

        List<Produk> searchList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUK
                + " ORDER BY nama_produk ASC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,new String[]{});

        if (cursor.moveToFirst()){
            do {
                Produk list = new Produk(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6), cursor.getString(7),cursor.getString(8),cursor.getInt(9),cursor.getString(10),cursor.getString(11));
                searchList.add(list);
            } while (cursor.moveToNext());
        }
        return searchList;
    }



    public DBHandler open() throws SQLException {
        database = this.getWritableDatabase();
        return this;
    }


}


