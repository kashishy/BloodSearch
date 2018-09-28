package com.example.ashish.bloodsearch;

public class user_data {
    String name,email,mobile,password,city,state,age,blood_group,id;
    public user_data(){

    }

    public user_data(String id,String name, String email, String mobile, String password, String city, String state, String age, String blood_group) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.city = city;
        this.state = state;
        this.age = age;
        this.blood_group = blood_group;
    }
    public  String getId() {
        return  id;
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

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getAge() {
        return age;
    }

    public String getBlood_group() {
        return blood_group;
    }
}
