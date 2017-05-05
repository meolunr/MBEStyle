package me.iacn.mbestyle.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }

    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        // 4.9 -> 4, 4.1 -> 4, 舍去小数
        int px = (int) (dip * density + 0.5f);

        return px;
    }

    public static float px2dip(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px / density;

        return dp;
    }
}