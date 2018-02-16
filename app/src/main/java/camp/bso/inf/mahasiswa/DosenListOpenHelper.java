package camp.bso.inf.mahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MUL-11 on 2/8/2018.
 */

public class DosenListOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DosenListOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String DOSEN_LIST_TABLE = "dosen";
    private static final String DATABASE_NAME = "univ_list";

    // Column names...
    public static final String KEY_ID = "_id";
    public static final String NIP = "nip";
    public static final String NAMA = "nama";

    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_ID, NIP, NAMA};

    // Build the SQL query that creates the table.
    private static final String DOSEN_LIST_TABLE_CREATE =
            "CREATE TABLE " + DOSEN_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    NIP + " TEXT, "+
                    NAMA + " TEXT);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DosenListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct DosenListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DOSEN_LIST_TABLE_CREATE);
        fillDatabaseWithData(db);
        // We cannot initialize mWritableDB and mReadableDB here, because this creates an infinite
        // loop of on Create being repeatedly called.
    }

    /**
     * Adds the initial data set to the database.
     * According to the docs, onCreate for the open helper does not run on the UI thread.
     *
     * @param db Database to fill with data since the member variables are not initialized yet.
     */
    public void fillDatabaseWithData(SQLiteDatabase db) {

        String[] nip = {"1234567890", "1234567890", "1234567890","1234567890","1234567890"};
        String[] nama = {"Gita", "Mugni", "Rifqi", "Ardian", "Nugraha"};

        // Create a container for the data.
        ContentValues values = new ContentValues();

        for (int i=0; i < nip.length;i++) {
            // Put column/value pairs into the container. put() overwrites existing values.
            values.put(NIP, nip[i]);
            values.put(NAMA, nama[i]);
            db.insert(DOSEN_LIST_TABLE, null, values);
        }
    }

    /**
     * Queries the database for an entry at a given position.
     *
     * @param position The Nth row in the table.
     * @return a WordItem with the requested database entry.
     */
    public ItemDosen query(int position) {
        String query = "SELECT  * FROM " + DOSEN_LIST_TABLE +
                " ORDER BY " + NIP + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        ItemDosen entry = new ItemDosen();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setNip(cursor.getString(cursor.getColumnIndex(NIP)));
            entry.setNama(cursor.getString(cursor.getColumnIndex(NAMA)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    /**
     * Gets the number of rows in the word list table.
     *
     * @return The number of entries in WORD_LIST_TABLE.
     */
    public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, DOSEN_LIST_TABLE);
    }

    /**
     * Adds a single word row/entry to the database.
     *
     * @param  nip Nip.
     * @param  nama Nama.
     * @return The id of the inserted word.
     */
    public long insert(String nip, String nama) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(NIP, nip);
        values.put(NAMA, nama);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(DOSEN_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    /**
     * Updates the word with the supplied id to the supplied value.
     *
     * @param id Id of the word to update.
     * @param word The new value of the word.
     * @return The number of rows affected or -1 of nothing was updated.
     */
    public int update(int id, String word) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            ContentValues values = new ContentValues();
            values.put(NIP, word);
            values.put(NAMA, word);

            mNumberOfRowsUpdated = mWritableDB.update(DOSEN_LIST_TABLE, //table to change
                    values, // new values to insert
                    KEY_ID + " = ?", // selection criteria for row (in this case, the _id column)
                    new String[]{String.valueOf(id)}); //selection args; the actual value of the id

        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    /**
     * Deletes one entry identified by its id.
     *
     * @param id ID of the entry to delete.
     * @return The number of rows deleted. Since we are deleting by id, this should be 0 or 1.
     */
    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            deleted = mWritableDB.delete(DOSEN_LIST_TABLE, //table name
                    KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
        return deleted;
    }

    /**
     * Called when a database needs to be upgraded. The most basic version of this method drops
     * the tables, and then recreates them. All data is lost, which is why for a production app,
     * you want to back up your data first. If this method fails, changes are rolled back.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DosenListOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DOSEN_LIST_TABLE);
        onCreate(db);
    }
}
