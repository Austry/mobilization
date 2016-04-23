package com.austry.mobilization;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.austry.mobilization.activities.ArtistActivity;
import com.austry.mobilization.fragments.ArtistFragment;
import com.austry.mobilization.model.Artist;
import com.austry.mobilization.model.Cover;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.Arrays.asList;

@RunWith(AndroidJUnit4.class)
public class ArtistActivityTest {

    @Rule
    public ActivityTestRule<ArtistActivity> mActivityRule = new ActivityTestRule<>(
            ArtistActivity.class, true, false);

    private static Artist mockArtist;

    private static final String MOCK_ARTIST_DESCRIPTION = "Description";
    private static final String MOCK_ARTIST_NAME = "Name";
    private static final int MOCK_ARTIST_ALBUMS = 10;
    private static final int MOCK_ARTIST_TRACKS = 25;
    private static final List<String> MOCK_ARTIST_GENRES = asList("rock","hard rock");

    @BeforeClass
    public static void init(){
        mockArtist = new Artist();
        mockArtist.setDescription(MOCK_ARTIST_DESCRIPTION);
        mockArtist.setName(MOCK_ARTIST_NAME);
        mockArtist.setGenres(MOCK_ARTIST_GENRES);
        mockArtist.setAlbums(MOCK_ARTIST_ALBUMS);
        mockArtist.setTracks(MOCK_ARTIST_TRACKS);

        mockArtist.setCover(new Cover());
    }

    @Before
    public void reset(){
        Intent artistActivityIntent = new Intent();
        artistActivityIntent.putExtra(ArtistFragment.EXTRA_ARTIST, mockArtist);
        mActivityRule.launchActivity(artistActivityIntent);
    }

    @Test
    public void description(){
        onView(withId(R.id.tvArtistDescription))
                .check(matches(withText(MOCK_ARTIST_DESCRIPTION)));
    }

    @Test
    public void albums(){
        onView(withId(R.id.tvArtistAlbums))
                .check(matches(withText(MOCK_ARTIST_ALBUMS + " альбомов")));
    }

    @Test
    public void tracks(){
        onView(withId(R.id.tvArtistTracks))
                .check(matches(withText(MOCK_ARTIST_TRACKS + " песен")));
    }

    @Test
    public void genres(){
        onView(withId(R.id.tvArtistGenres))
                .check(matches(withText("rock, hard rock")));
    }

}