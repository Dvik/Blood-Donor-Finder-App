package com.bloodbank.slidingmenu;

import java.util.HashMap;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NeedBloodForm extends Fragment implements android.view.View.OnClickListener{
	
	public NeedBloodForm(){}

    private String spinner_blood_type[],spinner_gender[];
    private SQLiteHandler db=null;
	HashMap<String, String> hms;
	EditText edt1,edt2,edt3,edt4,edt5;
	Spinner s;
	TextView tv;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
    View rootView = inflater.inflate(R.layout.need_blood_form, container, false);
    //getActivity().getActionBar().setTitle("Need Blood");
	//MainActivity1.changeDrawerItem(1);
	
	
	db = new SQLiteHandler(getActivity().getApplicationContext());
    db.getReadableDatabase();
    hms=new HashMap<String, String>();
    hms = db.getUserDetails();
    
    String name = hms.get("name");
    String gender = hms.get("gender");
    String bgroup = hms.get("bgroup");
    String weight = hms.get("weight");
    String age = hms.get("age");
    
    int select=0;
    if(gender.endsWith("M") || gender.endsWith("m"))
    	select=0;
    if(gender.endsWith("F") || gender.endsWith("f"))
    	select=1;
    
    spinner_blood_type= new String[4];
    spinner_blood_type[0]="A+";
    spinner_blood_type[1]="B+";
    spinner_blood_type[2]="A";
    spinner_blood_type[3]="O";
  
    s=(Spinner)rootView.findViewById(R.id.form_type);
    ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinner_blood_type);
    s.setAdapter(adapter);
    
    spinner_gender= new String[2];
    spinner_gender[0]="M";
    spinner_gender[1]="F";
    
    Spinner s1=(Spinner)rootView.findViewById(R.id.form_gender);
    ArrayAdapter adapter1=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,spinner_gender);
    s1.setAdapter(adapter1);
    s1.setSelection(select);
    
    
    
    edt1 = (EditText) rootView.findViewById(R.id.form_name);
    edt1.setText(name);
    edt2 = (EditText) rootView.findViewById(R.id.form_age);
    edt2.setText(age);   
    edt5 = (EditText) rootView.findViewById(R.id.form_weight);
    edt5.setText(weight);
    
//    
//    Location currentLocation= getMyCurrentLocation();
//    
//    Double lt=currentLocation.getLatitude();
//    Double ln=currentLocation.getLongitude();
//    
//    String des= lt.toString()+" "+ln.toString();
//   
//    tv= (TextView)rootView.findViewById(R.id.form_location);
//    tv.setText(des);
    
    Button b1 = (Button) rootView.findViewById(R.id.form_btn);
    b1.setOnClickListener(NeedBloodForm.this);
    
    return rootView;

}
	 @Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Fragment fragment = null;

			int id = v.getId();
			if (id == R.id.form_btn) {
				AppConfig.type= s.getSelectedItemPosition();
				fragment = new NeedBloodFragment();
			} else {
			}
			
		
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
		/*private Location getMyCurrentLocation() {
		    // Get location from GPS if it's available
		    LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
		    Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		    // Location wasn't found, check the next most accurate place for the current location
		    if (myLocation == null) {
		        Criteria criteria = new Criteria();
		        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		        // Finds a provider that matches the criteria
		        String provider = lm.getBestProvider(criteria, true);
		        // Use the provider to get the last known location
		        myLocation = lm.getLastKnownLocation(provider);
		    }

		    return myLocation;
		}*/
}