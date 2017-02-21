package me.iacn.mbestyle.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.presenter.IconPresenter;
import me.iacn.mbestyle.ui.adapter.IconTabAdapter;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconFragment extends BaseFragment {

    private TabLayout mTab;
    private ViewPager mViewPager;
    private IconPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_icon;
    }

    @Override
    protected void findView() {
        mTab = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        mPresenter = new IconPresenter(this);

        List<Fragment> fragments = new ArrayList();
        fragments.add(makeIconShowFragment(false));
        fragments.add(makeIconShowFragment(true));

        String[] titles = new String[]{"已适配", "全部"};

        mTab.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new IconTabAdapter(getFragmentManager(), fragments, titles));

        mPresenter.calcIconTotal();
    }

    public void setIconTotal(int total) {
        String all = String.format(Locale.getDefault(), "全部(%d)", total);
        TabLayout.Tab allTab = mTab.getTabAt(1);

        if (allTab != null && total != 0) {
            allTab.setText(all);
        }
    }

    private IconShowFragment makeIconShowFragment(boolean ifShowAllIcons) {
        IconShowFragment fragment = new IconShowFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("ifShowAllIcons", ifShowAllIcons);
        fragment.setArguments(bundle);

        return fragment;
    }
}