    package ptacs.ekatalog.com.e_katalogproduk.adapter;

    import android.content.Context;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;
    import com.github.chrisbanes.photoview.PhotoView;

    import ptacs.ekatalog.com.e_katalogproduk.R;
    import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

    import java.util.ArrayList;
    import java.util.List;
    /**
     * Created by Maxoto on 1/16/2018.
     */

    public class KelompokAdapter extends RecyclerView.Adapter<KelompokAdapter.ListViewHolder> {

        private Context mContext;

        private List<Produk> kelompokList = new ArrayList<>();

        public KelompokAdapter(List<Produk> listList, Context mContext){

            this.kelompokList = listList;
            this.mContext = mContext;

        }

        @Override
        public KelompokAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kelompok ,parent, false);

            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(KelompokAdapter.ListViewHolder holder, int position) {

            holder.txt_rKelompok.setText((kelompokList.get(position).getKelompok_produk()));
            holder.txt_rDeskripsi.setText(kelompokList.get(position).getDeskripsi_produk());
            String currentUrl = kelompokList.get(position).getFoto_produk();

            //INITIALISASI


            Glide.with(mContext)
                    .load(currentUrl)
                    .into(holder.iv_rFoto);








        }

        @Override
        public int getItemCount() {
            return kelompokList.size();
        }

        public void  setFilter(List<Produk> newList){
            kelompokList = new ArrayList<>();
            kelompokList.addAll(newList);
            notifyDataSetChanged();
        }

        //COMMIT MAS INDRA CS
        public Produk getItem(int Position) {
            return kelompokList.get(Position);
        }

        public static class ListViewHolder extends RecyclerView.ViewHolder {


            TextView txt_rKelompok;
            TextView txt_rDeskripsi;
            PhotoView iv_rFoto;



            public ListViewHolder(View itemView) {
                super(itemView);

    // TODO Txt result jenis ditampilin belum dirubah dari txt_resultkelompok -> txt_result jenis
                txt_rKelompok = (TextView) itemView.findViewById(R.id.txt_resultkelompok);
                txt_rDeskripsi = (TextView) itemView.findViewById(R.id.txt_resultdeskripsi);
                iv_rFoto = (PhotoView)itemView.findViewById(R.id.txt_resultfoto);

            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

