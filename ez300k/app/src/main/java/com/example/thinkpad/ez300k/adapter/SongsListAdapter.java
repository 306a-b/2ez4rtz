package com.example.thinkpad.ez300k.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.thinkpad.ez300k.R;
import com.example.thinkpad.ez300k.Song;

import java.util.ArrayList;

public class SongsListAdapter extends ArrayAdapter<Song> {
    private ArrayList<Song> mData;

    public SongsListAdapter(Context context, ArrayList<Song> values) {
        super(context, 0, values);
    }



    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Song songLine = getItem(pos);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.song_line, parent, false);
            TextView songsNameButton = (TextView) convertView.findViewById(R.id.playSong);
            songsNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("LOG", ((TextView)v).getText().toString());

                }
            });
        }

        TextView songsNameButton = (TextView) convertView.findViewById(R.id.playSong);
        songsNameButton.setText(songLine.songName);
        //songsNameButton.getText()

        return convertView;
    }
}
