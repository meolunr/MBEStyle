package me.iacn.mbestyle.network;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import me.iacn.mbestyle.bean.leancloud.LeanBatchRequest;
import me.iacn.mbestyle.bean.leancloud.LeanBatchResponse;
import me.iacn.mbestyle.bean.leancloud.LeanQueryBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by iAcn on 2017/3/11
 * Email i@iacn.me
 */

interface LeanService {

    @GET("1.1/classes/AppReport")
    Observable<LeanQueryBean> queryRequestTotal(@Query("where") String where);

    @POST("1.1/batch")
    Flowable<List<LeanBatchResponse>> batch(@Body LeanBatchRequest request);
}