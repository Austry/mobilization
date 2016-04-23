package com.austry.mobilization.net;

import com.android.volley.Response;
import com.austry.mobilization.Application;
import com.austry.mobilization.R;
import com.austry.mobilization.model.Artist;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ArtistsResponseListener implements Response.Listener<String> {
    private ArtistsResponseCallback callback;
    public ArtistsResponseListener(ArtistsResponseCallback callback){
        this.callback = callback;
    }
    @Override
    public void onResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            List<Artist> artistsL = mapper.readValue(response, new TypeReference<List<Artist>>() {});
            callback.success(artistsL);
        } catch (Exception e) {
            e.printStackTrace();
            callback.error(Application.getContext().getString(R.string.mapping_error));
        }
    }
}
