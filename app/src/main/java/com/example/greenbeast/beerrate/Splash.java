package com.example.greenbeast.beerrate;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by GreenBeast on 10/26/2017.
 */
public class Splash extends AppCompatActivity {
    MediaPlayer mpStartmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();

                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        };
        Timer opening = new Timer( );
        opening.schedule(task,2000);
    }

}
