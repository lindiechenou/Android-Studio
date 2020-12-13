package edu.cs570.lindiechenou.rater;

public class Restaurant {

    private static final String TAG = "Dish";

    private int RestaurantID;
    private String RestaurantName;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public Restaurant(){
    }

    public int getRestaurantID(){
        return RestaurantID;
    }

    public void setRestaurantID(int RestaurantID){
        this.RestaurantID = RestaurantID;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String RestaurantName) {
        this.RestaurantName = RestaurantName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
