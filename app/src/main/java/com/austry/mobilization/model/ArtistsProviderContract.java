package com.austry.mobilization.model;


import android.net.Uri;

public class ArtistsProviderContract {

    private static final String AUTHORITY = "com.austry.artistsProvider";
    private static final String ARTISTS_PATH = "artists";

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + ARTISTS_PATH);

    public static final String ID = "artists.id";
    public static final String NAME = "artists.name";
    public static final String ALBUM = "artists.albums";
    public static final String TRACKS = "artists.tracks";
    public static final String DESCRIPTION = "artists.description";
    public static final String LINK = "artists.link";
    public static final String URL_SMALL = "covers.url_small";
    public static final String URL_BIG = "covers.url_big";
    public static final String GENRES = "GROUP_CONCAT(genres.name, ',')";

}
