package com.bloodbank.slidingmenu;

import java.util.HashMap;

import com.example.rateapp.RateThisApp;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity1 extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
	private FragmentDrawer drawerFragment;
	private Toolbar mToolbar;
	 private SQLiteHandler db;
	    private SessionManager session;
	    LocationManager ln;
	    ImageButton ib;
	  TextView tv1;
	    String provider;
	    public static int donorUID,reqUID;
		public static String donPhone,reqPhone,reqNAME;
		public static String bloodgroup,meetingDate,meetingTime,meetingPlace,status;
		public static String latitude,longitude;
		
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        //getActionBar().hide();
        db = new SQLiteHandler(getApplicationContext());
		session = new SessionManager(getApplicationContext());
		if (!session.isLoggedIn()) {
            logoutUser();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        
        
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
 
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

       tv1 = (TextView) findViewById(R.id.nav_tv1);
       db.getReadableDatabase();
       HashMap hms=new HashMap<String, String>();
       hms = db.getUserDetails();
       tv1.setText((String) hms.get("name"));
       
       ib = (ImageButton) findViewById(R.id.ib1);
       
       ib.setOnClickListener(new OnClickListener() {
    	 
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			displayView(-1);
			
			
		}
	});
        /*GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()){ 
        	
        	Double d1= gps.getLatitude();
        	t1.setText(d1.toString()); // returns latitude
        	Double d2= gps.getLongitude();
        	t2.setText(d2.toString()); // returns longitude
        }
        else
        {
        	gps.showSettingsAlert();
        }*/
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
        
        drawerFragment.setDrawerListener(this);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
 
    
    
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
        case -1:
			fragment = new AccountFragment();
			break;
			
		case 0:
			fragment = new NeedBloodForm();
			break;
		case 1:
			fragment = new AppointmentsFragment();
			break;
		case 2:
			fragment = new AssociatesFragment();
			break;
	
		case 3:
			
			//fragment = new SuggestFragment();
			shareIt();
			break;
			
		case 4:
			RateThisApp.showRateDialog(MainActivity1.this);
			break;
		case 5:
			fragment = new ContactFragment();
			break;
			
		case 6:
			
			logoutUser();
			break;

		default:
			break;
		}

 
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
 
            // set the toolbar title
            getSupportActionBar().setTitle("Blood Donation App");
        }
    }
    
    private void logoutUser() {
        session.setLogin(false);
 
        db.deleteUsers();
 
        // Launching the login activity
        Intent intent = new Intent(MainActivity1.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

	 @Override
	 public void onBackPressed() {
		 
		 
	     

	 	    new AlertDialog.Builder(this)
	 	           .setMessage("Are you sure you want to exit?")
	 	           .setCancelable(true)
	 	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	 	               public void onClick(DialogInterface dialog, int id) {
	 	                    MainActivity1.this.finish();
	 	               }
	 	           })
	 	           .setNegativeButton("No", null)
	 	           .show();
	     
	  }

	 
	 private void shareIt()
	 {
		 Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		 sharingIntent.setType("text/plain");
		 String shareBody = "Donate blood and save life. Join the cause now at [playstore link] ";
		 sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		 startActivity(Intent.createChooser(sharingIntent, "Share Via"));
	 }
}
//SELECT latitude, longitude, 
//SQRT( POW(69.1 * (latitude - 30), 2) + 
//POW(69.1 * (79 - longitude) * COS(latitude / 57.3), 2)) 
//AS distance FROM userinfo ORDER BY distance
