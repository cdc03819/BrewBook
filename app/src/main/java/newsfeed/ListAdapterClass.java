package newsfeed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.greenbeast.beerrate.R;

import java.util.List;

public class ListAdapterClass extends BaseAdapter {

    Context context;
    List<Users> valueList;

    public ListAdapterClass(List<Users> listValue, Context context)
    {
        this.context = context;
        this.valueList = listValue;
    }

    @Override
    public int getCount()
    {
        return this.valueList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.valueList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.layout_items, null);

            viewItem.TextViewUserName = (TextView)convertView.findViewById(R.id.userName);
            viewItem.TextViewBeerName = (TextView)convertView.findViewById(R.id.beerName);
           viewItem.TextViewPostDate = (TextView)convertView.findViewById(R.id.postDate);
           viewItem.TextViewPostInfo = (TextView)convertView.findViewById(R.id.postInfo);
            viewItem.TextViewPostRating = (TextView)convertView.findViewById(R.id.postRating);
            viewItem.TextViewUserLocation = (TextView)convertView.findViewById(R.id.userLocation);

            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.TextViewUserName.setText(valueList.get(position).userName);
        viewItem.TextViewBeerName.setText(valueList.get(position).beerName);
        viewItem.TextViewPostDate.setText(valueList.get(position).postDate);
        viewItem.TextViewPostInfo.setText(valueList.get(position).postInfo);
        viewItem.TextViewPostRating.setText(valueList.get(position).postRating);
        viewItem.TextViewUserLocation.setText(valueList.get(position).userLocation);

        return convertView;
    }
}

class ViewItem
{
    TextView TextViewUserName;
    TextView TextViewBeerName;
    TextView TextViewPostDate;
    TextView TextViewPostInfo;
    TextView TextViewPostRating;
    TextView TextViewUserLocation;



}
