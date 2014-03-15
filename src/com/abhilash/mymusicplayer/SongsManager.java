package com.abhilash.mymusicplayer;

import java.io.File;

import com.abhilash.mymusicplayer.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;

@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public class SongsManager extends Activity {
//SD card path
	
	DatabaseHandler db = new DatabaseHandler(this);
    public String[] msg = {"Please Wait...","Getting Metadata...","Adding to Library..."};
    ProgressDialog dialog;
    int msgchanger = 0;
	MediaMetadataRetriever retriever = new MediaMetadataRetriever(); 
	final String MEDIA_PATH = Environment.getExternalStorageDirectory().getParent()+'/';
	// Constructor
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songsmanager);
	
		/**
	     * Function to read all mp3 files from sdcard
	     * and store the details in ArrayList
	     * */
		dialog = ProgressDialog.show(this, "Finding Music", "Please Wait...", true);
		
		new Thread(new Runnable(){
			public void run(){
		try{
			
			if(MEDIA_PATH != null){
			
			File home = new File(MEDIA_PATH);
			File[] listFiles = home.listFiles();
			if (listFiles != null && listFiles.length > 0){
				for(File file : listFiles){
					Thread.sleep(5000);
					System.out.println(file.getAbsolutePath());
					if(file.isDirectory()){
						scanDirectory(file);
					}else{
						addSongToDB(file);
					}
						
				}
			}
				
			}
		 /*List<SongList> songs = db.getAllSongs();    
	     for (SongList cn : songs) {
	         String log = "id:"+cn.getID()+" Path: "+cn.getPath()+" ,Title: " + cn.getTitle();
	             // Writing Contacts to log
	     Log.d("Name: ", log);
	     
	     }*/
			
	     if (dialog.isShowing()) {
	    	
             dialog.dismiss(); 
             Intent in1= new Intent(getBaseContext(),PlaylistActivity.class);
             startActivity(in1);
             
         }	
	     } catch (InterruptedException e){
			e.printStackTrace();
		}
			}
			
		}).start();  
    	
  
	}
    	
	private void scanDirectory(File directory){
		if(directory != null){
			File[] listFiles = directory.listFiles();
			if(listFiles != null && listFiles.length > 0){
				for(File file : listFiles){
					if (file.isDirectory()){
						scanDirectory(file);
					}else {
						
						addSongToDB(file);
					}
				}
			}
		}
	}

private void addSongToDB(File song)
{
	if (song.getName().endsWith(".mp3")||song.getName().endsWith(".MP3"))
	{
		try{
			retriever.setDataSource(song.getAbsolutePath());
			}
			catch(NullPointerException e){
				Log.e("abdef","fuck");}
			catch(RuntimeException e){
				Log.e("SongManager:92", "fuck");
			}
			finally{
			String songTitle =retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
			
			 String artistName = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
			if (songTitle==null)
			{
				songTitle=song.getName().substring(0, song.getName().length()-4);
			   
			}
			if (artistName == null)
			{
				artistName = "Unknown Artist";
			}
			db.addSong(new SongList(songTitle,song.getAbsolutePath(),artistName));}
	}
}

}
/*final Runnable changeMessage = new Runnable() {
    @Override
    public void run() {
    	
    	if (dialog.isShowing()){
    	  dialog.setMessage(msg[msgchanger]);
    	  msgchanger = (msgchanger+1)%3;
    	}
    }
};
for(int i = 0;i<100000;i++)
{   if (i>50000){
	runOnUiThread(changeMessage);
	}}*/