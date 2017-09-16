package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.GlideUtils;

/**
 * Created by iAcn on 2017/5/2
 * Email i@iacn.me
 */

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperHolder> {

    private int[] mIds;
    private GlideUtils mGlide;
    private OnItemClickListener mListener;

    public WallpaperAdapter(int[] mIds, GlideUtils glide) {
        this.mIds = mIds;
        this.mGlide = glide;
    }

    @Override
    public WallpaperHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WallpaperHolder holder = new WallpaperHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_wallpaper, parent, false));
        holder.mListener = mListener;
        return holder;

    }

    @Override
    public void onBindViewHolder(WallpaperHolder holder, int position) {
        mGlide.showImage(mIds[position], holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mIds.length;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

class WallpaperHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    OnItemClickListener mListener;

    WallpaperHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageview);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}