package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.RequestBean;
import me.iacn.mbestyle.presenter.RequestPresenter;
import me.iacn.mbestyle.ui.adapter.RequestAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class RequestFragment extends ILazyFragment implements OnItemClickListener {

    private RecyclerView rvApp;
    private RequestPresenter mPresenter;
    private List<RequestBean> mApps;

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
        mPresenter = new RequestPresenter(this);
        mPresenter.loadInstallApp();
    }

    @Override
    protected boolean isDataComplete() {
        return mApps != null;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        RequestBean bean = mApps.get(position);
        CheckBox cbCheck = (CheckBox) itemView.findViewById(R.id.cb_check);

        // 反向选择
        cbCheck.setChecked(!bean.isCheck);
        bean.isCheck = !bean.isCheck;
    }

    public void onLoadData(List<RequestBean> list) {
        super.onLoadData();

        mApps = list;
        RequestAdapter adapter = new RequestAdapter(mApps);
        adapter.setOnItemClickListener(this);
        rvApp.setAdapter(adapter);
    }
}