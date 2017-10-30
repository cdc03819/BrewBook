package com.example.greenbeast.beerrate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

/**
 * Created by GreenBeast on 10/29/2017.
 */


public class location extends AppCompatActivity {
    ImageButton trendingBtm,userBtm,locationBtm,settingsBtm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending);

        trendingBtm = (ImageButton) findViewById(R.id.imageBtmtrending);
        userBtm = (ImageButton) findViewById(R.id.imageBtmuser);
        locationBtm = (ImageButton) findViewById(R.id.imageBtmlocation);
        settingsBtm = (ImageButton) findViewById(R.id.imageBtmSettings);


    }
}
