package com.example.shutapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListChatRoomRowArrayAdapter extends ArrayAdapter<String>{
	
	  private final Activity context;
	  private final String[] names;
	  private final int numberOfNearbyChatRooms;

	  static class ViewHolder {
	    public TextView text;
	    public ImageView image;
	  }

	  public ListChatRoomRowArrayAdapter(Activity context, String[] names, int numberOfNearbyChatRooms) {
	    super(context, android.R.layout.simple_list_item_1, names);
	    this.context = context;
	    this.names = names;
	    this.numberOfNearbyChatRooms = numberOfNearbyChatRooms;
	    
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    View chatRoomRow = convertView;
	    if (chatRoomRow == null) {
	      LayoutInflater inflater = context.getLayoutInflater();
	      chatRoomRow = inflater.inflate(R.layout.chatroom_row, null);
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.text = (TextView) chatRoomRow.findViewById(R.id.textV);
	      viewHolder.image = (ImageView) chatRoomRow
	          .findViewById(R.id.image1);
	      chatRoomRow.setTag(viewHolder);
	    }

	    ViewHolder holder = (ViewHolder) chatRoomRow.getTag();
	    String chatRoomName = names[position];
	    holder.text.setText(chatRoomName);
	    if (position < numberOfNearbyChatRooms) {
	      holder.image.setImageResource(R.drawable.ok);
	    } else {
	      holder.image.setImageResource(R.drawable.no);
	    }

	    return chatRoomRow;
	  }

}
