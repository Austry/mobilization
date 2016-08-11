package com.austry.mobilization.fragments;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.austry.mobilization.Application;
import com.austry.mobilization.R;
import com.austry.mobilization.adapters.ArtistClickCallback;
import com.austry.mobilization.adapters.ArtistsRecyclerAdapter;
import com.austry.mobilization.model.Artist;
import com.austry.mobilization.model.ArtistsProviderContract;
import com.austry.mobilization.model.Cover;
import com.austry.mobilization.net.ArtistsResponseCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

import static java.util.Arrays.asList;


public class AllArtistsFragment extends Fragment implements ArtistsResponseCallback, ArtistClickCallback,
         LoaderManager.LoaderCallbacks<List<Artist>>{

    private static final String ARTIST_FRAGMENT_NAME = "artist_fragment";
    private static final String TAG = AllArtistsFragment.class.getName();
    private static final int LOADER_ID = 123;

    private RecyclerView rvArtists;
    private SwipeRefreshLayout srlRoot;
    private Resources resources;
    private static final String[] COLUMNS_TO_REQUEST = {
            ArtistsProviderContract.ID,
            ArtistsProviderContract.NAME,
            ArtistsProviderContract.DESCRIPTION,
            ArtistsProviderContract.LINK,
            ArtistsProviderContract.GENRES,
            ArtistsProviderContract.ALBUM,
            ArtistsProviderContract.TRACKS,
            ArtistsProviderContract.URL_BIG,
            ArtistsProviderContract.URL_SMALL
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = getResources();
        View fragmentView = inflater.inflate(R.layout.fragment_all_artists, container, false);
        initViews(fragmentView);
        getActivity().setTitle(getString(R.string.app_name));
        setRefreshState(true);
        if(savedInstanceState == null) {
            initLoader();
        }
        return fragmentView;
    }

    @Override
    public Loader<List<Artist>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<Artist>>(getContext()) {
            @Override
            public List<Artist> loadInBackground() {
                return loadData();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> loader, List<Artist> data) {
        success(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> loader) {

    }

    private void initViews(View fragmentView) {
        rvArtists = (RecyclerView) fragmentView.findViewById(R.id.rvArtists);
        srlRoot = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srlRoot);

        rvArtists.setLayoutManager(new LinearLayoutManager(getActivity()));

        srlRoot.setColorSchemeResources(R.color.colorAccent);
        srlRoot.setOnRefreshListener(this::initLoader);
    }

    private void initLoader() {
        getLoaderManager()
                .initLoader(LOADER_ID, null, this).forceLoad();
    }

    private List<Artist> loadData() {
        Cursor cursor = getActivity().getContentResolver().query(ArtistsProviderContract.CONTENT_URI,
                COLUMNS_TO_REQUEST, null, null, null);
        List<Artist> artists = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            artists = parseArtists(cursor);
        }
        return artists;
    }

    private List<Artist> parseArtists(@NonNull Cursor cursor) {
        List<Artist> result = new LinkedList<>();
        do {
            Artist newArtist = new Artist();
            newArtist.setId(cursor.getLong(0));
            newArtist.setName(cursor.getString(1));
            newArtist.setDescription(cursor.getString(2));
            newArtist.setLink(cursor.getString(3));
            String genres = cursor.getString(4);
            newArtist.setGenres(asList(genres.split(",")));
            newArtist.setAlbums(cursor.getInt(5));
            newArtist.setTracks(cursor.getInt(6));
            Cover cover =new Cover();
            cover.setBig(cursor.getString(7));
            cover.setSmall(cursor.getString(8));
            newArtist.setCover(cover);

            result.add(newArtist);
        } while (cursor.moveToNext());
        cursor.close();
        return result;
    }

    @Override
    public void success(List<Artist> artists) {
        setRefreshState(false);
        ImageLoader loader = ((Application) getActivity().getApplication()).getVolley().getImageLoader();
        ArtistsRecyclerAdapter adapter = new ArtistsRecyclerAdapter(artists, resources, loader, this);
        RecyclerView.Adapter animatedAdapter = new AlphaInAnimationAdapter(adapter);
        rvArtists.setAdapter(animatedAdapter);
    }

    @Override
    public void error(String errorMessage) {
        setRefreshState(false);
        Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void setRefreshState(final boolean state) {
        srlRoot.post(() -> srlRoot.setRefreshing(state));
    }

    @Override
    public void elementClick(Artist artist) {
        if(!srlRoot.isRefreshing()) {
            ArtistFragment artistFragment = new ArtistFragment();

            Bundle args = new Bundle();
            args.putSerializable(ArtistFragment.EXTRA_ARTIST, artist);
            artistFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .addToBackStack(ARTIST_FRAGMENT_NAME)
                    .replace(R.id.flFragmentContainer, artistFragment, ARTIST_FRAGMENT_NAME)
                    .commit();
        }
    }


}
