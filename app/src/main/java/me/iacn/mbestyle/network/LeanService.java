package me.iacn.mbestyle.network;

import io.reactivex.Observable;
import me.iacn.mbestyle.bean.LeanBean;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by iAcn on 2017/3/11
 * Emali iAcn0301@foxmail.com
 */

interface LeanService {

    @POST("1.1/classes/{class}")
    void createObject(@Path("class") String className, @Body Object obj);

    @GET("1.1/classes/AppReport")
    Observable<LeanBean> queryRequestTotal(@Query("where") String where);
}