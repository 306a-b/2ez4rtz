package com.example.thinkpad.ez300k;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;




public class DataBaseClient {
    private SQLiteDatabase database;
    private DataBaseSongs dbHelper;

    private String[] allColumns = { DataBaseSongs.COLUMN_ID,
            DataBaseSongs.COLUMN_SONGNAME, DataBaseSongs.COLUMN_AUTHORNAME,
            DataBaseSongs.COLUMN_AUDIOURL, DataBaseSongs.COLUMN_IMAGEURL};

    public DataBaseClient(Context context) {
        dbHelper = new DataBaseSongs(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean createSong(Song song) {
        boolean isChanged = false;
        ContentValues values = new ContentValues();
        Cursor cursor = database.query(DataBaseSongs.TABLE_NAME, null,"_id = "+ song.id, null, null, null, null);
        if(cursor.getCount() <= 0) {
            isChanged = true;
            values.put(DataBaseSongs.COLUMN_ID, song.id);
            values.put(DataBaseSongs.COLUMN_SONGNAME, song.songName);
            values.put(DataBaseSongs.COLUMN_AUTHORNAME, song.artistName);
            values.put(DataBaseSongs.COLUMN_AUDIOURL, song.audioURL);
            values.put(DataBaseSongs.COLUMN_IMAGEURL, song.imageURL);

            long insertId = database.insert(dbHelper.TABLE_NAME, null,
                    values);
        }
        return isChanged;

        /*cursor = database.query(DataBaseSongs.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        Song newComment = cursorToSong(cursor);
        cursor.close();*/

        //return newComment;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> comments = new ArrayList<>();
        //long insertId = database.insert(dbHelper.TABLE_COMMENTS, null,);
        Cursor cursor = database.query(DataBaseSongs.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Song comment = cursorToSong(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        Collections.reverse(comments);
        return comments;
    }

    private Song cursorToSong(Cursor cursor) {
        Song song = new Song();
        //song.setId(cursor.getLong(0));
        song.setSong(cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_SONGNAME)),
                cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_AUTHORNAME)),
                cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_AUDIOURL)),
                cursor.getString(cursor.getColumnIndex(DataBaseSongs.COLUMN_IMAGEURL    )));
        return song;
    }
}
