package com.skylightdeveloper.moviemaasala.networking;

import com.skylightdeveloper.moviemaasala.config.MConstants;
import com.skylightdeveloper.moviemaasala.model.Movie;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public interface NetworkService {

    @GET(MConstants.POLULAR_MOVIES)
    Call<Movie> getPolularMovieList(@QueryMap Map<String, String> options);
}
