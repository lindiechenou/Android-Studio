package edu.cs570.lindiechenou.rater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RestaurantAdapter extends BaseAdapter{

    public static final String TAG = "ListRestaurantAdapter";

    private List<Restaurant> mRestaurant;
    private LayoutInflater mInflater;

    class ViewHolder {
        TextView txtRestaurantName;
    }

    public RestaurantAdapter(Context context, List<Restaurant> listRestaurant){
        this.setItems(listRestaurant);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() :0;
    }

    @Override
    public Restaurant getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) :null;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getRestaurantID() : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null)
        {
            v = mInflater.inflate(R.layout.list_item_restaurant, parent, false);
            holder = new ViewHolder();
            holder.txtRestaurantName = (TextView) v.findViewById(R.id.textRestaurantName);
            v.setTag(holder);
        }
        else{
            holder = (ViewHolder) v.getTag();
        }

        Restaurant restaurantitem = getItem(position);
        if(restaurantitem != null){
            holder.txtRestaurantName.setText(restaurantitem.getRestaurantName());
        }
        return v;
    }

    public List<Restaurant> getItems(){
        return mRestaurant;
    }

    public void setItems(List<Restaurant> mRestaurant){
        this.mRestaurant = mRestaurant;
    }

}
