package by.it.andersen.cleanarchitecturenews.mitch.di.modules;

import by.it.andersen.cleanarchitecturenews.mitch.presentation.view.NewsActivity;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.view.NewsDetailsActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract NewsActivity contributeNewsActivity();

    @ContributesAndroidInjector()
    abstract NewsDetailsActivity contributeActivity();
}
