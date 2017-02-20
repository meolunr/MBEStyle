package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/20
 * Emali iAcn0301@foxmail.com
 */

public class LauncherAdapter extends RecyclerView.Adapter<LauncherHolder> {

    private int[] mLauncherIcons;
    private String[] mLauncherNames;
    private OnItemClickListener mListener;

    public LauncherAdapter(int[] mLauncherIcons, String[] mLauncherNames) {
        this.mLauncherIcons = mLauncherIcons;
        this.mLauncherNames = mLauncherNames;
    }

    @Override
    public LauncherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LauncherHolder holder = new LauncherHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_launcher, parent, false));
        holder.mListener = mListener;

        return holder;
    }

    @Override
    public void onBindViewHolder(LauncherHolder holder, int position) {
        System.out.println(mLauncherIcons);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

class LauncherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView ivLauncherIcon;
    TextView tvLauncherName;
    OnItemClickListener mListener;

    LauncherHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}