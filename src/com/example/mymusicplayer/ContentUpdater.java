package com.example.mymusicplayer;

import android.os.AsyncTask;
import android.support.v4.content.Loader;

public abstract class ContentUpdater<T1,T2,T3> extends AsyncTask<T1, T2, T3> {
	 private Loader<?> loader=null;
     ContentUpdater(Loader<?> loader){
    	 this.loader=loader;
     }
     @Override
     protected void onPostExecute(T3 Param){
    	 loader.onContentChanged();
     }
}
