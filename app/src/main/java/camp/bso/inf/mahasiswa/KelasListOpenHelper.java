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

public class KelasListOpenHelper extends SQLiteOpenHelper{
    private static final String TAG = KelasListOpenHelper.class.getSimpleName();

    // Declaring all these as constants makes code a lot more readable, and looking like SQL.

    // Versions has to be 1 first time or app will crash.
    private static final int DATABASE_VERSION = 1;
    private static final String KELAS_LIST_TABLE = "class";
    private static final String DATABASE_NAME = "univ_list2";

    // Column names...
    public static final String KEY_ID = "_id";
    public static final String MATKUL = "matkul";
    public static final String KELAS = "kelas";

    // ... and a string array of columns.
    private static final String[] COLUMNS =
            {KEY_ID, MATKUL, KELAS};

    // Build the SQL query that creates the table.
    private static final String KELAS_LIST_TABLE_CREATE =
            "CREATE TABLE " + KELAS_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed
                    MATKUL + " TEXT, "+
                    KELAS + " TEXT);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public KelasListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct KelasListOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KELAS_LIST_TABLE_CREATE);
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

        String[] matkul = {"Android", "Android", "Database","Web","Web"};
        String[] kelas = {"A", "B", "A", "A", "B"};

        // Create a container for the data.
        ContentValues values = new ContentValues();

        for (int i=0; i < matkul.length;i++) {
            // Put column/value pairs into the container. put() overwrites existing values.
            values.put(MATKUL, matkul[i]);
            values.put(KELAS, kelas[i]);
            db.insert(KELAS_LIST_TABLE, null, values);
        }
    }

    /**
     * Queries the database for an entry at a given position.
     *
     * @param position The Nth row in the table.
     * @return a WordItem with the requested database entry.
     */
    public ItemKelas query(int position) {
        String query = "SELECT  * FROM " + KELAS_LIST_TABLE +
                " ORDER BY " + MATKUL + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        ItemKelas entry = new ItemKelas();

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setMatKul(cursor.getString(cursor.getColumnIndex(MATKUL)));
            entry.setKelas(cursor.getString(cursor.getColumnIndex(KELAS)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor.close();
            return entry;
        }
    }

    /**
     * Gets the number of rows in the kelas list table.
     *
     * @return The number of entries in KELAS_LIST_TABLE.
     */
    public long count() {
        if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
        return DatabaseUtils.queryNumEntries(mReadableDB, KELAS_LIST_TABLE);
    }

    /**
     * Adds a single word row/entry to the database.
     *
     * @param  matkul Matkul.
     * @param  kelas Kelas.
     * @return The id of the inserted word.
     */
    public long insert(String matkul, String kelas) {
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KELAS, matkul);
        values.put(MATKUL, kelas);
        try {
            if (mWritableDB == null) {mWritableDB = getWritableDatabase();}
            newId = mWritableDB.insert(KELAS_LIST_TABLE, null, values);
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
            values.put(MATKUL, word);
            values.put(KELAS, word);

            mNumberOfRowsUpdated = mWritableDB.update(KELAS_LIST_TABLE, //table to change
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
            deleted = mWritableDB.delete(KELAS_LIST_TABLE, //table name
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
        Log.w(KelasListOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + KELAS_LIST_TABLE);
        onCreate(db);
    }
}
