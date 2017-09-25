package com.skylightdeveloper.moviemaasala.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skylightdeveloper.moviemaasala.R;
import com.skylightdeveloper.moviemaasala.config.MConstants;
import com.skylightdeveloper.moviemaasala.model.MovieData;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by akash.wangalwar on 15/09/17.
 */

public class MovieDetailsActivity extends BaseActivity {

    private static final String TAG = MovieDetailsActivity.class.getSimpleName();
    private TextView mMovieTitleTv;
    private RatingBar mMovieRatingBar;
    private TextView mReleaseDateTv;
    private TextView mLangTv;
    private TextView mMovieOverviewTv;
    private ImageView mMovieIv;
    private ImageView mMovieHeaderIv;
    private MovieData data;
    private SimpleDateFormat sdf;
    private SimpleDateFormat formatter;
    private DecimalFormat df = new DecimalFormat("#.#");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_details_activity_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setIdsToViews();
        getIntentData();
        setDataToView();
    }

    private void setIdsToViews() {

        mMovieTitleTv = (TextView) findViewById(R.id.movie_name_tv_id);
        mMovieRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mReleaseDateTv = (TextView) findViewById(R.id.release_date_tv_id);
        mLangTv = (TextView) findViewById(R.id.lang_tv_id);
        mMovieOverviewTv = (TextView) findViewById(R.id.overview_tv_id);

        mMovieIv = (ImageView) findViewById(R.id.detail_movie_iv_id);
        mMovieHeaderIv = (ImageView) findViewById(R.id.header_iv_id);

    }

    private void getIntentData() {

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            MovieData data = (MovieData) bundle.getParcelable(MConstants.MOVIE_DETAILS_PARCELABLE_FILTER);
            this.data = data;
            Log.d(TAG, "getIntentData: " + data.getTitle());
        } else {
            Log.e(TAG, "getIntentData: bundle null");
        }
    }


    private void setDataToView() {
        df.setRoundingMode(RoundingMode.CEILING);
        formatter = new SimpleDateFormat("MMM dd, yyyy");
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        mMovieTitleTv.setText(data.getTitle());
        mMovieRatingBar.setRating(Float.valueOf(df.format(data.getVote_average() / 2)));

        try {
            mReleaseDateTv.setText(formatter.format(sdf.parse(data.getRelease_date())));
        } catch (ParseException e) {
            Log.e(TAG, "setDataToView: " + e.getMessage());
        }
        mLangTv.setText(data.getOriginal_language());
        mMovieOverviewTv.setText(data.getOverview());

        Picasso.with(this).load(MConstants.IMAGE_URL_BASE_URL_TYPE_ORIGINAL + data.getPoster_path()).into(mMovieIv);
        Picasso.with(this).load(MConstants.IMAGE_URL_BASE_URL_TYPE_HEADER + data.getBackdrop_path()).into(mMovieHeaderIv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
