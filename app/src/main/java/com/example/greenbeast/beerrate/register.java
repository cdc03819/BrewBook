package com.example.greenbeast.beerrate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class register extends AppCompatActivity {
    Button register;
    EditText email, password;
    String userName;
    EditText username;
    String userPassword, userLocation;
    EditText location;
    String add_user_url, login_url, newurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register = (Button) findViewById(R.id.registerbtn);
        register.setOnClickListener(btRegister);
        username = (EditText) findViewById(R.id.regUser);
        password = (EditText) findViewById(R.id.regPass);

    }

    ImageButton.OnClickListener btRegister = new ImageButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveinfo(v);
            String pass = password.getText().toString();
            String user = username.getText().toString();
            if (TextUtils.isEmpty(user)) {
                username.setError("You must enter a valid username");
                return;
            } else if (TextUtils.isEmpty(pass)) {
                password.setError("You must enter a valid password");
                return;
            } else {
                startActivity(new Intent(register.this, LoginActivity.class));
                Toast.makeText(register.this, "User Registered!", Toast.LENGTH_SHORT).show();
            }

        }

    };

        public void saveinfo(View view) {
            userName = username.getText().toString();
            userPassword = password.getText().toString();
            BackgroundTask backgroundTask = new BackgroundTask();
            backgroundTask.execute(userName, userPassword);
        }

        class BackgroundTask extends AsyncTask<String, Void, String> {

            String add_user_url;

            @Override
            protected void onPreExecute() {
                add_user_url = "http://lincoln.sjfc.edu/~cdc03819/CSCI375/adduser.php";

            }

            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected String doInBackground(String... arg) {
                String userName, userPassword;
                userName = arg[0];
                userPassword = arg[1];
                try {
                    String data_String = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8") + "&" +
                            URLEncoder.encode("userPassword", "UTF-8") + "=" + URLEncoder.encode(userPassword, "UTF-8");
                    newurl = add_user_url + "?" + data_String;
                    URL url = new URL(newurl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();

                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    Log.d("userName = ", userName);
                    Log.d("userPassword = ", userPassword);
                    Log.d("url = ", newurl);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }





