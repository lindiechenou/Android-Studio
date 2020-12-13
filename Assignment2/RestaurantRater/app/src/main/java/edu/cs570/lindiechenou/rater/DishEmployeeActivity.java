package edu.cs570.lindiechenou.rater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DishEmployeeActivity extends Activity implements OnClickListener, OnItemLongClickListener, OnItemClickListener {

    public static final String TAG = "DishEmployeeActivity";

    public static final int REQUEST_CODE_ADD_DISH = 40;
    public static final int REQUEST_CODE_EDIT_DISH = 50;
    public static final String EXTRA_ADDED_DISH = "extra_key_added_dish";
    public static final String EXTRA_SELECTED_RESTAURANT_ID = "extra_key_selected_company_id";
    public static final String EXTRA_SELECTED_RESTAURANT_NAME = "extra_key_selected_company_name";
    public static final String EXTRA_SELECTED_RESTAURANT_ADDRESS = "extra_key_selected_company_address";
    public static final String EXTRA_SELECTED_RESTAURANT_CITY = "extra_key_selected_company_city";
    public static final String EXTRA_SELECTED_RESTAURANT_STATE = "extra_key_selected_company_state";

    private ListView mListviewdish;
    private RestaurantDataSource mRestaurantDao;

    private EditText mTxtRestaurantName;
    private EditText mTxtAddress;
    private EditText mTxtcity;
    private EditText mTxtstate;
    private EditText mTxtzipcode;
    private Button saveRestaurant;
    private Button adddish;
    private Button restaurant1;
    private DishAdapter mAdapter;
    private List<Dish> mListdish;
    private DishDataSource mdishDAO;
    private Restaurant currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant_dish);

        initTextChangedEvents();
       // initAddContactButton();
        initView();

        Bundle extras = getIntent().getExtras();
        currentRestaurant = new Restaurant();
        if(extras != null){
            //initRestaurant(extras.getInt("restaurantId"));
            //initRestaurant(extras.getInt(EXTRA_SELECTED_RESTAURANT_ID));

            currentRestaurant.setRestaurantID(extras.getInt(EXTRA_SELECTED_RESTAURANT_ID));
            currentRestaurant.setRestaurantName(extras.getString("restaurantName"));
            currentRestaurant.setStreetAddress(extras.getString("restaurantaddress"));
            currentRestaurant.setCity(extras.getString("restaurantcity"));
            currentRestaurant.setState(extras.getString("restaurantstate"));
            currentRestaurant.setZipCode(extras.getString("restaurantzipcode"));
        }
        else{

        }
        mdishDAO = new DishDataSource(this);

        Intent intent = getIntent();
        mTxtRestaurantName = findViewById(R.id.editName);
        String txt_put = intent.getStringExtra("restaurantName");
        mTxtRestaurantName.setText(txt_put);

        mTxtAddress = (EditText) findViewById(R.id.editAddress);
        String txt_address = intent.getStringExtra("restaurantaddress");
        mTxtAddress.setText(txt_address);

        mTxtcity = findViewById(R.id.editCity);
        String txt_city = intent.getStringExtra("restaurantcity");
        mTxtcity.setText(txt_city);

        mTxtstate = findViewById(R.id.editState);
        String txt_state = intent.getStringExtra("restaurantstate");
        mTxtstate.setText(txt_state);

        mTxtzipcode = findViewById(R.id.editZipcode);
        String txt_zipcode = intent.getStringExtra("restaurantzipcode");
        mTxtzipcode.setText(txt_zipcode);

        refreshUI();
        this.mRestaurantDao = new RestaurantDataSource(this);
    }

    private void refreshUI() {
        mListdish = mdishDAO.getDishOfRestaurant(currentRestaurant.getRestaurantID());
        //mListdish = mdishDAO.getAllDish();
        if(mListdish != null && !mListdish.isEmpty()){
            mAdapter = new DishAdapter(this, mListdish);
            mAdapter.setDishListener(new DishAdapter.DishListener() {
                @Override
                public void onDeleteDish(int dishId) {
                    mdishDAO.deleteDish(dishId);
                    refreshUI();
                }

                @Override
                public void onOpenActivity(int dishID) {
                    Dish clickdish = mAdapter.getItemById(dishID);
                    Log.d(TAG, "clickeditem:"+clickdish.getDishname());
                    Intent intent = new Intent(DishEmployeeActivity.this, DishActivity.class);
                    intent.putExtra(DishActivity.EXTRA_SELECTED_DISH_ID, clickdish.getDishID());
                    intent.putExtra("dishname", clickdish.getDishname());
                    intent.putExtra("dishtype", clickdish.getDishtype());
                    intent.putExtra("dishrating", clickdish.getRating());
                    intent.putExtra("resturantid", clickdish.getRestaurant().getRestaurantID());
                    startActivityForResult(intent, REQUEST_CODE_EDIT_DISH);
                }
            });
            mListviewdish.setAdapter(mAdapter);
        }
        else{
            mListviewdish.setVisibility(View.GONE);
        }
    }

    private void initView(){
        this.mListviewdish = (ListView) findViewById(R.id.navigationBar);
        this.adddish = (Button) findViewById(R.id.DishBu);
        this.mListviewdish.setOnItemClickListener(this);
        this.mListviewdish.setOnItemLongClickListener( this);
        this.adddish.setOnClickListener(this);

    }


    private void initRestaurant(long id) {

        RestaurantDataSource ds = new RestaurantDataSource(DishEmployeeActivity.this);
        try {
            ds.open();
            currentRestaurant = ds.getRestaurantById(id);
            ds.close();
        } catch (Exception e) {
            Toast.makeText(this, "Load Restaurant Failed", Toast.LENGTH_LONG).show();
        }

        EditText editName = findViewById(R.id.editName);
        EditText editAddress = findViewById(R.id.editAddress);
        EditText editCity = findViewById(R.id.editCity);
        EditText editState = findViewById(R.id.editState);
        EditText editZipCode = findViewById(R.id.editZipcode);


        editName.setText(currentRestaurant.getRestaurantName());
        editAddress.setText(currentRestaurant.getStreetAddress());
        editCity.setText(currentRestaurant.getCity());
        editState.setText(currentRestaurant.getState());
        editZipCode.setText(currentRestaurant.getZipCode());

    }





    private void initTextChangedEvents(){
        this.mTxtRestaurantName = (EditText) findViewById(R.id.editName);
        this.mTxtAddress = (EditText) findViewById(R.id.editAddress);
        this.mTxtcity = (EditText) findViewById(R.id.editCity);
        this.mTxtstate = (EditText) findViewById(R.id.editState);
        this.mTxtzipcode = (EditText) findViewById(R.id.editZipcode);
        this.saveRestaurant = (Button)findViewById(R.id.buttonSaveRestaurant);
        this.restaurant1 = (Button)findViewById(R.id.buttonRestaurant);
        //this.adddish = (Button)findViewById(R.id.DishBu);

        this.saveRestaurant.setOnClickListener(this);
        this.restaurant1.setOnClickListener(this);
        //this.adddish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonSaveRestaurant:
                Editable restaurantname = mTxtRestaurantName.getText();
                Editable address = mTxtAddress.getText();
                Editable city = mTxtcity.getText();
                Editable state = mTxtstate.getText();
                Editable zipcode = mTxtzipcode.getText();
                if(!TextUtils.isEmpty(restaurantname) && !TextUtils.isEmpty(address)
                        && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(state)
                        && !TextUtils.isEmpty(zipcode)){
                    Restaurant createdRestaurant = mRestaurantDao.createRestaurant(restaurantname.toString(),
                            address.toString(), city.toString(),
                            state.toString(), zipcode.toString());
                    Log.d(TAG, "added restaurant: "+createdRestaurant.getRestaurantName());
                    //Intent intent = new Intent();
                    //intent.putExtra(MainActivity.EXTRA_ADDED_RESTAURANT, (Serializable) createdRestaurant);
                    //setResult(RESULT_OK, intent);
                   // finish();
                    }
                else {
                    Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.DishBu:
                //Intent intent = new Intent(this, DishActivity.class);
                //startActivityForResult(intent, REQUEST_CODE_ADD_DISH);

                String text = mTxtRestaurantName.getText().toString();
                Intent myIntent = new Intent(v.getContext(),DishActivity.class);

                //Intent intent1 = new Intent(DishEmployeeActivity.this, DishActivity.class);
                myIntent.putExtra(DishActivity.EXTRA_SELECTED_RESTAURANT_ID, currentRestaurant.getRestaurantID());

                myIntent.putExtra("mytext", text);
                startActivityForResult(myIntent, REQUEST_CODE_ADD_DISH);
               // startActivity(intent1);
                break;
            case  R.id.buttonRestaurant:
                Intent intent = new Intent(DishEmployeeActivity.this, MainActivity.class);
                startActivity(intent);
        default:
            break;
        }
    }

   /* private void initAddContactButton() {
        Button newContact = findViewById(R.id.DishBu);
        newContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Intent intent = new Intent(DishEmployeeActivity.this, DishActivity.class);
                startActivity(intent);
            }
        });
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRestaurantDao.close();
    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        if(requestcode == REQUEST_CODE_EDIT_DISH){
            switch (resultcode) {
                case ResultCodes.ACTION_DISH_EDIT:
                    refreshUI();
                    break;
            }
        }
        if(requestcode == REQUEST_CODE_ADD_DISH){
            switch (resultcode){
                case RESULT_OK:
                    if(data != null) {
                        Dish createDish = (Dish) data.getSerializableExtra(EXTRA_ADDED_DISH);
                        if (createDish != null) {
                            if (mListdish == null)
                                mListdish = new ArrayList<Dish>();
                            mListdish.add(createDish);

                            if (mAdapter == null) {
                                if (mListviewdish.getVisibility() != View.VISIBLE) {
                                    mListviewdish.setVisibility(View.VISIBLE);
                                }
                                mAdapter = new DishAdapter(this, mListdish);
                                mListviewdish.setAdapter(mAdapter);
                            } else {
                                mAdapter.setItems(mListdish);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    break;
                case ResultCodes.ACTION_DISH_ADDED:
                    refreshUI();
                    break;

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dish clickdish = mAdapter.getItem(position);
        Log.d(TAG, "clickeditem:"+clickdish.getDishname());
        Intent intent = new Intent(DishEmployeeActivity.this, DishActivity.class);
        intent.putExtra(DishActivity.EXTRA_SELECTED_DISH_ID, clickdish.getDishID());
        intent.putExtra("dishname", clickdish.getDishname());
        intent.putExtra("dishtype", clickdish.getDishtype());
        intent.putExtra("dishrating", clickdish.getRating());
        startActivity(intent);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

}
