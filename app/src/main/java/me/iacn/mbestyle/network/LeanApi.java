package me.iacn.mbestyle.network;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.bean.RequestBean;
import me.iacn.mbestyle.bean.leancloud.LeanBatchRequest;
import me.iacn.mbestyle.bean.leancloud.LeanQueryBean;
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

    public Observable<LeanQueryBean> queryRequestTotal(String packageName) {
        Map<String, String> where = new HashMap<>();
        where.put("packageName", packageName);

        return mLeanService.queryRequestTotal(new Gson().toJson(where));
    }

    public Observable<List<Boolean>> postRequests(List<RequestBean> list) {
        Map<String, String> map = new HashMap<>();
        Gson gson = new Gson();
        StringBuilder builder = new StringBuilder();

        LeanBatchRequest request = new LeanBatchRequest();
        request.requests = new ArrayList<>();

        for (RequestBean bean : list) {
            LeanBatchRequest.RequestsBean req = new LeanBatchRequest.RequestsBean();

            if (TextUtils.isEmpty(bean.objectId)) {
                // 新建 object

                map.put("appName", bean.name);
                map.put("packageName", bean.packageName);
                map.put("componentInfo", bean.activity);

                req.method = LeanBatchRequest.METHOD_POST;
                req.path = "/1.1/classes/AppReport/";
                req.body = gson.toJson(map);

                map.clear();

            } else {
                // 更新+1 requestTotal

                req.method = LeanBatchRequest.METHOD_PUT;
                req.path = builder.append("/1.1/classes/AppReport/").append(bean.objectId).toString();
                req.body = "{\"requestTotal\":{\"__op\":\"Increment\",\"amount\":1}}";

                builder.delete(0, builder.length());
            }
        }

        String str = gson.toJson(request);
        System.out.println(str);
        return null;
    }
}