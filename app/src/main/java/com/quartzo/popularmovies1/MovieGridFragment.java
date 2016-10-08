package com.quartzo.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.ImageAdapter;
import data.Movie;
import workers.FetchMoviesTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ImageAdapter mAdapter;
    private ArrayList<Movie> movieList;

    public MovieGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<>();

        } else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", mAdapter.getMovieList());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAdapter = new ImageAdapter(getActivity(), movieList);

        View rootView = inflater.inflate(R.layout.fragment_list_movies, container, false);
        GridView gridViewMovies = (GridView) rootView.findViewById(R.id.fragment_list_movies_gridview);

        gridViewMovies.setAdapter(mAdapter);
        gridViewMovies.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (movieList.isEmpty()) {
            updateMoviesList();
        }
        updateActionBarSubTitle();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent it = new Intent(getActivity(), MovieDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", (Movie) mAdapter.getItem(position));
        it.putExtras(bundle);
        startActivity(it);

    }

    private void updateMoviesList() {

        if(isOnline()) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());

            FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(mAdapter);
            fetchMoviesTask.execute(prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default_value)));
        }else{
            Toast.makeText(getActivity(),getString(R.string.not_connection),Toast.LENGTH_LONG).show();
        }
    }

    private void updateActionBarSubTitle() {

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(isOnline()){

            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());

            String sortCriteria = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default_value));

            if (sortCriteria.equals(getString(R.string.pref_sort_popularity_value))) {
                actionBar.setSubtitle(getString(R.string.action_sort_popularity));
            } else {
                actionBar.setSubtitle(getString(R.string.action_sort_rated));
            }

        }else{

            actionBar.setSubtitle(getString(R.string.not_connection));
        }

    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_grid_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = prefs.edit();

        switch (item.getItemId()) {
            case R.id.action_sort_popularity:

                editor.putString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_popularity_value));
                editor.commit();

                updateMoviesList();
                updateActionBarSubTitle();

                return true;
            case R.id.action_sort_rate:

                editor.putString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_rate_value));
                editor.commit();

                updateMoviesList();
                updateActionBarSubTitle();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
