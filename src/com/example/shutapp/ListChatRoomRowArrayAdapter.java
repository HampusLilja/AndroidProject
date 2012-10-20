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
	    private TextView text;
	    private ImageView image;
	    
	    public void setTextView(TextView textView){
	    	text = textView;
	    }
	    
	    public void setImageView(ImageView imageView){
	    	image = imageView;
	    }
	    
	    public TextView getTextView(){
	    	return text;
	    }
	    
	    public ImageView getImageView(){
	    	return image;
	    }
	  }

	  /**
	   * Constructor
	   * 
	   * @param context
	   * @param names	an array of the strings that should be listed
	   * @param numberOfNearbyChatRooms		how many chat rooms that should be available 
	   */
	  public ListChatRoomRowArrayAdapter(Activity context, String[] names, int numberOfNearbyChatRooms) {
	    super(context, android.R.layout.simple_list_item_1, names.clone());
	    this.context = context;
	    this.names = names.clone();
	    this.numberOfNearbyChatRooms = numberOfNearbyChatRooms;
	    
	  }
	  
	  /**
	   * Customized getView
	   * 
	   * @return View	the customized view
	   * 
	   * @param position	position in this list
	   * @param convertView		the layout to be converted
	   * @param parent		
	   */
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
