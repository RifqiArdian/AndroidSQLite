package camp.bso.inf.mahasiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.LinkedList;

public class ListKelas extends AppCompatActivity {

    private final LinkedList<String> mKelasListMatkul = new LinkedList<>();
    private final LinkedList<String> mKelasListKelas = new LinkedList<>();

    String matkul[] = {"Android", "Android", "Database","Web","Web"};
    String kelas[] = {"A", "B", "A", "A", "C"};

    private KelasListOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private KelasListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kelas);

        mDB = new KelasListOpenHelper(this);

        for (int i = 0; i < matkul.length; i++) {
            mKelasListMatkul.addLast(matkul[i]);
            mKelasListKelas.addLast(kelas[i]);
            Log.d("KelasListMatkul:", mKelasListMatkul.getLast());
            Log.d("KelasListKelas:", mKelasListKelas.getLast());
        }

        //	Get	a	handle	to	the	RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //	Create	an	adapter	and	supply	the	data	to	be	displayed.
        mAdapter = new KelasListAdapter(this, mDB);

        //	Connect	the	adapter	with	the	RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        //	Give	the	RecyclerView	a	default	layout	manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
