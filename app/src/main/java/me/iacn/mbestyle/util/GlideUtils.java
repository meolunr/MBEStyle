package me.iacn.mbestyle.util;

import android.app.Activity;
import android.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

/**
 * Created by iAcn on 2017/9/1
 * Email i@iacn.me
 */

public class GlideUtils {

    private static GlideUtils sSelf;
    private static RequestManager mGlide;

    private GlideUtils(Activity activity) {
        mGlide = Glide.with(activity);
    }

    private GlideUtils(Fragment fragment) {
        mGlide = Glide.with(fragment);
    }

    public static GlideUtils with(Activity activity) {
        sSelf = new GlideUtils(activity);
        return sSelf;
    }

    public static GlideUtils with(Fragment fragment) {
        sSelf = new GlideUtils(fragment);
        return sSelf;
    }

    public void showImage(int resourceId, ImageView view) {
        assert sSelf != null;
        mGlide.load(resourceId).transition(new DrawableTransitionOptions().crossFade(300)).into(view);
    }
}