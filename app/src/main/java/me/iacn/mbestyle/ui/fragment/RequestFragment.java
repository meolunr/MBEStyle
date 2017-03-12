package me.iacn.mbestyle.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import java.util.List;
import java.util.Locale;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.RequestBean;
import me.iacn.mbestyle.presenter.RequestPresenter;
import me.iacn.mbestyle.ui.activity.MainActivity;
import me.iacn.mbestyle.ui.adapter.RequestAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class RequestFragment extends ILazyFragment implements OnItemClickListener, View.OnClickListener {

    private RecyclerView rvApp;
    private FloatingActionButton mFab;

    private RequestPresenter mPresenter;
    private List<RequestBean> mApps;
    private RequestAdapter mAdapter;

    private int mCheckedCount;
    private MainActivity mActivity;

    @Override
    protected int getContentView() {
        return R.layout.fragment_apply;
    }

    @Override
    protected void findView() {
        rvApp = (RecyclerView) findViewById(R.id.rv_app);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    protected void setListener() {
        rvApp.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 优化同大小 Item 的性能
        rvApp.setHasFixedSize(true);
        // 确保每个 Item 都会走 onBindViewHolder()
        rvApp.setItemViewCacheSize(0);

        mFab.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        mPresenter = new RequestPresenter(this);
        mPresenter.loadInstallApp();

        mActivity = (MainActivity) getActivity();
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

        handleFabShow(bean.isCheck);
    }

    public void onLoadData(List<RequestBean> list) {
        super.onLoadData();

        mApps = list;
        mAdapter = new RequestAdapter(mApps, mPresenter);
        mAdapter.setOnItemClickListener(this);
        rvApp.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    public void onBackPressed() {
        if (mCheckedCount > 0) {
            mCheckedCount = 0;
            handleFabShow(false);

            // 取消所有 Bean 内记录的选中状态
            for (RequestBean bean : mApps) {
                bean.isCheck = false;
            }

            // 当前可见的所有 Item 取消选择
            for (int i = 0; i < rvApp.getChildCount(); i++) {
                View childAt = rvApp.getChildAt(i);
                CheckBox cbCheck = (CheckBox) childAt.findViewById(R.id.cb_check);
                cbCheck.setChecked(false);
            }

        } else {
            getActivity().finish();
        }
    }

    /**
     * 处理是否显示 Fab
     */
    private void handleFabShow(boolean isCheck) {
        mCheckedCount = isCheck ? ++mCheckedCount : --mCheckedCount;


        if (mCheckedCount > 0) {
            mFab.show();
            mActivity.setToolbarTitle(String.format(Locale.getDefault(), "已选中 %d 个", mCheckedCount));
        } else if (mFab.isShown()) {
            mFab.hide();
            mActivity.setToolbarTitle(getString(R.string.app_title));
            mCheckedCount = 0;
        }
    }
}