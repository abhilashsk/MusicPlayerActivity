package com.abhilash.mymusicplayer;

public class SongList {
//private variables
	int _id;
	String _path;
	String _songTitle;
	String _artist;
	
	
	//Empty Constructor
	public SongList(){
	
	}
	public SongList(int id,String songTitle,String path,String artist){
		this._id=id;
		this._path = path;
		this._songTitle = songTitle;
		this._artist = artist;
		
		
	}
	 // constructor
    public SongList(String songTitle,String path,String artist){
        this._songTitle = songTitle;
        this._path=path;
        this._artist = artist;
    }
    public SongList(String songTitle,String path){
        this._songTitle = songTitle;
        this._path=path;
        
    }
    //getting ID
    public String getPath(){
    	return this._path;
    }
    //setting ID
    public void setPath(String path){
    	this._path = path;
    }
    //getting song Title
    public String getTitle(){
    	return this._songTitle;
    }
    //setting song Title
    public void setTitle(String songTitle){
    	this._songTitle = songTitle;
    }
    public void setID(int id){
    	this._id = id;
    }
    public int getID(){
    	return this._id;
    }
    public void setArtist(String artist){
    	this._artist = artist;
    }
    public String getArtist(){
    	return this._artist;
    }
	
}
