package com.skylightdeveloper.moviemaasala.model;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class Failure {
    public int getCode() {
        return code;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    private int code;
    private int success;
    private String message;
}
