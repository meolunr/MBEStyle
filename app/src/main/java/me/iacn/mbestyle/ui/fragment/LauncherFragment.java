package me.iacn.mbestyle.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.adapter.LauncherAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class LauncherFragment extends BaseFragment implements OnItemClickListener {

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
        LauncherAdapter adapter = new LauncherAdapter();
        adapter.setOnItemClickListener(this);

        rvLauncher.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        System.out.println(position);
    }
}