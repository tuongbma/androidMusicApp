package com.ptit.android;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.androidhive.musicplayer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.android.model.Song;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {

	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	
	// Constructor
	public SongsManager(){
		
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<HashMap<String, String>> getOfflineList(){
		// SDCard Path
		String MEDIA_PATH = new String("/sdcard/Download/");
		File home = new File(MEDIA_PATH);

		if (home.listFiles(new FileExtensionFilter()).length > 0) {
			for (File file : home.listFiles(new FileExtensionFilter())) {
				HashMap<String, String> song = new HashMap<String, String>();
				song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
				song.put("songPath", file.getPath());
				// Adding each song to SongList
				songsList.add(song);
			}
		}
		// return songs list array
		return songsList;
	}
	ArrayList<HashMap<String, String>> songListOnline;
	public ArrayList<HashMap<String, String>> getOnlineListByName(final String text) {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		songListOnline = new ArrayList<HashMap<String, String>>();
		DatabaseReference myRef = database.getReference("songs");
		myRef.addValueEventListener(new ValueEventListener() {

			@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for (DataSnapshot data: dataSnapshot.getChildren()) {
					Song s = data.getValue(Song.class);
					String songTitle = s.getTitle();
					String songArtist = s.getArtist();
					String songId = data.getKey();
					if(songTitle.toUpperCase().startsWith(text.toUpperCase())
							|| songTitle.equals(text)
							|| songArtist.toUpperCase().startsWith(text.toUpperCase())
							|| songArtist.equals(text) ) {

						HashMap<String, String> song = new HashMap<String, String>();
						song.put("songTitle", songTitle);
						song.put("songPath", s.getSource());

						// Adding each song to SongList
						songListOnline.add(song);
						System.out.println("song size 3: " + songListOnline.size());

					}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
		System.out.println("song size: " + songListOnline.size());

		return songListOnline;
	}

	public interface MyCallback {
		void onCallback(ArrayList<HashMap<String, String>> value);
	}

	public void readData(final String text, final MyCallback myCallback) {
		FirebaseDatabase database = FirebaseDatabase.getInstance();
		songListOnline = new ArrayList<HashMap<String, String>>();
		DatabaseReference myRef = database.getReference("songs");
		myRef.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				for (DataSnapshot data: dataSnapshot.getChildren()) {
					Song s = data.getValue(Song.class);
					String songTitle = s.getTitle();
					String songArtist = s.getArtist();
					String songId = data.getKey();
					if(songTitle.toUpperCase().startsWith(text.toUpperCase())
							|| songTitle.equals(text)
							|| songArtist.toUpperCase().startsWith(text.toUpperCase())
							|| songArtist.equals(text) ) {

						HashMap<String, String> song = new HashMap<String, String>();
						song.put("songTitle", songTitle);
						song.put("songPath", s.getSource());

						// Adding each song to SongList
						songListOnline.add(song);

					}
				}
				myCallback.onCallback(songListOnline);
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}
			/**
	 * Class to filter files which are having .mp3 extension
	 * */
	class FileExtensionFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return (name.endsWith(".mp3") || name.endsWith(".MP3"));
		}
	}

}
