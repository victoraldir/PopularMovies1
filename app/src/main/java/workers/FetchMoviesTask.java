package workers;

import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quartzo.popularmovies1.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import adapters.ImageAdapter;
import data.Movie;

/**
 * Created by victoraldir on 05/10/2016.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final String TAG_LOG = FetchMoviesTask.class.getSimpleName();
    private ImageAdapter mImageAdapter;

    public FetchMoviesTask(ImageAdapter mImageAdapter) {
        this.mImageAdapter = mImageAdapter;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        HttpURLConnection urlConnection;
        BufferedReader reader;
        final String sort = params[0];
        final String key = BuildConfig.THE_MOVIE_DATABASE_API_KEY;

        try {

            String MOVIES_BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
            String SORT_PARAM = "sort_by";
            String KEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, sort)
                    .appendQueryParameter(KEY_PARAM, key)
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            Gson gson = new Gson();

            JSONObject forecastJson = new JSONObject(buffer.toString());
            Type listType = new TypeToken<ArrayList<Movie>>() {
            }.getType();

            return gson.fromJson(forecastJson.getString("results"), listType);

        } catch (IOException ignored) {

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {

        if (movies != null) {

            mImageAdapter.setMovieList(movies);
            mImageAdapter.notifyDataSetChanged();

        }

    }
}
