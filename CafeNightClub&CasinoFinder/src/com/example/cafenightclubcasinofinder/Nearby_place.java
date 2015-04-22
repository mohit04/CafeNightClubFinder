package com.example.cafenightclubcasinofinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Nearby_place extends Fragment implements LocationListener{

	Bitmap bmp;
	Spinner mSprPlaceType;
	String place_name;
	 MapView m;
	 GoogleMap map;
	
	String[] mPlaceType={"cafe","night_club","casino"};
	String[] mPlaceTypeName={"Cafe","Night Club","Casino"};
	public String type;
	Button btnFind;
	Button list_button;
	
	ArrayList<HashMap<String,String>> newList;
	
	double mLatitude=0;
   double mLongitude=0;

   
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("oncreate view", container.toString());
		//int n=container.getChildCount();
		//Log.i("child_count",Integer.toString(n));
		
		View v = inflater.inflate(R.layout.nearby, container,false);
	 /*   n=v1.getChildCount();
	    Log.i("child_count",Integer.toString(n));
	    for(int i=0;i<n;i++)
		{
			Log.i("container's child", v1.getChildAt(i).toString());
			v=v1.getChildAt(i);
			
		}*/
	
	
		
	//	container.addView(v);
		
		
		
		m = (MapView) v.findViewById(R.id.map);
		Log.i("map_parent",m.getParent().toString());
	    m.onCreate(savedInstanceState);

		
//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);
		
	//	mSprPlaceType = (Spinner) v.findViewById(R.id.spr_place_type);
		
      //  mSprPlaceType.setAdapter(adapter);
        
       
        
        

        Log.i("bwahhh","before_get_map");
        map = m.getMap();
        Log.i("bwahhh","after_get_map");
        MapsInitializer.initialize(getActivity());
        Log.i("bwahhh","after_map_initializer");
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.setMyLocationEnabled(true);
		map.setInfoWindowAdapter(new InfoWindowAdapterMarker());
		
		 LocationManager locationManager1 = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
         
         Criteria criteria = new Criteria();
          
         String provider = locationManager1.getBestProvider(criteria, true);

         Location location = locationManager1.getLastKnownLocation(provider);

         if(location!=null){
             onLocationChanged(location);
         }

         locationManager1.requestLocationUpdates(provider,5000, 0,this);
         
        
		
		return v;
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public class InfoWindowAdapterMarker implements InfoWindowAdapter {

		@Override
		public View getInfoContents(Marker marker) {
			// TODO Auto-generated method stub
			 LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
	            
	            // Getting view from the layout file info_window_layout
	            View popUp = inflater.inflate(R.layout.layout_popup,null);
	            TextView popUpTitle = (TextView) popUp.findViewById(R.id.textView1);
	        //    ImageView popUpContent = (ImageView) popUp.findViewById(R.id.imageView1);
	          //  popUpContent.seti
	            popUpTitle.setText(marker.getTitle());
	            
	            float[] distance = new float[1];
	           // Location currentLocation = PECApplication.getInstance().getLocationClient().getLastLocation();
	            Location.distanceBetween(mLatitude,mLongitude, marker.getPosition().latitude, marker.getPosition().longitude, distance);
	            float disInKm=distance[0]/(float)1000;
	            
	            DecimalFormat df=new DecimalFormat("#.#");
	            String formatted=df.format(disInKm);
	            TextView distanceView = (TextView) popUp.findViewById(R.id.textView2);
	            distanceView.setText(formatted);
	            
	            
	         
	         
	           
	           
	            
			return popUp;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			
	          
	            
			return null;
		}
	
	}
	
	
	
	
	private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
           
            urlConnection = (HttpURLConnection) url.openConnection();
 
           
            urlConnection.connect();
 
            
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb  = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
 
        return data;
    }
    
  
	
	
	private class PlacesTask extends AsyncTask<String, Integer, String>{
		 
        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        
        @Override
        protected void onPostExecute(String result){
          ParserTask parserTask = new ParserTask();
 
      
            parserTask.execute(result);
        }
 
    }
    
	
	 private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{
		 
	        JSONObject jObject;
	 
	       
	        @Override
	        protected List<HashMap<String,String>> doInBackground(String... jsonData) {
	 
	            List<HashMap<String, String>> places = null;
	            PlaceJSONParser placeJsonParser = new PlaceJSONParser();
	 
	            try{
	                jObject = new JSONObject(jsonData[0]);
	 
	              
	                places = placeJsonParser.parse(jObject);
	 
	            }catch(Exception e){
	                Log.d("Exception",e.toString());
	            }
	            return places;
	        }
	 
	        @Override
	        protected void onPostExecute(List<HashMap<String,String>> list){
	 
	            newList = new ArrayList<HashMap<String,String>>(list);
	            HashMap<String, String> current = new HashMap<String, String>();
	            current.put("latitude",Double.toString(mLatitude));
	            current.put("longitude",Double.toString(mLongitude));
	            newList.add(current);
	            map.clear();
	 
	            for(int i=0;i<list.size();i++){
	 
	            	
	            
	                MarkerOptions markerOptions = new MarkerOptions();
	 
	              HashMap<String,String>  hmPlace = list.get(i);
	 
	                double lat = Double.parseDouble(hmPlace.get("lat"));
	 
	                double lng = Double.parseDouble(hmPlace.get("lng"));
	 
	                 String name = hmPlace.get("place_name");
	                 String icon_url=hmPlace.get("icon");
	                // String icon=  hmPlace.get("icon");
	            //    String vicinity = hmPlace.get("vicinity");
	 
	                LatLng latLng = new LatLng(lat, lng);
	 
	                markerOptions.position(latLng);
	            //    markerOptions.
	                
	                markerOptions.title(name);
	              
	                //Log.i("parser_task", "before icontask");
	              //  IconTask icont = new IconTask();
	                //Log.i("parser_task", "before execute");
	                
	                //icont.execute(icon_url);
	                //Log.i("parser_task", "after execute");
	                //if(bmp!=null)
	              //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bmp));
	               // else
	              //markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	              
	                
	                

	             
	              //  if(type=="airport")
	                	
	                	
	               if(type=="cafe")
	                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurants));
	               if(type=="night_club")
	            	   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	               if(type=="casino")
	            	   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	              /*  if(type=="bank")
	                  	markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	                         	
	                if(type=="bus_station")
	                 	markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.transport));
	                             	
	                if(type=="church")
	                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	                                 	
	                if(type=="doctor")
	                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.doctors));
	                                     	
	                if(type=="hospital")
	                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.medical));
	                                         	
	                if(type=="mosque")
	                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	                                             	
	                if(type=="movie_theatre")
	                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.movies));
	                                                 	
	                if(type=="hindu_temple")
	                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def));
	                                                     	
	                if(type=="restaurant")
	                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurants));
	                   
	                if(type=="taxi_stand")
	                   markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.automotive));
	                   
	                 if(type=="train_station")
	                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.def)); 

	                 if(type=="metro_station")
	                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tours));
	                        
	                 if(type=="shopping_mall")
	                     markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.shopping));*/
	 
	                // Placing a marker on the touched position
	                map.addMarker(markerOptions);
	                
	            }
	        }
	    }
	 
	 
	/* private class IconTask extends AsyncTask <String,Void,Void>
	 {
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();

	}
	
	@Override
	protected Void doInBackground(String... icon_url) {
		// TODO Auto-generated method stub
		  URL url ;
		    try {
		    url = new URL(icon_url[0]);
		    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		    Log.i("ibackend","in try");
		    } catch (Exception e) {
		    	Log.i("ibackend","in catch");		   
		      e.printStackTrace();
		      
		         }
		return  null;
	}

	@Override
	protected void onPostExecute(Void result) {
    Log.i("on execute", "before super");
	super.onPostExecute(result);
	 Log.i("parser_task", "after super");
	                                      
	    }
	
	}*/

	 
	 @Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			inflater.inflate(R.menu.right_menu, menu);
			mSprPlaceType = (Spinner)menu.findItem(R.id.spinner).getActionView();
			btnFind=(Button)menu.findItem(R.id.btn_find).getActionView();
			final MenuItem list_item = menu.findItem(R.id.btn_getList);
			list_item.setVisible(false);
			
	 btnFind.setOnClickListener(new OnClickListener() {
	          	 
	             @Override
	             public void onClick(View v) {

	                 int selectedPosition = mSprPlaceType.getSelectedItemPosition();
	                 type = mPlaceType[selectedPosition];

	                 StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
	                 sb.append("location="+mLatitude+","+mLongitude);
	                 sb.append("&radius=5000");
	                 sb.append("&types="+type);
	                 sb.append("&sensor=true");
	                 sb.append("&key=AIzaSyA2_1L9RA_ZB0oD2tEGkQiV5MufXJ1Ts9M");
	                 
	                 PlacesTask placesTask = new PlacesTask();

	                 placesTask.execute(sb.toString());
	                 
	              //  menu.add(0,0,0,"GetList");
	                 list_item.setVisible(true);
	                 list_item.setTitle("Get List");
	                 list_button = (Button) list_item.getActionView();
	                 list_button.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(getActivity(),GetList.class);
							i.putExtra("pl_list",newList);
							startActivity(i);
						}
	                	 
	                 });   
	             }
	         });
	      
		}
        
        @Override
	public void onResume() {
		// TODO Auto-generated method stub
        	m.onResume();
		super.onResume();
		

	}

    


		private void showDataDisabledAlertToUser(){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage("Data is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                    new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    Intent callDataSettingIntent = new Intent(
                            android.provider.Settings.ACTION_SETTINGS);
                    startActivity(callDataSettingIntent);
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
        
		@Override
		public void onLowMemory() {
			super.onLowMemory();
			m.onLowMemory();
		}
        


	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.i("ondestroy view","before removing map fragment");
		super.onDestroyView();
		
		 
	}
	
	public void onDestroy(){
		Log.i("ondestroy ","before removing map fragment");
		
	    super.onDestroy();
	    m.onDestroy();
	    
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.i("onpause","before super");
		 super.onPause();
		 m.onPause();

		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
        LatLng latLng = new LatLng(mLatitude, mLongitude);
 
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(12));
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
