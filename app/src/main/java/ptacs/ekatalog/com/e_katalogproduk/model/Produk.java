package ptacs.ekatalog.com.e_katalogproduk.model;

/**
 * Created by Maxoto on 1/15/2018.
 */

public class Produk {
    private int id;

    private String kode_produk;
    private String kode2_produk;
    private String nama_produk;
    private String merk_produk;
    private String jenis_produk;
    private String kelompok_produk;
    private String perkemasan_produk;
    private String stok_produk;
    private String stok2_produk;
    private int harga_produk;
    private String foto_produk;
    private String deskripsi_produk;


    //TODO - INISIALISASI PERTAMA

     public Produk(String kode_produk,String kode2_produk,String nama_produk,String merk_produk, String jenis_produk,
             String kelompok_produk,String perkemasan_produk,String stok_produk,String stok2_produk,int harga_produk,String foto_produk,String deskripsi_produk){


         this.kode_produk = kode_produk;
         this.kode2_produk = kode2_produk;
         this.nama_produk = nama_produk;
         this.merk_produk = merk_produk;
         this.jenis_produk = jenis_produk;
         this.kelompok_produk = kelompok_produk;
         this.perkemasan_produk = perkemasan_produk;
         this.stok_produk = stok_produk;
         this.stok2_produk = stok2_produk;
         this.harga_produk = harga_produk;
         this.foto_produk = foto_produk;
         this.deskripsi_produk = deskripsi_produk;
     }
     public int getId(){

         return id;
     }
     public void setId(int id){

         this.id = id ;
     }
    //INISIALISASI TB PRODUK


    //GET CODE TB_PRODUK

    public String getKode_produk() {return kode_produk;}
    public String getKode2_produk() {return kode2_produk;}
    public String getNama_produk(){return nama_produk;}
    public String getMerk_produk(){ return merk_produk; }
    public String getJenis_produk(){
        return jenis_produk;
    }
    public String getKelompok_produk(){ return kelompok_produk; }
    public String getPerkemasan_produk(){return perkemasan_produk;}
    public String getStok_produk(){return stok_produk;}
    public String getStok2_produk(){return stok2_produk;}
    public int getHarga_produk(){return harga_produk;}
    public String getFoto_produk(){
        return foto_produk;
    }
    public String getDeskripsi_produk() { return deskripsi_produk;}

    //SET CODE TB_PRODUK


    public void setKode_produk(String kode_produk) { this.kode_produk = kode_produk;}
    public void setKode2_produk(String kode2_produk) { this.kode2_produk = kode2_produk;}
    public void setNama_produk(String nama_produk ){ this.nama_produk = nama_produk; }
    public void setMerk_produk(String merk_produk ){
        this.merk_produk = merk_produk;
    }
    public void setJenis_produk(String jenis_produk){
        this.jenis_produk = jenis_produk;
    }
    public void setKelompok_produk(String kelompok_produk){ this.kelompok_produk = kelompok_produk; }

    public void setPerkemasan_produk(String perkemasan_produk){this.perkemasan_produk = perkemasan_produk;}
    public void setStok_produk(String stok_produk){
        this.stok_produk = stok_produk ;
    }
    public void setStok2_produk(String stok2_produk){
        this.stok2_produk = stok2_produk ;
    }
    public void setHarga_produk(int harga_produk){
        this.harga_produk = harga_produk ;
    }
    public void setFoto_produk (String foto_produk){
        this.foto_produk = foto_produk;
    }
    public void setDeskripsi_produk(String deskripsi_produk){this.deskripsi_produk = deskripsi_produk;}
}
