package com.skylightdeveloper.moviemaasala.networking;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skylightdeveloper.moviemaasala.config.MConstants;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class NetworkModule {
    File cacheFile;
    private String TAG = NetworkModule.class.getSimpleName();

    public NetworkModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    public Retrofit provideCall(/*final String user_agent, final String authKey*/) {
        Cache cache = null;
//        Log.d(TAG, "provideCall: authKey : "+authKey);
//        Log.d(TAG, "provideCall: user_agent : "+user_agent);
        try {
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

// add logging as last interceptor
//        httpClient.addInterceptor(logging);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request.Builder builder = original.newBuilder()
                                .header("Accept", "application/json")
//                                .header("user_agent", user_agent)
                                .removeHeader("Pragma");

                        /*if ( !authKey.isEmpty()) {
                            builder.header("authorization", authKey);

                        }*/
                        Request request = builder.build();

                        Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(MConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Service providesService(NetworkService networkService) {
        return new Service(networkService);
    }

    public NetworkService providesNetworkService(Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
}
