package by.it.andersen.cleanarchitecturenews.mitch.presentation.viewmodel;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;

import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.ArticleDao;
import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.NewsDatabase;
import by.it.andersen.cleanarchitecturenews.mitch.data.network.ApiClient;
import by.it.andersen.cleanarchitecturenews.mitch.data.network.ApiInterface;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class NewsViewModelTest {
    NewsViewModel viewModel;
    NewsDatabase database;
    ArticleDao articleDao;
    ApiInterface apiInterface;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public final ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() {
            Context context = ApplicationProvider.getApplicationContext();
            database = Room.inMemoryDatabaseBuilder(context, NewsDatabase.class).build();
            articleDao = database.article();
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
            viewModel = new NewsViewModel(articleDao, apiInterface );
        }
        @Override
        protected void after() {
            database.close();
        }
    };

    @Test
    public void getArticles() {
        assertNotNull(viewModel.getArticles("software"));
        assertEquals(false, viewModel.getArticles("software").hasObservers());
    }

    @Test
    public void getErrorMessage() {
        assertNotNull(viewModel.getErrorMessage());
    }

    @Test
    public void getProcessMessage() {
        assertNotNull(viewModel.getProcessMessage());
    }
}