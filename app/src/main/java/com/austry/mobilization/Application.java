package com.austry.mobilization;

import android.content.Context;

import com.austry.mobilization.net.VolleyInstance;

public class Application extends android.app.Application{

    private static Application instance;
    private VolleyInstance volleyInstance;


    public Application() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    public VolleyInstance getVolley(){
        if(volleyInstance == null){
            volleyInstance = new VolleyInstance(this);
        }
        return volleyInstance;
    }

}
