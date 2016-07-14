package com.austry.mobilization.adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.austry.mobilization.R;
import com.austry.mobilization.fragments.ArtistFragment;
import com.austry.mobilization.model.Artist;
import com.austry.mobilization.net.VolleySingleton;

import java.util.List;

import static com.austry.mobilization.utils.StringUtils.getGenres;

public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistViewHolder> {

    public static final String ARTIST_FRAGMENT_NAME = "artist_fragment";
    private List<Artist> data;
    private FragmentActivity activity;

    public ArtistsRecyclerAdapter(List<Artist> data, FragmentActivity context) {
        this.data = data;
        this.activity = context;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArtistViewHolder holder, int position) {
        Artist currentArtist = data.get(position);

        holder.tvGenres.setText(getGenres(currentArtist.getGenres()));
        holder.ivCover.setImageUrl(currentArtist.getCover().getSmall(), VolleySingleton.getInstance().getImageLoader());

        int albums = currentArtist.getAlbums(),
                tracks = currentArtist.getTracks();

        holder.tvAlbumsInfo.setText(String.format("%s , %s",
                activity.getResources().getQuantityString(R.plurals.albums, albums, albums),
                activity.getResources().getQuantityString(R.plurals.tracks, tracks, tracks)));

        holder.tvName.setText(currentArtist.getName());

        holder.root.setOnClickListener((v) -> elementClick(holder));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvGenres;
        public TextView tvAlbumsInfo;
        public NetworkImageView ivCover;
        public View root;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvArtistName);
            tvGenres = (TextView) itemView.findViewById(R.id.tvArtistGenres);
            tvAlbumsInfo = (TextView) itemView.findViewById(R.id.tvArtistAlbumsInfo);
            ivCover = (NetworkImageView) itemView.findViewById(R.id.ivArtistLogo);
            root = itemView.getRootView();

            ivCover.setDefaultImageResId(R.drawable.ic_person_black_48dp);
            ivCover.setErrorImageResId(R.drawable.ic_error_outline_black_48dp);
        }
    }

    private void elementClick(ArtistViewHolder holder){

        Artist artist = data.get(holder.getAdapterPosition());

        ArtistFragment artistFragment = new ArtistFragment();

        Bundle args = new Bundle();
        args.putSerializable(ArtistFragment.EXTRA_ARTIST, artist);
        artistFragment.setArguments(args);

        activity.getSupportFragmentManager().beginTransaction()
                .addToBackStack(ARTIST_FRAGMENT_NAME)
                .replace(R.id.flFragmentContainer, artistFragment , ARTIST_FRAGMENT_NAME)
                .commit();
        activity.setTitle(artist.getName());
    }
}
