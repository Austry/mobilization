package com.austry.mobilization.net;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.austry.mobilization.Application;


public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleySingleton(){
        requestQueue = Volley.newRequestQueue(Application.getContext());
        imageLoader = new ImageLoader(this.requestQueue, new DiskCachedImageLoader());
    }

    public static VolleySingleton getInstance(){
        if(mInstance == null){
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.imageLoader;
    }
}
