package me.iacn.mbestyle.presenter;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.ApplyBeanV2;
import me.iacn.mbestyle.ui.fragment.ApplyFragmentV2;
import me.iacn.mbestyle.util.PackageUtils;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class ApplyPresenterV2 {

    private ApplyFragmentV2 mView;

    public ApplyPresenterV2(ApplyFragmentV2 mView) {
        this.mView = mView;
    }

    public void loadInstallApp() {
        Flowable.create(new FlowableOnSubscribe<List<ApplyBeanV2>>() {
            @Override
            public void subscribe(FlowableEmitter<List<ApplyBeanV2>> e) throws Exception {
                // 获得已适配的所有主 Activity 全名
                XmlResourceParser xml = mView.getResources().getXml(R.xml.appfilter);
                Set<String> adaptedActivitySet = new HashSet<>();

                while (xml.getEventType() != XmlResourceParser.END_DOCUMENT) {
                    if (xml.getEventType() == XmlPullParser.START_TAG) {
                        if (xml.getName().startsWith("item")) {
                            String component = xml.getAttributeValue(null, "component");
                            adaptedActivitySet.add(findActivityName(component));
                        }
                    }
                    xml.next();
                }

                PackageManager manager = mView.getActivity().getPackageManager();
                List<ResolveInfo> list = PackageUtils.getAppByMainIntent(mView.getActivity());
                List<ApplyBeanV2> apps = new ArrayList<>();

                StringBuilder builder = new StringBuilder();

                for (ResolveInfo info : list) {
                    // 排除已经适配的应用
                    if (adaptedActivitySet.contains(info.activityInfo.name)) continue;

                    ApplyBeanV2 bean = new ApplyBeanV2();
                    bean.name = info.loadLabel(manager).toString();
                    bean.icon = info.loadIcon(manager);

                    builder.append("ComponentInfo{")
                            .append(info.activityInfo.packageName)
                            .append("/")
                            .append(info.activityInfo.name)
                            .append("}");

                    bean.activity = builder.toString();
                    builder.delete(0, builder.length());

                    apps.add(bean);
                }

                e.onNext(apps);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ApplyBeanV2>>() {
                    @Override
                    public void accept(@NonNull List<ApplyBeanV2> list) throws Exception {
                        mView.onLoadData(list);
                    }
                });
    }

    private String findActivityName(String component) {
        try {
            String str = component.split("/")[1];
            return str.substring(0, str.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}