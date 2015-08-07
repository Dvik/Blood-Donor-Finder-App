package com.bloodbank.slidingmenu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends Activity {
    // LogCat tag
    //private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
 
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login1);
        btnLinkToRegister = (Button) findViewById(R.id.login_to_register);
 
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
 
        // Session manager
        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        // Check if user is already logged in or not
        db.getWritableDatabase();
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LogInActivity.this, MainActivity1.class);
            startActivity(intent);
            finish();
        }
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
 
                // Check for empty data in the form
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }
 
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity1.class);
                startActivity(i);
                finish();
            }
        });
 
    }
 
    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
    	
    	
    	
    		
        // Tag used to cancel the request
        String tag_string_req = "req_login";
 
        pDialog.setMessage("Logging in ...");
        showDialog();
 
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
 
                    @Override
                    public void onResponse(String response) {
          //              Log.d(TAG, "Login Response: " + response.toString());
                        hideDialog();
 
                        try {
                            JSONObject jObj = new JSONObject(response);
                            int success = jObj.getInt("success");
 
                            // Check for error node in json
                            if (success==1) {
                                // user successfully logged in
                                // Create login session
                                session.setLogin(true);
                                
                                Integer id = jObj.getInt("id");
                                String name = jObj.getString("name");
                                String email = jObj.getString("email");
                                String gender = jObj.getString("gender");
                                String bgroup = jObj.getString("bgroup");
                                String phone = jObj.getString("phone");
                                String country = jObj.getString("country");
                                String state = jObj.getString("state");
                                String city = jObj.getString("city");
                                String address = jObj.getString("address");
                                String candonate = jObj.getString("candonate");
                                String description = jObj.getString("description");
                                String weight = jObj.getString("weight");
                                String age = jObj.getString("age");
                                String deviceid = jObj.getString("deviceid");
                                String latitude = jObj.getString("latitude");
                                String longitude = jObj.getString("longitude");
                                String createdDate = jObj.getString("createdDate");
                                String modifiedDate = jObj.getString("modifiedDate");
                                
                                
                             
                                db.addUser(id.toString(), name,email,gender , bgroup, phone, country,state , city, address,candonate,description,weight, age,deviceid,
                                		latitude,longitude,createdDate,modifiedDate);
                                // Launch main activity
                                Intent intent = new Intent(LogInActivity.this,
                                        MainActivity1.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Error in login. Get the error message
                                //String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        "Error in logging in", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
 
                    }
                }, new Response.ErrorListener() {
 
                    @Override
                    public void onErrorResponse(VolleyError error) {
             //           Log.e(TAG, "Login Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
 
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", "login");
                params.put("email", email);
                params.put("password", password);
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