package com.bloodbank.slidingmenu;

import java.util.HashMap;


import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AccountFragment extends Fragment implements android.view.View.OnClickListener{
	
	public AccountFragment(){}
	TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	private SQLiteHandler db=null;
	HashMap<String, String> hms;
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View v = inflater.inflate(R.layout.fragment_account, container, false);
     
    	//MainActivity.changeDrawerItem(0);
        db = new SQLiteHandler(getActivity().getApplicationContext());
        db.getReadableDatabase();
        hms=new HashMap<String, String>();
        hms = db.getUserDetails();
        
        String id = hms.get("id");
        String name = hms.get("name");
       
        String email = hms.get("email");
        String gender = hms.get("gender");
        String bgroup = hms.get("bgroup");
        String phone = hms.get("phone");
        String country = hms.get("country");
        String  state = hms.get("state");
        String city = hms.get("city");
        String address = hms.get("address");
        String candonate = hms.get("candonate");
        String description = hms.get("description");
        String weight = hms.get("weight");
        String age = hms.get("age");
        String deviceid = hms.get("deviceid");
        
        tv1=(TextView) v.findViewById(R.id.account_email1);
        tv1.setText(email);
        
        
        tv3=(TextView) v.findViewById(R.id.account_name1);
        tv3.setText(name);
        
        tv4=(TextView) v.findViewById(R.id.account_bg1);
        tv4.setText(bgroup);
        
        tv5=(TextView) v.findViewById(R.id.account_age1);
        tv5.setText(age);
        
        tv6=(TextView) v.findViewById(R.id.account_sex1);
        tv6.setText(gender);
        
        tv7=(TextView) v.findViewById(R.id.account_contact1);
        tv7.setText(phone);
        
        tv8=(TextView) v.findViewById(R.id.account_address1);
        tv8.setText(address+","+city+","+state+","+country);
        
        
        
        
        Button b1 = (Button) v.findViewById(R.id.btn_edit);
        b1.setOnClickListener(AccountFragment.this);
        return v;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Fragment fragment = null;

		int id = v.getId();
		if (id == R.id.btn_edit) {
			fragment = new EditDetailsFragment();
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
	/*fragment.getView().setFocusableInTouchMode(true);
	fragment.getView().requestFocus();
	fragment.getView().setOnKeyListener( new OnKeyListener()
	{
	    @Override
	    public boolean onKey( View v, int keyCode, KeyEvent event )
	    {
	        if( keyCode == KeyEvent.KEYCODE_BACK )
	        {
	            return true;
	        }
	        return false;
	    }
	} );*/
}
/*@Override
	public void onPrepareOptionsMenu(Menu menu) {

	    //Hides MenuItem action_edit
	    menu.getItem(R.id.action_edit).setVisible(false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
		inflater=getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu1, menu);
		//return true;
		super.onCreateOptionsMenu(menu,inflater);
	}
	*/
}