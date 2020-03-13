package by.it.andersen.cleanarchitecturenews.mitch.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.ArticleDao;
import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.NewsDatabase;
import by.it.andersen.cleanarchitecturenews.mitch.data.model.Article;
import by.it.andersen.cleanarchitecturenews.mitch.data.model.News;
import by.it.andersen.cleanarchitecturenews.mitch.data.network.ApiInterface;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.util.Utils;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.view.NewsActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository {
    private static final String TAG = "REPOSITORY";
    private static final String APY_KEY = "3f2b4d3e212c4c8399901475b294cbab";
    private static final String PUBLISHED_AT = "publishedAt";
    private List<Article> articleList;
    private String currentDate = Utils.getDate();
    private LiveData<List<Article>> allNews;
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private MutableLiveData<String> process = new MutableLiveData<>();
    private Set<String> themes = new LinkedHashSet<>();
    private int timeToRefresh = 60000;
    private ArticleDao mArticleDao;
    private ApiInterface mApiInterface;

    public NewsRepository(ArticleDao articleDao, ApiInterface apiInterface) {
        mArticleDao = articleDao;
        mApiInterface = apiInterface;
    }

    public List<Article> loadNews(final String message) {
        Log.d(TAG, "loadNews: ");
        long currentTime = System.currentTimeMillis();
        if (articleList == null) {
            getData(message);
        } else {
            if (themes.size() < 5 || (currentTime - articleList.get(0).getTime() > timeToRefresh)) {
                getData(message);
            }
        }
        return articleList;
    }

    @SuppressLint("CheckResult")
    public List<Article> getData(String message) {
        Log.d(TAG, "getData: START");
        if (process.getValue() != null || errorData.getValue() != null) {
            process.setValue("");
            errorData.setValue("");
        }
        mApiInterface.getNewsFlowable(message, currentDate, PUBLISHED_AT, APY_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(this::refactList)
                .map(news -> news.getArticles())
                .map(articles -> {
                    for (Article article : articles) {
                        String string = article.getTitle();
                        article.setTitle("  Filtered  " + string);
                    }
                    return articles;
                }).subscribe(new Observer<List<Article>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(List<Article> articles) {
                for (Article article : articles) {
                    article.setTheme(message);
                    article.setTime(System.currentTimeMillis());
                }
                articleList = articles;
                themes.add(message);
                new Thread(() -> mArticleDao.insert(articleList)).start();
            }

            @Override
            public void onError(Throwable e) {
                errorData.setValue("error");
            }

            @Override
            public void onComplete() {
                process.postValue("stop");
            }
        });

        return articleList;
    }

    private boolean refactList(News news) {
        if (news.getArticles().get(0).getTitle().length() > 20) {
            return true;
        } else {
            return false;
        }
    }

    public LiveData<List<Article>> getAllNews(String string) {
        if (articleList == null) {
            articleList = new ArrayList<>();
        }
        loadNews(string);
        allNews = mArticleDao.getArticleByTheme(string);
        process.postValue("stop");
        Log.d(TAG, "getAllNews: from database");
        return allNews;
    }

    public LiveData<String> getErrorMessage() {
        return errorData;
    }

    public LiveData<String> getProcessMessage() {
        return process;
    }
}
