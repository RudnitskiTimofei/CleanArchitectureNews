package by.it.andersen.cleanarchitecturenews.mitch.presentation.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import by.it.andersen.cleanarchitecturenews.R;
import by.it.andersen.cleanarchitecturenews.databinding.ActivityNewsDetailViewBinding;
import by.it.andersen.cleanarchitecturenews.mitch.data.model.Article;
import by.it.andersen.cleanarchitecturenews.mitch.data.model.Source;

public class NewsDetailsActivity extends AppCompatActivity {
    private Article article;
    ActivityNewsDetailViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail_view);
        initIntent();
        mBinding.setArticle(article);
    }

    public void initIntent() {
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String description = intent.getExtras().getString("description");
        String sourceName = intent.getExtras().getString("source:name");
        String imageUrl = intent.getExtras().getString("imageUrl");
        Source source = new Source(null, sourceName);
        article = new Article(source, null, title, description, "", imageUrl, "");
    }
}