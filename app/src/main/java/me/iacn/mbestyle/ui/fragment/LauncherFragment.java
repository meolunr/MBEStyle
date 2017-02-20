package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class LauncherFragment extends BaseFragment {

    private RecyclerView rvLauncher;

    @Override
    protected int getInflateView() {
        return R.layout.fragment_launcher;
    }

    @Override
    protected void findView() {
        rvLauncher = (RecyclerView) findViewById(R.id.rv_launcher);
    }

    @Override
    protected void setListener() {
        rvLauncher.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    protected void initData() {

    }
}