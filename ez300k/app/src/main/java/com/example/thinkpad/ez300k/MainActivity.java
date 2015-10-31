package com.example.thinkpad.ez300k;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thinkpad.ez300k.adapter.BePopAdapter;

import java.util.ArrayList;
import java.util.List;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private Client client;
    //String tmp = "xuy";
    //TextView text;
    DataBaseClient dataBase;
    ListView list;
    Button plus,minus;
    EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = (Button)findViewById(R.id.add);
        Button delete = (Button)findViewById(R.id.delete);
        textField = (EditText)findViewById(R.id.set);

        dataBase = new DataBaseClient(this);
        dataBase.open();
        List<Song> values = dataBase.getAllSongs();
        list = (ListView)findViewById(R.id.list);
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
    }
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Song> adapter = (ArrayAdapter<Song>) list.getAdapter();
        Song song = null;
        switch (view.getId()) {
            case R.id.add:
                song = dataBase.createSong("0","1","2","3");
                textField.setText("");
                adapter.add(song);
                break;

        }
        adapter.notifyDataSetChanged();
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

}
