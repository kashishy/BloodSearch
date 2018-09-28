package com.example.ashish.bloodsearch;

import android.widget.ImageView;

public class display_data {
    private String id,name,email,mobile,state,city,blood_group;

    public display_data(){

    }

    public display_data(String blood_group, String city, String email, String id, String mobile, String name, String state) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.state = state;
        this.city = city;
        this.blood_group = blood_group;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getBlood_group() {
        return blood_group;
    }
}
