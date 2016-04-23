package com.austry.mobilization.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.austry.mobilization.R;
import com.austry.mobilization.activities.ArtistActivity;
import com.austry.mobilization.fragments.ArtistFragment;
import com.austry.mobilization.model.Artist;
import com.austry.mobilization.net.VolleySingleton;

import java.util.List;

import static com.austry.mobilization.utils.StringUtils.getAlbums;
import static com.austry.mobilization.utils.StringUtils.getGenres;
import static com.austry.mobilization.utils.StringUtils.getTracks;

public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistViewHolder> {

    private List<Artist> data;
    private Context context;

    public ArtistsRecyclerAdapter(List<Artist> data, Context context) {
        this.data = data;
        this.context = context;
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

        holder.tvAlbumsInfo.setText(String.format("%s , %s",
                getAlbums(currentArtist.getAlbums()),
                getTracks(currentArtist.getTracks())));

        holder.tvName.setText(currentArtist.getName());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent artistActivityIntent = new Intent(context, ArtistActivity.class);
                artistActivityIntent.putExtra(ArtistFragment.EXTRA_ARTIST, data.get(holder.getAdapterPosition()));
                context.startActivity(artistActivityIntent);
            }
        });
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
