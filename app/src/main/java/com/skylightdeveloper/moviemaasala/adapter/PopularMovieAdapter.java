package com.skylightdeveloper.moviemaasala.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skylightdeveloper.moviemaasala.R;
import com.skylightdeveloper.moviemaasala.config.MConstants;
import com.skylightdeveloper.moviemaasala.listeners.MovieItemClickListener;
import com.skylightdeveloper.moviemaasala.model.MovieData;
import com.squareup.picasso.Picasso;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {

    private static final String TAG = PopularMovieAdapter.class.getSimpleName();
    private final ImageLoader imageLoader;
    private final DisplayImageOptions options;
    private final SimpleDateFormat sdf;
    private final SimpleDateFormat formatter;
    private Context mContext;
    private ArrayList<MovieData> list;
    private LayoutInflater inflater;
    private DecimalFormat df = new DecimalFormat("#.#");
    private MovieItemClickListener listener;

    public PopularMovieAdapter(Context mContext, ArrayList<MovieData> list, MovieItemClickListener listener) {
        this.mContext = mContext;
        this.list = list;
        this.listener = listener;
        inflater = LayoutInflater.from(mContext);
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .showImageOnLoading(R.mipmap.ic_launcher)
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        df.setRoundingMode(RoundingMode.CEILING);
        formatter = new SimpleDateFormat("MMM dd, yyyy");
        sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public PopularMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularMovieViewHolder(inflater.inflate(R.layout.popular_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final PopularMovieViewHolder holder, int position) {

        final MovieData item = list.get(position);
        try {
            Log.d(TAG, "onBindViewHolder:" + "Name : " + item.getTitle() + "  Rating : " + Float.valueOf(df.format(item.getVote_average() / 2))
                    + "  date : " + formatter.format(sdf.parse(item.getRelease_date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.mMovieTitleTv.setText(item.getTitle());
        try {
            holder.mReleaseDateTv.setText(formatter.format(sdf.parse(item.getRelease_date())));
        } catch (ParseException e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }
        holder.mMovieRatingBar.setRating(Float.valueOf(df.format(item.getVote_average() / 2)));
        holder.mReleaseLang.setText(item.getOriginal_language());
//        imageLoader.displayImage(MConstants.IMAGE_URL_BASE_URL_TYPE_ORIGINAL+item.getPoster_path(),holder.mMovieIv,options);
        if (holder.mMovieIv.getWidth() == 0) {
            Picasso.with(mContext).load(MConstants.IMAGE_URL_BASE_URL_TYPE_ORIGINAL + item.getPoster_path())
//                    .placeholder(R.drawable.ic_local_movies_black_48dp)
                    .into(holder.mMovieIv);
        } else {
            Picasso.with(mContext)
                    .load(MConstants.IMAGE_URL_BASE_URL_TYPE_ORIGINAL + item.getPoster_path())
                    .resize(holder.mMovieIv.getWidth(), holder.mMovieIv.getHeight())
                    .centerCrop()
//                    .placeholder(R.drawable.ic_local_movies_black_48dp)
                    .into(holder.mMovieIv);
        }
        holder.mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMovieItemClick(item, (View) holder.mMovieIv);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mMovieIv;
        private TextView mMovieTitleTv;
        private TextView mReleaseDateTv;
        private TextView mReleaseLang;
        private RatingBar mMovieRatingBar;
        private RelativeLayout mParentView;

        public PopularMovieViewHolder(View itemView) {
            super(itemView);

            mParentView = (RelativeLayout) itemView.findViewById(R.id.parent_view_id);
            mMovieTitleTv = (TextView) itemView.findViewById(R.id.movie_name_tv_id);
            mReleaseDateTv = (TextView) itemView.findViewById(R.id.release_date_tv_id);
            mReleaseLang = (TextView) itemView.findViewById(R.id.lang_tv_id);
            mMovieIv = (ImageView) itemView.findViewById(R.id.movie_iv_id);
            mMovieRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
