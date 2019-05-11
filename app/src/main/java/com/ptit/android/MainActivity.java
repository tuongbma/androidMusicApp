package com.ptit.android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.androidhive.musicplayer.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
    private Button  btnOffline;
    private ImageButton btnOnline;
    private ListView lvSearch;
    private SongsManager songsManager = new SongsManager();

    private ArrayAdapter<String> adapter;

    private EditText edtSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnOnline = (ImageButton) findViewById(R.id.btnSearch);
        btnOffline = (Button) findViewById(R.id.btnOffline);
        edtSearch = (EditText) findViewById(R.id.searchText);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvSearch = getListView();
        lvSearch.setAdapter(adapter);
        btnOffline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);
                startActivity(intent);
            }
        });

        btnOnline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String txtSearch = edtSearch.getText().toString();
                if(txtSearch != null && !txtSearch.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
                    intent.putExtra("txtSearch", txtSearch);
                    startActivity(intent);
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(edtSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void performSearch(String txtSearch) {
        SongsManager songsManager = new SongsManager();
        songsManager.readData(txtSearch, new SongsManager.MyCallback() {
            @Override
            public void onCallback(ArrayList<HashMap<String, String>> songList) {
                System.out.println("size songlist:" + songList.size());
                ListAdapter adapter = new SimpleAdapter(MainActivity.this, songList,
                        R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
                        R.id.songTitle });
                setListAdapter(adapter);
            }

        });
    }
}
