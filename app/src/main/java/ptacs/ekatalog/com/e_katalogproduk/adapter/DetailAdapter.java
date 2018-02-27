package ptacs.ekatalog.com.e_katalogproduk.adapter;

import android.content.Context;
import java.text.NumberFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;
import ptacs.ekatalog.com.e_katalogproduk.activity.ActivityDetail;


/**
 * Created by Maxoto on 2/9/2018.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private Context mContext;

    private List<Produk> detailList = new ArrayList<>();

    public DetailAdapter(List<Produk> detailList, Context mContext){

        this.detailList = detailList;
        this.mContext = mContext;

    }

    @Override
    public DetailAdapter.DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_detail ,parent, false);

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailAdapter.DetailViewHolder holder, int position) {

        holder.txt_rTipe.setText((detailList.get(position).getTipe_produk()));
        holder.txt_rNama.setText(detailList.get(position).getNama_produk());
        holder.txt_rMerk.setText(detailList.get(position).getMerk_produk());
        holder.txt_rJenis.setText(detailList.get(position).getJenis_produk());
        holder.txt_rStok.setText(detailList.get(position).getStok_produk());
        holder.txt_rPerkemasan.setText(detailList.get(position).getPerkemasan_produk());

//        holder.txt_rHarga.setText(detailList.get(position).getHarga_produk());

        Locale localeID = new Locale("in","ID");

        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.txt_rHarga.setText(formatRupiah.format((double)detailList.get(position).getHarga_produk()));


//        String currentUrl = detailList.get(position).getFoto_produk();


//        Glide.with(mContext)
//                .load(currentUrl)
//                .into(holder.iv_rFoto);




    }
    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public void  setFilter(List<Produk> searchList){
        detailList = new ArrayList<>();
        detailList.addAll(searchList);
        notifyDataSetChanged();
    }

    public Produk getItem(int Position) {
        return detailList.get(Position);
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {


        TextView txt_rTipe;
        TextView txt_rNama;
        TextView txt_rMerk;
        TextView txt_rJenis;
        TextView txt_rStok;
        TextView txt_rHarga;
        TextView txt_rPerkemasan;
//        ImageView iv_rFoto;



        public DetailViewHolder(View itemView) {
            super(itemView);


            txt_rTipe = (TextView) itemView.findViewById(R.id.txt_resulttipe);
            txt_rNama = (TextView) itemView.findViewById(R.id.txt_resultnama);
            txt_rMerk = (TextView) itemView.findViewById(R.id.txt_resultmerk);
            txt_rJenis = (TextView) itemView.findViewById(R.id.txt_resultjenis);
            txt_rStok  = (TextView) itemView.findViewById(R.id.txt_resultstok);
            txt_rHarga = (TextView) itemView.findViewById(R.id.txt_resultharga);
            txt_rPerkemasan = (TextView) itemView.findViewById(R.id.txt_resultkemasan);
//            iv_rFoto = (ImageView) itemView.findViewById(R.id.IvFoto);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
