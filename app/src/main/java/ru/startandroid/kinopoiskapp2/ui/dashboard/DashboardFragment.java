package ru.startandroid.kinopoiskapp2.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.startandroid.kinopoiskapp2.ApiClient;
import ru.startandroid.kinopoiskapp2.CardOfMovie;
import ru.startandroid.kinopoiskapp2.Movies;
import ru.startandroid.kinopoiskapp2.MoviesAdapter;
import ru.startandroid.kinopoiskapp2.MoviesAdapterHor;
import ru.startandroid.kinopoiskapp2.R;
import ru.startandroid.kinopoiskapp2.ui.home.HomeViewModel;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private HomeViewModel homeViewModel;

    RecyclerView mRecyclerView;
    RecyclerView mRecyclerViewHor;

    List<Movies> mMovies;

    TextView movieName;
    ImageView moviePoster;

    int id;
    private SharedPreferences mSettings;

    private final static String PHOTO_URL = "http://cinema.areas.su/up/images/";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        mMovies = new ArrayList<>();

        mRecyclerView = (RecyclerView) root.findViewById(R.id.rvMainTrendMovies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        MoviesAdapter moviesAdapter = new MoviesAdapter(mMovies);
        mRecyclerView.setAdapter(moviesAdapter);


        final Call<List<Movies>> call = ApiClient.getService().getMovieTrend();
        call.enqueue(new Callback<List<Movies>>() {
            @Override
            public void onResponse(Call<List<Movies>> call, Response<List<Movies>> response) {
                if (response.isSuccessful()) {
                    mMovies.addAll(response.body());
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movies>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }
}
