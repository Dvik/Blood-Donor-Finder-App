package com.bloodbank.slidingmenu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.registerscreen5);

// Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
 
 
        Button btn_yes=(Button)findViewById(R.id.button_yes);
        Button btn_no=(Button)findViewById(R.id.button_no);
        
        Intent i = getIntent();  
        final HashMap<String,String> hm = (HashMap<String, String>) i.getSerializableExtra("key4");
        
        
        
        // Register Button Click event
        btn_yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hm.put("candonate", "yes");
                registerUser(hm);
                
            }
        });
 
        btn_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hm.put("candonate", "no");
                registerUser(hm);    
            }
        });
    }
 
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final HashMap<String,String> hm) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
 
        pDialog.setMessage("Registering ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
 
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        hideDialog();
 
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
                            if (success==1) {
                                // User successfully stored in MySQL
                                // Now store the user in sqlite
                                
                            	System.out.println("inside no error");
                                //JSONObject user = jObj.getJSONObject("user");
                                
                                String email = jObj.getString("email");
                                
                             
                                // Launch login activity
                                Intent intent = new Intent(
                                        RegisterActivity.this,
                                        LogInActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
 
                                // Error occurred in registration. Get the error
                                // message
                                //String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        "Error in registering", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(
                                        RegisterActivity.this,
                                        HomeActivity.class);
                                startActivity(intent);
                                finish();
                            
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
 
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Registration Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LogInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }) {
 
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("register", "register");
                params.put("name", hm.get("name").toString());
                params.put("email",hm.get("email").toString());
                params.put("password", hm.get("password").toString());
                params.put("gender", hm.get("gender").toString());
                params.put("bgroup", hm.get("bgroup").toString());
                params.put("phone", hm.get("phone").toString());
                params.put("country", hm.get("country").toString());
                params.put("state", hm.get("state").toString());
                params.put("city", hm.get("city").toString());
                params.put("address",hm.get("address").toString());
                params.put("candonate", hm.get("candonate").toString());
                params.put("description", hm.get("description").toString());
                params.put("weight", hm.get("weight").toString());
                params.put("age", hm.get("age").toString());
                params.put("deviceid", AppController.deviceId);
                params.put("latitude", MainActivity1.latitude);
                params.put("longitude", MainActivity1.longitude);
                
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