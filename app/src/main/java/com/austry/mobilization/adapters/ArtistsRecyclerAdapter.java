package com.austry.mobilization.adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.austry.mobilization.R;
import com.austry.mobilization.model.Artist;

import java.util.List;

import static com.austry.mobilization.utils.StringUtils.getGenres;

public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistViewHolder> {

    private List<Artist> data;
    private ArtistClickCallback clickCallback;
    private Resources resources;
    private ImageLoader imageLoader;

    public ArtistsRecyclerAdapter(List<Artist> data, Resources resources, ImageLoader imageLoader, ArtistClickCallback clickCallback) {
        this.data = data;
        this.clickCallback = clickCallback;
        this.resources = resources;
        this.imageLoader = imageLoader;
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
        holder.ivCover.setImageUrl(currentArtist.getCover().getSmall(), imageLoader);

        int albums = currentArtist.getAlbums();
        int tracks = currentArtist.getTracks();

        holder.tvAlbumsInfo.setText(String.format("%s , %s",
                resources.getQuantityString(R.plurals.albums, albums, albums),
                resources.getQuantityString(R.plurals.tracks, tracks, tracks)));

        holder.tvName.setText(currentArtist.getName());

        holder.root.setOnClickListener((v) ->
                clickCallback.elementClick(data.get(holder.getAdapterPosition()))
        );
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

}
