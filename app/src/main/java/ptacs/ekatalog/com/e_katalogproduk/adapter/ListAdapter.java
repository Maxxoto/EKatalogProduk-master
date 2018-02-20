    package ptacs.ekatalog.com.e_katalogproduk.adapter;

    import android.content.Context;
    import android.net.Uri;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;

    import com.bumptech.glide.Glide;

    import ptacs.ekatalog.com.e_katalogproduk.R;
    import ptacs.ekatalog.com.e_katalogproduk.activity.ActivityList;
    import ptacs.ekatalog.com.e_katalogproduk.model.Produk;

    import java.util.ArrayList;
    import java.util.List;
    /**
     * Created by Maxoto on 1/16/2018.
     */

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

        private Context mContext;

        private List<Produk> listList = new ArrayList<>();

        public ListAdapter(List<Produk> listList, Context mContext){

            this.listList = listList;
            this.mContext = mContext;

        }

        @Override
        public ListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_list ,parent, false);

            return new ListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ListAdapter.ListViewHolder holder, int position) {

            holder.txt_rTipe.setText((listList.get(position).getTipe_produk()));
            holder.txt_rDeskripsi.setText(listList.get(position).getDeskripsi_produk());
            String currentUrl = listList.get(position).getFoto_produk();


            Glide.with(mContext)
                    .load(currentUrl)
                    .into(holder.iv_rFoto);




        }

        @Override
        public int getItemCount() {
            return listList.size();
        }

        public void  setFilter(List<Produk> newList){
            listList = new ArrayList<>();
            listList.addAll(newList);
            notifyDataSetChanged();
        }

        //COMMIT MAS INDRA CS
        public Produk getItem(int Position) {
            return listList.get(Position);
        }

        public static class ListViewHolder extends RecyclerView.ViewHolder {


            TextView txt_rTipe;
            TextView txt_rDeskripsi;
            ImageView iv_rFoto;



            public ListViewHolder(View itemView) {
                super(itemView);


                txt_rTipe = (TextView) itemView.findViewById(R.id.txt_resulttipe);
                txt_rDeskripsi = (TextView) itemView.findViewById(R.id.txt_resultdeskripsi);
                iv_rFoto = (ImageView) itemView.findViewById(R.id.txt_resultfoto);
            }
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
    }

