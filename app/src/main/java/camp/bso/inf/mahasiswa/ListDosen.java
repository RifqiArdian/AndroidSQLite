package camp.bso.inf.mahasiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;

public class ListDosen extends AppCompatActivity {

    private final LinkedList<String> mDosenListNip = new LinkedList<>();
    private final LinkedList<String> mDosenListNama = new LinkedList<>();

    String nip[] = {"1234567890", "1234567890", "1234567890", "1234567890", "1234567890"};
    String nama[] = {"aaa", "bbb", "ccc" , "ddd", "eeee"};

    private DosenListOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private DosenListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dosen);

        mDB = new DosenListOpenHelper(this);

        for (int i = 0; i < nip.length; i++) {
            mDosenListNip.addLast(nip[i]);
            mDosenListNama.addLast(nama[i]);
            Log.d("DosenListNip:", mDosenListNip.getLast());
            Log.d("DosenListNama:", mDosenListNama.getLast());
        }

        //	Get	a	handle	to	the	RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //	Create	an	adapter	and	supply	the	data	to	be	displayed.
        mAdapter = new DosenListAdapter(this, mDB);

        //	Connect	the	adapter	with	the	RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        //	Give	the	RecyclerView	a	default	layout	manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
