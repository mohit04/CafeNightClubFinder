package com.example.cafenightclubcasinofinder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.cafenightclubcasinofinder.Options.Selection;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GetList extends Activity {
	
	String pla_name[]= new String[20];
	String name="-NA";
	ArrayList<HashMap<String, String>> arl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_places);
		 arl = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("pl_list");
		 int n=arl.size();
		 Log.i("arl.size",Integer.toString(n));
		 
		 HashMap<String,String>  hmPlace = arl.get(n-1);
         double mlat = Double.parseDouble(hmPlace.get("latitude"));
         double mlng = Double.parseDouble(hmPlace.get("longitude"));
         Log.i("my_lat", Double.toString(mlat));
         Log.i("my_lng", Double.toString(mlng));
         
         arl.remove(n-1);
         int n1=arl.size();
         Log.i("arl.size",Integer.toString(n1));
          for(int i=0;i<n1;i++)
          {
        	  HashMap<String,String>  hmPlace1 = arl.get(i);
        	   name = hmPlace1.get("place_name");
        	  Log.i("places_name",name);
        	 pla_name[i]=name;
        	 Log.i("place_name",pla_name[i]);
        	 
        	 double lat = Double.parseDouble(hmPlace1.get("lat"));
        	 double lng = Double.parseDouble(hmPlace1.get("lng"));
        	  
        	  
        	  float[] distance = new float[1];
        	  // Location currentLocation = PECApplication.getInstance().getLocationClient().getLastLocation();
        	   Location.distanceBetween(mlat,mlng,lat,lng, distance);
        	   float disInKm=distance[0]/(float)1000;
        	   
        	   DecimalFormat df=new DecimalFormat("#.#");
        	   String formatted=df.format(disInKm);
        	   StringBuilder sb= new StringBuilder();
        	   sb.append("-");
        	   sb.append(formatted);
        	   sb.append("Km");
        	   String dist=sb.toString();
        	   hmPlace1.put("distance",dist);
        	 
          }
          
          
          Pl_List adapter = new Pl_List(this,arl,pla_name);
          
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		   // lv.setAdapter(adapter)
		lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                	HashMap<String,String> hm = arl.get(position);
                	Intent i = new Intent(GetList.this,PlacesDetailsActivity.class);
                    i.putExtra("place_details",hm);
                	startActivity(i);
                  //  Toast.makeText(this, "You Clicked at " +optionsar[+ position], Toast.LENGTH_SHORT).show();
                   // ( (Selection) getActivity()).onSelection(position);
                }
            });
	}
	
	
	

}
