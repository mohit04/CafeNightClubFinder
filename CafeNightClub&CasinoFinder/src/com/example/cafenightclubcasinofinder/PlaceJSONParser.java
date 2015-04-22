package com.example.cafenightclubcasinofinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceJSONParser {

	public List<HashMap<String,String>> parse(JSONObject jObject){
		 
        JSONArray jPlaces = null;
        try {
            
            jPlaces = jObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       
       
        return getPlaces(jPlaces);
    }
 
    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces){
        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> place = null;
 
     
        for(int i=0; i<placesCount;i++){
            try {
              
                place = getPlace((JSONObject)jPlaces.get(i));
                placesList.add(place);
 
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
 
        return placesList;
    }
 
 
    private HashMap<String, String> getPlace(JSONObject jPlace){
 
        HashMap<String, String> place = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String formatted_address="-NA-";
        String formatted_phone="-NA-";
        String website="-NA-";
        String rating="-NA-";
        String international_phone_number="-NA-";
        String url="-NA-";
      
        String icon = "-NA-";
 
        try {
           
            if(!jPlace.isNull("name")){
                placeName = jPlace.getString("name");
            }
 
            if(!jPlace.isNull("icon")){
                icon = jPlace.getString("icon");
                
            }
            
            if(!jPlace.isNull("formatted_address")){
                formatted_address = jPlace.getString("formatted_address");
            }
 
            // Extracting Place formatted_phone, if available
            if(!jPlace.isNull("formatted_phone_number")){
                formatted_phone = jPlace.getString("formatted_phone_number");
            }
 
            // Extracting website, if available
            if(!jPlace.isNull("website")){
                website = jPlace.getString("website");
            }
 
            // Extracting rating, if available
            if(!jPlace.isNull("rating")){
                 rating = jPlace.getString("rating");
            }
            
            if(!jPlace.isNull("vicinity")){
                vicinity = jPlace.getString("vicinity");
            }
            
            if(!jPlace.isNull("url")){
                url = jPlace.getString("url");
            }
 
 
            latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
 
            place.put("place_name", placeName);
            place.put("vicinity", vicinity);
            place.put("lat", latitude);
            place.put("lng", longitude);
            place.put("icon", icon);
            
            place.put("formatted_address", formatted_address);
            place.put("formatted_phone", formatted_phone);
            place.put("website", website);
            place.put("rating", rating);
            place.put("url", url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return place;
    }
}
