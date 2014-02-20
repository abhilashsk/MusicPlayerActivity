package com.example.mymusicplayer;

import java.util.List;

import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class PlaylistActivity extends ListActivity {
     //Songs List
	//public ArrayList<HashMap<String,String>> songsList = new ArrayList<HashMap<String,String>>(); 
	List<SongList> songList ;
	DatabaseHandler db = new DatabaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		Log.d("abc", ""+DatabaseHandler.DATABASE_VERSION);
		 //songList =  db.getAllSongs();
	    //get all songs from sd card
		db.getReadableDatabase();
	    songList = db.getAllSongs();
	    db.close();
	    //looping through play list
	   /* for(int i = 0;i < songsList.size();i++)
	    {
	    	// creating new HashMap
	    	HashMap<String,String> song = songsList.get(i);
	    	//adding Hash List to Array List
	    	songsListData.add(song);
	    }*/
	    // adding Menu Items to list View
	    //ListAdapter adapter = new SimpleAdapter (this,songsList,R.layout.playlist_item,new String[]{"songTitle"},new int[]{R.id.songTitle});
	    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
	            R.layout.playlist_item, 
	            db.getValues(), 
	            new String[] {DatabaseHandler.KEY_TITLE }, 
	            new int[] { R.id.songTitle },0);
	   // ArrayAdapter<SongList> adapter = new ArrayAdapter<SongList> (this,R.layout.playlist_item, songList);
	    setListAdapter(adapter);
	    //selecting Single ListView item
	    ListView lv = getListView();
	    //listening to single listitem click
	    lv.setOnItemClickListener(new OnItemClickListener() {
	    	 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting listitem index
                int songIndex = position+1;
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
	/*@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("Restarted");
		db.getReadableDatabase();
	    songList = db.getAllSongs();
	    db.close();
	    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, 
	            R.layout.playlist_item, 
	            db.getValues(), 
	            new String[] {DatabaseHandler.KEY_TITLE }, 
	            new int[] { R.id.songTitle },0);
	   // ArrayAdapter<SongList> adapter = new ArrayAdapter<SongList> (this,R.layout.playlist_item, songList);
	    setListAdapter(adapter);
	    //selecting Single ListView item
	    ListView lv = getListView();
	}*/
	
	
	

}
