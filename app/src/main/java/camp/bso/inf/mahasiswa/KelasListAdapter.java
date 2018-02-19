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

public class KelasListAdapter extends RecyclerView.Adapter<KelasListAdapter.KelasViewHolder> {
    Context mContext;
    KelasListOpenHelper mDB;
    private LayoutInflater mInflater;

    public KelasListAdapter(Context context, KelasListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
//        this.mMatkul = matkul;
//        this.mKelas = kelas;
    }

    @Override
    public KelasListAdapter.KelasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_kelas, parent, false);
        return new KelasListAdapter.KelasViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(KelasListAdapter.KelasViewHolder holder, int position) {
        ItemKelas current = mDB.query(position);
        holder.kelasItemViewMatkul.setText(current.getMatKul());
        holder.kelasItemViewKelas.setText(current.getKelas());

//        String mCurrent1 = mWordListHome.get(position);
//        String mCurrent2 = mWordListAway.get(position);
//        holder.wordItemView1.setText(mCurrent1);
//        holder.wordItemView2.setText(mCurrent2);
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

    public class KelasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView kelasItemViewMatkul;
        public final TextView kelasItemViewKelas;

        final KelasListAdapter mAdapter;

        public KelasViewHolder(View itemView, KelasListAdapter adapter) {
            super(itemView);
            kelasItemViewMatkul = (TextView) itemView.findViewById(R.id.matkul);
            kelasItemViewKelas = (TextView) itemView.findViewById(R.id.kelas);
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
