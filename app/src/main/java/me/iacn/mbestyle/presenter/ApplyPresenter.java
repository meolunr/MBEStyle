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

        for (ResolveInfo info : list) {
            AppBean bean = new AppBean();
            bean.name = info.loadLabel(manager).toString();
            bean.icon = info.loadIcon(manager);

            // 缩短主 Activity 的显示长度
            String pkgName = info.activityInfo.packageName;
            bean.activity = info.activityInfo.name.replace(pkgName, pkgName + "/");

            apps.add(bean);
        }

        mView.showApps(apps);
    }
}