package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.IconBean;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.GlideUtils;

/**
 * Created by iAcn on 2017/2/18
 * Email i@iacn.me
 */

public class IconAdapter extends RecyclerView.Adapter<IconHolder> {

    private List<IconBean> mIcons;
    private GlideUtils mGlide;
    private OnItemClickListener mListener;

    public IconAdapter(List<IconBean> mIcons, GlideUtils glide) {
        this.mIcons = mIcons;
        this.mGlide = glide;
    }

    @Override
    public IconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IconHolder holder = new IconHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_icon, parent, false));
        holder.mListener = mListener;

        return holder;
    }

    @Override
    public void onBindViewHolder(IconHolder holder, int position) {
        IconBean bean = mIcons.get(position);
        mGlide.showImage(bean.id, holder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return mIcons.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

class IconHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnItemClickListener mListener;

    ImageView ivIcon;

    IconHolder(View itemView) {
        super(itemView);
        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);

        // 为了 Activity 传递动画，此处给 ImageView 设置
        ivIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}