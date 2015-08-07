package com.bloodbank.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity1 extends Activity{
	SQLiteDatabase db;
	EditText tv1,tv2,tv3;
	HashMap<String,String> hm=new HashMap<String,String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.registerscreen1);
		Button b=(Button)findViewById(R.id.next1);;
        tv1=(EditText)findViewById(R.id.reg_name);
        tv2=(EditText)findViewById(R.id.reg_email);
        tv3=(EditText)findViewById(R.id.reg_password);
        /*
        db = openOrCreateDatabase("User", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS reg1(name VARCHAR,email VARCHAR,password VARCHAR,);");
        */
       
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name= tv1.getText().toString();
				String email= tv2.getText().toString();
				String password= tv3.getText().toString();
		         
				if (name.trim().length() > 0 && email.trim().length() > 0 && password.trim().length() > 0) 
				{
                    
					hm.put("name", name);
			        hm.put("email", email);
			        hm.put("password", password);
			        
					Intent i1 = new Intent(RegisterActivity1.this,RegisterActivity2.class);
					i1.putExtra("key1", hm);  
					startActivity(i1);
					finish();
                } 
				else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter all credentials to proceed", Toast.LENGTH_LONG)
                            .show();
                }
				 
		        
			}
		});
	}
}


