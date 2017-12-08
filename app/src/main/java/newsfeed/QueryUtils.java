package newsfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lara on 20/9/16.
 */
public class QueryUtils {

    static String createStringUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("lincoln.sjfc.edu")
                .appendPath("~cdc03819")
                .appendPath("CSCI375")
                .appendPath("Users.php");
        String url = builder.build().toString();
        return url;
    }

    static URL createUrl() {
        String stringUrl = createStringUrl();
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Queryutils", "Error creating URL: ", e);
            return null;
        }
    }

    private static String formatDate(String rawDate) {
            String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
            SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
            try {
                Date parsedJsonDate = jsonFormatter.parse(rawDate);
                String finalDatePattern = "MMM d, yyy";
                SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
                return finalDateFormatter.format(parsedJsonDate);
            } catch (ParseException e) {
                Log.e("QueryUtils", "Error parsing JSON date: ", e);
                return "";
            }
    }

    static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("mainActivity", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Queryutils", "Error making HTTP request: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    static List<News> parseJson(String response) {
        ArrayList<News> listOfNews = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray resultsArray = jsonResults.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject oneResult = resultsArray.getJSONObject(i);
                String userName = oneResult.getString("userName");
                String beerName = oneResult.getString("beerName");
                String postDate = oneResult.getString("postDate");
                String postInfo = oneResult.getString("postInfo");
                String postRating = oneResult.getString("postRating");
                String userLocation = oneResult.getString("userLocation");
                //date = formatDate(date);
                //String section = oneResult.getString("sectionName");
               // JSONArray tagsArray = oneResult.getJSONArray("tags");
               // String author = "";

               // if (tagsArray.length() == 0) {
               //     author = null;
               // } else {
               //     for (int j = 0; j < tagsArray.length(); j++) {
                       // JSONObject firstObject = tagsArray.getJSONObject(j);
                        //author += firstObject.getString("webTitle") + ". ";
                   // }
                //}
                listOfNews.add(new News(userName, beerName, postDate, postInfo, postRating, userLocation));
            }
        } catch (JSONException e) {
            Log.e("Queryutils", "Error parsing JSON response", e);
        }
        return listOfNews;
    }
}
