package com.skylightdeveloper.moviemaasala.dagger;

import com.skylightdeveloper.moviemaasala.activity.MovieActivity;
import javax.inject.Singleton;

import dagger.Component;
/**
 * Created by akash.wangalwar on 13/09/17.
 */

@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {

    void inject(MovieActivity activity);
}
