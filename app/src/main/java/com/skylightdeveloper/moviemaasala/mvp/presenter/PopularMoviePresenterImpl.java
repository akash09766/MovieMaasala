package com.skylightdeveloper.moviemaasala.mvp.presenter;

import android.content.Context;

import com.skylightdeveloper.moviemaasala.model.Movie;
import com.skylightdeveloper.moviemaasala.mvp.model.PopularMovieModel;
import com.skylightdeveloper.moviemaasala.mvp.model.popularMovieModelImpl;
import com.skylightdeveloper.moviemaasala.mvp.view.MainActivityView;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class PopularMoviePresenterImpl implements PopularMoviePresenter, PopularMovieModel.OnPopularMovieFinishedListener {


    private String TAG = PopularMoviePresenterImpl.class.getSimpleName();

    private final PopularMovieModel popularMovieModel;
    private MainActivityView mainActivityView;
    private Context context;

    public PopularMoviePresenterImpl(MainActivityView mainActivityView, Context context) {
        this.mainActivityView = mainActivityView;
        this.context = context;
        popularMovieModel = new popularMovieModelImpl(this, context);
    }


    @Override
    public void getPopularMovie(int pageNum) {
        mainActivityView.showProgress();
        popularMovieModel.getPopularMovie(pageNum);
    }

    @Override
    public void onDestroy() {
        mainActivityView = null;
    }

    @Override
    public void onError(String error) {
        mainActivityView.hideProgress();
        mainActivityView.onErrorPopularMovie(error);
    }

    @Override
    public void onSuccess(Movie movie) {
        mainActivityView.hideProgress();
        mainActivityView.onSuccessPopularMovie(movie);
    }

}
