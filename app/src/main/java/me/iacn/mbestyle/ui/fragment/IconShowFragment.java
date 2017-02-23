package me.iacn.mbestyle.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.IconBean;
import me.iacn.mbestyle.presenter.IconShowPresenter;
import me.iacn.mbestyle.ui.activity.IconViewActivity;
import me.iacn.mbestyle.ui.adapter.IconAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconShowFragment extends ILazyFragment implements OnItemClickListener {

    private RecyclerView rvIcon;
    private IconShowPresenter mPresenter;
    private List<IconBean> mIcons;

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
        rvIcon.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    }

    @Override
    protected void initData() {
        mPresenter = new IconShowPresenter(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (getArguments().getBoolean("ifShowAllIcons", false)) {
                mPresenter.getAllIcons();
            } else {
                mPresenter.getAdaptedIcons();
            }
        }
    }

    @Override
    protected boolean isDataComplete() {
        return mIcons != null;
    }

    public void onLoadData(List<IconBean> icons) {
        super.onLoadData();

        mIcons = icons;
        RequestManager glide = Glide.with(this);
        IconAdapter adapter = new IconAdapter(mIcons, glide);
        rvIcon.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        System.out.println(position);
        startActivity(new Intent(getActivity(), IconViewActivity.class));
    }
}