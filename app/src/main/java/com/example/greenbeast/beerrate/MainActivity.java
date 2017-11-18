package com.example.greenbeast.beerrate;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {
   ImageButton trendingBtm,userBtm,locationBtm,settingsBtm;
   FloatingActionButton addReviewBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton trendingBtm = (ImageButton) findViewById(R.id.imageBtmtrending);
        ImageButton userBtm = (ImageButton) findViewById(R.id.imageBtmuser);
        ImageButton locationBtm = (ImageButton) findViewById(R.id.imageBtmlocation);
        ImageButton settingsBtm = (ImageButton) findViewById(R.id.imageBtmSettings);
        FloatingActionButton addReviewBtn = (FloatingActionButton) findViewById(R.id.addReview);

        trendingBtm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent trending;
                trending = new Intent(MainActivity.this, newsfeed.MainActivity.class);
                startActivity(trending);
            }

        });
        userBtm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent user = new Intent(MainActivity.this, com.example.greenbeast.beerrate.user.class);
                startActivity(user);
            }

        });
        locationBtm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent location = new Intent(MainActivity.this, com.example.greenbeast.beerrate.MapsActivity.class);
                startActivity(location);
            }
        });
        settingsBtm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                Intent setting = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(setting);
            }
        });

        FloatingActionButton.OnClickListener btAdd = new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, add.class));
            }

        };


    }







}
