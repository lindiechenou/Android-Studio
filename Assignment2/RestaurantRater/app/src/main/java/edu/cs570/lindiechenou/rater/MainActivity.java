package edu.cs570.lindiechenou.rater;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemLongClickListener, OnItemClickListener, OnClickListener{

    public static final String TAG = "MainActivity";

    public static final int REQUEST_CODE_ADD_RESTAURANT = 40;
    public static final String EXTRA_ADDED_RESTAURANT = "extra_key_added_restaurant";

    private ListView mListviewrestaurant;
    private Button mAddrestaurant;

    private RestaurantAdapter mAdapter;
    private List<Restaurant> mListrestaurant;
    //Restaurant send;
    private RestaurantDataSource mrestaurantDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
       // initAddContactButton()

        mrestaurantDAO = new RestaurantDataSource(this);
        mListrestaurant = mrestaurantDAO.getAllRestaurants();
        if(mListrestaurant != null && !mListrestaurant.isEmpty()){
            mAdapter = new RestaurantAdapter(this, mListrestaurant);
            mListviewrestaurant.setAdapter(mAdapter);
        }
        else{
            mListviewrestaurant.setVisibility(View.GONE);
        }
    }

    private void initView(){
        this.mListviewrestaurant = (ListView) findViewById(R.id.list_restaurant);
        this.mAddrestaurant = (Button) findViewById(R.id.buttonAddRestaurant);
        this.mListviewrestaurant.setOnItemClickListener(this);
        this.mListviewrestaurant.setOnItemLongClickListener(this);
        this.mAddrestaurant.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddRestaurant:
                Intent intent = new Intent(this, DishEmployeeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_RESTAURANT);
                break;
            default:
                  break;
            }
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        if(requestcode == REQUEST_CODE_ADD_RESTAURANT){
            if(resultcode == RESULT_OK){
                if(data != null){
                    Restaurant createRestaurant = (Restaurant) data.getSerializableExtra(EXTRA_ADDED_RESTAURANT);
                    if(createRestaurant != null){
                        if(mListrestaurant == null)
                            mListrestaurant = new ArrayList<Restaurant>();
                        mListrestaurant.add(createRestaurant);

                        if(mAdapter == null){
                            if(mListviewrestaurant.getVisibility() != View.VISIBLE){
                                mListviewrestaurant.setVisibility(View.VISIBLE);
                            }
                            mAdapter = new RestaurantAdapter(this, mListrestaurant);
                            mListviewrestaurant.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListrestaurant);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
        else
            super.onActivityResult(requestcode, resultcode, data);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mrestaurantDAO.close();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Restaurant clickedrestaurant = mAdapter.getItem(position);
        Log.d(TAG, "clickeditem: " +clickedrestaurant.getRestaurantName());
        Intent intent = new Intent(MainActivity.this, DishEmployeeActivity.class);
        //intent.putExtra("restaurantid", clickedrestaurant.getRestaurantID());
       //intent.putExtra(DishEmployeeActivity.EXTRA_SELECTED_RESTAURANT_ID, clickedrestaurant.getRestaurantName());
        intent.putExtra(DishEmployeeActivity.EXTRA_SELECTED_RESTAURANT_ID, clickedrestaurant.getRestaurantID());
        intent.putExtra("restaurantName", clickedrestaurant.getRestaurantName());
        intent.putExtra("restaurantaddress", clickedrestaurant.getStreetAddress());
        intent.putExtra("restaurantcity", clickedrestaurant.getCity());
        intent.putExtra("restaurantstate", clickedrestaurant.getState());
        intent.putExtra("restaurantzipcode", clickedrestaurant.getZipCode());
      //  Intent intent1 = new Intent(MainActivity.this, DishActivity.class);
        //intent.putExtra(DishActivity.EXTRA_SELECTED_RESTAURANT_ID, clickedrestaurant.getRestaurantID());
        startActivity(intent);
       // startActivity(intent1);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}