package ptacs.ekatalog.com.e_katalogproduk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ptacs.ekatalog.com.e_katalogproduk.R;
import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

/**
 * Created by Maxoto on 2/14/2018.
 */

public class KelompokAdapter extends RecyclerView.Adapter<KelompokAdapter.KelompokViewHolder>  {
    private List<Produk> kelompokList = new ArrayList<>();

    public KelompokAdapter(List<Produk> kelompokList){
        this.kelompokList = kelompokList;
    }

    @Override
    public KelompokAdapter.KelompokViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kelompok ,parent, false);

        KelompokAdapter.KelompokViewHolder KelompokViewHolder = new KelompokAdapter.KelompokViewHolder(view);
        return KelompokViewHolder;
    }

    @Override
    public void onBindViewHolder(KelompokAdapter.KelompokViewHolder holder, int position) {

        holder.txt_rKelompok.setText(kelompokList.get(position).getKelompok_produk());
        holder.txt_rJenis.setText(kelompokList.get(position).getJenis_produk());


    }

    @Override
    public int getItemCount() {
        return kelompokList.size();
    }

    //COMMIT MAS INDRA CS
    public Produk getItem(int Position) {
        return kelompokList.get(Position);
    }

    public static class KelompokViewHolder extends RecyclerView.ViewHolder {



        TextView txt_rKelompok;
        TextView txt_rJenis;



        public KelompokViewHolder(View itemView) {
            super(itemView);

            txt_rKelompok = (TextView) itemView.findViewById(R.id.txt_resultkelompok);
            txt_rJenis = (TextView) itemView.findViewById(R.id.txt_resultjenis);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

