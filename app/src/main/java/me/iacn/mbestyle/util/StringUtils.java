package me.iacn.mbestyle.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by iAcn on 2016/8/17
 * Email i@iacn.me
 */
public class StringUtils {

    public static void copyToClipboard(Context context, String str) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText(null, str));
    }
}