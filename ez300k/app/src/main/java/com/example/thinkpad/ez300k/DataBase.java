package com.example.thinkpad.ez300k;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThinkPad on 31.10.2015.
 */
public class DataBase extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static String DB_PATH = "/data/data/linkwithweb/databases/";
    private static final  String DB_NAME = "AllTabs.db";
    private static int DB_VERSION = 1;
    public static final String TABLE_NAME = "TEST";
    private final Context myContext;
    private SQLiteStatement insertStmt;

    public DataBase(Context context){
        super(context,DB_NAME, null, DB_VERSION);
        this.myContext = context;
        this.db = getWritableDatabase();
    }
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
        } else {

            this.getReadableDatabase();

            try {
                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();

        super.close();

    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * Compiled Insert Statement
     *
     * @param name
     * @return
     */
    public long insert(String name) {
        this.insertStmt.bindString(1, name);
        return this.insertStmt.executeInsert();
    }

    /**
     *
     */
    public void deleteAll() {
        this.db.delete(TABLE_NAME, null, null);
    }

    /**
     * @return
     */
    public List selectAll() {
        List list = new ArrayList();
        Cursor cursor = this.db.query(TABLE_NAME,
                new String[] { "name" }, null, null, null, null, "name desc");
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "( " + BaseColumns._ID
                + " integer primary key autoincrement, name text not null);";
        Log.d("EventsData", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;

        String sql = null;
        if (oldVersion == 1)
            sql = "alter table " + TABLE_NAME + " add COLUMN_2 text;";
        if (oldVersion == 2)
            sql = "";

        Log.d("EventsData", "onUpgrade	: " + sql);
        if (sql != null)
            db.execSQL(sql);
    }


}
