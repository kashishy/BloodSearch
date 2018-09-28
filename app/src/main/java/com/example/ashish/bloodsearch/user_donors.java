package com.example.ashish.bloodsearch;

public class user_donors {

        String name,email,mobile,city,state,age,blood_group,id;
        public user_donors(){

        }

        public user_donors(String id,String name, String email, String mobile, String city, String state, String age, String blood_group) {
            this.id=id;
            this.name = name;
            this.email = email;
            this.mobile = mobile;
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

        public String getMoblie() {

            return mobile;
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

