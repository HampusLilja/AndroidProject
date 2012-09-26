package com.example.shutapp;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("ParserError")
public class ListOfNearbyConversationsActivity extends ListActivity {
	
	
	private List<Chatroom> nearbyCR;
	private ArrayAdapter<String> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.id.listOfNearbyConversations);
        
        initiateTestRooms();
        createArrayAdapter(initTestArray());
        

    }
    
    private void initiateTestRooms() {
		Chatroom cr1 = new Chatroom("Chatroom1");Chatroom cr2 = new Chatroom("Chatroom2");Chatroom cr3 = new Chatroom("Chatroom3");
		Chatroom cr4 = new Chatroom("Chatroom4");Chatroom cr5 = new Chatroom("Chatroom5");
		nearbyCR = new ArrayList<Chatroom>();
		nearbyCR.add(cr1); nearbyCR.add(cr2); nearbyCR.add(cr3); nearbyCR.add(cr4); nearbyCR.add(cr5);
		
	}

	private String[] initTestArray() {
		String[] testArray = new String[nearbyCR.size()];
		int i = 0;
		for(Chatroom cr : nearbyCR){
			testArray[i] = cr.getName();
			i++;
		}
		return testArray;
	}

	private void createArrayAdapter(String[] myStringArray) {
    	setListAdapter(new ArrayAdapter<String>(this, R.id.listOfNearbyConversations, myStringArray));
    	ListView listView = getListView();
    	listView.setTextFilterEnabled(true);
    	
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}
    

}
