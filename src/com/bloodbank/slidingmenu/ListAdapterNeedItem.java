package com.bloodbank.slidingmenu;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapterNeedItem extends ArrayAdapter<NeedBloodItem> {
	private final Context context;
	  private final List<NeedBloodItem> items;

//    public ListAdapterNeedItem(Context context, int textViewResourceId) {
//        super(context, textViewResourceId);
//    }

    public ListAdapterNeedItem(Context context, int resource, List<NeedBloodItem> items) {
        super(context, resource, items);
        this.context=context;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	 LayoutInflater inflater = (LayoutInflater) getContext()
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             View rowView = inflater.inflate(R.layout.need_list_row, parent, false);
             String g=null;
             TextView tt1 = (TextView) rowView.findViewById(R.id.textTitleNeed1);
             TextView tt2 = (TextView) rowView.findViewById(R.id.textTitleNeed2);
             TextView tt3 = (TextView) rowView.findViewById(R.id.textTitleNeed3);
             TextView tt4 = (TextView) rowView.findViewById(R.id.textTitleNeed4);
             TextView tt5 = (TextView) rowView.findViewById(R.id.textTitleNeed5);
             TextView tt6 = (TextView) rowView.findViewById(R.id.textTitleNeed6);
             
             if((String) items.get(position).email == "M")
            	 g="Male";
             else
            	 g="Female";
             
             tt1.setText((String) items.get(position).name);
             tt2.setText((String) items.get(position).email);
             tt3.setText((String) items.get(position).age+" "+"years old "+g);
             tt4.setText((String) items.get(position).phone);
             tt5.setText((String) items.get(position).address+","+(String) items.get(position).city+","+(String) items.get(position).state+","+(String) items.get(position).country);
             tt6.setText((String) items.get(position).distance+" kms");
             

               return rowView;
    }

}