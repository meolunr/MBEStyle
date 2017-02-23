package me.iacn.mbestyle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/23
 * Emali iAcn0301@foxmail.com
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
            String iconName = intent.getStringExtra("icon_name");
            int resourceId = intent.getIntExtra("resource_id", 0);

            tvTitle.setText(iconName);
            Glide.with(this).load(resourceId).into(ivIcon);
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
}