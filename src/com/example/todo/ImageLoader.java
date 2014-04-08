package com.example.todo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageLoader {
    
    
    //FileCache fileCache;
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService; 
    
    public ImageLoader(Context context){
      //  fileCache=new FileCache(context);
        executorService=Executors.newFixedThreadPool(5);
    }
    
    final int unchecked_id=R.drawable.stub;
    final int checked_id=R.drawable.icon;
    int i =0;
    public void DisplayImage(ImageView imageView, String flag)
    {
        System.out.println("Flag is :"+ flag+"end");
        if(flag.equals("0"))
        	imageView.setImageResource(checked_id);
        else
        {
            
            imageView.setImageResource(unchecked_id);
        }
        i++;
    }
        
  
    //Used to display bitmap in the UI thread
  
    public void clearCache() {
        
        //fileCache.clear();
    }

}
