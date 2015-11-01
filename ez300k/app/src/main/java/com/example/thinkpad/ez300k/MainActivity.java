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
import com.example.thinkpad.ez300k.activity.VkGroupActivity;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<Song> songsList;

    private Client client;
    private DataBaseClient dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getBaseContext(), BePopActivity.class);
        startActivity(intent);

          //Intent intent = new Intent(getApplicationContext(), MediaPlayerServices.class);
          //startActivity(intent);


        setContentView(R.layout.activity_main);
        //Button add = (Button)findViewById(R.id.add);
        //Button delete = (Button)findViewById(R.id.delete);
        //textField = (EditText)findViewById(R.id.set);
        //setContentView(R.layout.activity_main);

        dataBase = new DataBaseClient(this);
        dataBase.open();
        ArrayList<Song> values = new ArrayList<>();
        values.add(new Song("V","","",""));
        values.add(new Song("No Way Out","","",""));
        values.add(new Song("Army of Noise","","",""));
        values.add(new Song("Worthless","","",""));
        values.add(new Song("Skin","","",""));
        values.add(new Song("Broken","","",""));

        ArrayList<NewsInfo> cil = new ArrayList<NewsInfo>();

        NewsInfo newsInfo = new NewsInfo("Bullet for My Valentine", "Venom", "https://upload.wikimedia.org/wikipedia/en/4/43/BFMV_Venom.jpg", "", values);
        newsInfo.setType(NewsInfo.CARD_TYPE.ALBUM_IMAGE);
        cil.add(newsInfo);

        values = new ArrayList<>();
        values.add(new Song("Flow up","","",""));
        values.add(new Song("Life is a credit card","","",""));
        values.add(new Song("Hi this world", "", "", ""));
        values.add(new Song("This evening","","",""));

        newsInfo = new NewsInfo("Mark Crotf", "Evening Flood", "http://s3.amazonaws.com/content.sitezoogle.com/u/170447/f0f986203a3d8b758dffb5d79d2101e7fa6a684f/large/evening-flood-cover-hi-res.jpg?1434031833", "", values);
        newsInfo.setType(NewsInfo.CARD_TYPE.ALBUM_IMAGE);
        cil.add(newsInfo);

        values = new ArrayList<>();
        values.add(new Song("мы старались","","",""));
        values.add(new Song("это было не легко", "", "", ""));
        values.add(new Song("и это пасхалка","","",""));


        newsInfo = new NewsInfo("Чахохбили не промахнется", "Урок всей жизни", "https://www.google.ru/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png", "", values);
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

    /*public void onClick(View view) {
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

    public void update() {

    }

    public void redraw(ArrayList<Song> songArrayList) {
        //songsList.addAll(songArrayList);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        dataBase.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataBase.close();
        super.onPause();
    }
    */

}
