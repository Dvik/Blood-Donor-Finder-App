package com.bloodbank.slidingmenu;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity4 extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registerscreen4);

        final EditText et1=(EditText)findViewById(R.id.reg_bio);
        
        
        Button b=(Button)findViewById(R.id.next4);
        
        Intent i = getIntent();  
        final HashMap<String,String> hm = (HashMap<String, String>) i.getSerializableExtra("key3");
        
        
        
       
        
        b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String bio=et1.getText().toString();
				if (bio.trim().length() > 0 ) {
					
					hm.put("description",bio);
			        
					// TODO Auto-generated method stub
					       
				      Intent i4 = new Intent(RegisterActivity4.this,RegisterActivity.class);
				      i4.putExtra("key4", hm); 
				      startActivity(i4);
				      finish();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter your bio to proceed", Toast.LENGTH_LONG)
                            .show();
                }
		        
				
			}
		});
        
	}
}


