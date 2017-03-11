package me.iacn.mbestyle.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.fragment.AboutFragment;
import me.iacn.mbestyle.ui.fragment.IconFragment;
import me.iacn.mbestyle.ui.fragment.LauncherFragment;
import me.iacn.mbestyle.ui.fragment.RequestFragment;
import me.iacn.mbestyle.util.ScreenUtils;
import me.iacn.mbestyle.util.StatusBarUtils;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;

    private int mCurrentIndex = -1;
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));

        BottomNavigationView bottomView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomView.setOnNavigationItemSelectedListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFragments = Arrays.asList(
                new IconFragment(),
                new LauncherFragment(),
                new RequestFragment(),
                new AboutFragment());

        mFragmentManager = getFragmentManager();

        switchFragment(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // 默认隐藏设置菜单
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int order = item.getOrder();

        if (order >= 0 && order <= 3) {
            handleToolbar(order);
            switchFragment(order);
            return true;
        }

        return false;
    }

    private void handleToolbar(int index) {
        // 只在图标 Fragment 隐藏阴影
        ViewCompat.setElevation(mToolbar, index == 0 ? 0 : ScreenUtils.dip2px(this, 2));

        // 只在关于 Fragment 显示设置
        MenuItem settingItem = mToolbar.getMenu().getItem(0);
        settingItem.setVisible(index == 3);
    }

    private void switchFragment(int index) {
        if (index == mCurrentIndex) return;

        Fragment fragment = mFragments.get(index);
        FragmentTransaction transaction =
                mFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.animator.fragment_in, R.animator.fragment_out);

        String indexString = String.valueOf(index);
        Fragment targetFragment = mFragmentManager.findFragmentByTag(indexString);

        if (mCurrentIndex != -1) {
            // 不是首次启动
            transaction.hide(mFragments.get(mCurrentIndex));
        }

        if (targetFragment == null) {
            // 之前没有添加过
            transaction.add(R.id.fl_content, fragment, indexString);
        } else {
            transaction.show(targetFragment);
        }

        transaction.commit();
        mCurrentIndex = index;
    }
}