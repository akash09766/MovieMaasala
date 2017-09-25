package com.skylightdeveloper.moviemaasala.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class MovieData implements Parcelable {

    public MovieData() {
    }

    protected MovieData(Parcel in) {
        id = in.readInt();
        vote_count = in.readInt();
        vote_average = in.readFloat();
        title = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        original_language = in.readString();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private int id;
    private int vote_count;
    private float vote_average;
    private String title;
    private String poster_path;
    private String release_date;
    private String backdrop_path;
    private String overview;
    private String original_language;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.id);
        dest.writeInt(this.vote_count);
        dest.writeFloat(this.vote_average);
        dest.writeString(this.title);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.overview);
        dest.writeString(this.original_language);
    }
}
