package edu.cs570.lindiechenou.rater;

public class Dish {

    public static final String TAG = "Dish";

    private int DishID;
    private String dishname;
    private String dishtype;
    private String rating;
    private Restaurant RestaurantId;

    public Dish(){
    }

    public int getDishID(){
        return DishID;
    }

    public void setDishID(int DishID){
        this.DishID = DishID;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public String getDishtype() {
        return dishtype;
    }

    public void setDishtype(String dishtype) {
        this.dishtype = dishtype;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Restaurant getRestaurant() {
        return  RestaurantId;
    }

    public void setRestaurantId(Restaurant RestaurantId) {
        this.RestaurantId = RestaurantId;
    }
}
