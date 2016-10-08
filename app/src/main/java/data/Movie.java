package data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by victoraldir on 05/10/2016.
 */


public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    private String thumbImagePath;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("vote_average")
    private double rate;
    @SerializedName("release_date")
    private String releaseDate;

    protected Movie(Parcel in) {
        id = in.readLong();
        originalTitle = in.readString();
        thumbImagePath = in.readString();
        backdrop = in.readString();
        synopsis = in.readString();
        rate = in.readDouble();
        releaseDate = in.readString();
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getThumbImagePath() {
        return thumbImagePath;
    }

    public void setThumbImagePath(String thumbImagePath) {
        this.thumbImagePath = thumbImagePath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(originalTitle);
        dest.writeString(thumbImagePath);
        dest.writeString(backdrop);
        dest.writeString(synopsis);
        dest.writeDouble(rate);
        dest.writeString(releaseDate);

    }


}
