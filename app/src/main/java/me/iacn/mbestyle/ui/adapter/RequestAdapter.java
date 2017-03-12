package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.RequestBean;
import me.iacn.mbestyle.presenter.RequestPresenter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestHolder> {

    private List<RequestBean> mApps;
    private RequestPresenter mPresenter;
    private OnItemClickListener mListener;

    public RequestAdapter(List<RequestBean> mApps, RequestPresenter presenter) {
        this.mApps = mApps;
        this.mPresenter = presenter;
    }

    @Override
    public RequestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RequestHolder holder = new RequestHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_apply_v2, parent, false));
        holder.mListener = mListener;

        return holder;
    }

    @Override
    public void onBindViewHolder(RequestHolder holder, int position) {
        RequestBean bean = mApps.get(position);

        holder.ivIcon.setImageDrawable(bean.icon);
        holder.tvName.setText(bean.name);
        holder.cbCheck.setChecked(bean.isCheck);

        System.out.println("========= " + bean.name + " ==========");
        System.out.println(bean.isCheck);
        mPresenter.getRequestTotal(bean.packageName, bean, holder.tvTotal);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}

class RequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnItemClickListener mListener;

    ImageView ivIcon;
    TextView tvName;
    TextView tvTotal;
    CheckBox cbCheck;

    RequestHolder(View itemView) {
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