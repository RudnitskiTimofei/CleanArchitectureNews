package by.it.andersen.cleanarchitecturenews.mitch.data.datasource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.it.andersen.cleanarchitecturenews.mitch.data.model.Article;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    LiveData<List<Article>> getAll();

    @Query("SELECT * FROM articles WHERE theme = :topic  ORDER BY publishedAt DESC ")
    LiveData<List<Article>> getArticleByTheme(String topic);

    @Query(("SELECT * FROM articles ORDER BY publishedAt DESC"))
    List<Article> getAllTest();

    @Query("SELECT * FROM articles WHERE theme = :topic  ORDER BY publishedAt DESC ")
    List<Article> getArticleTest(String topic);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Article> articles);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void inserArticle(Article article);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<Article> articles);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM articles ")
    void deleteAll();
}
