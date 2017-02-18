package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconAdapter extends RecyclerView.Adapter<IconHolder> {

    @Override
    public IconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IconHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(IconHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 25;
    }
}

class IconHolder extends RecyclerView.ViewHolder {

    IconHolder(View itemView) {
        super(itemView);
    }
}