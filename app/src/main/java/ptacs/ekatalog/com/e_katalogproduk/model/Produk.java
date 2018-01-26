package ptacs.ekatalog.com.e_katalogproduk.model;

import android.graphics.Bitmap;
/**
 * Created by Maxoto on 1/15/2018.
 */

public class Produk {
    private int id;
    private String kd_produk;
    private String nama_produk;
    private String merk_produk;
    private String jenis_produk;
    private String variasi_produk;
    private String foto_produk;


    //TODO - INISIALISASI PERTAMA

     public Produk(String kd_produk,String nama_produk,String merk_produk, String jenis_produk,
             String variasi_produk,String foto_produk){

         this.kd_produk = kd_produk;
         this.nama_produk = nama_produk;
         this.merk_produk = merk_produk;
         this.jenis_produk = jenis_produk;
         this.variasi_produk = variasi_produk;
         this.foto_produk = foto_produk;
     }
     public int getId(){

         return id;
     }
     public void setId(int id){

         this.id = id ;
     }
    //INISIALISASI TB PRODUK


    //GET CODE TB_PRODUK
    public String getKd_produk(){return kd_produk; }
    public String getNama_produk(){return nama_produk;}
    public String getMerk_produk(){
        return merk_produk;
    }
    public String getJenis_produk(){
        return jenis_produk;
    }
    public String getVariasi_produk(){
        return variasi_produk;
    }
    public String getFoto_produk(){
        return foto_produk;
    }

    //SET CODE TB_PRODUK
    public void setKd_produk(String kd_produk){
        this.kd_produk = kd_produk;
    }
    public void setNama_produk(String nama_produk ){
        this.nama_produk = nama_produk;
    }
    public void setMerk_produk(String merk_produk ){
        this.merk_produk = merk_produk;
    }
    public void setJenis_produk(String jenis_produk){
        this.jenis_produk = jenis_produk;
    }
    public void setVariasi_produk(String variasi_produk){
        this.variasi_produk = variasi_produk ;
    }
    public void setFoto_produk (String foto_produk){
        this.foto_produk = foto_produk;
    }

}
