package com.bloodbank.slidingmenu;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapterAppointments extends ArrayAdapter<AppointmentsItem> {
	private final Context context;
	  private final List<AppointmentsItem> items;

//    public ListAdapterAppointments(Context context, int textViewResourceId) {
//        super(context, textViewResourceId);
//    }

    public ListAdapterAppointments(Context context, int resource, List<AppointmentsItem> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	 LayoutInflater inflater = (LayoutInflater) getContext()
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             View rowView = inflater.inflate(R.layout.appoint_item_row, parent, false);
     
             TextView tt1 = (TextView) rowView.findViewById(R.id.textTitleAppoint1);
             TextView tt2 = (TextView) rowView.findViewById(R.id.textTitleAppoint2);
             TextView tt3 = (TextView) rowView.findViewById(R.id.textTitleAppoint3);
             TextView tt4 = (TextView) rowView.findViewById(R.id.textTitleAppoint4);
             
             tt1.setText((String) items.get(position).reqname + " ");
             tt2.setText((String) items.get(position).requid);
             tt3.setText((String) items.get(position).donoruid);
             

        return rowView;
    }

}