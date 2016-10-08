package com.quartzo.popularmovies1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import data.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private final String IMAGE_BG_SIZE = "w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView backGround = (ImageView) findViewById(R.id.detail_imageview_background);
        ImageView poster = (ImageView) findViewById(R.id.detail_imageview_poster);
        TextView overview = (TextView) findViewById(R.id.detail_textview_overview);
        TextView score = (TextView) findViewById(R.id.detail_textview_score);
        TextView release = (TextView) findViewById(R.id.detail_textview_release);
        TextView title = (TextView) findViewById(R.id.detail_textview_title);


        Movie movie = getIntent().getExtras().getParcelable("movie");

        overview.setText(movie.getSynopsis());
        score.setText(Double.toString(movie.getRate()));
        release.setText(String.format(getString(R.string.detail_release), movie.getReleaseDate()));
        title.setText(movie.getOriginalTitle());
        getSupportActionBar().setTitle(movie.getOriginalTitle());

        Picasso.with(getApplicationContext())
                .load(createURIByPhoto(movie.getBackdrop()))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .tag(getApplicationContext())
                .into(backGround);

        Picasso.with(getApplicationContext())
                .load(createURIByPhoto(movie.getThumbImagePath()))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .tag(getApplicationContext())
                .into(poster);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Uri createURIByPhoto(String imagePath) {
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_BG_SIZE)
                .appendEncodedPath(imagePath)
                .build();

        return builtUri;
    }
}
