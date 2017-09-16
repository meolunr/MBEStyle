package me.iacn.mbestyle.network;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.bean.RequestBean;
import me.iacn.mbestyle.bean.leancloud.LeanBatchRequest;
import me.iacn.mbestyle.bean.leancloud.LeanBatchRequest.RequestsBean.BodyAutoBean;
import me.iacn.mbestyle.bean.leancloud.LeanBatchRequest.RequestsBean.BodyCreateBean;
import me.iacn.mbestyle.bean.leancloud.LeanBatchResponse;
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
 * Email i@iacn.me
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

    public Observable<Boolean> postRequests(final List<RequestBean> list) {
        LeanBatchRequest request = new LeanBatchRequest();
        request.requests = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        for (RequestBean bean : list) {
            LeanBatchRequest.RequestsBean req = new LeanBatchRequest.RequestsBean();

            if (TextUtils.isEmpty(bean.objectId)) {
                // 新建 object

                BodyCreateBean body = new BodyCreateBean();
                body.requestTotal = 1;
                body.appName = bean.name;
                body.componentInfo = bean.activity;
                body.packageName = bean.packageName;

                req.method = LeanBatchRequest.METHOD_POST;
                req.path = "/1.1/classes/AppReport";
                req.body = body;

            } else {
                // 更新+1 requestTotal

                BodyAutoBean body = new BodyAutoBean("Increment", 1);

                req.method = LeanBatchRequest.METHOD_PUT;
                req.path = builder.append("/1.1/classes/AppReport/").append(bean.objectId).toString();
                req.body = body;

                builder.delete(0, builder.length());
            }

            request.requests.add(req);
        }

        return mLeanService.batch(request)
                .map(new Function<List<LeanBatchResponse>, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull List<LeanBatchResponse> list) throws Exception {
                        for (LeanBatchResponse response : list) {
                            if (response.success == null || response.success.objectId == null) {
                                return false;
                            }
                        }
                        return true;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();
    }
}