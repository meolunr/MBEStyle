package me.iacn.mbestyle.ui.fragment;

import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.adapter.IconTabAdapter;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class IconFragment extends BaseFragment {

    private TabLayout mTab;
    private ViewPager mViewPager;

    @Override
    protected int getInflateView() {
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
        List<Fragment> fragments = new ArrayList();
        fragments.add(new IconAdaptedFragment());
        fragments.add(new IconAllFragment());

        List<String> titles = new ArrayList<>();
        titles.add("已适配");
        titles.add("全部");

        mTab.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new IconTabAdapter(getFragmentManager(), fragments, titles));
    }
}