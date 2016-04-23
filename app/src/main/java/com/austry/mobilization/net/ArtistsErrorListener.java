package com.austry.mobilization.net;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.austry.mobilization.Application;
import com.austry.mobilization.R;

public class ArtistsErrorListener implements Response.ErrorListener {
    private ArtistsResponseCallback callback;

    public ArtistsErrorListener(ArtistsResponseCallback callback){
        this.callback = callback;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.error(Application.getContext().getString(R.string.network_error));
    }
}
