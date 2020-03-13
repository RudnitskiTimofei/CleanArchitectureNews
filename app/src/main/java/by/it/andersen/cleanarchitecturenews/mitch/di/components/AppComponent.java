package by.it.andersen.cleanarchitecturenews.mitch.di.components;


import android.app.Application;

import javax.inject.Singleton;

import by.it.andersen.cleanarchitecturenews.mitch.BaseApplication;
import by.it.andersen.cleanarchitecturenews.mitch.di.modules.ActivityModule;
import by.it.andersen.cleanarchitecturenews.mitch.di.modules.DataBaseModule;
import by.it.andersen.cleanarchitecturenews.mitch.di.modules.NetworkModule;
import by.it.andersen.cleanarchitecturenews.mitch.di.modules.NewsViewModelModule;
import by.it.andersen.cleanarchitecturenews.mitch.di.modules.PicassoModule;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.view.NewsActivity;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = { NetworkModule.class, PicassoModule.class, DataBaseModule.class, NewsViewModelModule.class,
ActivityModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent{

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }


    void inject(NewsActivity activity);

    void inject(BaseApplication application);


}
