package com.example.cafenightclubcasinofinder;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String>{
	
private final Activity context;
private final String[] options;
private final Integer[] imageId;

public CustomList(Activity context,String[] options, Integer[] imageId) {
super(context, R.layout.list_single, options);
this.context = context;
this.options = options;
this.imageId = imageId;
}

@Override
public View getView(int position, View view, ViewGroup parent) {
LayoutInflater inflater = context.getLayoutInflater();
View rowView= inflater.inflate(R.layout.list_single, null, true);
TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
txtTitle.setText(options[position]);
imageView.setImageResource(imageId[position]);
return rowView;
}
}
