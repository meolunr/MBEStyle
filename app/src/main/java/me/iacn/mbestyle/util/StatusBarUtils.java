package me.iacn.mbestyle.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * 状态栏工具类
 * <p/>
 * Modify from 2016.5.6 By iAcn
 */
public class StatusBarUtils {

    /**
     * 设置状态栏颜色
     * <p/>
     * 需要在设置的Activity的根布局中加入android:fitsSystemWindows="true"属性，需要在Theme设置android:windowTranslucentStatus=true属性
     * <p/>
     * 使用原生半透明效果(Android 5.0+黑色遮罩， Android 4.4渐变)
     *
     * @param activity 需要设置的Activity
     * @param color    状态栏颜色值
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 创建一个和状态栏一样大小的View
            View statusBarView = createStatusView(activity, color);

            // 添加statusBarView到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusBarView);
        }
    }

    /**
     * 创建一个和状态栏一样大小的View
     *
     * @param activity 需要设置的Activity
     * @param color    状态栏颜色值
     */
    private static View createStatusView(Activity activity, int color) {
        View statusBarView = new View(activity);
        statusBarView.setBackgroundColor(color);
        statusBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));

        return statusBarView;
    }

    /**
     * 获取状态栏高度
     *
     * @param context Context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        // 获得状态栏高度在系统中的的Id
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}