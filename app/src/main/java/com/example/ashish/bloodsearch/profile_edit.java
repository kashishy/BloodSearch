package com.example.ashish.bloodsearch;

public class profile_edit {

    String name,age,email,mobile,id,state;
    String city;
    String blood_group;

    public  profile_edit (){

    }

    public profile_edit(String age,String blood_group,String city,String mobile, String name, String state) {
        this.age=age;
        this.mobile=mobile;
        this.state=state;
        this.name = name;
        this.city = city;
        this.blood_group = blood_group;
    }
}
