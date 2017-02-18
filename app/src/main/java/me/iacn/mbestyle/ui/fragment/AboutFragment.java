package me.iacn.mbestyle.ui.fragment;

import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.widget.AboutItem;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class AboutFragment extends BaseFragment {

    private AboutItem aiVersion;

    @Override
    protected int getInflateView() {
        return R.layout.fragment_about;
    }

    @Override
    protected void findView() {
        aiVersion = (AboutItem) findViewById(R.id.ai_version);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        aiVersion.setSummary(BuildConfig.VERSION_NAME);
    }
}