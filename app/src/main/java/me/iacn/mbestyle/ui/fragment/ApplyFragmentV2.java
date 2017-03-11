package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.ApplyBeanV2;
import me.iacn.mbestyle.presenter.ApplyPresenterV2;
import me.iacn.mbestyle.ui.adapter.ApplyAdapterV2;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class ApplyFragmentV2 extends ILazyFragment implements OnItemClickListener {

    private RecyclerView rvApp;
    private ApplyPresenterV2 mPresenter;
    private List<ApplyBeanV2> mApps;

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
        mPresenter = new ApplyPresenterV2(this);
        mPresenter.loadInstallApp();
    }

    @Override
    protected boolean isDataComplete() {
        return mApps != null;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        ApplyBeanV2 bean = mApps.get(position);
        CheckBox cbCheck = (CheckBox) itemView.findViewById(R.id.cb_check);

        // 反向选择
        cbCheck.setChecked(!bean.isCheck);
        bean.isCheck = !bean.isCheck;
    }

    public void onLoadData(List<ApplyBeanV2> list) {
        super.onLoadData();

        mApps = list;
        ApplyAdapterV2 adapter = new ApplyAdapterV2(mApps);
        adapter.setOnItemClickListener(this);
        rvApp.setAdapter(adapter);
    }
}