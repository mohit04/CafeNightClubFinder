package com.example.cafenightclubcasinofinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Options extends Fragment {

	ListView listv;
	int pos1;
	String[] optionsar={"Nearby Places","Favourites"};

	Integer[] imageId = {
		      R.drawable.ic_launcher,
		      R.drawable.ic_launcher};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.options, container, true);
	//	ArrayAdapter<String> listadap = new ArrayAdapter<String>(this,,arr);
		 
		//        listv.setAdapter(listadap);
		
		CustomList adapter = new
		        CustomList(getActivity(), optionsar, imageId);
		    listv=(ListView)v.findViewById(R.id.listview);
		        listv.setAdapter(adapter);
		        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		                @Override
		                public void onItemClick(AdapterView<?> parent, View view,
		                                        int position, long id) {
		                	pos1=position;
		                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +optionsar[+ position], Toast.LENGTH_SHORT).show();
		                    ( (Selection) getActivity()).onSelection(position);
		                }
		            });
		 
		return v;
	}
	
	public interface Selection {
		public void onSelection(int pos1);
	}

}
