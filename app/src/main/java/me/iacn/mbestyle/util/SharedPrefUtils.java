package me.iacn.mbestyle.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference工具类
 * <p/>
 * Modify for 2016.5.28 by iAcn
 */
public class SharedPrefUtils {

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPref(context).getBoolean(key, defValue);
    }


    public static void putBoolean(Context context, String key, boolean value) {
        getPref(context).edit().putBoolean(key, value).apply();
    }

    public static long getLong(Context context, String key, long defValue) {
        return getPref(context).getLong(key, defValue);
    }

    public static void putLong(Context context, String key, long value) {
        getPref(context).edit().putLong(key, value).apply();
    }
}