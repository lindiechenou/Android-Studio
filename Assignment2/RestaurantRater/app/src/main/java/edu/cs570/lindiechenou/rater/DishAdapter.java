package edu.cs570.lindiechenou.rater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class DishAdapter extends BaseAdapter{

    public static final String TAG = "ListRestaurantAdapter";

    private List<Dish> mDish;
    private LayoutInflater mInflater;
    private DishListener dishListener;

    class ViewHolder {
        TextView txtDishName;
        TextView txtDishType;
        TextView txtDishRating;
        Button deleteButton;
    }

    public DishAdapter(Context context, List<Dish> listDish){
        this.setItems(listDish);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() :0;
    }

    @Override
    public Dish getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) :null;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getDishID() : position;
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
            v = mInflater.inflate(R.layout.dish_list, parent, false);
            holder = new ViewHolder();
            holder.txtDishName = (TextView) v.findViewById(R.id.textMealName);
            holder.txtDishType = (TextView) v.findViewById(R.id.textMealType);
            holder.txtDishRating = (TextView) v.findViewById(R.id.textMealRating);
            holder.deleteButton = (Button) v.findViewById(R.id.buttonDeleteMeal);
            v.setTag(holder);
        }
        else{
            holder = (ViewHolder) v.getTag();
        }

        final Dish dishitem = getItem(position);
        if(dishitem != null){
            holder.txtDishName.setText(dishitem.getDishname());
            holder.txtDishType.setText(dishitem.getDishtype());
            holder.txtDishRating.setText(dishitem.getRating());
            holder.deleteButton.setVisibility(View.VISIBLE);
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dishListener != null)
                    dishListener.onDeleteDish(dishitem.getDishID());
            }
        });
        holder.txtDishName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dishListener != null){
                    dishListener.onOpenActivity(dishitem.getDishID());
                }
            }
        });
        return v;
    }
    public Dish getItemById(int id){
        for (Dish d :
                mDish) {
            if (d.getDishID() == id)
                return d;
        }
        return null;

    }
    public List<Dish> getItems(){
        return mDish;
    }

    public void setItems(List<Dish> mDish){
        this.mDish = mDish;
    }

    public void setDishListener(DishListener dishListener) {
        this.dishListener = dishListener;
    }

    public interface DishListener{
        void onDeleteDish(int dishId);

        void onOpenActivity(int dishID);
    }

}