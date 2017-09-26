package me.iacn.mbestyle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import io.reactivex.disposables.Disposable;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.IconBean;
import me.iacn.mbestyle.presenter.IconShowPresenter;
import me.iacn.mbestyle.ui.activity.IconViewActivity;
import me.iacn.mbestyle.ui.adapter.IconAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.GlideUtils;

/**
 * Created by iAcn on 2017/2/18
 * Email iAcn0301@foxmail.com
 */

public class IconShowFragment extends ILazyFragment implements OnItemClickListener {

    public static final int ICONS_LOAD_INSTALLED = 567;
    public static final int ICONS_LOAD_WHATSNEW = 184;
    public static final int ICONS_LOAD_ALL = 575;

    private RecyclerView rvIcon;
    private IconShowPresenter mPresenter;
    private List<IconBean> mIcons;

    private boolean mIsInstalledFragment;
    private Disposable mDisposable;


    @Override
    protected int getContentView() {
        return R.layout.fragment_show_icon;
    }

    @Override
    protected void findView() {
        rvIcon = (RecyclerView) findViewById(R.id.rv_icon);
    }

    @Override
    protected void setListener() {
        rvIcon.setHasFixedSize(true);
        rvIcon.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    }

    @Override
    protected void initData() {
        mPresenter = new IconShowPresenter(this);
        Bundle bundle = getArguments();

        if (bundle != null) {
            switch (bundle.getInt("showIconCategory")) {
                case ICONS_LOAD_INSTALLED:
                    mIsInstalledFragment = true;
                    mDisposable = mPresenter.getAdaptedIcons();
                    break;
                case ICONS_LOAD_WHATSNEW:
                    mDisposable = mPresenter.getWhatsNewIcons();
                    break;
                case ICONS_LOAD_ALL:
                    mDisposable = mPresenter.getAllIcons();
                    break;
            }
        }
    }

    @Override
    protected void destroyData() {
        if (mIcons != null) {
            mIcons.clear();
            mIcons = null;
        }

        if (mDisposable != null) mDisposable.dispose();
    }

    @Override
    protected boolean isDataComplete() {
        return mIcons != null;
    }

    public void onLoadData(List<IconBean> icons) {
        super.onLoadData();

        mIcons = icons;
        IconAdapter adapter = new IconAdapter(mIcons, GlideUtils.with(this));
        rvIcon.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        IconBean bean = mIcons.get(position);
        Intent intent = new Intent(getActivity(), IconViewActivity.class);
        intent.putExtra("icon_name", bean.name);
        intent.putExtra("resource_id", bean.id);

        if (mIsInstalledFragment) {
            // 已适配 Fragment，传入包名
            intent.putExtra("package_name", bean.iconPkgName);
        }

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), itemView, "dialog_icon");

        startActivity(intent, options.toBundle());
    }
}