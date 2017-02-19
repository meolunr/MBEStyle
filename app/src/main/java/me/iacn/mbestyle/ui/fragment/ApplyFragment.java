package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.AppBean;
import me.iacn.mbestyle.presenter.ApplyPresenter;
import me.iacn.mbestyle.ui.adapter.AppAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class ApplyFragment extends BaseFragment implements OnItemClickListener {

    private RecyclerView rvApp;
    private ApplyPresenter mPresenter;

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
        mPresenter = new ApplyPresenter(this);
        mPresenter.loadInstallApp();
    }

    public void showApps(List<AppBean> list) {
        AppAdapter adapter = new AppAdapter(list);
        adapter.setOnItemClickListener(this);
        rvApp.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        System.out.println(itemView);
    }
}