package me.iacn.mbestyle.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import me.iacn.mbestyle.BuildConfig;
import me.iacn.mbestyle.R;
import me.iacn.mbestyle.ui.activity.LicenseActivity;
import me.iacn.mbestyle.ui.widget.AboutItem;
import moe.feng.alipay.zerosdk.AlipayZeroSdk;

/**
 * Created by iAcn on 2017/2/18
 * Emali iAcn0301@foxmail.com
 */

public class AboutFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivLogo;
    private AboutItem aiVersion;
    private AboutItem aiDesigner;
    private AboutItem aiDeveloper;
    private AboutItem aiDonate;
    private AboutItem aiOpenSource;

    @Override
    protected int getContentView() {
        return R.layout.fragment_about;
    }

    @Override
    protected void findView() {
        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        aiVersion = (AboutItem) findViewById(R.id.ai_version);
        aiDesigner = (AboutItem) findViewById(R.id.ai_designer);
        aiDeveloper = (AboutItem) findViewById(R.id.ai_developer);
        aiDonate = (AboutItem) findViewById(R.id.ai_donate);
        aiOpenSource = (AboutItem) findViewById(R.id.ai_open_source);
    }

    @Override
    protected void setListener() {
        aiVersion.setOnClickListener(this);
        aiDesigner.setOnClickListener(this);
        aiDeveloper.setOnClickListener(this);
        aiDonate.setOnClickListener(this);
        aiOpenSource.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        aiVersion.setSummary(BuildConfig.VERSION_NAME);
        Glide.with(this).load(R.drawable.bg_about_logo).into(ivLogo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ai_designer:
                openUrl("http://coolapk.com/u/433446");
                break;
            case R.id.ai_developer:
                openUrl("http://coolapk.com/u/532152");
                break;
            case R.id.ai_donate:
                openAliPay();
                break;
            case R.id.ai_open_source:
                startActivity(new Intent(getActivity(), LicenseActivity.class));
                break;
        }
    }

    private void openUrl(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openAliPay() {
        if (AlipayZeroSdk.hasInstalledAlipayClient(getActivity())) {
            AlipayZeroSdk.startAlipayClient(getActivity(), "aex08398iixbcgfl5ryqk3c");
        } else {
            ((ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE))
                    .setPrimaryClip(ClipData.newPlainText(null, "18588533502"));

            Toast.makeText(getActivity(),
                    "未安装支付宝客户端\n已将支付宝ID复制到剪贴板", Toast.LENGTH_SHORT).show();
        }
    }
}