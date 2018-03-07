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
    private boolean isFirstCall;

    public PopularMoviePresenterImpl(MainActivityView mainActivityView, Context context) {
        this.mainActivityView = mainActivityView;
        this.context = context;
        popularMovieModel = new popularMovieModelImpl(this, context);
    }


    @Override
    public void getPopularMovie(int pageNum, boolean isFirstCall) {
        this.isFirstCall = isFirstCall;

        if (isFirstCall) {
            mainActivityView.showCenterProgress();
        } else {
            mainActivityView.showProgress();
        }

        popularMovieModel.getPopularMovie(pageNum);
    }

    @Override
    public void onDestroy() {
        mainActivityView = null;
    }

    @Override
    public void onError(String error) {

        if (isFirstCall) {
            mainActivityView.hideCenterProgress();
        } else {
            mainActivityView.hideProgress();
        }
        mainActivityView.onErrorPopularMovie(error);
    }

    @Override
    public void onSuccess(Movie movie) {
        if (isFirstCall) {
            mainActivityView.hideCenterProgress();
        } else {
            mainActivityView.hideProgress();
        }
        mainActivityView.onSuccessPopularMovie(movie);
    }

}
