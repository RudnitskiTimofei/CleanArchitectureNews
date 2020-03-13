package by.it.andersen.cleanarchitecturenews.mitch.data.datasource;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.it.andersen.cleanarchitecturenews.mitch.data.model.Article;

@Database(entities = Article.class, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String DB_NAME = "database";
    private static NewsDatabase instance;

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ArticleDao article();
}