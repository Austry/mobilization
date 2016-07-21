package com.austry.mobilization.net;

import android.content.res.Resources;

import com.android.volley.Response;
import com.austry.mobilization.R;
import com.austry.mobilization.model.Artist;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ArtistsResponseListener implements Response.Listener<String> {
    private ArtistsResponseCallback callback;
    private Resources resources;
    public ArtistsResponseListener(ArtistsResponseCallback callback, Resources resources){
        this.callback = callback;
        this.resources  = resources;
    }
    @Override
    public void onResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            List<Artist> artistsL = mapper.readValue(response, new TypeReference<List<Artist>>() {});
            callback.success(artistsL);
        } catch (Exception e) {
            e.printStackTrace();
            callback.error(resources.getString(R.string.mapping_error));
        }
    }
}
