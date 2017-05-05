package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/5/2
 * Emali iAcn0301@foxmail.com
 */

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperHolder> {

    private int[] mIds;
    private RequestManager mGlide;

    public WallpaperAdapter(int[] mIds, RequestManager glide) {
        this.mIds = mIds;
        this.mGlide = glide;
    }

    @Override
    public WallpaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WallpaperHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_wallpaper, parent, false));
    }

    @Override
    public void onBindViewHolder(WallpaperHolder holder, int position) {
        mGlide.load(mIds[position]).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mIds.length;
    }
}

class WallpaperHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    WallpaperHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.imageview);
    }
}