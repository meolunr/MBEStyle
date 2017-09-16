package me.iacn.mbestyle.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.activity.WallpaperActivity;
import me.iacn.mbestyle.ui.adapter.ApplyAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.GlideUtils;

/**
 * Created by iAcn on 2017/2/18
 * Email i@iacn.me
 */

public class ApplyFragment extends BaseFragment implements OnItemClickListener {

    private RecyclerView rvApply;

    @Override
    protected int getContentView() {
        return R.layout.fragment_apply;
    }

    @Override
    protected void findView() {
        rvApply = (RecyclerView) findViewById(R.id.rv_wallpaper);
    }

    @Override
    protected void setListener() {
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? manager.getSpanCount() : 1;
            }
        });

        rvApply.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        int[] launcherIcons = new int[]{
                R.drawable.nova_launcher,
                R.drawable.action_launcher,
                R.drawable.adw_launcher,
                R.drawable.apex_launcher,
                R.drawable.atom_launcher,
                R.drawable.aviate_launcher,
                R.drawable.go_launcher,
                R.drawable.kk_launcher,
                R.drawable.lg_launcher,
                R.drawable.next_launcher,
                R.drawable.smart_launcher,
                R.drawable.solo_launcher,
        };

        String[] launcherNames = getResources().getStringArray(R.array.launchers);

        ApplyAdapter adapter = new ApplyAdapter(launcherIcons, launcherNames, GlideUtils.with(this));
        adapter.setOnItemClickListener(this);

        rvApply.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), WallpaperActivity.class));
                break;
            case 1:
                NovaLauncher();
                break;
            case 2:
                ActionLauncher();
                break;
            case 3:
                AdwLauncher();
                break;
            case 4:
                ApexLauncher();
                break;
            case 5:
                AtomLauncher();
                break;
            case 6:
                AviateLauncher();
                break;
            case 7:
                GoLauncher();
                break;
            case 8:
                KKLauncher();
                break;
            case 9:
                LgHomeLauncher();
                break;
            case 10:
                NextLauncher();
                break;
            case 11:
                SmartLauncher();
                break;
            case 12:
                SoloLauncher();
                break;
        }
    }

    private void NovaLauncher() {
        Intent intent = new Intent("com.teslacoilsw.launcher.APPLY_ICON_THEME");
        intent.setPackage("com.teslacoilsw.launcher");
        intent.putExtra("com.teslacoilsw.launcher.extra.ICON_THEME_TYPE", "GO");
        intent.putExtra("com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE", BuildConfig.APPLICATION_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openActivity(intent);
    }

    private void ActionLauncher() {
        try {
            Intent intent = getActivity().getPackageManager()
                    .getLaunchIntentForPackage("com.actionlauncher.playstore");
            intent.putExtra("apply_icon_pack", BuildConfig.APPLICATION_ID);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.launcher_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void AdwLauncher() {
        Intent intent = new Intent("org.adw.launcher.SET_THEME");
        intent.putExtra("org.adw.launcher.theme.NAME", BuildConfig.APPLICATION_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openActivity(intent);
    }

    private void ApexLauncher() {
        Intent intent = new Intent("com.anddoes.launcher.SET_THEME");
        intent.putExtra("com.anddoes.launcher.THEME_PACKAGE_NAME", BuildConfig.APPLICATION_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openActivity(intent);
    }

    private void AtomLauncher() {
        Intent atom = new Intent("com.dlto.atom.launcher.intent.action.ACTION_VIEW_THEME_SETTINGS");
        atom.setPackage("com.dlto.atom.launcher");
        atom.putExtra("packageName", BuildConfig.APPLICATION_ID);
        atom.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openActivity(atom);
    }

    private void AviateLauncher() {
        Intent aviate = new Intent("com.tul.aviate.SET_THEME");
        aviate.setPackage("com.tul.aviate");
        aviate.putExtra("THEME_PACKAGE", BuildConfig.APPLICATION_ID);
        aviate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openActivity(aviate);
    }

    private void GoLauncher() {
        try {
            Intent intent = getActivity().getPackageManager()
                    .getLaunchIntentForPackage("com.gau.go.launcherex");

            Intent go = new Intent("com.gau.go.launcherex.MyThemes.mythemeaction");
            go.putExtra("type", 1);
            go.putExtra("pkgname", BuildConfig.APPLICATION_ID);

            getActivity().sendBroadcast(go);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.launcher_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void KKLauncher() {
        Intent intent = new Intent("com.kk.launcher.APPLY_ICON_THEME");
        intent.putExtra("com.kk.launcher.theme.EXTRA_PKG", BuildConfig.APPLICATION_ID);
        intent.putExtra("com.kk.launcher.theme.EXTRA_NAME", getString(R.string.app_name));
        openActivity(intent);
    }

    private void LgHomeLauncher() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName("com.lge.launcher2",
                "com.lge.launcher2.homesettings.HomeSettingsPrefActivity"));
        openActivity(intent);
    }

    private void NextLauncher() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            Intent intent = manager.getLaunchIntentForPackage("com.gtp.nextlauncher");

            if (intent == null) {
                intent = manager.getLaunchIntentForPackage("com.gtp.nextlauncher.trial");
            }

            Intent next = new Intent("com.gau.go.launcherex.MyThemes.mythemeaction");
            next.putExtra("type", 1);
            next.putExtra("pkgname", BuildConfig.APPLICATION_ID);
            getActivity().sendBroadcast(next);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getActivity(), R.string.launcher_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void SmartLauncher() {
        Intent intent = new Intent("ginlemon.smartlauncher.setGSLTHEME");
        intent.putExtra("package", BuildConfig.APPLICATION_ID);
        openActivity(intent);
    }

    private void SoloLauncher() {
        try {
            Intent intent = getActivity().getPackageManager()
                    .getLaunchIntentForPackage("home.solo.launcher.free");
            Intent solo = new Intent("home.solo.launcher.free.APPLY_THEME");
            solo.putExtra("EXTRA_PACKAGENAME", BuildConfig.APPLICATION_ID);
            solo.putExtra("EXTRA_THEMENAME", getString(R.string.app_name));
            getActivity().sendBroadcast(solo);
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