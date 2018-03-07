package com.skylightdeveloper.moviemaasala.mvp.view;

import com.skylightdeveloper.moviemaasala.model.Movie;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public interface MainActivityView {

    void onSuccessPopularMovie(Movie movie);
    void onErrorPopularMovie(String error);
    void showProgress();
    void hideProgress();
    void showCenterProgress();
    void hideCenterProgress();
}
