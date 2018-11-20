package com.example.ashish.bloodsearch;

public class User {
    String name,age,email,mobile,id,state;
    String city;
    String blood_group;

    public  User (){

    }

    public User(String age,String blood_group,String city,String email,String id,String mobile,
                String name, String state) {
        this.age=age;
        this.email=email;
        this.id=id;
        this.mobile=mobile;
        this.state=state;
        this.name = name;
        this.city = city;
        this.blood_group = blood_group;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getId() {
        return id;
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
