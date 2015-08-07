package com.bloodbank.slidingmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity
{
	private String array_spinner[];
	Button b1;
	private SessionManager session;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		   requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    setContentView(R.layout.first_screen);
		    
		    GPSTracker gps = new GPSTracker(this);
	        if(gps.canGetLocation()){ 
	        	
	        	Double d1= gps.getLatitude();
	        	MainActivity1.latitude = d1.toString();
	        	//t1.setText(d1.toString()); // returns latitude
	        	Double d2= gps.getLongitude();
	        	MainActivity1.longitude = d2.toString();
	        	//t2.setText(d2.toString()); // returns longitude
	        }
	        else
	        {
	        	gps.showSettingsAlert();
	        }
		  
		    session = new SessionManager(getApplicationContext());
		 
		    // Check if user is already logged in or not
	        if (session.isLoggedIn()) {
	            // User is already logged in. Take him to main activity
	            Intent intent = new Intent(HomeActivity.this,
	                    MainActivity1.class);
	            startActivity(intent);
	            finish();
	        }
		    b1=(Button)findViewById(R.id.signup);
	        b1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				      Intent i = new Intent(HomeActivity.this,RegisterActivity1.class);
				      startActivity(i);
				      finish();
					
				}
			});
	        Button b2=(Button)findViewById(R.id.loginbutton);
	        
	        b2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				      Intent i = new Intent(HomeActivity.this,LogInActivity.class);
				      startActivity(i);
				      finish();
					
				}
			});
		    
	}

}
