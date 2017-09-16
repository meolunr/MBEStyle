package me.iacn.mbestyle.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by iAcn on 2017/2/18
 * Email i@iacn.me
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), getContentView(), null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        setListener();
        initData();
    }

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void findView();

    protected abstract void setListener();

    protected abstract void initData();

    protected final View findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }
}