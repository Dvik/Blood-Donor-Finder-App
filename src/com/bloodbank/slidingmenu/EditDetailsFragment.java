package com.bloodbank.slidingmenu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditDetailsFragment extends Fragment implements android.view.View.OnClickListener{
	EditText edt2,edt3,edt4,edt5,edt6,edt7,edt8,edt9,edt10,edt11,edt12,edt13,edt14,edt15,edt16;
	TextView edt1;
	private SQLiteHandler db=null;
	HashMap<String, String> emap;
	private ProgressDialog pDialog;
	Fragment fragment=null;
	String id,email,description,deviceid;
	public EditDetailsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
	
        View v = inflater.inflate(R.layout.edit_basic_info, container, false);
        
        //getActivity().getActionBar().setTitle("Edit Details");
    	//MainActivity.changeDrawerItem(0);
    	
    	 db = new SQLiteHandler(getActivity().getApplicationContext());
         db.getReadableDatabase();
         emap=new HashMap<String, String>();
         emap = db.getUserDetails();
         
         id = emap.get("id");
         String name = emap.get("name");
        
         email = emap.get("email");
         String gender = emap.get("gender");
         String bgroup = emap.get("bgroup");
         String phone = emap.get("phone");
         String country = emap.get("country");
         String  state = emap.get("state");
         String city = emap.get("city");
         String address = emap.get("address");
         String candonate = emap.get("candonate");
         description = emap.get("description");
         String weight = emap.get("weight");
         String age = emap.get("age");
         deviceid = emap.get("deviceid");
         
         edt1=(TextView) v.findViewById(R.id.edit_account_email1);
         edt1.setText(email);
         
         edt2=(EditText) v.findViewById(R.id.edit_account_password1);
         //edt2.setText(password);
         
         edt3=(EditText) v.findViewById(R.id.edit_account_name1);
         edt3.setText(name);
         
         edt4=(EditText) v.findViewById(R.id.edit_account_bg1);
         edt4.setText(bgroup);
         
         edt5=(EditText) v.findViewById(R.id.edit_account_age1);
         edt5.setText(age);
         
         edt6=(EditText) v.findViewById(R.id.edit_account_sex1);
         edt6.setText(gender);
         
         edt7=(EditText) v.findViewById(R.id.edit_account_contact1);
         edt7.setText(phone);
         
         edt8=(EditText) v.findViewById(R.id.edit_account_address1);
         edt8.setText(address);
         

         edt9=(EditText) v.findViewById(R.id.edit_account_country1);
         edt9.setText(country);

         edt10=(EditText) v.findViewById(R.id.edit_account_state1);
         edt10.setText(state);

         edt11=(EditText) v.findViewById(R.id.edit_account_city1);
         edt11.setText(city);

         edt12=(EditText) v.findViewById(R.id.edit_account_weight1);
         edt12.setText(weight);

         edt13=(EditText) v.findViewById(R.id.edit_account_donor1);
         edt13.setText(candonate);
         
         pDialog = new ProgressDialog(getActivity());
         pDialog.setCancelable(false);
         
        Button b2 = (Button) v.findViewById(R.id.edit_btn_save);
        b2.setOnClickListener(EditDetailsFragment.this);
        
        return v;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Fragment fragment = null;

		int id = v.getId();
		
		if (id == R.id.edit_btn_save) {
			editdetails();
		} 
		
}
	public void editdetails()
	{
		String tag_string_req = "req_edit";
		 
        pDialog.setMessage("Editing ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_EDIT, new Response.Listener<String>() {
 
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
 
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
                            if (success==1) {
                            	
                               db.deleteUsers();
                               System.out.println("inside no er ror");
                               
                            
                               db.addUser(id, edt3.getText().toString(), email, edt6.getText().toString(),
                            		   edt4.getText().toString(),edt7.getText().toString(),
                            		   edt9.getText().toString(), edt10.getText().toString(), 
                            		   edt11.getText().toString(), edt8.getText().toString(), 
                            		   edt13.getText().toString(), description, edt12.getText().toString(), 
                            		   edt5.getText().toString(), deviceid, MainActivity1.latitude, MainActivity1.longitude , null, null);
                            
                            fragment = new AccountFragment();
                            if (fragment != null) {
                        		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        		fragmentManager.beginTransaction()
                        				.replace(R.id.container_body, fragment).addToBackStack( null ).commit();
                            }
                        	else {
                        		// error in creating fragment
                        		Log.e("MainActivity", "Error in creating fragment");
                        	}
                            }
                            
                            if(success==0) {
                            		
                            		Toast.makeText(getActivity().getApplicationContext(),
                                        "Error in registering", Toast.LENGTH_LONG).show();
                            		
                                    System.out.println("inside no error");
                                    fragment = new AccountFragment();
                                 
                                 
                                 
                                 if (fragment != null) {
                             		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                             		fragmentManager.beginTransaction()
                             				.replace(R.id.container_body, fragment).addToBackStack( null ).commit();
                                 }
                             	else {
                             		// error in creating fragment
                             		Log.e("MainActivity", "Error in creating fragment");
                             	}
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
 
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       
                        hideDialog();
                        /*Intent intent = new Intent(
                                RegisterActivity.this,
                                LogInActivity.class);
                        startActivity(intent);
                        finish();
*/                    }
                }) {
        	
        	@Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("edit", "register");
                params.put("id", id);
                params.put("name", edt3.getText().toString());
                params.put("password", edt2.getText().toString());
                
                
                params.put("gender", edt6.getText().toString());
                params.put("bgroup", edt4.getText().toString());
                params.put("phone", edt7.getText().toString());
                params.put("country", edt9.getText().toString());
                params.put("state", edt10.getText().toString());
                params.put("city", edt11.getText().toString());
                params.put("address",edt8.getText().toString());
                params.put("candonate", edt13.getText().toString());
                
                params.put("weight", edt12.getText().toString());
                params.put("age", edt5.getText().toString());
                
              
                
                return params;
            }
 
        };
 
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
 
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
 
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}


