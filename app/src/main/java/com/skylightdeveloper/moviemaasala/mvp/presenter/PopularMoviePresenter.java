package com.skylightdeveloper.moviemaasala.mvp.presenter;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public interface PopularMoviePresenter {
    void getPopularMovie(int pageNum);
    void onDestroy();
}
