package com.bloodbank.slidingmenu;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapterHistory extends ArrayAdapter<HistoryItem> {
	private final Context context;
	  private final List<HistoryItem> items;

	 
//    public ListAdapterHistory(Context context, int textViewResourceId) {
//        super(context, textViewResourceId);
//    }

    public ListAdapterHistory(Context context, int resource, List<HistoryItem> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_view_item, parent, false);
    
            TextView tt1 = (TextView) rowView.findViewById(R.id.textTitle1);
            TextView tt2 = (TextView) rowView.findViewById(R.id.textTitle2);
            tt1.setText((String) items.get(position).title1);
            tt2.setText((String) items.get(position).title2);

            // change the icon for Windows and iPhone
            
//        HistoryItem p = getItem(position);
//
//        if (p != null) {
//            TextView tt1 = (TextView) v.findViewById(R.id.textTitle1);
//            
//
//            if (tt1 != null) {
//                tt1.setText("Name");
//            }
//
//            if (tt2 != null) {
//                tt2.setText("Date");
//            }
//        }

        return rowView;
    }

}