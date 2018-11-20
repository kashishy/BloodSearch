package com.example.ashish.bloodsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParse {
    private HashMap<String,String > getPlace(JSONObject googlePlaceJason){

        HashMap<String,String> googlePlaceMap = new HashMap<>();
        String placeName="-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String reference="";
        try {
            if(!googlePlaceJason.isNull("name")){

                placeName=googlePlaceJason.getString("name");

            }
            if(!googlePlaceJason.isNull("vicinity")){
                vicinity=googlePlaceJason.getString("vicinity");

            }
            latitude=googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googlePlaceJason.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlaceJason.getString("reference");

            googlePlaceMap.put("place_name",placeName);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put( "lat",latitude);
            googlePlaceMap.put( "lng",longitude);
            googlePlaceMap.put( "reference",reference);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    private List<HashMap<String ,String >> getPlaces(JSONArray jsonArray){
        int count=jsonArray.length();
        List<HashMap<String,String >>placesList=new ArrayList<>();
        HashMap<String,String>placeMap=null;

        for (int i=0;i<count;i++){
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    public List<HashMap<String,String >>parse(String jsonData){

        JSONArray jsonArray=null;
        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

}
