package me.iacn.mbestyle.network;

import java.io.IOException;

import me.iacn.mbestyle.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iAcn on 2017/3/11
 * Emali iAcn0301@foxmail.com
 */

public class LeanApi {
    private static LeanApi sLeanApi;
    private LeanService mLeanService;
    private static final String BASE_URL = "https://api.leancloud.cn/";

    public static LeanApi getInstance() {
        if (sLeanApi == null) {
            synchronized (LeanApi.class) {
                if (sLeanApi == null) {
                    sLeanApi = new LeanApi();
                }
            }
        }

        return sLeanApi;
    }

    private LeanApi() {
        // 使用 OkHttp 拦截器添加 RequestHeader
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder()
                                .addHeader("X-LC-Id", BuildConfig.LEANCLOUD_ID)
                                .addHeader("X-LC-Key", BuildConfig.LEANCLOUD_KEY)
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        mLeanService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(LeanService.class);
    }
}