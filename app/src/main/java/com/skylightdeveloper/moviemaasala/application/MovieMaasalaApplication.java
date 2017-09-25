package com.skylightdeveloper.moviemaasala.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by akash.wangalwar on 13/09/17.
 */

public class MovieMaasalaApplication extends Application {

    private DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();

        setUpImageLoader();
    }

    private void setUpImageLoader() {
       String root = getFilesDir().getAbsolutePath() + "/MovieMaasala/";

        File cacheDir = StorageUtils.getOwnCacheDirectory(this, root);
        // Create global configuration and initialize ImageLoader with this config

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(options)
//                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
