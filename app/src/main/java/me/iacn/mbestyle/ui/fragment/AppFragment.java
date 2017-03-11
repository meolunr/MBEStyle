package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.AppBean;
import me.iacn.mbestyle.presenter.AppPresenter;
import me.iacn.mbestyle.ui.adapter.AppAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.StringUtils;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class AppFragment extends ILazyFragment implements OnItemClickListener {

    private RecyclerView rvApp;
    private AppPresenter mPresenter;
    private List<AppBean> mApps;

    @Override
    protected int getContentView() {
        return R.layout.fragment_apply;
    }

    @Override
    protected void findView() {
        rvApp = (RecyclerView) findViewById(R.id.rv_app);
    }

    @Override
    protected void setListener() {
        rvApp.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    protected void initData() {
        mPresenter = new AppPresenter(this);
        mPresenter.loadInstallApp();
    }

    @Override
    protected boolean isDataComplete() {
        return mApps != null;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        AppBean bean = mApps.get(position);
        String template = getString(R.string.component_template);
        String[] split = bean.activity.split("/");

        if (split[1].startsWith(".")) {
            // 正常应用
            template = template
                    .replace("$packageName$", split[0])
                    .replace("$activityName$", split[0] + split[1]);
        } else {
            // 蛋疼应用
            template = template
                    .replace("$packageName$", split[0])
                    .replace("$activityName$", split[1]);
        }

        StringUtils.copyToClipboard(getActivity(), template);
        Toast.makeText(getActivity(), "应用信息已复制到剪贴板", Toast.LENGTH_SHORT).show();
    }

    public void onLoadData(List<AppBean> list) {
        super.onLoadData();

        mApps = list;
        AppAdapter adapter = new AppAdapter(mApps);
        adapter.setOnItemClickListener(this);
        rvApp.setAdapter(adapter);
    }
}