package com.ptit.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidhive.musicplayer.R;

public class OnlineActivity extends ListActivity {
    private String TAG = "FIREBASE";
    private static String STORE_FIREBASE_SERVER = "https://firebasestorage.googleapis.com/v0/b/musicapplication-f21a5.appspot.com/o/";

    private ListView lvSong;
    private ArrayAdapter<String> adapter;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private int currentSongIndex = 0;
    private String txtSearch;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_songs);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvSong = getListView();
        lvSong.setAdapter(adapter);


        // Getting all songs list
        Intent intent = getIntent();
        currentSongIndex = intent.getExtras().getInt("songIndex");
        txtSearch = intent.getExtras().getString("textSearch");



        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting listitem index
                int songIndex = position;
                // Starting new intent
                Intent in = new Intent();
                // Sending songIndex to PlayMusicActivity
                in.putExtra("songOnlineIndex", songIndex);
                in.putExtra("txtSearch", txtSearch);
                setResult(101, in);
                finish();
            }
        });
    }

    public void performSearch(String txtSearch) {
        SongsManager songsManager = new SongsManager();
        songsManager.readData(txtSearch, new SongsManager.MyCallback() {
            @Override
            public void onCallback(ArrayList<HashMap<String, String>> songList) {
                System.out.println("size songlist:" + songList.size());
                ListAdapter adapter = new SimpleAdapter(OnlineActivity.this, songList,
                        R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
                        R.id.songTitle });
                setListAdapter(adapter);
            }

        });
    }

}
