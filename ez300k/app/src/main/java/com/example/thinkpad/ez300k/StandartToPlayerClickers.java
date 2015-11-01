package com.example.thinkpad.ez300k;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;


public class StandartToPlayerClickers implements View.OnClickListener {
    //private Song mSong;
    private ArrayList<Song> mPlaylist;
    private boolean isList;
    private Context mContext;

    public StandartToPlayerClickers(Context context, Song song) {
        this.mPlaylist = new ArrayList<>();
        this.mPlaylist.add(song);
        //this.isList = false;
        this.mContext = context;
    }

    public StandartToPlayerClickers(Context context, ArrayList<Song> mPlaylist) {
        this.mPlaylist = mPlaylist;
        //this.isList = true;
        this.mContext = context;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //play
            case R.id.playButton:
            case R.id.playSong:
                //if (!isList) {}
                Intent intent = new Intent(MediaPlayerServices.ACTION_PLAYPLAYLIST);
                Parcelable songList = Parcels.wrap(this.mPlaylist);
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaPlayerServices.PARCELABLE_PLAYLIST, songList);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startService(intent);
                break;

            case R.id.addPlaylistButton:
                break;

            //case R.id.playPlayList:
                //break;

            case R.id.addSongToPlaylist:
                break;

        }
    }
}
