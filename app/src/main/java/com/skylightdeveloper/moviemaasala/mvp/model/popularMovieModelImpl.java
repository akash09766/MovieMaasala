package com.skylightdeveloper.moviemaasala.mvp.model;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.skylightdeveloper.moviemaasala.config.MConstants;
import com.skylightdeveloper.moviemaasala.model.Movie;
import com.skylightdeveloper.moviemaasala.networking.NetworkError;
import com.skylightdeveloper.moviemaasala.networking.NetworkModule;
import com.skylightdeveloper.moviemaasala.networking.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class popularMovieModelImpl implements PopularMovieModel{

    public static final String TAG = popularMovieModelImpl.class.getSimpleName();
    private final Context context;
    private PopularMovieModel.OnPopularMovieFinishedListener listener;
    private Map<String,String>  mQueryParameters;

    public popularMovieModelImpl(PopularMovieModel.OnPopularMovieFinishedListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        mQueryParameters = new HashMap<>();
    }

    @Override
    public void getPopularMovie(int pageNum) {

        mQueryParameters.put("page",String.valueOf(pageNum));
        mQueryParameters.put("language",MConstants.LANGAUGE);
        mQueryParameters.put("api_key",MConstants.API_KEY);

        File cacheFile = new File(context.getCacheDir(), "responses");

        NetworkModule networkModule = new NetworkModule(cacheFile);

        Service service = new Service(networkModule.
                providesNetworkService(networkModule.provideCall()));

        service.getPopularMovieList(mQueryParameters, new Service.PopularMovieListCallback() {
            @Override
            public void onSuccess(Movie movie) {
                listener.onSuccess(movie);
            }

            @Override
            public void onError(NetworkError networkError) {
                listener.onError(networkError.getAppErrorMessage());
            }

            @Override
            public void onError(String errorMsg) {
                listener.onError(errorMsg);
            }
        });
    }
}
