package newsfeed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.greenbeast.beerrate.MapsActivity;
import com.example.greenbeast.beerrate.R;
import com.example.greenbeast.beerrate.SettingsActivity;
import com.example.greenbeast.beerrate.add;
import com.example.greenbeast.beerrate.user;

import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>, SwipeRefreshLayout.OnRefreshListener {
    ImageButton trendingBtm,userBtm,locationBtm,settingsBtm;
    FloatingActionButton addReviewBtm;
    private NewsAdapter adapter;
    private static int LOADER_ID = 0;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new NewsAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = adapter.getItem(i);
                String url = news.url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

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
    ImageButton.OnClickListener btTrending= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(newsfeed.MainActivity.this, newsfeed.MainActivity.class));
        }

    };
    ImageButton.OnClickListener btUser= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(newsfeed.MainActivity.this, user.class));
        }

    };
    ImageButton.OnClickListener btlocation= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(newsfeed.MainActivity.this, MapsActivity.class));
        }

    };
    ImageButton.OnClickListener btSettings= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(newsfeed.MainActivity.this, SettingsActivity.class));
        }

    };
    FloatingActionButton.OnClickListener btAdd= new ImageButton.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(new Intent(newsfeed.MainActivity.this, add.class));
        }

    };


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        swipe.setRefreshing(false);
        if (data != null) {
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.setNotifyOnChange(true);
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
