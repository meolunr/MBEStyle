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
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.fragment.AboutFragment;
import me.iacn.mbestyle.ui.fragment.ApplyFragment;
import me.iacn.mbestyle.ui.fragment.IconFragment;
import me.iacn.mbestyle.ui.fragment.LauncherFragment;
import me.iacn.mbestyle.util.ScreenUtils;
import me.iacn.mbestyle.util.StatusBarUtils;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;

    private List<Fragment> mFragments;
    private int mCurrentIndex = -1;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));

        BottomNavigationView bottomView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        bottomView.setOnNavigationItemSelectedListener(this);

        mFragments = Arrays.asList(
                new IconFragment(),
                new LauncherFragment(),
                new ApplyFragment(),
                new AboutFragment());

        mFragmentManager = getFragmentManager();

        switchFragment(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_icon:
                handleToolbarElevation(0);
                switchFragment(0);
                return true;

            case R.id.menu_launcher:
                handleToolbarElevation(1);
                switchFragment(1);
                return true;

            case R.id.menu_apply:
                handleToolbarElevation(2);
                switchFragment(2);
                return true;

            case R.id.menu_about:
                handleToolbarElevation(3);
                switchFragment(3);
                return true;

            default:
                return false;
        }
    }

    private void handleToolbarElevation(int index) {
        if (index != 0) {
            ViewCompat.setElevation(mToolbar, ScreenUtils.dip2px(this, 2));
        } else {
            ViewCompat.setElevation(mToolbar, 0);
        }
    }

    private void switchFragment(int index) {
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