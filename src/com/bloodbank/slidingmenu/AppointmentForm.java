package com.bloodbank.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AppointmentForm extends Fragment{
	DatePicker dp;
	TimePicker tp;
	EditText et1,et2,et3;
	private ProgressDialog pDialog;
	Button btn;
	Fragment fragment;
	private SQLiteHandler db=null;
	HashMap<String, String> hms;
	String meetingPlace,meetingTime,meetingDate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.appointment_form, container, false);
	        //getActivity().getActionBar().setTitle("Appointment Form");
	    	//MainActivity.changeDrawerItem(1);
	    	
	    	dp= (DatePicker) rootView.findViewById(R.id.datePicker1);
	
	    	
	    	
	    	tp=(TimePicker) rootView.findViewById(R.id.timePicker1);
	    	
	    	et1 = (EditText) rootView.findViewById(R.id.form_appoint_state);
	    	et2 = (EditText) rootView.findViewById(R.id.form_appoint_city);
	    	et3 = (EditText) rootView.findViewById(R.id.form_appoint_address);
	    	
	    	btn = (Button) rootView.findViewById(R.id.form_appoint_btn);
	    	 
	    	btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

			    	meetingDate=dp.getDayOfMonth()+" "+dp.getMonth()+" "+dp.getYear();
			
			    	meetingTime=tp.getCurrentHour()+" "+tp.getCurrentMinute();
			    	
			    	meetingPlace= et1.getText().toString()+" "+et2.getText().toString()+" "+et3.getText().toString();
				
			    	new addAppointment().execute();
				}
			});
	        return rootView;

}
	
	class addAppointment extends AsyncTask<String, String, String> {
		 
		JSONParser jsonParser = new JSONParser();
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Creating Appointment. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	
           
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            db = new SQLiteHandler(getActivity().getApplicationContext());
            db.getReadableDatabase();
            hms=new HashMap<String, String>();
            hms = db.getUserDetails();
            
            String id = hms.get("id");
            Integer uid = MainActivity1.donorUID;
            
            params.add(new BasicNameValuePair("meet", "m"));
            params.add(new BasicNameValuePair("donorUID", uid.toString()));
            params.add(new BasicNameValuePair("reqUID", id));
            params.add(new BasicNameValuePair("donPhone", "75757575"));
            params.add(new BasicNameValuePair("reqPhone", "646464646"));
            params.add(new BasicNameValuePair("reqNAME", "fdee"));
            params.add(new BasicNameValuePair("bloodgroup", "o"));
            params.add(new BasicNameValuePair("meetingDate", meetingDate));
            params.add(new BasicNameValuePair("meetingTime", meetingTime));
            params.add(new BasicNameValuePair("meetingPlace", meetingPlace));
            params.add(new BasicNameValuePair("status", "finalised"));
            

 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(AppConfig.url_create_appointment,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt("success");
 
                if (success == 1) {
                	getActivity().runOnUiThread(new Runnable() {              
                	    @Override
                	    public void run() {                         
                	    	Toast.makeText(getActivity().getApplicationContext(),
                                    "Appointment Added to Database", Toast.LENGTH_LONG)
                                    .show();                	    }
                	});
                	 
                	Log.d("Success","Appointment Added to Database");
                    
                } else {
                	getActivity().runOnUiThread(new Runnable() {              
                	    @Override
                	    public void run() {                         
                	    	 Toast.makeText(getActivity().getApplicationContext(),
                                     "Appointment Can't be Added to Database", Toast.LENGTH_LONG)
                                     .show();
                        	Log.e("Error","Appointment could not be added to Database");                	    }
                	});
                	
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            
            fragment = new AppointmentsFragment();
            
            if (fragment != null) {
    			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    			fragmentManager.beginTransaction()
    					.replace(R.id.container_body, fragment).addToBackStack(null).commit();

    			
    		} else {
    			// error in creating fragment
    			Log.e("MainActivity", "Error in creating fragment");
    		}
        }
 
    }
}