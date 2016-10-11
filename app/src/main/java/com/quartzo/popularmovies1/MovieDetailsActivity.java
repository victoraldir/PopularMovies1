package com.quartzo.popularmovies1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import data.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private final String IMAGE_BG_SIZE = "w500";

    @BindView(R.id.detail_imageview_background) ImageView backGround;
    @BindView(R.id.detail_imageview_poster) ImageView poster;
    @BindView(R.id.detail_textview_overview) TextView overview;
    @BindView(R.id.detail_textview_score) TextView score;
    @BindView(R.id.detail_textview_release) TextView release;
    @BindView(R.id.detail_textview_title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Movie movie = getIntent().getExtras().getParcelable("movie");

        overview.setText(movie.getSynopsis());
        score.setText(Double.toString(movie.getRate()));
        release.setText(String.format(getString(R.string.detail_release), movie.getReleaseDate()));
        title.setText(movie.getOriginalTitle());
        getSupportActionBar().setTitle(movie.getOriginalTitle());

        loadImageView(movie,poster);
        loadImageView(movie,backGround);

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

    public void loadImageView(Movie movie, ImageView imageView){
        Picasso.with(getApplicationContext())
                .load(createURIByPhoto(movie.getThumbImagePath()))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .tag(getApplicationContext())
                .into(imageView);
    }

    public Uri createURIByPhoto(String imagePath) {
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_BG_SIZE)
                .appendEncodedPath(imagePath)
                .build();

        return builtUri;
    }
}
