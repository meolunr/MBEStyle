package me.iacn.mbestyle.ui.adapter;

import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/19
 * Emali iAcn0301@foxmail.com
 */

public class AppAdapter extends RecyclerView.Adapter<AppHolder> {

    private List<ApplicationInfo> mApps;

    public AppAdapter(List<ApplicationInfo> mApps) {
        this.mApps = mApps;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_app, parent, false));
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        ApplicationInfo info = mApps.get(position);
        System.out.println("==========");
        System.out.println(info.icon);
        System.out.println(info.loadLabel(holder.ivIcon.getContext().getPackageManager()));


//        holder.ivIcon.setImageResource(info.icon);
//        holder.tvName.setText(info.name);
//        holder.tvActivity.setText(info.labelRes);
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
