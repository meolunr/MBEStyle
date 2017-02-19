package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.AppBean;
import me.iacn.mbestyle.ui.adapter.AppAdapter;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class ApplyFragment extends BaseFragment {

    private RecyclerView rvApp;

    @Override
    protected int getInflateView() {
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

    }

    public void showApps(List<AppBean> list) {
        AppAdapter adapter = new AppAdapter(list);
        rvApp.setAdapter(adapter);
    }
}
