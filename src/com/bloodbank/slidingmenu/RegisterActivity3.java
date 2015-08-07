package com.bloodbank.slidingmenu;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity3 extends Activity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registerscreen3);

        final EditText et1=(EditText)findViewById(R.id.reg_contact);
        final EditText et2=(EditText)findViewById(R.id.reg_country);
        final EditText et3=(EditText)findViewById(R.id.reg_state);
        final EditText et4=(EditText)findViewById(R.id.reg_city);
        final EditText et5=(EditText)findViewById(R.id.reg_address);
        
        
        Button b=(Button)findViewById(R.id.next3);
        
        Intent i = getIntent();  
        final HashMap<String,String> hm = (HashMap<String, String>) i.getSerializableExtra("key2");
        
        
        
       
        
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phone = et1.getText().toString();
				String country = et2.getText().toString();
				String state = et3.getText().toString();
				String city = et4.getText().toString();
				String address = et5.getText().toString();
				
				 if (phone.trim().length() > 0 && country.trim().length() > 0 && state.trim().length() > 0&& city.trim().length() > 0 && address.trim().length() > 0) 
				 {
					 	hm.put("phone",phone);
				        hm.put("country",country );
				        hm.put("state",state);
				        hm.put("city",city);
				        hm.put("address",address);
				        
						// TODO Auto-generated method stub
						
					      Intent i3 = new Intent(RegisterActivity3.this,RegisterActivity4.class);
					      i3.putExtra("key3", hm); 
					      startActivity(i3);
					      finish();
	                } else {
	                    // Prompt user to enter credentials
	                    Toast.makeText(getApplicationContext(),
	                            "Please enter all the credentials to continue", Toast.LENGTH_LONG)
	                            .show();
	                }
				
				
			}
		});
        
	}
}





