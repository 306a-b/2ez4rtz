package com.example.thinkpad.ez300k.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.example.thinkpad.ez300k.Client;
import com.example.thinkpad.ez300k.Group;
import com.example.thinkpad.ez300k.IntToSend;
import com.example.thinkpad.ez300k.R;
import com.example.thinkpad.ez300k.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class VkGroupActivity extends AppCompatActivity
        implements View.OnClickListener{
    private Client client;
    boolean is_client = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page);
        /*
        client.sharedInstance().getGroupInfo(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                String json = new String(((TypedByteArray) response.getBody()).getBytes());
                try {

                    JSONObject obj = new JSONObject(json);
                    JSONArray for_array = obj.getJSONArray("feed");
                    Gson parser = new Gson();
                    ArrayList<Group> array = new ArrayList<>(Arrays.asList(parser.fromJson(for_array.toString(), Group[].class)));
                    //array[0].

                    //вывод группы
                } catch (Exception e) {

                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        */
        Button follow = (Button) findViewById(R.id.followButton);
        follow.setOnClickListener(this);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Песни");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Альбомы");
        tabSpec.setContent(R.id.tab2);

        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Информация");
        tabSpec.setContent(R.id.tab3);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag2");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.followButton:
                //client.sharedInstance().postSubscribe(new IntToSend(1));
                break;
        }
    }
}
