package com.austry.mobilization.net;

import com.austry.mobilization.model.Artist;

import java.util.List;

public interface ArtistsResponseCallback {

    void success(List<Artist> artists);
    void error(String errorMessage);

}
