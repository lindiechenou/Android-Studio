package edu.cs570.lindiechenou.rater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DishDataSource {

    public static final String TAG = "DishDataSource";

    private Context mContext;

    private SQLiteDatabase mDatabase;
    private APPDBhelper mDBHelper;
    private String[] mAllColums = {APPDBhelper.COLUMN_DISHID,
            APPDBhelper.COLUMN_DISHNAME,
            APPDBhelper.COLUMN_DISHTYPE,
            APPDBhelper.COLUMN_DISHRATING,
            APPDBhelper.COLUMN_DISH_RESTAURANTID,};

    public DishDataSource(Context context) {
        this.mContext = context;
        mDBHelper = new APPDBhelper(context);
        try{
            open();
        }
        catch (SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void open() throws SQLException{
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public long createDish(String dishName, String dishType, String dishrating, long restaurantId){
        ContentValues values = new ContentValues();
        values.put(APPDBhelper.COLUMN_DISHNAME, dishName);
        values.put(APPDBhelper.COLUMN_DISHTYPE, dishType);
        values.put(APPDBhelper.COLUMN_DISHRATING, dishrating);
        values.put(APPDBhelper. COLUMN_DISH_RESTAURANTID, restaurantId);
        return mDatabase.insert(APPDBhelper.TABLE_DISH, null, values);
    }
    public long updateDish(int dishId, String dishName, String dishType, String dishrating, long restaurantId){
        ContentValues values = new ContentValues();
        values.put(APPDBhelper.COLUMN_DISHNAME, dishName);
        values.put(APPDBhelper.COLUMN_DISHTYPE, dishType);
        values.put(APPDBhelper.COLUMN_DISHRATING, dishrating);
        values.put(APPDBhelper. COLUMN_DISH_RESTAURANTID, restaurantId);
        String where = APPDBhelper.COLUMN_DISHID+"=?";
        String[] args = new String[] {String.valueOf(dishId)};
        return mDatabase.update(APPDBhelper.TABLE_DISH, values,where, args);
    }

    public void deleteDish(int dishId){
        mDatabase.delete(APPDBhelper.TABLE_DISH, APPDBhelper.COLUMN_DISHID
                + " = "+ dishId, null);
    }

    public List<Dish> getAllDish(){
        List<Dish> listDish = new ArrayList<Dish>();

        Cursor cursor = mDatabase.query(APPDBhelper.TABLE_DISH, mAllColums,
                null, null, null, null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Dish dish = cursorToDish(cursor);
            listDish.add(dish);
            cursor.moveToNext();
        }
        cursor.close();
        return listDish;
    }

    public List<Dish> getDishOfRestaurant(long restaurantId){
        List<Dish> listDish = new ArrayList<Dish>();

        Cursor cursor = mDatabase.query(APPDBhelper.TABLE_DISH, mAllColums, APPDBhelper.COLUMN_DISH_RESTAURANTID + " = ?",
                new String[] {String.valueOf(restaurantId)}, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Dish dish = cursorToDish(cursor);
            listDish.add(dish);
            cursor.moveToNext();
        }
        cursor.close();
        return listDish;
    }

    private Dish cursorToDish(Cursor cursor) {

        Dish dish = new Dish();
        dish.setDishID(cursor.getInt(0));
        dish.setDishname(cursor.getString(1));
        dish.setDishtype(cursor.getString(2));
        dish.setRating(cursor.getString(3));

        long restauranId = cursor.getLong(4);
        RestaurantDataSource dao = new RestaurantDataSource(mContext);
        Restaurant restaurant = dao.getRestaurantById(restauranId);
        if(restaurant != null)
            dish.setRestaurantId(restaurant);
        return dish;

    }
}