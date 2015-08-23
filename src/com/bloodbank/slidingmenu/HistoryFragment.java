package com.bloodbank.slidingmenu;


import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryFragment extends Fragment{
	
	public HistoryFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
	
        View rootView = inflater.inflate(R.layout.fragment_donation_history, container, false);
         
        ListView listView1 = (ListView)rootView.findViewById(R.id.listhistory);

        List<HistoryItem> historylist = new ArrayList<HistoryItem>();

        historylist.add(new HistoryItem("Ankit Jain", "99999999"));
        historylist.add(new HistoryItem("Abhi Sharma", "88888888"));
        historylist.add(new HistoryItem("Aditya Verma", "77788888"));
        historylist.add(new HistoryItem("Jon Snow", "234566721"));
        historylist.add(new HistoryItem("Tyrion Lannister", "9878499322"));
        historylist.add(new HistoryItem("Ned Stark", "643728821"));
        historylist.add(new HistoryItem("Daenerys", "838742174"));
        historylist.add(new HistoryItem("Arya Stark", "69649232"));

        
               
                ListAdapterHistory adapter = new ListAdapterHistory(getActivity(),
                        R.layout.list_view_item, historylist);
               
               /* View header = (View)rootView.getLayoutInflater().inflate(null, null);


				listView1.addHeaderView(n);
				listView1.addFooterView(new View(this));*/
//                listView1.addHeaderView(header);
               
                listView1.setAdapter(adapter);
        
        
        
        
        
        
        
        
        
//        ListView listView = (ListView)rootView.findViewById(R.id.listhistory);
//        //Here items must be a List<Items> according to your class instead of String[] array
//        String[] items = new String[] {"Ankit", "Somya", "Raj"};
//        ArrayAdapter<String> adapter =
//        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items); 
//
//        listView.setAdapter(adapter);  
         
        return rootView;
    }
}



