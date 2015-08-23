package com.bloodbank.slidingmenu;


import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapterAssociates extends ArrayAdapter<AssociatesItem>{
	private final Context context;
	  private final List<AssociatesItem> items;

//    public ListAdapterAssociates(Context context, int textViewResourceId) {
//        super(context, textViewResourceId);
//    }

    public ListAdapterAssociates(Context context, int resource, List<AssociatesItem> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

    	 LayoutInflater inflater = (LayoutInflater) getContext()
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             View rowView = inflater.inflate(R.layout.ass_item_list_row, parent, false);
     

             PhoneCallListener phoneListener = new PhoneCallListener(context.getApplicationContext());
     		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
     		telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
      
             
             TextView tt1 = (TextView) rowView.findViewById(R.id.textTitleAss1);
             TextView tt2 = (TextView) rowView.findViewById(R.id.textTitleAss2);
             TextView tt3 = (TextView) rowView.findViewById(R.id.textTitleAss3);
             TextView tt4 = (TextView) rowView.findViewById(R.id.textTitleAss4);
             TextView tt5 = (TextView) rowView.findViewById(R.id.textTitleAss5);
             
             tt1.setText((String) items.get(position).name);
             tt2.setText((String) items.get(position).personName);
             tt3.setText((String) items.get(position).contact+","+(String) items.get(position).personPhone);
             tt4.setText((String) items.get(position).storageType);
             tt5.setText((String) items.get(position).city+","+(String) items.get(position).state);
             
        return rowView;
    }

class PhoneCallListener extends PhoneStateListener {
	 
	private boolean isPhoneCalling = false;

	String LOG_TAG = "LOGGING 123";
	
	private Context context;
	
	public PhoneCallListener(Context context)
	{
		this.context=context;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {

		if (TelephonyManager.CALL_STATE_RINGING == state) {
			// phone ringing
			Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
		}

		if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
			// active
			Log.i(LOG_TAG, "OFFHOOK");

			isPhoneCalling = true;
		}

		if (TelephonyManager.CALL_STATE_IDLE == state) {
			
			Log.i(LOG_TAG, "IDLE");

			if (isPhoneCalling) {

				Log.i(LOG_TAG, "restart app");
				
				isPhoneCalling = false;
			}

		}
	}
}

}