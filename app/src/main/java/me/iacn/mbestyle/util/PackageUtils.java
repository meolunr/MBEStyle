package me.iacn.mbestyle.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class PackageUtils {

    public static List<ApplicationInfo> getAllApp(Context context) {
        PackageManager manager = context.getPackageManager();
        return manager.getInstalledApplications(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }
}