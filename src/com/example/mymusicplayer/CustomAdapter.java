package com.example.mymusicplayer;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

public class CustomAdapter extends SimpleCursorAdapter{
	private Context context;
	private int layout;
	public CustomAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layout = layout;
	}
	  @Override
	    public void bindView(View v, Context context, Cursor c) {
	 
	        String songTitle = c.getString(1);
	        String artistName = c.getString(2);
	        /**
	         * Next set the name of the entry.
	         */    
	        TextView songTitleLabel = (TextView) v.findViewById(R.id.songTitle);
	        if (songTitleLabel != null) {
	        	songTitleLabel.setText(songTitle);
	        }
	        TextView artistNameLabel = (TextView) v.findViewById(R.id.artist);
	        if (artistName != null){
	        	artistNameLabel.setText(artistName);
	        }
	        else {
	        	artistNameLabel.setText("Unknown Artist");
	        }
	    }


	
	
}
