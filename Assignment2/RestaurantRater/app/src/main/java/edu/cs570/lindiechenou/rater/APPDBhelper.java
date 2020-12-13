package edu.cs570.lindiechenou.rater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class APPDBhelper extends SQLiteOpenHelper {

    public static final String TAG = "APPDBhelper";

    public static final String TABLE_RESTAURANT = "restaurants";
    public static final String COLUMN_RESTAURANTID = "_id";
    public static final String COLUMN_RESTAURANTNAME = "restaurant_name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIPCODE = "zipcode";

    public static final String TABLE_DISH = "dishs";
    public static final String COLUMN_DISHID = COLUMN_RESTAURANTID;
    public static final String COLUMN_DISHNAME = "dish_name";
    public static final String COLUMN_DISHTYPE = "type";
    public static final String COLUMN_DISHRATING = "rating";
    public static final String COLUMN_DISH_RESTAURANTID = "restaurant_id";

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_RESTAURANT = "CREATE TABLE " + TABLE_RESTAURANT + "("
            +COLUMN_RESTAURANTID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_RESTAURANTNAME + " TEXT NOT NULL, "
            +COLUMN_ADDRESS + " TEXT NOT NULL, "
            +COLUMN_CITY + " TEXT NOT NULL, "
            +COLUMN_STATE + " TEXT NOT NULL, "
            +COLUMN_ZIPCODE + " TEXT NOT NULL "
            +");";

    private static final String CREATE_TABLE_DISH = "CREATE TABLE " + TABLE_DISH + "("
            +COLUMN_DISHID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_DISHNAME + " TEXT NOT NULL, "
            +COLUMN_DISHTYPE + " TEXT NOT NULL, "
            +COLUMN_DISHRATING + " TEXT NOT NULL, "
            +COLUMN_DISH_RESTAURANTID + " INTEGER NOT NULL "
            +");";

    public APPDBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(CREATE_TABLE_RESTAURANT);
        database.execSQL(CREATE_TABLE_DISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(TAG, "Upgrading the database from version " + oldVersion + " to "+newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH);

        onCreate(db);
    }

    public APPDBhelper(Context context, String name, CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

}
