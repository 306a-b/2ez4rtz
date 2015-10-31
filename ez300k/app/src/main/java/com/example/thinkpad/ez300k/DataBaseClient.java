package com.example.thinkpad.ez300k;

import java.util.ArrayList;
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

    public Song createSong(String songName,String authorName,String audioURL,String imageURL) {
        ContentValues values = new ContentValues();
        values.put(DataBaseSongs.COLUMN_SONGNAME, songName);
        values.put(DataBaseSongs.COLUMN_AUTHORNAME, authorName);
        values.put(DataBaseSongs.COLUMN_AUDIOURL, audioURL);
        values.put(DataBaseSongs.COLUMN_IMAGEURL, imageURL);

        long insertId = database.insert(dbHelper.TABLE_NAME, null,
                values);

        Cursor cursor = database.query(DataBaseSongs.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        Song newComment = cursorToSong(cursor);
        cursor.close();
        return newComment;
    }

    public List<Song> getAllSongs() {
        List<Song> comments = new ArrayList<>();
        //long insertId = database.insert(dbHelper.TABLE_COMMENTS, null,);

        //Падает Здесь!
        Cursor cursor = database.query(DataBaseSongs.TABLE_NAME,allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Song comment = cursorToSong(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Song cursorToSong(Cursor cursor) {
        Song song = new Song();
        //song.setId(cursor.getLong(0));
        song.setSong(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return song;
    }
}
