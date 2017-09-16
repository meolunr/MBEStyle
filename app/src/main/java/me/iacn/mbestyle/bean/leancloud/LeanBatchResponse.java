package me.iacn.mbestyle.bean.leancloud;

/**
 * Created by iAcn on 2017/3/12
 * Email i@iacn.me
 */

public class LeanBatchResponse {

    public SuccessBean success;

    public static class SuccessBean {
        public String objectId;
        public String createdAt;
    }
}