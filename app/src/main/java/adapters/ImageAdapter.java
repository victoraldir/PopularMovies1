package adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.quartzo.popularmovies1.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import data.Movie;
import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class ImageAdapter extends BaseAdapter {

    private String TAG_LOG = ImageAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<Movie> movieList;

    public ImageAdapter(Context c, ArrayList<Movie> movieList) {
        mContext = c;
        this.movieList = movieList;
        //Picasso.with(mContext).setLoggingEnabled(true);
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public int getCount() {
        return movieList.size();
    }

    public Object getItem(int position) {
        return movieList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
            view.setScaleType(CENTER_CROP);
        } else {
            view = (ImageView) convertView;
        }

        Movie movie = movieList.get(position);

        String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
        String IMAGE_SIZE = "w185";

        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE)
                .appendEncodedPath(movie.getThumbImagePath())
                .build();

        Picasso.with(mContext)
                .load(builtUri.toString())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .fit()
                .tag(mContext)
                .into(view);

        return view;
    }

}