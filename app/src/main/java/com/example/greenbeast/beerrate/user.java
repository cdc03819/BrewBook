package com.example.greenbeast.beerrate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import newsfeed.HttpServicesClass;
import newsfeed.ListAdapterClass;
import newsfeed.Users;


public class user extends AppCompatActivity {
    ImageButton trendingBtm,userBtm,locationBtm,settingsBtm;
    ListView userlistview;
    TextView usernameText;
    ProgressBar progressBarSubject;
    FloatingActionButton addReviewBtm;
    String ServerURL = "http://lincoln.sjfc.edu/~cdc03819/CSCI375/userpage.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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

       userlistview = (ListView) findViewById(R.id.listview1);
       usernameText = (TextView) findViewById(R.id.textView);

       progressBarSubject = (ProgressBar) findViewById(R.id.progressBar);

        new user.GetHttpResponse(user.this).execute();
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void> {
        public Context context;

        String ResultHolder;

        List<Users> subjectsList;
       // TextView<Users> textview;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpServicesClass httpServiceObject = new HttpServicesClass(ServerURL);
            try {
                httpServiceObject.ExecutePostRequest();

                if (httpServiceObject.getResponseCode() == 200) {
                    ResultHolder = httpServiceObject.getResponse();

                    if (ResultHolder != null) {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(ResultHolder);

                            JSONObject jsonObject;

                            Users Users;

                            subjectsList = new ArrayList<Users>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                Users = new Users();

                                jsonObject = jsonArray.getJSONObject(i);

                                Users.userName = jsonObject.getString("userID");


                                subjectsList.add(Users);
                            }
                            //Users = new Users();
                            //int i = 0;
                            //jsonObject = jsonArray.getJSONObject(i);
                            //Users.userName = jsonObject.getString("userName");
                            //usernameText.setText((CharSequence) Users);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBarSubject.setVisibility(View.GONE);

            userlistview.setVisibility(View.VISIBLE);

            if (subjectsList != null) {
                ListAdapterClass adapter = new ListAdapterClass(subjectsList, context);

                userlistview.setAdapter(adapter);
            }
        }
    }

    ImageButton.OnClickListener btTrending= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(user.this, newsfeed.MainActivity.class));
        }

    };
    ImageButton.OnClickListener btUser= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(user.this, user.class));
        }

    };
    ImageButton.OnClickListener btlocation= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(user.this, MapsActivity.class));
        }

    };
    ImageButton.OnClickListener btSettings= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(user.this, SettingsActivity.class));
        }

    };
    FloatingActionButton.OnClickListener btAdd= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(user.this, add.class));
        }

    };


}