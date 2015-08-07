package com.bloodbank.slidingmenu;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity2 extends Activity{
	private String array_spinner[];
	private String array_spinner1[];


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registerscreen2);

        array_spinner= new String[4];
        array_spinner[0]="A+";
        array_spinner[1]="B+";
        array_spinner[2]="A";
        array_spinner[3]="O";
      
        final Spinner s=(Spinner)findViewById(R.id.reg_type);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,array_spinner);
        s.setAdapter(adapter);
      
        array_spinner1= new String[2];
        array_spinner1[0]="M";
        array_spinner1[1]="F";
        
        final Spinner s1=(Spinner)findViewById(R.id.reg_gender);
        ArrayAdapter adapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,array_spinner1);
        s1.setAdapter(adapter1);
        
        final EditText et1=(EditText)findViewById(R.id.reg_age);
        final EditText et2=(EditText)findViewById(R.id.reg_weight);

        Button b=(Button)findViewById(R.id.next2);
        
        Intent i = getIntent();  
        final HashMap<String,String> hm = (HashMap<String, String>) i.getSerializableExtra("key1");
        
        
        
       
        
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String gender=s1.getSelectedItem().toString();
				String age=et1.getText().toString();
				String bgroup=s.getSelectedItem().toString();
				String weight=et2.getText().toString();
				 if (gender.trim().length() > 0 && age.trim().length() > 0 && bgroup.trim().length() > 0 && weight.trim().length() > 0) 
				 {
						hm.put("gender", gender);
				        hm.put("age",age );
				        hm.put("bgroup", bgroup);
				        hm.put("weight",weight );
				        
						// TODO Auto-generated method stub
						
					      Intent i2 = new Intent(RegisterActivity2.this,RegisterActivity3.class);
					      i2.putExtra("key2", hm); 
					      startActivity(i2);
					      finish();
						
	                } else {
	                    // Prompt user to enter credentials
	                    Toast.makeText(getApplicationContext(),
	                            "Please enter all credentials to proceed", Toast.LENGTH_LONG)
	                            .show();
	                }
			
			}
		});
        
	}
}





