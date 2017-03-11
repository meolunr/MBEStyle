package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.ApplyBeanV2;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class ApplyAdapterV2 extends RecyclerView.Adapter<ApplyHolderV2> {

    private List<ApplyBeanV2> mApps;
    private OnItemClickListener mListener;

    public ApplyAdapterV2(List<ApplyBeanV2> mApps) {
        this.mApps = mApps;
    }

    @Override
    public ApplyHolderV2 onCreateViewHolder(ViewGroup parent, int viewType) {
        ApplyHolderV2 holder = new ApplyHolderV2(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_apply_v2, parent, false));
        holder.mListener = mListener;

        return holder;
    }

    @Override
    public void onBindViewHolder(ApplyHolderV2 holder, int position) {
        ApplyBeanV2 bean = mApps.get(position);
        holder.ivIcon.setImageDrawable(bean.icon);
        holder.tvName.setText(bean.name);
        holder.tvTotal.setText(String.format(Locale.getDefault(), "已申请 %d 次", bean.total));
        holder.cbCheck.setChecked(bean.isCheck);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

class ApplyHolderV2 extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnItemClickListener mListener;

    ImageView ivIcon;
    TextView tvName;
    TextView tvTotal;
    CheckBox cbCheck;

    ApplyHolderV2(View itemView) {
        super(itemView);

        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTotal = (TextView) itemView.findViewById(R.id.tv_total);
        cbCheck = (CheckBox) itemView.findViewById(R.id.cb_check);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}