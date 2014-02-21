package com.example.mymusicplayer;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class PlaylistActivity extends ListActivity {
     List<SongList> songList ;
	DatabaseHandler db = new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		Log.d("abc", ""+DatabaseHandler.DATABASE_VERSION);
		
		db.getReadableDatabase();
	    songList = db.getAllSongs();
	    db.close();
	   
	   /* SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
	            R.layout.playlist_item, 
	            db.getValues(), 
	            new String[] {DatabaseHandler.KEY_TITLE }, 
	            new int[] { R.id.songTitle },0);*/
	   CustomAdapter adapter = new CustomAdapter(this,R.layout.playlist_item,
			   db.getValues(),
			   new String[] {DatabaseHandler.KEY_TITLE},
			   new int[] {R.id.songTitle},0);
	    setListAdapter(adapter);
	    //selecting Single ListView item
	    ListView lv = getListView();
	    //listening to single listitem click
	    lv.setOnItemClickListener(new OnItemClickListener() {
	    	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting listitem index
                int songIndex = position;
                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        MusicPlayerActivity.class);
                
                // Sending songIndex to PlayerActivity
               try{ in.putExtra("songIndex1", songIndex);
                setResult(100, in);
                }
                catch (NullPointerException e){
                	e.printStackTrace();
                }
                // Closing PlayListView
               finish();
            }
        });    
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.playlist, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            return true;
	        case R.id.action_refresh:
	        	try{
	        	db.reset();
	        	}
	        	catch(RuntimeException e){
	        		e.printStackTrace();
	        	}
	        	finally{
	            Intent in = new Intent (this,SongsManager.class);
	            startActivity(in);
	            finish();
	        	}
	            return true;
	        case R.id.action_create:
	        	try{
	        		SQLiteDatabase db1 = db.getWritableDatabase();
	        		db.onCreate(db1);}
	        	catch(RuntimeException e){
	        		Log.e("PlaylistActivity:106","Already Created");
	        	}
	        	finally{
	        		   AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);  
	        		   alertDialog.setTitle("Database Created!");  
	        		   alertDialog.setMessage("Please Click on Refresh.");  
	        		   alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
	        		   public void onClick(DialogInterface dialog, int which) {  
	        		        return;  
	        		   } });  
	        		   AlertDialog alert = alertDialog.create();
	        		   alert.show();

	        		
	        	}
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
		
		
		
}

