package com.skylightdeveloper.moviemaasala.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.skylightdeveloper.moviemaasala.R;
import com.skylightdeveloper.moviemaasala.adapter.PopularMovieAdapter;
import com.skylightdeveloper.moviemaasala.config.MConstants;
import com.skylightdeveloper.moviemaasala.dagger.DaggerNetComponent;
import com.skylightdeveloper.moviemaasala.dagger.NetModule;
import com.skylightdeveloper.moviemaasala.listeners.MovieItemClickListener;
import com.skylightdeveloper.moviemaasala.model.Movie;
import com.skylightdeveloper.moviemaasala.model.MovieData;
import com.skylightdeveloper.moviemaasala.mvp.presenter.PopularMoviePresenterImpl;
import com.skylightdeveloper.moviemaasala.mvp.view.MainActivityView;
import com.skylightdeveloper.moviemaasala.ui.VegaLayoutManager;

import java.util.ArrayList;

import javax.inject.Inject;


public class MovieActivity extends BaseActivity implements MainActivityView, MovieItemClickListener {

    private String TAG = MovieActivity.class.getSimpleName();

    //    private ProgressDialog dialog;
    private LinearLayoutManager layoutManager;
//    private VegaLayoutManager layoutManager;

    @Inject
    public PopularMoviePresenterImpl popularMoviePresenter;
    private RecyclerView mPopularMovieList;
    private boolean isLoading;
    private int pageNum = 1;
    private int mTotalPageCount = 1;
    private ArrayList<MovieData> movieList = new ArrayList<>();
    private PopularMovieAdapter popularMovieAdapter;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_activity_layout);
        setIdsToViews();
        setListenersToViews();

        DaggerNetComponent.builder().netModule(new NetModule(this, this)).build()
                .inject(this);
        getPopularMovieData();
    }

    private void setListenersToViews() {
        layoutManager = new LinearLayoutManager(this);
//        layoutManager =  new VegaLayoutManager();
        mPopularMovieList.setLayoutManager(layoutManager);

        mPopularMovieList.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled: ");
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0 && pageNum <= mTotalPageCount) {
                        popularMoviePresenter.getPopularMovie(pageNum, false);
                    }
                }
            }
        });
    }

    private void setIdsToViews() {
        mPopularMovieList = (RecyclerView) findViewById(R.id.popular_movie_list_id);
        mProgressBar = (ProgressBar) findViewById(R.id.prog_id);
        mProgressBarCenter = (ProgressBar) findViewById(R.id.prog_center_id);
    }

    private void getPopularMovieData() {
        popularMoviePresenter.getPopularMovie(pageNum, true);
    }

    @Override
    public void onSuccessPopularMovie(Movie movie) {

        if (movie != null) {
            mTotalPageCount = movie.getTotal_pages();
//            mTotalPageCount = 4;
            if (pageNum == 1) {
                setDataToList(movie);
            } else {
                movieList.addAll(movie.getResults());
                popularMovieAdapter.notifyDataSetChanged();
            }
            Log.d(TAG, "onSuccessPopularMovie: " + movie.getTotal_pages());
        } else {
            Log.e(TAG, "onSuccessPopularMovie: movie null");
        }
        if (movie.getTotal_pages() > pageNum) {
            pageNum++;
        }
    }

    private void setDataToList(Movie movie) {
        movieList.addAll(movie.getResults());
//        mPopularMovieList.setLayoutManager(new GridLayoutManager(this,2));
        popularMovieAdapter = new PopularMovieAdapter(this, movieList, this);
        mPopularMovieList.setAdapter(popularMovieAdapter);
    }

    @Override
    public void onErrorPopularMovie(String error) {
        showLongSnackBar(error);
    }

    @Override
    public void showProgress() {
        isLoading = true;
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showCenterProgress() {
        isLoading = true;
        mProgressBarCenter.setVisibility(View.VISIBLE);
        mPopularMovieList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideCenterProgress() {
        isLoading = false;
        mProgressBarCenter.setVisibility(View.GONE);
        mPopularMovieList.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        popularMoviePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMovieItemClick(MovieData data, View view) {
        Log.d(TAG, "onMovieItemClick: " + data.getTitle());
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MConstants.MOVIE_DETAILS_PARCELABLE_FILTER, data);
//        startActivity(intent);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "movie_title_iv");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
