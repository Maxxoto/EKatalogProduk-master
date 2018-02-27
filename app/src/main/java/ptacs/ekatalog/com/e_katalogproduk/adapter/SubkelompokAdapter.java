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
 * Created by Maxoto on 2/20/2018.
 */

public class SubkelompokAdapter extends RecyclerView.Adapter<SubkelompokAdapter.SubkelompokViewHolder>  {
    private List<Produk> subkelompokList = new ArrayList<>();

    public SubkelompokAdapter(List<Produk> subkelompokList){
        this.subkelompokList = subkelompokList;
    }

    @Override
    public SubkelompokAdapter.SubkelompokViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subkelompok ,parent, false);

        SubkelompokAdapter.SubkelompokViewHolder SubkelompokViewHolder = new SubkelompokAdapter.SubkelompokViewHolder(view);
        return SubkelompokViewHolder;
    }

    @Override
    public void onBindViewHolder(SubkelompokAdapter.SubkelompokViewHolder holder, int position) {

        holder.txt_rSubKelompok.setText(subkelompokList.get(position).getSubkelompok_produk());



    }

    @Override
    public int getItemCount() {
        return subkelompokList.size();
    }

    //COMMIT MAS INDRA CS
    public Produk getItem(int Position) {
        return subkelompokList.get(Position);
    }

    public static class SubkelompokViewHolder extends RecyclerView.ViewHolder {



        TextView txt_rSubKelompok;



        public SubkelompokViewHolder(View itemView) {
            super(itemView);

            txt_rSubKelompok = (TextView) itemView.findViewById(R.id.txt_resultsubkelompok);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

