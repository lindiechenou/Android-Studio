package edu.cs570.lindiechenou.rater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDataSource {

    public static final String TAG = "RestaurantDAO";

    private SQLiteDatabase mDatabase;
    private APPDBhelper mDbhelper;
    private Context mContext;
    private String[] mAllColums = {APPDBhelper.COLUMN_RESTAURANTID,
            APPDBhelper.COLUMN_RESTAURANTNAME, APPDBhelper.COLUMN_ADDRESS,
            APPDBhelper.COLUMN_CITY, APPDBhelper.COLUMN_STATE, APPDBhelper.COLUMN_ZIPCODE};

    public RestaurantDataSource(Context context){
        this.mContext = context;
        mDbhelper = new APPDBhelper(context);
        try{
            open();
        }
        catch (SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException{
        mDatabase = mDbhelper.getWritableDatabase();
    }
    public void close(){
        mDbhelper.close();
    }

    public Restaurant createRestaurant(String name, String address, String city, String state, String zipcode){
        ContentValues values = new ContentValues();
        values.put(APPDBhelper.COLUMN_RESTAURANTNAME, name);
        values.put(APPDBhelper.COLUMN_ADDRESS, address);
        values.put(APPDBhelper.COLUMN_CITY, city);
        values.put(APPDBhelper.COLUMN_STATE, state);
        values.put(APPDBhelper.COLUMN_ZIPCODE, zipcode);
        long insertId = mDatabase
                .insert(APPDBhelper.TABLE_RESTAURANT, null, values);
        Cursor cursor = mDatabase.query(APPDBhelper.TABLE_RESTAURANT, mAllColums,
                APPDBhelper.COLUMN_RESTAURANTID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Restaurant newRestaurant = cursorToRestaurant(cursor);
        cursor.close();
        return newRestaurant;
    }

    public void deleteRestaurant(Restaurant restaurant){
        long id = restaurant.getRestaurantID();

        DishDataSource dishDataSource = new DishDataSource(mContext);
       // List<Dish> listDishs = dishDataSource.getDishsOfRestaurant(id);
     /*   if(listDishs != null && !listDishs.isEmpty()){
            for(Dish e :listDishs){
                dishDataSource.deleteDish(e);
            }
        }*/
        mDatabase.delete(APPDBhelper.TABLE_RESTAURANT, APPDBhelper.COLUMN_RESTAURANTID + " = " + id, null);
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> listRestaurants = new ArrayList<Restaurant>();

        Cursor cursor = mDatabase.query(APPDBhelper.TABLE_RESTAURANT, mAllColums,
                null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Restaurant restaurant = cursorToRestaurant(cursor);
                listRestaurants.add(restaurant);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listRestaurants;
    }

    public Restaurant getRestaurantById(long id) {
        Cursor cursor = mDatabase.query(APPDBhelper.TABLE_RESTAURANT, mAllColums,
                APPDBhelper.COLUMN_RESTAURANTID + " = ?",
               // new String[] { String.valueOf(id) }, null, null, null);
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor !=null) {
            cursor.moveToFirst();
        }

        Restaurant restaurant = cursorToRestaurant(cursor);
        return restaurant;
    }

    protected Restaurant cursorToRestaurant(Cursor cursor){


        int colu = cursor.getColumnCount();
        int rows = cursor.getCount();
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantID(cursor.getInt(0));
       // restaurant.setRestaurantID(1);
        restaurant.setRestaurantName(cursor.getString(1));
        restaurant.setStreetAddress(cursor.getString(2));
        restaurant.setCity(cursor.getString(3));
        restaurant.setState(cursor.getString(4));
        restaurant.setZipCode(cursor.getString(5));
        return restaurant;
    }

    public Restaurant getSpecificRestaurant(int restaurantId) {
        Restaurant restaurant = new Restaurant();
        String query = "SELECT  * FROM restaurant WHERE _id =" + restaurantId;
        Cursor cursor = mDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            restaurant.setRestaurantID(cursor.getInt(0));
            restaurant.setRestaurantName(cursor.getString(1));
            restaurant.setStreetAddress(cursor.getString(2));
            restaurant.setCity(cursor.getString(3));
            restaurant.setState(cursor.getString(4));
            restaurant.setZipCode(cursor.getString(5));

            cursor.close();
        }
        return restaurant;
    }
}
