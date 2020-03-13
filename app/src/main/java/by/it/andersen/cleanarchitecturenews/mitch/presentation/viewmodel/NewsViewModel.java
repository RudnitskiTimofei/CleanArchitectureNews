package by.it.andersen.cleanarchitecturenews.mitch.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.ArticleDao;
import by.it.andersen.cleanarchitecturenews.mitch.data.model.Article;
import by.it.andersen.cleanarchitecturenews.mitch.data.network.ApiInterface;
import by.it.andersen.cleanarchitecturenews.mitch.data.repository.NewsRepository;

public class NewsViewModel extends ViewModel {
    String TAG = "VIEW_MODEL";
    private String mMessage;
    private NewsRepository repository;
    private LiveData<List<Article>> temp = new MutableLiveData<>();

@Inject
    public NewsViewModel(ArticleDao articleDao, ApiInterface apiInterface){
        Log.d(TAG, "NewsViewModel: ");
       repository = new NewsRepository(articleDao, apiInterface);
    }

    public LiveData<List<Article>> getArticles(String string) {
        Log.d(TAG, "getArticles: ");
        mMessage = string;
        temp = repository.getAllNews(mMessage);
        return temp;
    }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

    public LiveData<String> getProcessMessage() {
        return repository.getProcessMessage();
    }

}
