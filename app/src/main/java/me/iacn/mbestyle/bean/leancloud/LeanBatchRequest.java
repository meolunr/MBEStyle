package me.iacn.mbestyle.bean.leancloud;

import java.util.List;

/**
 * Created by iAcn on 2017/3/12
 * Email i@iacn.me
 */

public class LeanBatchRequest {

    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";

    public List<RequestsBean> requests;

    public static class RequestsBean {
        public String method;
        public String path;
        public Object body;

        /**
         * 新建对象
         */
        public static class BodyCreateBean {
            public String appName;
            public int requestTotal;
            public String componentInfo;
            public String packageName;
        }

        /**
         * 更新对象
         */
        public static class BodyAutoBean {

            private RequestTotalBean requestTotal;

            public BodyAutoBean(String __op, int amount) {
                this.requestTotal = new RequestTotalBean();
                this.requestTotal.__op = __op;
                this.requestTotal.amount = amount;
            }

            private class RequestTotalBean {
                String __op;
                int amount;
            }
        }
    }
}