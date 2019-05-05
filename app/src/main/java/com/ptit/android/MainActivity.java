package com.ptit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidhive.musicplayer.R;

public class MainActivity extends Activity {
    private Button btnOnline, btnOffline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnOnline = (Button) findViewById(R.id.btnOnline);
        btnOffline = (Button) findViewById(R.id.btnOffline);
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
                //online activity
            }
        });
    }
}
