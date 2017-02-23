package me.iacn.mbestyle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.iacn.mbestyle.R;

/**
 * Created by iAcn on 2017/2/23
 * Emali iAcn0301@foxmail.com
 */

public class IconViewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_view);

        findViewById(R.id.fl_container).setOnClickListener(this);
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