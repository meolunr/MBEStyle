package me.iacn.mbestyle.bean.leancloud;

import java.util.List;

/**
 * Created by iAcn on 2017/3/12
 * Emali iAcn0301@foxmail.com
 */

public class LeanBatchRequest {

    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";

    public List<RequestsBean> requests;

    public static class RequestsBean {
        public String method;
        public String path;
        public String body;
    }
}