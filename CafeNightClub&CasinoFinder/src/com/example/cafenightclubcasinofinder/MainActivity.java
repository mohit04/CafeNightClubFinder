package com.example.cafenightclubcasinofinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cafenightclubcasinofinder.Options.Selection;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity implements SlidingPaneLayout.PanelSlideListener,Selection {

	 Nearby_place myf;
	SlidingPaneLayout pane;
	String title;
	ImageView appImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
      
	    myf = new Nearby_place();
	    Log.i("main_activity", "before nearby_transaction");
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.mainframe,myf,"nearby");
		transaction.commit();
		
		
	
		
		Log.i("main_activity", "after nearby_transaction");
		//FrameLayout fl = new FrameLayout(this);
		//fl.setLayoutParams(params)
		//setContentView(R.layout.activity_main);
		pane = (SlidingPaneLayout) findViewById(R.id.sp);
		pane.setPanelSlideListener(this);
		pane.setParallaxDistance(200);
		pane.openPane();
		title="Nearby";
		  appImage = (ImageView)findViewById(android.R.id.home);
		getActionBar().setDisplayShowHomeEnabled(true);
	       getActionBar().setHomeButtonEnabled(true);
	       
	       int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
	       
	       if(status!=ConnectionResult.SUCCESS){ 
	    	   
	            int requestCode = 10;
	            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	            dialog.show();
	 
	        }else { 
	        	
	       // 	LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	        	ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	        	        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	        	        // if no network is available networkInfo will be null
	        	        // otherwise check if we are connected
	        	        if ((networkInfo != null && networkInfo.isConnected())) {
	        	        	 Toast.makeText(this, "Data is Enabled in your device", Toast.LENGTH_SHORT).show();
	        	        }
	                   else{
	                showDataDisabledAlertToUser();
	            }
	        }
	            
	      

	      // getActionBar().setDisplayHomeAsUpEnabled(true);
		
	}

	

	private void showDataDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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
	public void onPanelClosed(View arg0) {
		// TODO Auto-generated method stub
		System.out.println("Panel closed");
		//getFragmentManager().findFragmentById(R.id.rightpane).setHasOptionsMenu(true);
		myf.setHasOptionsMenu(true);
		getActionBar().setTitle(title);
		getActionBar().setLogo(R.drawable.ic_action_sort_by_size);
		
	
		
	}

	@Override
	public void onPanelOpened(View arg0) {
		// TODO Auto-generated method stub
		System.out.println("Panel opened");
		/*FragmentTransaction ft2 = getFragmentManager().beginTransaction();

		
	    ft2.remove( getFragmentManager().findFragmentById(R.id.map));
	    ft2.commit();*/
		myf.setHasOptionsMenu(false);
		getActionBar().setTitle(getString(R.string.app_name));
		getActionBar().setLogo(R.drawable.ic_action_forward);
	
		
	}

	@Override
	public void onPanelSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub
		System.out.println("Panel sliding");
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        case android.R.id.home:
            if(pane.isOpen()){
             //  appImage.animate().rotation(360);
                pane.closePane();
                getActionBar().setLogo(R.drawable.ic_action_sort_by_size);
    getActionBar().setTitle(title);
            }
            else{
             //  appImage.animate().rotation(360);
                pane.openPane();
                getActionBar().setLogo(R.drawable.ic_action_forward);
                getActionBar().setTitle(R.string.app_name);
            }
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onSelection(int position) {
		// TODO Auto-generated method stub
		if(position==0)
		{
			Log.i("onSelection", "before nearby_transaction");
			 myf = new Nearby_place();

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.mainframe,myf,"nearby");
			transaction.commit();
			title="Nearby";
			pane.closePane();
			
		}
		
		if(position==1)
		{
			Log.i("onSelection", "before favourite_transaction_1");
			Favourites myf = new Favourites();

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.mainframe,myf,"favourites");
			transaction.commit();
			title="My Favourites";
			pane.closePane();
		}
		
	}
}
