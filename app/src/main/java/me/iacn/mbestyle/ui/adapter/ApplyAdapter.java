package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.GlideUtils;

/**
 * Created by iAcn on 2017/2/20
 * Email i@iacn.me
 */

public class ApplyAdapter extends RecyclerView.Adapter<ApplyHolder> {

    private static final int TYPE_ITEM_WALLPAPER = 7;
    private static final int TYPE_ITEM_LAUNCHER = 103;

    private int[] mLauncherIcons;
    private String[] mLauncherNames;
    private GlideUtils mGlide;
    private OnItemClickListener mListener;

    public ApplyAdapter(int[] mLauncherIcons, String[] mLauncherNames, GlideUtils glide) {
        this.mLauncherIcons = mLauncherIcons;
        this.mLauncherNames = mLauncherNames;
        this.mGlide = glide;
    }

    @Override
    public ApplyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ApplyHolder holder;

        if (viewType == TYPE_ITEM_WALLPAPER) {
            holder = new ApplyHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.item_apply_wallpaper, parent, false), true);
        } else {
            holder = new ApplyHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.item_apply_launcher, parent, false), false);
        }

        holder.mListener = mListener;

        return holder;
    }

    @Override
    public void onBindViewHolder(ApplyHolder holder, int position) {
        if (position == 0) return;
        position--;

        mGlide.showImage(mLauncherIcons[position], holder.ivLauncherIcon);
        holder.tvLauncherName.setText(mLauncherNames[position]);
    }

    @Override
    public int getItemCount() {
        return mLauncherIcons.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_ITEM_WALLPAPER : TYPE_ITEM_LAUNCHER;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

class ApplyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView ivLauncherIcon;
    TextView tvLauncherName;
    OnItemClickListener mListener;

    ApplyHolder(View itemView, boolean isHeaderView) {
        super(itemView);

        if (isHeaderView) {
            itemView.setOnClickListener(this);
            return;
        }

        ivLauncherIcon = (ImageView) itemView.findViewById(R.id.iv_launcher_icon);
        tvLauncherName = (TextView) itemView.findViewById(R.id.tv_launcher_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}