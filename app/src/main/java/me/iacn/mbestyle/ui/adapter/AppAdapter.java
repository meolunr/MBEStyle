package me.iacn.mbestyle.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.AppBean;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class AppAdapter extends RecyclerView.Adapter<AppHolder> {

    private List<AppBean> mApps;

    public AppAdapter(List<AppBean> mApps) {
        this.mApps = mApps;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_app, parent, false));
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        AppBean bean = mApps.get(position);
        holder.ivIcon.setImageDrawable(bean.icon);
        holder.tvName.setText(bean.name);
        holder.tvActivity.setText(bean.activity);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }
}

class AppHolder extends RecyclerView.ViewHolder {

    ImageView ivIcon;
    TextView tvName;
    TextView tvActivity;

    AppHolder(View itemView) {
        super(itemView);

        ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvActivity = (TextView) itemView.findViewById(R.id.tv_activity);
    }
}
