package newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.greenbeast.beerrate.R;

import java.util.ArrayList;

/**
 * Created by Lara on 21/09/2016.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context) {
        super(context, -1, new ArrayList<News>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView
                    = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView beerName = (TextView) convertView.findViewById(R.id.beerName);
        TextView postDate = (TextView) convertView.findViewById(R.id.postDate);
        TextView postInfo = (TextView) convertView.findViewById(R.id.postInfo);
        TextView postRating = (TextView) convertView.findViewById(R.id.postRating);
        TextView userLocation = (TextView) convertView.findViewById(R.id.userLocation);

        News currentNews = getItem(position);
        userName.setText(currentNews.getuserName());
        beerName.setText(currentNews.getBeerName());
        postDate.setText(currentNews.getPostDate());
        postInfo.setText(currentNews.getPostInfo());
        postRating.setText(currentNews.getPostRating());
        userLocation.setText(currentNews.getUserLocation());

        return convertView;
    }
}
