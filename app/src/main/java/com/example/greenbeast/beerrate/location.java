package com.example.greenbeast.beerrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class location extends AppCompatActivity {
    ImageButton trendingBtm,userBtm,locationBtm,settingsBtm;
    FloatingActionButton addReviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);



        trendingBtm = (ImageButton) findViewById(R.id.imageBtmtrending);
        userBtm = (ImageButton) findViewById(R.id.imageBtmuser);
        locationBtm = (ImageButton) findViewById(R.id.imageBtmlocation);
        settingsBtm = (ImageButton) findViewById(R.id.imageBtmSettings);
        addReviewBtn = (FloatingActionButton) findViewById(R.id.addReview);

        trendingBtm.setOnClickListener(btTrending);
        userBtm.setOnClickListener(btUser);
        locationBtm.setOnClickListener(btlocation);
        settingsBtm.setOnClickListener(btSettings);
        addReviewBtn.setOnClickListener(btAdd);
    }
    ImageButton.OnClickListener btTrending= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(location.this, trending.class));
        }

    };
    ImageButton.OnClickListener btUser= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(location.this, user.class));
        }

    };
    ImageButton.OnClickListener btlocation= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(location.this, location.class));
        }

    };
    ImageButton.OnClickListener btSettings= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(location.this, SettingsActivity.class));
        }

    };
    FloatingActionButton.OnClickListener btAdd = new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(location.this, add.class));
        }

    };

}
