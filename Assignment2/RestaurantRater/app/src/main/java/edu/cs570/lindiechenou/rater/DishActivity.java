package edu.cs570.lindiechenou.rater;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DishActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "DishActivity";
    public static final String EXTRA_SELECTED_DISH_ID = "dish";
    public static final String EXTRA_SELECTED_RESTAURANT_ID = "restaurant";

    private TextView restaurantname;
    private EditText mTxtdishname;
    private EditText mTxtdishtype;
    private EditText mTxtdishrating;
    private Button mBtnadd;
    private Button mBtnrestaurant;
    private int mCompanyId = -1;
    private int mDishId = -1;

    private RestaurantDataSource mRestaurantDOA;
    private DishDataSource mDishDOA;

    private Restaurant mSelectedRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        initViews();

        this.mRestaurantDOA = new RestaurantDataSource(this);
        this.mDishDOA = new DishDataSource(this);

        //List<Restaurant> list
    }

    private void initViews() {
        this.mTxtdishname = (EditText)findViewById(R.id.editDish);
        this.mTxtdishtype = (EditText)findViewById(R.id.editType);
        this.mTxtdishrating = (EditText)findViewById(R.id.editRating);
        this.restaurantname = (TextView) findViewById(R.id.editRestaurant);
        restaurantname.setText(getIntent().getStringExtra("mytext"));
        mTxtdishname.setText(getIntent().getStringExtra("selected dish"));
        this.mBtnadd = (Button)findViewById(R.id.SaveDish);
        this.mBtnrestaurant = (Button)findViewById(R.id.BackRestaurant);

        this.mBtnadd.setOnClickListener(this);
        this.mBtnrestaurant.setOnClickListener(this);

        mDishId = getIntent().getIntExtra(DishActivity.EXTRA_SELECTED_DISH_ID,-1);
        if(mDishId != -1) {
            String dishName = getIntent().getStringExtra("dishname");
            String dishType = getIntent().getStringExtra("dishtype");
            String rating = getIntent().getStringExtra("dishrating");
            mTxtdishname.setText(dishName);
            mTxtdishtype.setText(dishType);
            mTxtdishrating.setText(rating);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.SaveDish:
            Editable dishname = mTxtdishname.getText();
            Editable dishtype = mTxtdishtype.getText();
            Editable dishrating = mTxtdishrating.getText();

            System.out.println("here");
            Intent intent1 = getIntent();
            mCompanyId = intent1.getIntExtra(EXTRA_SELECTED_RESTAURANT_ID, -1);
            int resturantId = intent1.getIntExtra("resturantid", -1);
            //mSelectedRestaurant = (Restaurant)restaurantname.getText();
            mSelectedRestaurant = new Restaurant();
            String name = restaurantname.getText().toString();
            mSelectedRestaurant.setRestaurantName(name);
            System.out.println("here1");
            if(mDishId ==-1 && !TextUtils.isEmpty(dishname) && !TextUtils.isEmpty(dishtype) && !TextUtils.isEmpty(dishrating) && mSelectedRestaurant !=null){

                mDishDOA.createDish(dishname.toString(), dishtype.toString(),dishrating.toString(),mCompanyId);
                setResult(ResultCodes.ACTION_DISH_ADDED);
                finish();
                //Intent intent = new Intent();
                //intent.putExtra(DishEmployeeActivity.EXTRA_ADDED_DISH, (Serializable) createDish);
                //setResult(RESULT_OK);
                //finish();
            }
            else if(!TextUtils.isEmpty(dishname) && !TextUtils.isEmpty(dishtype) && !TextUtils.isEmpty(dishrating) && mSelectedRestaurant !=null){

                mDishDOA.updateDish(mDishId, dishname.toString(), dishtype.toString(),dishrating.toString(),resturantId);
                setResult(ResultCodes.ACTION_DISH_EDIT);
                finish();
            }
            break;
            case R.id.BackRestaurant:
                Intent intent = new Intent(DishActivity.this, DishEmployeeActivity.class);
                startActivity(intent);
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRestaurantDOA.close();
    }


}
