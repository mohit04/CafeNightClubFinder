package com.example.cafenightclubcasinofinder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Pl_List extends ArrayAdapter<String>{
	
private final Activity context;
private final String[] placesName;
private final ArrayList<HashMap<String, String>> alist;


public Pl_List(Activity context,ArrayList<HashMap<String, String>> alist,String[] places_name) {
super(context, R.layout.pl_list, places_name);
this.context = context;
this.placesName = places_name;
this.alist=alist;

}


@Override
public View getView(int position, View view, ViewGroup parent) {
LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.pl_list, null, true);
TextView txtTitle = (TextView) rowView.findViewById(R.id.plc_name);
//ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
txtTitle.setText(placesName[position]);
//imageView.setImageResource(imageId[position]);

  HashMap<String,String>  hmPlace2 = alist.get(position);
  // setDistance(dist,position);
   TextView dis = (TextView) rowView.findViewById(R.id.distance);
   dis.setText(hmPlace2.get("distance"));
  
  
return rowView;
}



}