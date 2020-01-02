package com.yeyintkoko.techtricity.helper;

import android.content.Context;

import com.yeyintkoko.techtricity.model.ArticleByAuthorModel;
import com.yeyintkoko.techtricity.model.ArticleDetailModel;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.ArticleReturnModel;
import com.yeyintkoko.techtricity.model.AuthorListModel;
import com.yeyintkoko.techtricity.model.AuthorModel;
import com.yeyintkoko.techtricity.model.AuthorReturnModel;
import com.yeyintkoko.techtricity.model.BannerReturnModel;
import com.yeyintkoko.techtricity.model.MessageModel;
import com.yeyintkoko.techtricity.model.RecentReturnModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.yeyintkoko.techtricity.helper.AppConstant.BASE_URL;

public class ServiceHelper {
    private static ApiService apiService;
    private static Cache cache;

    public static ApiService getClient(final Context context) {
        if (apiService == null) {

            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    int maxAge = 300; // read from cache for 5 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                }
            };

            //setup cache
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            cache = new Cache(httpCacheDirectory, cacheSize);

            final OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
            okClientBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            okClientBuilder.cache(cache);

            OkHttpClient okClient = okClientBuilder.build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = client.create(ApiService.class);
        }
        return apiService;
    }

    public static void removeFromCache(String url) {
        try {
            Iterator<String> it = cache.urls();
            while (it.hasNext()) {
                String next = it.next();
                if (next.contains(BASE_URL + url)) {
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ApiService {
        @GET("api/android/homebanner")
        Call<BannerReturnModel>getHomeBanner();

        @GET("api/android/articlelist")
        Call<ArticleReturnModel> getArticleByCategory(@Query("pagesize") int pagesize, @Query("page") int page,
                                                      @Query("category") String category, @Query("search") String search);
        @GET("api/android/recentlist")
        Call<RecentReturnModel> getRecentNews(@Query("pagesize") int pagesize, @Query("page") int page);

        @GET("api/android/articledetail")
        Call<ArticleDetailModel> getNewsDetail(@Query("ID") int id);

        @GET("api/android/authorlist")
        Call<AuthorReturnModel> getAuthorList();

        @GET("api/android/authordetail")
        Call<AuthorModel> getAuthorDetail(@Query("ID") int id);

        @GET("api/android/relatedarticlebyauthor")
        Call<ArticleByAuthorModel> getNewsByAuthor(@Query("ID") int id, @Query("page") int page, @Query("pagesize") int pageSize);

        @POST("api/android/createmessage")
        Call<MessageModel> sendMessage(@Body MessageModel model);

        @GET("api/android/relatedarticlebymainarticle")
        Call<ArrayList<ArticleModel>> getRelatedNews(@Query("ID") int id);

        @GET("api/android/tagsarticlelist")
        Call<ArticleByAuthorModel> getSearchArticles(@Query("page") int page, @Query("pagesize") int pageSize, @Query("search") String search);
    }
}
