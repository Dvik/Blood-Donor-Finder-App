package com.bloodbank.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bloodbank.slidingmenu.AssociatesFragment.PhoneCallListener;
import com.example.ldialogbox.CustomDialog;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AppointmentsFragment extends Fragment{
	public String mid,requid,donoruid,donphone,reqphone,reqname,bloodgroup,meetingdate,meetingtime,meetingplace,status;
	 private ProgressDialog pDialog;
	   ListView listView1;
	   private SQLiteHandler db=null;
	   HashMap<String, String> hms;
	   JSONParser jParser = new JSONParser();
	 
	    //ArrayList<HashMap<String, String>> appointmentsList;
	    
	    JSONArray appointments = null;
	    
	    private static final String TAG_SUCCESS = "success";
	    
	 
List<AppointmentsItem> appointmentslist = new ArrayList<AppointmentsItem>();
	
	
	public AppointmentsFragment(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);
        
        //getActivity().getActionBar().setTitle("Appointments");
    	//MainActivity.changeDrawerItem(2);
    	
    	new LoadAllappointments().execute();
        
        listView1 = (ListView)rootView.findViewById(R.id.listappointments);
        
        
                listView1.setOnItemClickListener(new OnItemClickListener() {
                	  public void onItemClick(AdapterView<?> parent, View view,
                	    int position, long id) {
                		  showCustomDialog(appointmentslist,position,AppointmentsFragment.this.getActivity().getApplicationContext());
                	  }
                	}); 

                return rootView;
    }


	class LoadAllappointments extends AsyncTask<String, String, String> {
	 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Appointments. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
       }

    /**
     * getting All appointments from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        Log.d("ip: ", AppConfig.url_all_appointments);
        
        db = new SQLiteHandler(getActivity().getApplicationContext());
        db.getReadableDatabase();
        hms=new HashMap<String, String>();
        hms = db.getUserDetails();
        
        String id = hms.get("id");
        
        params.add(new BasicNameValuePair("allappointments", "al"));
        params.add(new BasicNameValuePair("id", id));
        
        
        JSONObject json = jParser.makeHttpRequest(AppConfig.url_all_appointments, "POST", params);

        // Check your log cat for JSON reponse
        Log.d("All appointments: ", json.toString());
        
        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // appointments found
                // Getting Array of appointments
                appointments = json.getJSONArray("meetings");

                // looping through All appointments
                for (int i = 0; i < appointments.length(); i++) 
                {
                    JSONObject c = appointments.getJSONObject(i);

                    // Storing each json item in variable
                    mid = c.getString("mid");
                    requid = c.getString("reqUID");
                    donoruid = c.getString("donorUID");
                    donphone = c.getString("donPhone");
                    reqphone = c.getString("reqPhone");
                    reqname = c.getString("reqNAME");
                    bloodgroup = c.getString("bloodGroup");
                    meetingdate = c.getString("meetingDate");
                    meetingtime = c.getString("meetingTime");
                    meetingplace = c.getString("meetingPlace");
                    status = c.getString("status");
                    
                    // creating new HashMap
                    appointmentslist.add(new AppointmentsItem(mid,requid,donoruid,donphone,reqphone
                    		,reqname,bloodgroup,meetingdate,meetingtime,meetingplace,status));
                    /*
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("name", name);
                    map.put("state", state);
                    map.put("contact", contact);
                    // adding HashList to ArrayList
                    appointmentsList.add(map);
                    */
                    
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
            	
            	 ListAdapterAppointments adapter = new ListAdapterAppointments(getActivity(),
                         R.layout.appoint_item_row, appointmentslist);
                 listView1.setAdapter(adapter);
                 
            }
        });

    }

}

	
	protected void showCustomDialog(final List<AppointmentsItem> appointmentslist,final int position,Context cnxt) {
        // TODO Auto-generated method stub
        /*final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        
        TextView tv1 = (TextView)dialog.findViewById(R.id.dialog_tv1);
        TextView tv2 = (TextView)dialog.findViewById(R.id.dialog_tv2);
        TextView tv3 = (TextView)dialog.findViewById(R.id.dialog_tv3);
        
        tv1.setText(appointmentslist.get(position).mid);
        tv2.setText(appointmentslist.get(position).requid);
        tv3.setText(appointmentslist.get(position).donoruid);
        
        
        Button button1 = (Button)dialog.findViewById(R.id.dialog_btn1);  
        Button button2 = (Button)dialog.findViewById(R.id.dialog_btn2);  
        
        PhoneCallListener phoneListener = new PhoneCallListener(cnxt);
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
 
        button1.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+appointmentslist.get(position).donphone.toString()));
				startActivity(callIntent);
                dialog.dismiss();
            }
        });
                
        button2.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
                dialog.dismiss();
            }
        });
 
        dialog.show();*/

		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity(),"Details of Appointment", "Call");
	    builder.negativeText("Cancel");
	    builder.darkTheme(true);
	    

	    View vm = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null);
        
	    TextView tv1 = (TextView)vm.findViewById(R.id.dialog_tv1);
        TextView tv2 = (TextView)vm.findViewById(R.id.dialog_tv2);
        TextView tv3 = (TextView)vm.findViewById(R.id.dialog_tv3);
        
        tv1.setText(appointmentslist.get(position).mid);
        tv2.setText(appointmentslist.get(position).requid);
        tv3.setText(appointmentslist.get(position).donoruid);
        
        
	    
	    builder.negativeColor("#00CCFF");
        builder.positiveColor("#00CCFF");
        builder.titleColor("#00CCFF");
        
	    final CustomDialog customDialog = builder.build();
	   
	    customDialog.setCustomView(vm);
    

        PhoneCallListener phoneListener = new PhoneCallListener(cnxt);
		TelephonyManager telephonyManager = (TelephonyManager) this
			.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
 
		customDialog.setClickListener(new CustomDialog.ClickListener() {
            @Override
            public void onConfirmClick() {
            	customDialog.dismiss();
       
			
            }

            @Override
            public void onCancelClick() {
                customDialog.dismiss();
            }
        });
		
		
        customDialog.show();
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
			
			Log.i(LOG_TAG, "IDLE");

			if (isPhoneCalling) {

				Log.i(LOG_TAG, "restart app");
				
				isPhoneCalling = false;
			}

		}
	}
}

}
	