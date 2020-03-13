package by.it.andersen.cleanarchitecturenews.mitch.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import by.it.andersen.cleanarchitecturenews.R;
import by.it.andersen.cleanarchitecturenews.mitch.data.datasource.ArticleDao;
import by.it.andersen.cleanarchitecturenews.mitch.data.model.Article;
import by.it.andersen.cleanarchitecturenews.mitch.data.network.ApiInterface;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.adapter.NewsAdapter;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.viewmodel.NewsViewModel;
import by.it.andersen.cleanarchitecturenews.mitch.presentation.viewmodel.ViewModelProviderFactory;
import dagger.android.AndroidInjection;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.OnNewsListener, AdapterView.OnItemSelectedListener{
    private static final String TAG = "AuthActivity";
    private String choose;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private LinearLayoutManager manager;
    private NewsViewModel viewModel;
    private Spinner spinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AlertDialog alertDialog;
    private List<Article> mArticleList;
    private LiveData<String> error;
    private LiveData<String> process;

    @Inject
    ViewModelProviderFactory factory;
    @Inject
    NewsAdapter adapter;
    @Inject
    ApiInterface apiInterface;
    @Inject
    ArticleDao articleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        pullToRefresh();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choose = spinner.getSelectedItem().toString();
        showDialog();
        setUpObservable(choose);
    }

    @Override
    public void OnNewsClick(int position) {
        Intent intent  = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("title", mArticleList.get(position).getTitle());
        intent.putExtra("description", mArticleList.get(position).getDescription());
        intent.putExtra("source:name", mArticleList.get(position).getSource().getName());
        intent.putExtra("imageUrl", mArticleList.get(position).getUrlToImage());
        startActivity(intent);
    }

    public void setUpObservable(String message){
        viewModel.getArticles(message).observe(NewsActivity.this, articles -> {
            mArticleList = articles;
            adapter.setData(mArticleList.subList(0,20), NewsActivity.this::OnNewsClick);
            adapter.notifyDataSetChanged();
            toolbar.setTitle(choose);
        });

            viewModel.getErrorMessage().observe(NewsActivity.this, s -> {
            if (s.equals("error")){
                showError();
            }
        });

        viewModel.getProcessMessage().observe(NewsActivity.this, s -> {
            if (s.equals("stop")){
                hideDialog();
            }
        });
    }

    public void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showDialog();
            setUpObservable(choose);
            adapter.setData(mArticleList, NewsActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void showDialog(){
        alertDialog.setMessage("content downloading");
        alertDialog.show();
        Log.d(TAG, "showDialog: ");
    }

    public void showError(){
        alertDialog.setMessage("Something Wrong!");
    }

    public void hideDialog(){
        alertDialog.dismiss();
    }

    public void init(){
        recyclerView = findViewById(R.id.recycle_view);
        manager = new LinearLayoutManager(this);
        toolbar = findViewById(R.id.toolbar);
        spinner = findViewById(R.id.spinner);
        alertDialog = new AlertDialog.Builder(this).setTitle("Process downloading").create();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        viewModel = new NewsViewModel(articleDao, apiInterface);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }
}
