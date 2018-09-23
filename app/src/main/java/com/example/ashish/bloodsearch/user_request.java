package com.example.ashish.bloodsearch;

public class user_request {

        String name,email,moblie,city,state,age,blood_group,id;
        public user_request(String donor_name, String donor_email, String donor_mobile, String donor_state, String donor_city, String donor_blood){

        }

        public user_request(String id,String name, String email, String moblie, String city, String state, String blood_group) {
            this.id=id;
            this.name = name;
            this.email = email;
            this.moblie = moblie;
            this.city = city;
            this.state = state;
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

            return moblie;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getBlood_group() {
            return blood_group;
        }
}
