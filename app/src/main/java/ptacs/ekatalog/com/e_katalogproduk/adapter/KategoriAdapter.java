package ptacs.ekatalog.com.e_katalogproduk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Maxoto on 1/16/2018.
 */

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder> {

    private List<Produk> kategoriList = new ArrayList<>();

    public KategoriAdapter(List<Produk> kategoriList){
        this.kategoriList = kategoriList;
    }

    @Override
    public KategoriAdapter.KategoriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kategory ,parent, false);

        KategoriViewHolder KategoriViewHolder = new KategoriViewHolder(view);
        return KategoriViewHolder;
    }

    @Override
    public void onBindViewHolder(KategoriAdapter.KategoriViewHolder holder, int position) {

        holder.txt_rMerk.setText(kategoriList.get(position).getMerk_produk());
        holder.txt_rJenis.setText(kategoriList.get(position).getJenis_produk());


    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    //COMMIT MAS INDRA CS
    public Produk getItem(int Position) {
        return kategoriList.get(Position);
    }

    public static class KategoriViewHolder extends RecyclerView.ViewHolder {



        TextView txt_rMerk;
        TextView txt_rJenis;



        public KategoriViewHolder(View itemView) {
            super(itemView);

            txt_rMerk = (TextView) itemView.findViewById(R.id.txt_resultmerk);
            txt_rJenis = (TextView) itemView.findViewById(R.id.txt_resultjenis);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
