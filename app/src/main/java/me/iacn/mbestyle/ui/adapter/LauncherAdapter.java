package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/20
 * Emali iAcn0301@foxmail.com
 */

public class LauncherAdapter extends RecyclerView.Adapter<LauncherHolder> {


    @Override
    public LauncherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LauncherHolder holder = new LauncherHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_launcher, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(LauncherHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }
}

class LauncherHolder extends RecyclerView.ViewHolder {

    LauncherHolder(View itemView) {
        super(itemView);
    }
}
