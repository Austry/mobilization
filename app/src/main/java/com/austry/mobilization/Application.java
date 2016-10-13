package com.austry.mobilization;

import android.content.Context;

import com.austry.mobilization.net.VolleyInstance;

public class Application extends android.app.Application{

    private Application instance;
    private VolleyInstance volleyInstance;


    public Application() {
        instance = this;
    }

    public Context getContext() {
        return instance;
    }

    public VolleyInstance getVolley(){
        if(volleyInstance == null){
            volleyInstance = new VolleyInstance(this);
        }
        return volleyInstance;
    }

    public static Application from(Context context){
        return (Application) context.getApplicationContext();
    }

}
