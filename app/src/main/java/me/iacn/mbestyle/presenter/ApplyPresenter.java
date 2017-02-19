package me.iacn.mbestyle.presenter;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

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

    }
}
