package me.iacn.mbestyle.presenter;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

import me.iacn.mbestyle.bean.AppBean;
import me.iacn.mbestyle.ui.fragment.ApplyFragment;
import me.iacn.mbestyle.util.PackageUtils;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class ApplyPresenter {

    private ApplyFragment mView;

    public ApplyPresenter(ApplyFragment mView) {
        this.mView = mView;
    }

    public void loadInstallApp() {
        PackageManager manager = mView.getActivity().getPackageManager();
        List<ResolveInfo> list = PackageUtils.getAppByMainIntent(mView.getActivity());
        List<AppBean> apps = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        for (ResolveInfo info : list) {
            AppBean bean = new AppBean();
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

            bean.activity = builder.toString();
            builder.delete(0, builder.length());

            apps.add(bean);
        }

        mView.showApps(apps);
    }
}