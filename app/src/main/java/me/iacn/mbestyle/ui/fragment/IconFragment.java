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
 * Email i@iacn.me
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
        fragments.add(makeIconShowFragment(IconShowFragment.ICONS_LOAD_INSTALLED));
        fragments.add(makeIconShowFragment(IconShowFragment.ICONS_LOAD_WHATSNEW));
        fragments.add(makeIconShowFragment(IconShowFragment.ICONS_LOAD_ALL));

        String[] titles = new String[]{getString(R.string.installed), getString(R.string.what_is_new), getString(R.string.all)};

        mTab.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new IconTabAdapter(getFragmentManager(), fragments, titles));

        mPresenter.calcIconTotal();
    }

    public void setIconTotal(int total) {
        String format = getString(R.string.all) + "(%d)";
        String all = String.format(Locale.getDefault(), format, total);
        TabLayout.Tab allTab = mTab.getTabAt(2);

        if (allTab != null && total != 0) {
            allTab.setText(all);
        }
    }

    private IconShowFragment makeIconShowFragment(int category) {
        IconShowFragment fragment = new IconShowFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("showIconCategory", category);
        fragment.setArguments(bundle);

        return fragment;
    }
}