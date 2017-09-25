package com.skylightdeveloper.moviemaasala.mvp.model;

import com.skylightdeveloper.moviemaasala.model.Movie;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public interface PopularMovieModel {

    interface OnPopularMovieFinishedListener {

        void onError(String error);

        void onSuccess(Movie movie);
    }


    void getPopularMovie(int pageNum);
}
