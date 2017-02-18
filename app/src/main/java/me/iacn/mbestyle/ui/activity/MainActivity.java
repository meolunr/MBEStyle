package me.iacn.mbestyle.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.fragment.AboutFragment;
import me.iacn.mbestyle.ui.fragment.ApplyFragment;
import me.iacn.mbestyle.ui.fragment.IconFragment;
import me.iacn.mbestyle.ui.fragment.LauncherFragment;
import me.iacn.mbestyle.util.StatusBarUtils;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private List mFragments;
    private int mCurrentIndex = -1;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        StatusBarUtils.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));

        BottomNavigationView bottomView = (BottomNavigationView) findViewById(R.id.bottom_bar);
        bottomView.setOnNavigationItemSelectedListener(this);

        mFragments = new ArrayList();
        mFragments.add(new IconFragment());
        mFragments.add(new LauncherFragment());
        mFragments.add(new ApplyFragment());
        mFragments.add(new AboutFragment());

        mFragmentManager = getFragmentManager();

        switchFragment(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_icon:
                switchFragment(0);
                return true;

            case R.id.menu_launcher:
                switchFragment(1);
                return true;

            case R.id.menu_apply:
                switchFragment(2);
                return true;

            case R.id.menu_about:
                switchFragment(3);
                return true;

            default:
                return false;
        }
    }

    private void switchFragment(int index) {
        Fragment fragment = (Fragment) mFragments.get(index);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        String indexString = String.valueOf(index);
        Fragment targetFragment = mFragmentManager.findFragmentByTag(indexString);

        if (mCurrentIndex != -1) {
            // 不是首次启动
            transaction.hide((Fragment) mFragments.get(mCurrentIndex));
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