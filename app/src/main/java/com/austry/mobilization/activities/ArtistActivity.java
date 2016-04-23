package com.austry.mobilization.activities;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.austry.mobilization.R;
import com.austry.mobilization.fragments.ArtistFragment;
import com.austry.mobilization.model.Artist;

public class ArtistActivity extends AppCompatActivity {

    public static final String ARTIST_FRAGMENT_NAME = "artist_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        Artist currentArtist = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentArtist = (Artist) extras.getSerializable(ArtistFragment.EXTRA_ARTIST);
            if (currentArtist != null) {
                setTitle(currentArtist.getName());
            }
        }
        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ArtistFragment artistFragment = new ArtistFragment();
            Bundle args = new Bundle();
            if (currentArtist != null) {
                args.putSerializable(ArtistFragment.EXTRA_ARTIST, currentArtist);

            }
            artistFragment.setArguments(args);

            ft.add(R.id.flFragmentContainer, artistFragment, ARTIST_FRAGMENT_NAME);
            ft.commit();
        }

    }

}
