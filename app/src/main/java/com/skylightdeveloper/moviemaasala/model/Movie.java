package com.skylightdeveloper.moviemaasala.model;

import java.util.ArrayList;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class Movie {

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MovieData> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieData> results) {
        this.results = results;
    }

    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<MovieData> results;
}
