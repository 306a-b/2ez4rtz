package com.example.thinkpad.ez300k.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.thinkpad.ez300k.Client;
import com.example.thinkpad.ez300k.DataBaseClient;
import com.example.thinkpad.ez300k.R;
import com.example.thinkpad.ez300k.Song;
import com.example.thinkpad.ez300k.adapter.BePopAdapter;
import com.example.thinkpad.ez300k.adapter.NewsAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by evgen on 31.10.2015.
 */
public class BePopActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Song> songsList;

    private Client client;
    private DataBaseClient dataBase;

    public BePopActivity() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bepop_activity_songlist);
        dataBase = new DataBaseClient(this);
        dataBase.open();

        getSongsFromBase();
        initAdapter();
        getSongsFromInternet();
    }

    private void getSongsFromBase() {
        songsList = dataBase.getAllSongs();
    }

    private void initAdapter() {
        mRecyclerView = (RecyclerView) findViewById(R.id.bePopList);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BePopAdapter(getApplicationContext(), songsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateView(ArrayList<Song> songArrayList) {
        //getSongsFromBase();
        songsList.addAll(songArrayList);
        mAdapter.notifyDataSetChanged();
    }

    private void getSongsFromInternet() {
        client.sharedInstance().getWhat(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String json = new String(((TypedByteArray) response.getBody()).getBytes());

                try {
                    JSONObject obj = new JSONObject(json);
                    JSONArray for_array = obj.getJSONArray("bepopular");
                    Gson parser = new Gson();
                    ArrayList<Song> array = new ArrayList<Song>(Arrays.asList(parser.fromJson(for_array.toString(), Song[].class)));
                    for (Song song : array) {
                        dataBase.createSong(song);
                    }
                    updateView(array);
                } catch (Exception e) {
                    Log.e("PUZDU", e.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("PUZDUu", error.toString());
            }
        });

    }

        @Override
        protected void onResume() {
            super.onResume();
        }

        @Override
        protected void onPause () {
            super.onPause();
        }
    }
