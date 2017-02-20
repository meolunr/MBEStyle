package me.iacn.mbestyle.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import me.iacn.mbestyle.BuildConfig;
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
        switch (position) {
            case 0:
                applyToNovaLauncher();
                break;

            case 1:
                applyToActionLauncher();
                break;
        }
    }

    private void applyToNovaLauncher() {
        Intent intent = new Intent("com.teslacoilsw.launcher.APPLY_ICON_THEME");
        intent.setPackage("com.teslacoilsw.launcher");
        intent.putExtra("com.teslacoilsw.launcher.extra.ICON_THEME_TYPE", "GO");
        intent.putExtra("com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE", BuildConfig.APPLICATION_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void applyToActionLauncher() {
        try {
            Intent intent = getActivity().getPackageManager()
                    .getLaunchIntentForPackage("com.actionlauncher.playstore");
            intent.putExtra("apply_icon_pack", BuildConfig.APPLICATION_ID);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.launcher_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void openActivity(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), R.string.launcher_not_found, Toast.LENGTH_SHORT).show();
        }
    }
}