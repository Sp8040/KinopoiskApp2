package ru.startandroid.kinopoiskapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private final static String PHOTO_URL = "http://cinema.areas.su/up/images/";
    private List<Movies> mMovies;
    private Context mContext;

    public MoviesAdapter(List<Movies> movies) {
        this.mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movies movie = mMovies.get(position);
        holder.nameTextView.setText(movie.getName());
        holder.descriptionTextView.setText(movie.getDescription());

        Picasso.with(mContext)
                .load(PHOTO_URL + movie.getPoster())
                .resize(500,700)
                .into(holder.posterImageView);

        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), CardOfMovie.class).putExtra("movieId", movie.getMovieId());
                Intent intent = new Intent(v.getContext(), CardOfMovie.class).putExtra("movieId", movie.getMovieId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView posterImageView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.tvName);
            descriptionTextView = (TextView) itemView.findViewById(R.id.tvDescription);
            posterImageView = (ImageView) itemView.findViewById(R.id.ivPoster);
        }
    }

}
