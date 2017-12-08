package newsfeed;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.greenbeast.beerrate.R;
import com.example.greenbeast.beerrate.SettingsActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity {
    Button go;
    EditText location;
    ListView SubjectListView;
    String userlocation, add_info_url, location_url, newurl;
    ProgressBar progressBarSubject;
    private ImageButton trendingBtm, userBtm, locationBtm, settingsBtm, addReviewBtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        go = (Button) findViewById(R.id.button2);
        go.setOnClickListener(btGo);
        location = (EditText) findViewById(R.id.editLocation);
        progressBarSubject = (ProgressBar) findViewById(R.id.progressBar);
        SubjectListView = (ListView) findViewById(R.id.listview1);
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


    }
    Button.OnClickListener btGo = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
           // startActivity(new Intent(newsfeed.MainActivity.this, SettingsActivity.class));
            saveinfo(v);
            new GetHttpResponse(MapsActivity.this).execute();

        }

    };

    public void saveinfo(View view) {
        userlocation = location.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(userlocation);
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {

        String add_info_url;

        @Override
        protected void onPreExecute() {
            location_url = "http://lincoln.sjfc.edu/~cdc03819/CSCI375/locationpage.php";
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... arg) {
            //String userLocation;
            userlocation = arg[0];
            try {
                String data_String = URLEncoder.encode("userLocation", "UTF-8") + "=" + URLEncoder.encode(userlocation, "UTF-8");
                newurl = location_url +"?" + data_String;
                URL url = new URL(newurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                String response = "";
                //for (login_url) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(newurl);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                    //if (response.endsWith("]")) {
                       // startActivity(new Intent(MapsActivity.this, newsfeed.MainActivity.class));
                       // msg = "Login Successful!";
                   // }
                   // else{
                        //msg = "Invalid username or password!";
                   // }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //}



                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.d("location = ", userlocation);
                Log.d("url = ", newurl);
                Log.d("valid = ", response);
                //return "Login Successful!";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private class GetHttpResponse extends AsyncTask<Void, Void, Void> {
        public Context context;

        String ResultHolder;

        List<Users> subjectsList;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("url = ", newurl);
            HttpServicesClass httpServiceObject = new HttpServicesClass(newurl);
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

                                Users.userName = jsonObject.getString("userName");
                                Users.postDate = jsonObject.getString("postDate");
                                Users.beerName = jsonObject.getString("beerName");
                                Users.postInfo = jsonObject.getString("postInfo");
                                Users.userLocation = jsonObject.getString("userLocation");
                                Users.postRating = jsonObject.getString("postRating");

                                subjectsList.add(Users);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    //Toast.makeText(context, httpServiceObject.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

            SubjectListView.setVisibility(View.VISIBLE);

            if (subjectsList != null) {
                ListAdapterClass adapter = new ListAdapterClass(subjectsList, context);

                SubjectListView.setAdapter(adapter);
            }
        }
    }
    ImageButton.OnClickListener btTrending = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MapsActivity.this, newsfeed.MainActivity.class));
        }

    };
    ImageButton.OnClickListener btUser = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MapsActivity.this, newsfeed.user.class));
        }

    };
    ImageButton.OnClickListener btlocation = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MapsActivity.this, MapsActivity.class));
        }

    };
    ImageButton.OnClickListener btSettings = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MapsActivity.this, SettingsActivity.class));
        }

    };
    FloatingActionButton.OnClickListener btAdd = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MapsActivity.this, add.class));
        }

    };


}



