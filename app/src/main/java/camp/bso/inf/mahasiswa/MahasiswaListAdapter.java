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

public class MahasiswaListAdapter  extends RecyclerView.Adapter<MahasiswaListAdapter.MahasiswaViewHolder> {
    Context mContext;
    MahasiswaListOpenHelper mDB;
    private LayoutInflater mInflater;

    public MahasiswaListAdapter(Context context, MahasiswaListOpenHelper db) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDB = db;
//        this.mWordListHome = wordList1;
//        this.mWordListAway = wordList2;
    }

    @Override
    public MahasiswaListAdapter.MahasiswaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_mahasiswa, parent, false);
        return new MahasiswaViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(MahasiswaViewHolder holder, int position) {
        ItemMahasiswa current = mDB.query(position);
        holder.wordItemView1.setText(current.getNim());
        holder.wordItemView2.setText(current.getNama());

//        String mCurrent1 = mWordListHome.get(position);
//        String mCurrent2 = mWordListAway.get(position);
//        holder.wordItemView1.setText(mCurrent1);
//        holder.wordItemView2.setText(mCurrent2);
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView1;
        public final TextView wordItemView2;

        final MahasiswaListAdapter mAdapter;

        public MahasiswaViewHolder(View itemView, MahasiswaListAdapter adapter) {
            super(itemView);
            wordItemView1 = (TextView) itemView.findViewById(R.id.word1);
            wordItemView2 = (TextView) itemView.findViewById(R.id.word2);
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
