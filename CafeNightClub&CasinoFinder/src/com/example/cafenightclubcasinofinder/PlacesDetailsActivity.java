package com.example.cafenightclubcasinofinder;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesDetailsActivity extends Activity {

	 protected void onCreate(Bundle savedInstanceState) {
		 
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.places_details);
	        final HashMap<String,String> hm = (HashMap<String, String>) getIntent().getSerializableExtra("place_details");
	        
	        TextView Pname= (TextView)findViewById(R.id.plc_name);
	        Pname.setText(hm.get("place_name"));
	        
	        TextView Pdist= (TextView)findViewById(R.id.distance);
	        Pdist.setText(hm.get("distance"));
	        
	    //    TextView Padd= (TextView)findViewById(R.id.address);
	      //  Padd.setText(hm.get("formatted_address"));
	       
	        TextView Pvic= (TextView)findViewById(R.id.vicinity);
	        Pvic.setText(hm.get("vicinity"));
	        
	        TextView Prat= (TextView)findViewById(R.id.rating);
	        Prat.setText(hm.get("rating"));
	        
	        ImageButton ph = (ImageButton)findViewById(R.id.phone);
	        ph.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String phone=hm.get("formatted_phone_number");
					StringBuilder sb1= new StringBuilder();
					sb1.append("tel:");
					sb1.append(phone);
					Intent intent = new Intent(Intent.ACTION_DIAL);
					intent.setData(Uri.parse(sb1.toString()));
					startActivity(intent); 
					
				}
			});
	        
	        ImageButton navig = (ImageButton)findViewById(R.id.navigation);
	        navig.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 double lat = Double.parseDouble(hm.get("lat"));
		                double lng = Double.parseDouble(hm.get("lng"));
		                StringBuilder sb1= new StringBuilder();
						sb1.append("http://maps.google.com/maps?&daddr=");
						sb1.append(Double.toString(lat));
						sb1.append(",");
						sb1.append(Double.toString(lng));
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						  Uri.parse(sb1.toString()));
						startActivity(intent);
				}
			});
	        
	        
	        ImageButton web = (ImageButton)findViewById(R.id.web);
	        navig.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phone=hm.get("website");
					if(phone=="-NA-")
					{
					
					}
					else
					{
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(phone));
						startActivity(i);
					}
				}
			});
	        
	        
	        ImageButton gplus = (ImageButton)findViewById(R.id.googleplus);
	        navig.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String gplus=hm.get("url");
					if(gplus=="-NA-")
					{
					
					}
					else
					{
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(gplus));
						startActivity(i);
					}
					
				}
			});
	        
	        // Getting reference to WebView ( wv_place_details ) of the layout activity_place_details
	    //    mWvPlaceDetails = (WebView) findViewById(R.id.wv_place_details);
	 
	      //  mWvPlaceDetails.getSettings().setUseWideViewPort(false);
	 
	        // Getting place reference from the map
	        //String reference = getIntent().getStringExtra("reference");
	 
	       // StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
	       // sb.append("reference="+reference);
	      //  sb.append("&sensor=true");
	       // sb.append("&key=YOUR_BROWSER_API_KEY_FOR_PLACES");
	 
	        // Creating a new non-ui thread task to download Google place details
	        //PlacesTask placesTask = new PlacesTask();
	 
	        // Invokes the "doInBackground()" method of the class PlaceTask
	       // placesTask.execute(sb.toString());
	 
	    };
}
