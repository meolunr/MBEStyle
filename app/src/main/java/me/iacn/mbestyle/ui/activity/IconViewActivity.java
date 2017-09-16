package me.iacn.mbestyle.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/23
 * Email i@iacn.me
 */

public class IconViewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_view);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        findViewById(R.id.fl_container).setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            String iconName = getIconName(intent);
            int resourceId = intent.getIntExtra("resource_id", 0);

            tvTitle.setText(iconName);
            ivIcon.setImageResource(resourceId);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_container:
                onBackPressed();
                break;
        }
    }

    private String getIconName(Intent intent) {
        String iconName = null;

        if (intent.hasExtra("package_name")) {
            // 显示已适配 App 的实际名称
            String packageName = intent.getStringExtra("package_name");

            try {
                PackageManager packageManager = getPackageManager();
                PackageInfo info = packageManager.getPackageInfo(packageName, 0);
                iconName = info.applicationInfo.loadLabel(packageManager).toString();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (iconName == null) {
            String name = intent.getStringExtra("icon_name");

            if (name.startsWith("_")) {
                // 处理 drawable 开头不能是数字的情况
                name = name.substring(1);
            }
            iconName = name;
        }
        return iconName;
    }
}