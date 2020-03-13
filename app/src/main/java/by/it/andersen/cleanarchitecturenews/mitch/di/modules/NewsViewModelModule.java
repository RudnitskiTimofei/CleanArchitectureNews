package by.it.andersen.cleanarchitecturenews.mitch.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import by.it.andersen.cleanarchitecturenews.mitch.di.util.ViewModelKey;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.viewmodel.NewsViewModel;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.viewmodel.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NewsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindAuthViewModel(NewsViewModel newsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory factory);

}
