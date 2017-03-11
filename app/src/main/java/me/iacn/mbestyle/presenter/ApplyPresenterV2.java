package me.iacn.mbestyle.presenter;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
                PackageManager manager = mView.getActivity().getPackageManager();
                List<ResolveInfo> list = PackageUtils.getAppByMainIntent(mView.getActivity());
                List<ApplyBeanV2> apps = new ArrayList<>();

                StringBuilder builder = new StringBuilder();

                for (ResolveInfo info : list) {
                    ApplyBeanV2 bean = new ApplyBeanV2();
                    bean.name = info.loadLabel(manager).toString();
                    bean.icon = info.loadIcon(manager);

                    String pkgName = info.activityInfo.packageName;
                    String activityName = info.activityInfo.name;

                    if (activityName.contains(pkgName)) {
                        // 缩短主 Activity 的显示长度
                        builder.append(pkgName)
                                .append("/")
                                .append(activityName.replace(pkgName, ""));
                    } else {
                        // 处理某些主 Activity 另起名的蛋疼应用
                        builder.append(pkgName)
                                .append("/")
                                .append(activityName);
                    }

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
}