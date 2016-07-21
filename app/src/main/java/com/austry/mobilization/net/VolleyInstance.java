package com.austry.mobilization.net;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.austry.mobilization.Application;


public class VolleyInstance {
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public VolleyInstance(Context context){
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(this.requestQueue, new DiskCachedImageLoader(context.getCacheDir()));
    }

    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }

    public ImageLoader getImageLoader(){
        return this.imageLoader;
    }
}
