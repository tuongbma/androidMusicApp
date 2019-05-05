package com.ptit.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidhive.musicplayer.R;

public class OnlineActivity extends ListActivity {
    // Songs list
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list_songs);

//		final ArrayList<HashMap<String, String>> listOffline = new ArrayList<HashMap<String, String>>();

        SongsManager songsManager = new SongsManager();
        // get all songs from sdcard
        Intent receivedIntent = new Intent();
        String searchQuery = receivedIntent.getStringExtra("searchQuery");
        this.songsList = songsManager.getOnlineList(searchQuery);
        if (songsList.size() == 0){
            toastMessage("Khong co bai hat nao");
        }
        else{
            ListAdapter adapter = new SimpleAdapter(this, songsList,
                    R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
                    R.id.songTitle });

            setListAdapter(adapter);

            // selecting single ListView item
            ListView lv = getListView();
            // listening to single listitem click
            lv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // getting listitem index
                    int songIndex = position;

                    // Starting new intent
                    Intent in = new Intent();
                    // Sending songIndex to PlayerMusicActivity
                    in.putExtra("songOnlineIndex", songIndex);
                    setResult(101, in);
                    finish();
                }
            });
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
