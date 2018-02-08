package camp.bso.inf.mahasiswa;

/**
 * Created by MUL-11 on 2/8/2018.
 */

public class ItemMahasiswa {
    private int mId;
    private String Nim;
    private String Nama;

    public ItemMahasiswa() {}


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getNim() {
        return Nim;
    }

    public void setNim(String Nim) {
        this.Nim = Nim;
    }

    public String getNama() {return Nama; }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }
}
