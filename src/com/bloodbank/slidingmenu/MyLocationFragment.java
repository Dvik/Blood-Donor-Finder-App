package com.bloodbank.slidingmenu;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyLocationFragment extends Fragment {
	
    private GoogleMap googleMap;
	
	public MyLocationFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_my_location, container, false);
         
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        googleMap.setMyLocationEnabled(true);
       
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        
        Location currentLocation= getMyCurrentLocation();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))      // Sets the center of the map to location user
        .zoom(17)                   // Sets the zoom
        .bearing(90)                // Sets the orientation of the camera to east
        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
        .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
        Double lt=currentLocation.getLatitude();
        Double ln=currentLocation.getLongitude();
        
        String des= lt.toString()+" "+ln.toString();
        TextView tv;
        tv= (TextView)rootView.findViewById(R.id.textView6);
        tv.setText(des);
        
        
        return rootView;
    }
	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
	@Override
	public void onResume() {
        super.onResume();
        initilizeMap();
    }
	private Location getMyCurrentLocation() {
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
	}
}
