package com.austry.mobilization.net;

import android.content.res.Resources;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.austry.mobilization.Application;
import com.austry.mobilization.R;

public class ArtistsErrorListener implements Response.ErrorListener {
    private ArtistsResponseCallback callback;
    private Resources resources;

    public ArtistsErrorListener(ArtistsResponseCallback callback, Resources resources){
        this.callback = callback;
        this.resources = resources;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.error(resources.getString(R.string.network_error));
    }
}
