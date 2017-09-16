package me.iacn.mbestyle.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by iAcn on 2017/2/19
 * Email i@iacn.me
 */

public class PackageUtils {

    public static List<ApplicationInfo> getAllApp(Context context) {
        PackageManager manager = context.getPackageManager();
        return manager.getInstalledApplications(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    public static List<ResolveInfo> getAppByMainIntent(Context context) {
        PackageManager manager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        return manager.queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }
}