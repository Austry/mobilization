package com.austry.mobilization.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.austry.mobilization.Application;
import com.austry.mobilization.R;
import com.austry.mobilization.adapters.ArtistClickCallback;
import com.austry.mobilization.adapters.ArtistsRecyclerAdapter;
import com.austry.mobilization.model.Artist;
import com.austry.mobilization.net.ArtistsErrorListener;
import com.austry.mobilization.net.ArtistsResponseCallback;
import com.austry.mobilization.net.ArtistsResponseListener;
import com.austry.mobilization.net.UTF8StringRequest;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class AllArtistsFragment extends Fragment implements ArtistsResponseCallback, ArtistClickCallback {

    private static final String ARTIST_FRAGMENT_NAME = "artist_fragment";
    private static final String LOG_TAG = "AllArtistsFragment";
    private static final String ARTISTS_DATA_URL = "http://download.cdn.yandex.net/mobilization-2016/artists.json";

    private RecyclerView rvArtists;
    private SwipeRefreshLayout srlRoot;
    private RequestQueue networkRequestsQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        networkRequestsQueue = ((Application) getActivity().getApplication()).getVolley().getRequestQueue();

        View fragmentView = inflater.inflate(R.layout.fragment_all_artists, container, false);
        initViews(fragmentView);
        getActivity().setTitle(getString(R.string.app_name));
        setRefreshState(true);
        loadData(false);
        return fragmentView;
    }

    private void initViews(View fragmentView) {
        rvArtists = (RecyclerView) fragmentView.findViewById(R.id.rvArtists);
        srlRoot = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srlRoot);

        rvArtists.setLayoutManager(new LinearLayoutManager(getActivity()));

        srlRoot.setColorSchemeResources(R.color.colorAccent);
        srlRoot.setOnRefreshListener(() -> loadData(true));
    }

    private void loadData(boolean refreshCache) {
        if (refreshCache) {
            networkRequestsQueue.getCache().remove(ARTISTS_DATA_URL);
        }
        if (isOnline()) {
            final StringRequest request =
                    new UTF8StringRequest(Request.Method.GET, ARTISTS_DATA_URL,
                            new ArtistsResponseListener(this), new ArtistsErrorListener(this));
            request.setShouldCache(true);
            networkRequestsQueue.add(request);

        } else {
            error(getActivity().getString(R.string.network_unavailable_error));
        }
    }

    @Override
    public void success(List<Artist> artists) {
        setRefreshState(false);
        ImageLoader loader = ((Application) getActivity().getApplication()).getVolley().getImageLoader();
        ArtistsRecyclerAdapter adapter = new ArtistsRecyclerAdapter(artists, getResources(), loader, this);
        RecyclerView.Adapter animatedAdapter = new AlphaInAnimationAdapter(adapter);
        rvArtists.setAdapter(animatedAdapter);
    }


    @Override
    public void error(String errorMessage) {
        setRefreshState(false);
        Toast.makeText(this.getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    //провеняет соединение с сетью
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
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
