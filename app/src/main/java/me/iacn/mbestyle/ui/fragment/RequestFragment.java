package me.iacn.mbestyle.ui.fragment;

import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.RequestBean;
import me.iacn.mbestyle.network.LeanApi;
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
    private List<Integer> mCheckedPositions;
    private RequestAdapter mAdapter;

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

        if (bean.isCheck) {
            mCheckedPositions.add(position);
        } else {
            mCheckedPositions.remove(Integer.valueOf(position));
        }

        handleFabShow();
    }

    public void onLoadData(List<RequestBean> list) {
        super.onLoadData();

        mApps = list;
        mCheckedPositions = new ArrayList<>();

        mAdapter = new RequestAdapter(mApps, mPresenter);
        mAdapter.setOnItemClickListener(this);
        rvApp.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        List<RequestBean> newRequests = new ArrayList<>();

        for (RequestBean bean : mApps) {
            if (bean.isCheck) newRequests.add(bean);
        }

        LeanApi.getInstance().postRequests(newRequests).subscribe(new Observer<Boolean>() {

            private ProgressDialog mProgressDialog;

            @Override
            public void onSubscribe(Disposable d) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("请稍后");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            public void onNext(Boolean success) {
                Toast.makeText(getActivity(), success ? "申请成功" : "申请失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getActivity(), "出现异常 " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                deselectAll();
                mProgressDialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        if (mCheckedPositions.size() > 0) {
            deselectAll();
        } else {
            getActivity().finish();
        }
    }

    /**
     * 处理是否显示 Fab
     */
    private void handleFabShow() {
        if (mCheckedPositions.size() > 0) {
            if (!mFab.isShown())
                mFab.show();

            mActivity.setToolbarTitle(String.format(
                    Locale.getDefault(), "已选中 %d 个", mCheckedPositions.size()));

        } else if (mFab.isShown()) {
            mFab.hide();
            mActivity.setToolbarTitle(getString(R.string.app_title));
            mCheckedPositions.clear();
        }
    }

    private void deselectAll() {
        mCheckedPositions.clear();
        handleFabShow();

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
    }
}