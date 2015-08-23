package com.bloodbank.slidingmenu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.bloodbank.slidingmenu.NeedBloodFragment.PhoneCallListener;
import com.bloodbank.slidingmenu.NeedBloodFragment.addBloodRequest;
import com.example.ldialogbox.CustomDialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AssociatesFragment extends Fragment{
	 public String id;
	    public String name;
	    public String state;
	    public String city;
	    public String contact;
	    public String personName;
	    public String personPhone;
	    public String timing;
	    public String storageType;
	    public String createdDate;
	    public String modifiedDate;
	   private ProgressDialog pDialog;
	   
	   ListView listView1;
	    // Creating JSON Parser object
	    JSONParser jParser = new JSONParser();
	 
	    //ArrayList<HashMap<String, String>> associatesList;
	    
	    JSONArray associates = null;
	    
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_ASSOCIATES = "associates";
	 
List<AssociatesItem> associateslist = new ArrayList<AssociatesItem>();
public AssociatesFragment(){}
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_associates, container, false);
        //getActivity().getActionBar().setTitle("Associates");
    	//MainActivity.changeDrawerItem(3);
         
        // Hashmap for ListView
        //associatesList = new ArrayList<HashMap<String, String>>();
 
        // Loading associates in Background Thread
        new LoadAllassociates().execute();
        
        listView1 = (ListView)rootView.findViewById(R.id.listassociates);
        
        
                listView1.setOnItemClickListener(new OnItemClickListener() {
                	  public void onItemClick(AdapterView<?> parent, View view,
                	    int position, long id) {
                		  showCustomDialog(associateslist,position,AssociatesFragment.this.getActivity().getApplicationContext());
                	  }
                	}); 

                return rootView;
    }


	class LoadAllassociates extends AsyncTask<String, String, String> {
	 
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Associates. Please wait...");
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
        // getting JSON string from URL
        Log.d("ip: ", AppConfig.url_all_associates);
        JSONObject json = jParser.makeHttpRequest(AppConfig.url_all_associates, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All associates: ", json.toString());
        
        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // associates found
                // Getting Array of associates
                associates = json.getJSONArray(TAG_ASSOCIATES);

                // looping through All associates
                for (int i = 0; i < associates.length(); i++) 
                {
                    JSONObject c = associates.getJSONObject(i);

                    // Storing each json item in variable
                    id = c.getString("id");
                    name = c.getString("name");
                    state = c.getString("state");
                    city = c.getString("city");
                    contact = c.getString("contact");
                    personName = c.getString("personName");
                    personPhone = c.getString("personPhone");
                    timing = c.getString("timing");
                    storageType = c.getString("storageType");
                    createdDate = c.getString("createdDate");
                    modifiedDate = c.getString("modifiedDate");
                    
                    // creating new HashMap
                    associateslist.add(new AssociatesItem(id,name,state,city,contact,personName,personPhone,
                    		timing,storageType,createdDate,modifiedDate));
                    /*
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put("name", name);
                    map.put("state", state);
                    map.put("contact", contact);
                    // adding HashList to ArrayList
                    associatesList.add(map);
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
            	 ListAdapterAssociates adapter = new ListAdapterAssociates(getActivity(),
                         R.layout.ass_item_list_row, associateslist);
                 listView1.setAdapter(adapter);
                 
            }
        });

    }

}

	
	protected void showCustomDialog(final List<AssociatesItem> associateslist,final int position,Context cnxt) {
        // TODO Auto-generated method stub
        /*final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        
        TextView tv1 = (TextView)dialog.findViewById(R.id.dialog_tv1);
        TextView tv2 = (TextView)dialog.findViewById(R.id.dialog_tv2);
        TextView tv3 = (TextView)dialog.findViewById(R.id.dialog_tv3);
        
        tv1.setText(associateslist.get(position).name);
        tv2.setText(associateslist.get(position).createdDate);
        tv3.setText(associateslist.get(position).personName);
        
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
				callIntent.setData(Uri.parse("tel:"+associateslist.get(position).contact.toString()));
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
		
		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity(),associateslist.get(position).name, "Call");
	    builder.negativeText("Cancel");
	    builder.darkTheme(true);
	    

	    View vm = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog, null);
        
	    TextView tv1 = (TextView)vm.findViewById(R.id.dialog_tv1);
        TextView tv2 = (TextView)vm.findViewById(R.id.dialog_tv2);
        TextView tv3 = (TextView)vm.findViewById(R.id.dialog_tv3);
        
        tv1.setText(associateslist.get(position).name);
        tv2.setText(associateslist.get(position).createdDate);
        tv3.setText(associateslist.get(position).personName);
        
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
            	Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"+associateslist.get(position).contact.toString()));
				startActivity(callIntent);
			
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
	