package com.skylightdeveloper.moviemaasala.networking;

import retrofit2.HttpException;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.skylightdeveloper.moviemaasala.model.Failure;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class NetworkError extends Throwable {
    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again.";
    public static final String NETWORK_ERROR_MESSAGE = "No Internet Connection!";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private final Throwable error;
    private String TAG = NetworkError.class.getSimpleName();

    public NetworkError(Throwable e) {
        super(e);
        this.error = e;
    }

    public String getMessage() {
        return error.getMessage();
    }

    public boolean isAuthFailure() {
        return error instanceof HttpException &&
                ((HttpException) error).code() == HTTP_UNAUTHORIZED;
    }

    public boolean isResponseNull() {
        return error instanceof HttpException && ((HttpException) error).response() == null;
    }

    public String getAppErrorMessage() {

        if (this.error instanceof IOException) return NETWORK_ERROR_MESSAGE;
        if (!(this.error instanceof HttpException)) return DEFAULT_ERROR_MESSAGE;
        if (!(this.error instanceof JsonSyntaxException)) return DEFAULT_ERROR_MESSAGE;
        if (!(this.error instanceof JsonParseException)) return DEFAULT_ERROR_MESSAGE;
        retrofit2.Response<?> response = ((HttpException) this.error).response();
        if (response != null) {
            String status = getJsonStringFromResponse(response);
            Log.d(TAG, "getAppErrorMessage: " + status);
            if (!TextUtils.isEmpty(status)) return status;

            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(ERROR_MESSAGE_HEADER))
                return headers.get(ERROR_MESSAGE_HEADER).get(0);
        }else {
            Log.d(TAG, "getAppErrorMessage: null ");
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    protected String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            String jsonString = response.errorBody().string();
            Log.d(TAG, "getJsonStringFromResponse: response : " + response);
            Failure errorResponse = new Gson().fromJson(jsonString, Failure.class);
            return errorResponse.getMessage();
        } catch (Exception e) {
            return null;
        }
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkError that = (NetworkError) o;

        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}