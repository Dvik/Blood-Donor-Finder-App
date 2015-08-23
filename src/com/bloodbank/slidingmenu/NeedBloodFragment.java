package com.bloodbank.slidingmenu;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ldialogbox.CustomDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NeedBloodFragment extends Fragment {
	private ProgressDialog pDialog;
	ListView listView1;
	private DatePicker datePicker;
	private Calendar calendar;
	private TextView dateView;
	private int year, month, day;
	Fragment fragment;
	private SQLiteHandler db=null;
	HashMap<String, String> hms;
	
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    //ArrayList<HashMap<String, String>> associatesList;
    
    JSONArray donors = null;
    
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DONORS = "donors";
    
    List<NeedBloodItem> donorslist = new ArrayList<NeedBloodItem>();
	
    
    public NeedBloodFragment(){}
    //private String spinner_sort_by[];

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_need_blood, container, false);
        
        //getActivity().getActionBar().setTitle("Need Blood");
    	//MainActivity.changeDrawerItem(1);
    	/*
        spinner_sort_by= new String[4];
        spinner_sort_by[0]="Proximity";
        spinner_sort_by[1]="Reliability";
        spinner_sort_by[2]="Age";
        spinner_sort_by[3]="Weight";
      
        Spinner s=(Spinner)rootView.findViewById(R.id.spinner_type);
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinner_sort_by);
        s.setAdapter(adapter);
      */  
        new LoadAlldonors().execute();
        
        listView1 = (ListView)rootView.findViewById(R.id.listViewNeed);
        

        listView1.setOnItemClickListener(new OnItemClickListener() {
      	  public void onItemClick(AdapterView<?> parent, View view,
      	    int position, long id) {
      		  showCustomDialog(rootView,donorslist,position,NeedBloodFragment.this.getActivity().getApplicationContext());
      	  }
      	});         
        
           
        return rootView;
    }
	
	
	class LoadAlldonors extends AsyncTask<String, String, String> {
		 
	    /**
	     * Before starting background thread Show Progress Dialog
	     * */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading donors. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

	       }

	    /**
	     * getting All associates from url
	     * */
	    protected String doInBackground(String... args) {
	        // Building Parameters
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        
	        String select="A";
	        
	        if(AppConfig.type==0)
	        	select="A+";
	        
	        if(AppConfig.type==1)
	        	select="B+";
	        
	        if(AppConfig.type==2)
	        	select="A";
	        
	        if(AppConfig.type==3)
	        	select="O";
	        
	        
	        params.add(new BasicNameValuePair("need", "n"));
	        params.add(new BasicNameValuePair("bgroup", select));
            params.add(new BasicNameValuePair("candonate","yes" ));
            params.add(new BasicNameValuePair("reqLatitude","20.8" ));
            params.add(new BasicNameValuePair("reqLongitude","40.9" ));
 
	        // getting JSON string from URL
	        Log.d("ip: ", AppConfig.URL_DONORS);
	        JSONObject json = jParser.makeHttpRequest(AppConfig.URL_DONORS, "POST", params);

	        // Check your log cat for JSON reponse
	        Log.d("All donors: ", json.toString());
	        
	        try {
	            // Checking for SUCCESS TAG
	            int success = json.getInt(TAG_SUCCESS);

	            if (success == 1) {
	                // donors found
	                // Getting Array of donors
	                donors = json.getJSONArray(TAG_DONORS);

	                // looping through All donors
	                for (int i = 0; i < donors.length(); i++) 
	                {
	                    JSONObject c = donors.getJSONObject(i);

	                    // Storing each json item in variable
	                    MainActivity1.donorUID = c.getInt("id");
	                    String name = c.getString("name");
	                    String email = c.getString("email");
	                    String gender = c.getString("gender");
	                    String bgroup = c.getString("bgroup");
	                    String contact = c.getString("phone");
	                    String country = c.getString("country");
	                    String state = c.getString("state");
	                    String city = c.getString("city");
	                    String address = c.getString("address");
	                    String description = c.getString("description");
	                    String weight = c.getString("weight");
	                    String age = c.getString("age");
	                    String distance = c.getString("distance");
	                    
	                    // creating new HashMap
	                    donorslist.add(new NeedBloodItem(name,email,gender,bgroup,contact,country,state,city,
	                    		address,description,weight,age,distance));
	                    	                    
	                }
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
	    	pDialog.dismiss();
	        // updating UI from Background Thread
	    	getActivity().runOnUiThread(new Runnable(){
	            public void run() {
	                /**
	                 * Updating parsed JSON data into ListView
	                 * */
	            	 ListAdapterNeedItem adapter = new ListAdapterNeedItem(getActivity(),
	                         R.layout.need_list_row, donorslist);
	                 listView1.setAdapter(adapter);
	                 
	            }
	        });

	    }

	}
	protected void showCustomDialog(View v,final List<NeedBloodItem> needlist,final int position,Context cnxt) {
        // TODO Auto-generated method stub
		  
	    calendar = Calendar.getInstance();
	    year = calendar.get(Calendar.YEAR);
	      
	    month = calendar.get(Calendar.MONTH);
	    day = calendar.get(Calendar.DAY_OF_MONTH);
	    
	    
	    CustomDialog.Builder builder = new CustomDialog.Builder(getActivity(),needlist.get(position).name, "Make Appointment");
	    builder.negativeText("Call");
	    builder.darkTheme(true);
	    
	    View vm = getActivity().getLayoutInflater().inflate(R.layout.need_item_dialog, null);
        
        TextView tv1 = (TextView)vm.findViewById(R.id.dialog_tv1);
        TextView tv2 = (TextView)vm.findViewById(R.id.dialog_tv2);
        TextView tv3 = (TextView)vm.findViewById(R.id.dialog_tv3);
        
        tv1.setText(needlist.get(position).name);
        tv2.setText(needlist.get(position).phone);
        
        builder.negativeColor("#00CCFF");
        builder.positiveColor("#00CCFF");
        builder.titleColor("#00CCFF");
        
	    final CustomDialog customDialog = builder.build();
	    
	    

	    customDialog.setCustomView(vm);
	    
        //final Dialog dialog = new Dialog(getActivity());
        
        //dialog.setContentView(R.layout.need_item_dialog);
        
       
        PhoneCallListener phoneListener = new PhoneCallListener(cnxt);
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
 
		customDialog.setClickListener(new CustomDialog.ClickListener() {
            @Override
            public void onConfirmClick() {

            	customDialog.dismiss();
                
                fragment = new AppointmentForm();
                
                if (fragment != null) {
        			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        			fragmentManager.beginTransaction()
        					.replace(R.id.container_body, fragment).addToBackStack(null).commit();
        			
        		} else {
        			// error in creating fragment
        			Log.e("MainActivity", "Error in creating fragment");
        		}
            }

            @Override
            public void onCancelClick() {

            	Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+needlist.get(position).phone.toString()));
				startActivity(callIntent);
				
            	new addBloodRequest().execute();
            	
            	
                customDialog.dismiss();
            }
        });
		
		
        customDialog.show();
    }
	 
	class addBloodRequest extends AsyncTask<String, String, String> {
		 
		JSONParser jsonParser = new JSONParser();
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	
           
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("request", "re"));
            params.add(new BasicNameValuePair("donorUID", "12"));
            params.add(new BasicNameValuePair("reqUID", "1"));
            params.add(new BasicNameValuePair("bloodgroup", "a+"));
            params.add(new BasicNameValuePair("reqNAME", "fdee"));
            params.add(new BasicNameValuePair("stateName", "dsds"));
            params.add(new BasicNameValuePair("city", "svver"));
            params.add(new BasicNameValuePair("needDate", "1961-01-01"));
            params.add(new BasicNameValuePair("reqPhone", "8888"));
            params.add(new BasicNameValuePair("donPhone", "1222"));

 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(AppConfig.URL_REQUEST,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	getActivity().runOnUiThread(new Runnable() {              
                	    @Override
                	    public void run() {                         
                	    	Toast.makeText(getActivity().getApplicationContext(),
                                    "Request Added to Database", Toast.LENGTH_LONG)
                                    .show();                	    }
                	});
                	 
                	Log.d("Success","Request Added to Database");
                    
                } else {
                	getActivity().runOnUiThread(new Runnable() {              
                	    @Override
                	    public void run() {                         
                	    	 Toast.makeText(getActivity().getApplicationContext(),
                                     "Request Can't be Added to Database", Toast.LENGTH_LONG)
                                     .show();
                        	Log.e("Error","Request could not be added to Database");                	    }
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
            
        }
 
    }

	
class PhoneCallListener extends PhoneStateListener {
	 
	private boolean isPhoneCalling = false;

	String LOG_TAG = "LOGGING 123";
	
	private Context context;
	
	public PhoneCallListener(Context context)
	{
		this.context=context;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {

		if (TelephonyManager.CALL_STATE_RINGING == state) {
			// phone ringing
			Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
		}

		if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
			// active
			Log.i(LOG_TAG, "OFFHOOK");

			isPhoneCalling = true;
		}

		if (TelephonyManager.CALL_STATE_IDLE == state) {
			// run when class initial and phone call ended, 
			// need detect flag from CALL_STATE_OFFHOOK
			Log.i(LOG_TAG, "IDLE");

			if (isPhoneCalling) {

				Log.i(LOG_TAG, "restart app");
				
				isPhoneCalling = false;
			}

		}
	}
}

}
