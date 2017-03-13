package me.iacn.mbestyle.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import me.iacn.mbestyle.R;
import me.iacn.mbestyle.bean.AppBean;
import me.iacn.mbestyle.presenter.AppPresenter;
import me.iacn.mbestyle.ui.adapter.AppAdapter;
import me.iacn.mbestyle.ui.callback.OnItemClickListener;
import me.iacn.mbestyle.util.StringUtils;

/**
 * Created by iAcn on 2017/3/13
 * Emali iAcn0301@foxmail.com
 */

public class AppListActivity extends AppCompatActivity implements OnItemClickListener {

    private FrameLayout flInitial;
    private View mContentView;

    private RecyclerView rvApp;

    private AppPresenter mPresenter;
    private List<AppBean> mApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flInitial = (FrameLayout) View.inflate(this, R.layout.inflate_loading, null);
        mContentView = View.inflate(this, R.layout.activity_applist, null);

        setContentView(flInitial);

        rvApp = (RecyclerView) mContentView.findViewById(R.id.rv_app);
        rvApp.setLayoutManager(new LinearLayoutManager(this));

        mPresenter = new AppPresenter(this);
        mPresenter.loadInstallApp();
    }

    public void onLoadData(List<AppBean> list) {
        if (flInitial != null) {
            flInitial.removeAllViews();
            flInitial.addView(mContentView);
        }

        mApps = list;
        AppAdapter adapter = new AppAdapter(mApps);
        adapter.setOnItemClickListener(this);
        rvApp.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        AppBean bean = mApps.get(position);
        String template = getString(R.string.component_template);
        String[] split = bean.activity.split("/");

        if (split[1].startsWith(".")) {
            // 正常应用
            template = template
                    .replace("$packageName$", split[0])
                    .replace("$activityName$", split[0] + split[1]);
        } else {
            // 蛋疼应用
            template = template
                    .replace("$packageName$", split[0])
                    .replace("$activityName$", split[1]);
        }

        StringUtils.copyToClipboard(this, template);
        Toast.makeText(this, "应用信息已复制到剪贴板", Toast.LENGTH_SHORT).show();

    }
}