package com.skylightdeveloper.moviemaasala.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.skylightdeveloper.moviemaasala.model.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class Service {
    private final NetworkService networkService;
    private String TAG = Service.class.getSimpleName();

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public void getPopularMovieList(Map queryParameters, final PopularMovieListCallback callback) {

        Call<Movie> call = networkService.getPolularMovieList(queryParameters);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Log.d(TAG, "onResponse: " + response.code());

                try {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                        return;
                    }
                    if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                        String errorResponse = response.errorBody().string().toString();
                        Log.d(TAG, "onResponse: response : " + errorResponse);
                        Gson gson = new Gson();
                        try {
                            Movie movie = gson.fromJson(errorResponse.trim(),
                                    Movie.class);
                            callback.onSuccess(movie);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: Exception : " + e.getMessage());
                            callback.onError(new NetworkError(e));
                        }

                        return;
                    }
                } catch (Exception e) {
                    callback.onError(new NetworkError(e));
                    Log.e(TAG, "onResponse: exception :: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "onFailure: Exception : " + t.getMessage());
                callback.onError(new NetworkError(t));
            }
        });

    }

    public interface PopularMovieListCallback {
        void onSuccess(Movie  movie);

        void onError(NetworkError networkError);

        void onError(String errorMsg);
    }

}
