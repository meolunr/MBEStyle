package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.adapter.IconAdapter;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconAllFragment extends BaseFragment {

    private RecyclerView rvIcon;

    @Override
    protected int getInflateView() {
        return R.layout.fragment_all_icon;
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
        rvIcon.setAdapter(new IconAdapter());
    }
}