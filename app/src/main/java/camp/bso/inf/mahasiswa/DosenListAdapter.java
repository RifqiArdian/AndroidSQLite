package camp.bso.inf.mahasiswa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MUL-11 on 2/8/2018.
 */

public class DosenListAdapter extends RecyclerView.Adapter<DosenListAdapter.DosenViewHolder>{
    Context mContext;
    DosenListOpenHelper mDB;
    private LayoutInflater mInflater;

    public DosenListAdapter(Context context, DosenListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
//        this.mNip = nip;
//        this.mNama = nama;
    }

    @Override
    public DosenListAdapter.DosenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_dosen, parent, false);
        return new DosenViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(DosenViewHolder holder, int position) {
        ItemDosen current = mDB.query(position);
        holder.dosenItemViewNip.setText(current.getNip());
        holder.dosenItemViewNama.setText(current.getNama());

//        String mCurrent1 = mWordListHome.get(position);
//        String mCurrent2 = mWordListAway.get(position);
//        holder.wordItemView1.setText(mCurrent1);
//        holder.wordItemView2.setText(mCurrent2);
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

    public class DosenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView dosenItemViewNip;
        public final TextView dosenItemViewNama;

        final DosenListAdapter mAdapter;

        public DosenViewHolder(View itemView, DosenListAdapter adapter) {
            super(itemView);
            dosenItemViewNip = (TextView) itemView.findViewById(R.id.nip);
            dosenItemViewNama = (TextView) itemView.findViewById(R.id.nama);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            //	Get	the	position	of	the	item	that	was	clicked.
//            int mPosition = getLayoutPosition();
//
//            //	Use	that	to	access	the	affected	item	in	mWordList.
//            String element = mWordListHome.get(mPosition);
//
//            //	Change	the	word	in	the	mWordList.
//            mWordListHome.set(mPosition, "Clicked!	" + element);
//
//            //	Notify	the	adapter,	that	the	data	has	changed	so	it	can
//            //	update	the	RecyclerView	to	display	the	data.
//            mAdapter.notifyDataSetChanged();
        }
    }

}
