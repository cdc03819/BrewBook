package com.example.greenbeast.beerrate;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.greenbeast.beerrate.info.androidhive.listviewfeed.adapter.FeedListAdapter;
import com.example.greenbeast.beerrate.info.androidhive.listviewfeed.app.AppController;
import com.example.greenbeast.beerrate.info.androidhive.listviewfeed.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class trending extends AppCompatActivity {
        ImageButton trendingBtm,userBtm,locationBtm,settingsBtm;
        FloatingActionButton addReviewBtm;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "https://api.androidhive.info/feed/feed.json";

    @SuppressLint("NewApi")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trending);

            trendingBtm = (ImageButton) findViewById(R.id.imageBtmtrending);
            userBtm = (ImageButton) findViewById(R.id.imageBtmuser);
            locationBtm = (ImageButton) findViewById(R.id.imageBtmlocation);
            settingsBtm = (ImageButton) findViewById(R.id.imageBtmSettings);
            addReviewBtm = (FloatingActionButton) findViewById(R.id.addReview);
            trendingBtm.setOnClickListener(btTrending);
            userBtm.setOnClickListener(btUser);
            locationBtm.setOnClickListener(btlocation);
            settingsBtm.setOnClickListener(btSettings);
            addReviewBtm.setOnClickListener(btAdd);

        ListView listView;
        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(this,feedItems);
        listView.setAdapter(listAdapter);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }

    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ImageButton.OnClickListener btTrending= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(trending.this, trending.class));
        }

    };
    ImageButton.OnClickListener btUser= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(trending.this, user.class));
        }

    };
    ImageButton.OnClickListener btlocation= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(trending.this, MapsActivity.class));
        }

    };
    ImageButton.OnClickListener btSettings= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(trending.this, SettingsActivity.class));
        }

    };
    FloatingActionButton.OnClickListener btAdd= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(trending.this, add.class));
        }

    };

}

