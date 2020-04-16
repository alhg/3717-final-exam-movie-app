package com.allanhoang.a3717finalexam;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> movies;

    public MovieItemAdapter(ArrayList<HashMap<String, String>> movies) {
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textViewMovieTitle = cardView.findViewById(R.id.card_view_movie_title);
        TextView textViewMovieDir = cardView.findViewById(R.id.card_view_movie_director);
        TextView textViewMovieGenre = cardView.findViewById(R.id.card_view_movie_genre);
        TextView textViewMovieDesc = cardView.findViewById(R.id.card_view_movie_description);
        TextView textViewMovieURL = cardView.findViewById(R.id.card_view_movie_url);


        HashMap<String, String> movie = movies.get(position);
        textViewMovieTitle.setText(movie.get("title"));
        textViewMovieDir.setText("Directed by " + movie.get("director"));
        textViewMovieGenre.setText(movie.get("genre"));
        textViewMovieDesc.setText(movie.get("description"));
        textViewMovieURL.setText(movie.get("url"));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }
}
