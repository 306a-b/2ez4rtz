package com.example.thinkpad.ez300k;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.thinkpad.ez300k.activity.BePopActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.example.thinkpad.ez300k.adapter.NewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getBaseContext(), BePopActivity.class);
        startActivity(intent);
    }
        /*setContentView(R.layout.activity_main);




        setContentView(R.layout.activity_main);

        dataBase = new DataBaseClient(this);
        dataBase.open();
        List<Song> values = dataBase.getAllSongs();
        list = (ListView)findViewById(R.id.list);
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);


        add.setOnClickListener(this);
        delete.setOnClickListener(this);


        ArrayList<NewsInfo> cil = new ArrayList<NewsInfo>();

        NewsInfo newsInfo = new NewsInfo("Bullet for bitch","fuck it","http://cs7062.vk.me/c7005/v7005440/132d0/jM3V0wxE9pQ.jpg","",songsList);
        newsInfo.setType(NewsInfo.CARD_TYPE.ALBUM_IMAGE);
        cil.add(newsInfo);

        //ArrayList<Song> lst2 = new ArrayList<Song>();
        //lst2.add(new Song(0,"name","artist","audiourl",""));
        //lst2.add(new Song(1, "name", "artist", "audiourl", ""));
        //NewsInfo newsInfo2 = new NewsInfo("RTZ","2ez","","https://www.google.ru/images/nav_logo242.png",lst2);
        //newsInfo2.setType(NewsInfo.CARD_TYPE.ALBUM_NO_IMAGE);
        //cil.add(newsInfo2);




        mRecyclerView = (RecyclerView) findViewById(R.id.bePopList);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NewsAdapter(getApplicationContext(), cil);
        mRecyclerView.setAdapter(mAdapter);


    }
    public void onClick(View view) {/*
        @SuppressWarnings("unchecked")
        ArrayAdapter<Song> adapter = (ArrayAdapter<Song>) list.getAdapter();
        Song song = null;
        switch (view.getId()) {
            case R.id.add:
                song = dataBase.createSong("0","1","2","3");
                textField.setText("");
                adapter.add(song);
                break;
            case R.id.delete:
                client.sharedInstance().getWhat(new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        String json = new String(((TypedByteArray) response.getBody()).getBytes());

                        try{
                            JSONObject obj = new JSONObject(json);
                            JSONArray for_array = obj.getJSONArray("bepopular");
                            Gson parser = new Gson();
                            Song[] array  = parser.fromJson(for_array.toString(), Song[].class);
                            for(int i=0;i<array.length;i++){
                                textField.setText(array[i].toString());
                            }
                            //вывод этого массива
                        } catch (Exception e){
                            textField.setText("FUCK!");
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        textField.setText(error.toString());
                    }
                });
                break;

        }

        adapter.notifyDataSetChanged();
        */
    public void update() {

    }

    public void redraw(ArrayList<Song> songArrayList) {
        //songsList.addAll(songArrayList);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        //dataBase.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //dataBase.close();
        super.onPause();
    }


}
