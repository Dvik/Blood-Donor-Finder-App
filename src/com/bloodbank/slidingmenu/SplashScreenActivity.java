package com.bloodbank.slidingmenu;

import java.util.Timer;
import java.util.TimerTask;

import com.example.rateapp.RateThisApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
public class SplashScreenActivity extends Activity {
	 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RateThisApp.init(new RateThisApp.Config(3, 2));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	   
        setContentView(R.layout.activity_splash);
        
        Timer t = new Timer();
        boolean checkConnection=new Connectivity().checkConnection(this);
        //if (checkConnection) {
            t.schedule(new splash(), 3000);
        /*} else {
            
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    				context);
        	
        	alertDialogBuilder.setTitle("No Internet Connectivity Found");
        	alertDialogBuilder
			.setMessage("Click Yes to exit and No to move to connection settings")
			.setCancelable(false)
			.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					SplashScreenActivity.this.finish();
				}
			  })
			.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					  Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
					    startActivity(intent);
				}
			});
        	
        	AlertDialog alertDialog = alertDialogBuilder.create();
        	alertDialog.show();
            
        }*/
    }

    class splash extends TimerTask {

        @Override
        public void run() {
            Intent i = new Intent(SplashScreenActivity.this,HomeActivity.class);
            finish();
            startActivity(i);
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        // Monitor launch times and interval from installation
        RateThisApp.onStart(this);
        // Show a dialog if criteria is satisfied
        RateThisApp.showRateDialogIfNeeded(this);
    }
	
}