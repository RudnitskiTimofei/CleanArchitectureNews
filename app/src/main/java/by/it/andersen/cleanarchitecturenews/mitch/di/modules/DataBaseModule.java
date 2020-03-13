package by.it.andersen.cleanarchitecturenews.mitch.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.ArticleDao;
import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.NewsDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {

    @Singleton
    @Provides
    NewsDatabase provideNewsDatabase(Application application){
        return Room.databaseBuilder(application, NewsDatabase.class, "NewsDataBase").build();
    }

    @Singleton
    @Provides
    ArticleDao provideArticleDao(NewsDatabase database){
        return database.article();
    }

}
