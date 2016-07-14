package com.austry.mobilization.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.austry.mobilization.R;
import com.austry.mobilization.model.Artist;
import com.austry.mobilization.net.VolleySingleton;
import com.austry.mobilization.utils.StringUtils;

public class ArtistFragment extends Fragment {

    public static final String EXTRA_ARTIST = "com.austry.mobilization.ARTIST";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_artist, container, false);
        Bundle args = getArguments();
        if (args != null) {
            Artist artist = (Artist) args.getSerializable(EXTRA_ARTIST);
            initViewAndSetData(fragmentView, artist);
        }
        return fragmentView;
    }

    private void initViewAndSetData(View fragmentView, Artist artist) {
        NetworkImageView ivCover = (NetworkImageView) fragmentView.findViewById(R.id.ivArtistCover);
        TextView tvGenres = (TextView) fragmentView.findViewById(R.id.tvArtistGenres);
        TextView tvAlbums = (TextView) fragmentView.findViewById(R.id.tvArtistAlbums);
        TextView tvTracks = (TextView) fragmentView.findViewById(R.id.tvArtistTracks);
        TextView tvDescription = (TextView) fragmentView.findViewById(R.id.tvArtistDescription);

        //установка данных
        tvGenres.setText(StringUtils.getGenres(artist.getGenres()));
        ivCover.setImageUrl(artist.getCover().getBig(), VolleySingleton.getInstance().getImageLoader());
        tvAlbums.setText(getPlural(R.plurals.albums, artist.getAlbums()));
        tvTracks.setText(getPlural(R.plurals.tracks, artist.getTracks()));
        tvDescription.setText(artist.getDescription());
    }

    private String getPlural(int pluralId, int number) {
        return getResources().getQuantityString(pluralId, number, number);
    }
}
