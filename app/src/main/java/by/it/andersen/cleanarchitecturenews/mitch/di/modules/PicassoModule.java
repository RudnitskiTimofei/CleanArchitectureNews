package by.it.andersen.cleanarchitecturenews.mitch.di.modules;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {NetworkModule.class})
public class PicassoModule {

    @Provides
    @Singleton
    public Picasso providePicasso(Application application, OkHttp3Downloader okHttp3Downloader){
        return new Picasso.Builder(application).downloader(okHttp3Downloader).build();
    }

    @Provides
    @Singleton
    public OkHttp3Downloader provideOkHttpDowmnloader(OkHttpClient okHttpClient){
        return new OkHttp3Downloader(okHttpClient);
    }
}
