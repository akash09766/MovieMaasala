package com.skylightdeveloper.moviemaasala.dagger;

import android.content.Context;

import com.skylightdeveloper.moviemaasala.mvp.presenter.PopularMoviePresenterImpl;
import com.skylightdeveloper.moviemaasala.mvp.view.MainActivityView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akash.wangalwar on 13/09/17.
 */
@Module
public class NetModule {

    private final Context context;
    private MainActivityView mainActivityView;

    public NetModule(MainActivityView mainActivityView, Context context) {
        this.context = context;
        this.mainActivityView = mainActivityView;
    }


    @Singleton
    @Provides
    PopularMoviePresenterImpl providePopularMoviePresenter() {
        return new PopularMoviePresenterImpl(mainActivityView, context);
    }
}
