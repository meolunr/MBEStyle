package me.iacn.mbestyle.bean.leancloud;

import java.util.List;

/**
 * Created by iAcn on 2017/3/11
 * Email i@iacn.me
 */

public class LeanQueryBean {

    public List<ResultsBean> results;

    public static class ResultsBean {
        public int requestTotal;
        public String packageName;
        public String createdAt;
        public String updatedAt;
        public String componentInfo;
        public String objectId;
    }
}