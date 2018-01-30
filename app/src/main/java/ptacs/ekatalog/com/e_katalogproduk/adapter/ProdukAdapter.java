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

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {

    private List<Produk> produkList = new ArrayList<>();

    public ProdukAdapter(List<Produk> produkList){
        this.produkList = produkList;
    }

    @Override
    public ProdukAdapter.ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_produk ,parent, false);

        ProdukViewHolder produkViewHolder = new ProdukViewHolder(view);
        return produkViewHolder;
    }

    @Override
    public void onBindViewHolder(ProdukAdapter.ProdukViewHolder holder, int position) {

        holder.txt_rMerk.setText(produkList.get(position).getMerk_produk());

    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }
    
    //COMMIT MAS INDRA CS
    public Produk getItem(int Position) {
        return produkList.get(Position);
    }

    public static class ProdukViewHolder extends RecyclerView.ViewHolder {

        TextView txt_rMerk;



        public ProdukViewHolder(View itemView) {
            super(itemView);

            txt_rMerk = (TextView) itemView.findViewById(R.id.txt_resultmerk);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
