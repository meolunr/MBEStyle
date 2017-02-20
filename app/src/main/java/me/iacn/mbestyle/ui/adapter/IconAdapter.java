package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.IconBean;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconAdapter extends RecyclerView.Adapter<IconHolder> {

    private List<IconBean> mIcons;
    private RequestManager mGlide;

    public IconAdapter(List<IconBean> mIcons, RequestManager glide) {
        this.mIcons = mIcons;
        this.mGlide = glide;
    }

    @Override
    public IconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IconHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_icon, parent, false));
    }

    @Override
    public void onBindViewHolder(IconHolder holder, int position) {
        IconBean bean = mIcons.get(position);
        mGlide.load(bean.id).into(holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return mIcons.size();
    }
}

class IconHolder extends RecyclerView.ViewHolder {

    ImageView ivIcon;

    IconHolder(View itemView) {
        super(itemView);
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
    }
}